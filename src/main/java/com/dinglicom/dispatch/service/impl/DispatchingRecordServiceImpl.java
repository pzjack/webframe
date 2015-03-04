/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.dispatch.service.impl;

import com.dinglicom.dispatch.domain.DispathingTaskItem;
import com.dinglicom.dispatch.domain.DispathingTaskReq;
import com.dinglicom.dispatch.domain.DispathingTaskResp;
import com.dinglicom.dispatch.domain.TaskDetailItem;
import com.dinglicom.dispatch.domain.TaskDetailReq;
import com.dinglicom.dispatch.domain.TaskDetailResp;
import com.dinglicom.dispatch.domain.TaskStatisticsItem;
import com.dinglicom.dispatch.domain.TaskStatisticsResp;
import com.dinglicom.dispatch.entity.DispatchingRecord;
import com.dinglicom.dispatch.repository.DispatchingRecordDao;
import com.dinglicom.dispatch.service.DispatchingRecordService;
import static com.dinglicom.dispatch.service.DispatchingRecordService.DISPATCHING_NOT_COMPELTE;
import com.dinglicom.frame.sys.entity.SysOranizagion;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.frame.sys.service.SysOranizagionService;
import com.dinglicom.frame.sys.service.UserInfoService;
import com.dinglicom.frame.sys.util.DateUtil;
import com.dinglicom.order.entity.UserOrder;
import com.dinglicom.order.service.UserOrderService;
import static com.dinglicom.order.service.UserOrderService.DISTRIBUTION_TYPE_END;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 配送任务及操作
 *
 * @author panzhen
 */
@Component
@Transactional
public class DispatchingRecordServiceImpl implements DispatchingRecordService {

    private final static Logger LOG = LoggerFactory.getLogger(DispatchingRecordServiceImpl.class);

    @Resource
    private DispatchingRecordDao dispatchingRecordDao;
    @Resource
    private SysOranizagionService sysOranizagionService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private UserOrderService userOrderService;

    @Override
    public DispatchingRecord save(DispatchingRecord dispatchingRecord) {
        return dispatchingRecordDao.save(dispatchingRecord);
    }

    public Iterable<DispatchingRecord> save(Iterable<DispatchingRecord> dispatchingRecords) {
        return dispatchingRecordDao.save(dispatchingRecords);
    }

    @Override
    @Transactional(readOnly = true)
    public DispatchingRecord read(long id) {
        return dispatchingRecordDao.findOne(id);
    }

    /**
     * 送奶工当天已完成和未完成任务查询
     *
     * @param req
     * @param user
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public DispathingTaskResp findCurDayTask(DispathingTaskReq req, UserInfo user) {
        DispathingTaskResp r = new DispathingTaskResp();
        Calendar c = Calendar.getInstance();
        Page<DispathingTaskItem> data;
        if (DISPATCHING_NOT_COMPELTE.equalsIgnoreCase(req.getTask_status())) {
            data = dispatchingRecordDao.findDispathchingTaskNotComplete(buildPageRequest(req.getPage(), req.getNum()), DateUtil.getYear(c), DateUtil.getMonth(c), DateUtil.getDay(c), user, false);
        } else {
            data = dispatchingRecordDao.findDispathchingTaskComplete(buildPageRequest(req.getPage(), req.getNum()), DateUtil.getYear(c), DateUtil.getMonth(c), DateUtil.getDay(c), user, false);
        }
        if (null != data) {
            r.setData(data.getContent());
            r.setTotal_page(data.getTotalPages());
        }
        return r;
    }

    /**
     * 送奶工当天任务详情
     *
     * @param req
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public TaskDetailResp findByOrderNoDetails(TaskDetailReq req) {
        TaskDetailResp r = new TaskDetailResp();
        Calendar c = Calendar.getInstance();
        List<DispatchingRecord> data;
        data = dispatchingRecordDao.findDispathchingTaskDetails(req.getOrder_no(), c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH), false);
        if (null != data) {
            List<TaskDetailItem> list = new ArrayList<TaskDetailItem>();
            for (DispatchingRecord d : data) {
                if (null == r.getName()) {
                    r.setName(d.getConsigneeName());
                    r.setMobile_phone(d.getPhone());
                }
                TaskDetailItem t = new TaskDetailItem(d.getProduct().getId(), d.getProductName(), d.getDispatchingNum());
                list.add(t);
            }
            r.setData(list);
        }
        return r;
    }

    /**
     * 送奶工统计信息
     *
     * @param user
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public TaskStatisticsResp findStatisticsNum(UserInfo user) {
        TaskStatisticsResp r = new TaskStatisticsResp();
        if (null == user.getOrg()) {
            r.setCode(1);
            r.setResult("无对应的奶站信息");
            return r;
        }
        Calendar c = Calendar.getInstance();
        Page<TaskStatisticsItem> data;
        List<TaskStatisticsResp> rp = dispatchingRecordDao.findWorkerStatistics(user.getOrg().getId(), user.getId(), c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH), Boolean.FALSE);
        if (null != rp && rp.size() > 0) {
            r = rp.get(0);
            if (r.getTotal_left_num() > 0 || r.getTotal_send_num() > 0) {
                List<TaskStatisticsItem> items = dispatchingRecordDao.findWorkerProductStatistics(user.getOrg().getId(), user.getId(), c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH), Boolean.FALSE);
                if (null != items) {
                    r.setData(items);
                }
            }
        }
        return r;
    }

    @Override
    public void completeDispachingOrder(UserInfo worker, String orderNo) throws IOException {
        List<DispatchingRecord> tasks = dispatchingRecordDao.findByWorkerIdAndOrderNo(orderNo, worker.getId(), Boolean.FALSE);
        if (null == tasks || tasks.size() <= 0) {
            throw new IOException("该用户没有需要完成的订单");
        }
        Date confirmDate = new Date();
        for (DispatchingRecord task : tasks) {
            if (task.getConfirmNum() > 0) {
                continue;
            }
            task.setConfirmDate(confirmDate);
            task.setConfirmName(worker.getRealname());
            task.setConfirmNum(task.getDispatchingNum());
            UserOrder order = task.getOrder();
            if (null != order) {
                order.setCompletenum(order.getCompletenum() + task.getConfirmNum());
                switch (order.getDistributionType()) {
                    case UserOrderService.DISTRIBUTION_TYPE_EVERY:
                        order.setEndDistributionDate(DateUtil.getAfterDay(confirmDate, (order.getProductnum() - order.getTotaldistributionnum()) / order.getDistributionNum()));
                        break;
                    case DISTRIBUTION_TYPE_END://周末
                        order.setFirstDistributionDate(DateUtil.getNextMonthFirstWorkEndDay(order.getEndDistributionDate()));
                        order.setEndDistributionDate(DateUtil.getAfterWorkDay(order.getEndDistributionDate(), order.getDistributionPeriod() * order.getProductnum()));
                        break;
                    case UserOrderService.DISTRIBUTION_TYPE_WORK:
                        Date firstworkday = DateUtil.getLastWorkday(confirmDate);
                        order.setEndDistributionDate(DateUtil.getAfterWorkDay(firstworkday, (order.getProductnum() - order.getTotaldistributionnum()) / order.getDistributionNum()));
                        break;
                }
            }
        }
    }

    @Override
//    @Scheduled(cron = "2/20 * * * * ?")
    public void createDispatchingTaskByWorderOrg() {
        Calendar c = Calendar.getInstance();
        LOG.info("start schedule..." + c.toString());
        //查找所有奶站
        List<SysOranizagion> nzhs = sysOranizagionService.findAllWorkerOrg();

        //找出奶站的一个送奶工人
        for (SysOranizagion nz : nzhs) {
            LOG.info("Schedule naizhan:" + nz.getName());
            long rc = dispatchingRecordDao.findByStationReportCount(DateUtil.getYear(c), DateUtil.getMonth(c), DateUtil.getDay(c), nz.getId(), Boolean.FALSE);
            if (rc > 0) {//首先判断配送任务是否已经生成
                continue;
            }
//            UserInfo worker = userInfoService.findNaizhaiWorkers(nz);
//            if (null == worker) {
//                LOG.warn("Create dispatching task: The Naizhan_id(" + nz.getId() + ") name(" + nz.getName() + ") has not worker.");
//                continue;
//            }
            createDispatchingTask(nz, c);
        }
    }

    /**
     * 逐个奶站创建配送任务，改变订单及订单条目的状态
     *
     * @param nz
     * @param c
     */
    public void createDispatchingTask(SysOranizagion nz, Calendar c) {
//        List<UserOrder> items = userOrderService.findAllDispatchingItem(DateUtil.getCronDayMaxDate(c));
        List<DispatchingRecord> dispatchings = new ArrayList<DispatchingRecord>();
        List<UserOrder> orders = userOrderService.findAllDispatchingItem(nz, DateUtil.getCronDayMaxDate(c));
        List<UserOrder> orderchgs = new ArrayList<UserOrder>();
        for (UserOrder order : orders) {
            if (order.getTotaldistributionnum() >= order.getProductnum()) {
                continue;
            }
            DispatchingRecord d = createDispatchingByItem(order, c);
            dispatchings.add(d);
            order.setTotaldistributionnum(order.getTotaldistributionnum() + order.getDistributionNum());
//            if (null != order.getOrderState() && !UserOrderService.ORDER_STATE_DISPATCHING.equals(order.getOrderState())) {
//                order.setOrderState(UserOrderService.ORDER_STATE_DISPATCHING);
//            }
            orderchgs.add(order);
        }
        if (dispatchings.size() > 0) {
            save(dispatchings);
            if (orderchgs.size() > 0) {
                userOrderService.save(orderchgs);
            }
        }
    }

    private DispatchingRecord createDispatchingByItem(UserOrder order, Calendar c) {
        DispatchingRecord d = new DispatchingRecord();
        d.setYear(DateUtil.getYear(c));
        int m = DateUtil.getMonth(c);
        d.setMonth(m);
        d.setQuarter(DateUtil.getQuarter(c));
        d.setDay(DateUtil.getDay(c));
        d.setOrderNo(order.getOrderNo());
        d.setOrder(order);
        d.setProduct(order.getProduct());
        d.setProductName(order.getProductname());
//        d.setOrderItem(item);
        d.setDispatchingNum(order.getDistributionNum());
        d.setProductPrice(order.getProductPrice());
        d.setDispatchingTarget(order.getDistributionTarget());
        d.setDispatchingType(order.getDistributionType());

        d.setConsignee(order.getUser());
        d.setConsigneeName(order.getConsigneename());
        d.setPhone(order.getConsigneephone());
        d.setAddress(order.getConsigneeaddress());

        d.setDispatchingOrg(order.getOrg());
        d.setDispatchingOrgName(order.getOrgmame());
        if (null != order.getMilkman()) {
            d.setDispatchingWorker(order.getMilkman());
            d.setDispatchingWorkerName(order.getMilkman().getRealname());
            d.setDispatchingWorkerPhone(order.getMilkman().getPhone());
        }

        return d;
    }

    /**
     * 创建分页请求.
     */
    private PageRequest buildPageRequest(final int page, final int size) {
        return new PageRequest(page - 1, size);
    }
}
