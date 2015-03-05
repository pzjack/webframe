/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.reportnum.service.impl;

import com.dinglicom.frame.sys.domain.BaseMsgBean;
import com.dinglicom.frame.sys.entity.SysOranizagion;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.frame.sys.service.SysOranizagionService;
import com.dinglicom.frame.sys.service.UserInfoService;
import com.dinglicom.frame.sys.util.DateUtil;
import com.dinglicom.order.service.OrderChangeRecordService;
import com.dinglicom.order.service.UserOrderService;
import com.dinglicom.product.domain.ProductItem;
import com.dinglicom.product.entity.UserProduct;
import com.dinglicom.product.service.UserProductService;
import com.dinglicom.reportnum.demain.AutoReportLastNum;
import com.dinglicom.reportnum.demain.LineDataTmp;
import com.dinglicom.reportnum.demain.ReportNumberItemResp;
import com.dinglicom.reportnum.demain.ReportNumberPostReq;
import com.dinglicom.reportnum.demain.ReportNumberResp;
import com.dinglicom.reportnum.demain.WebReportnumberDetailItem;
import com.dinglicom.reportnum.demain.WebReportnumberDetailResp;
import com.dinglicom.reportnum.entity.EveryDayEveryOrgReport;
import com.dinglicom.reportnum.entity.ReportSubscribeNumber;
import com.dinglicom.reportnum.entity.ReportnumberTime;
import com.dinglicom.reportnum.repository.ReportSubscribeNumberDao;
import com.dinglicom.reportnum.service.EveryDayEveryOrgReportService;
import com.dinglicom.reportnum.service.ReportSubscribeNumberService;
import com.dinglicom.reportnum.service.ReportnumberTimeService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author panzhen
 */
@Component
@Transactional
public class ReportSubscribeNumberServiceImpl implements ReportSubscribeNumberService {

    private final static Logger LOG = LoggerFactory.getLogger(ReportSubscribeNumberServiceImpl.class);
    @Resource
    private ReportSubscribeNumberDao reportSubscribeNumberDao;
    @Resource
    private UserOrderService userOrderService;
    @Resource
    private UserProductService userProductService;
    @Resource
    private SysOranizagionService sysOranizagionService;
    @Resource
    private EveryDayEveryOrgReportService everyDayEveryOrgReportService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private ReportnumberTimeService reportnumberTimeService;
    @Resource
    private OrderChangeRecordService orderChangeRecordService;

    @Override
    public ReportSubscribeNumber save(ReportSubscribeNumber reportSubscribeNumber) {
        return reportSubscribeNumberDao.save(reportSubscribeNumber);
    }

    @Override
    public Iterable<ReportSubscribeNumber> save(List<ReportSubscribeNumber> reportSubscribeNumbers) {
        return reportSubscribeNumberDao.save(reportSubscribeNumbers);
    }

    @Override
    public List<ReportSubscribeNumber> findByEveryid(long everyId) {
        return reportSubscribeNumberDao.findByeveryDayEveryOrgReportId(everyId);
    }

    /**
     * 自动报量，报明天的数据
     */
    public void autoReportNumber() {
        Calendar t = Calendar.getInstance();
        Calendar n = Calendar.getInstance();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        c = DateUtil.getOneDayMintime(c);
        n.add(Calendar.DAY_OF_MONTH, -1);

        int year = DateUtil.getYear(t);
        int month = DateUtil.getMonth(t);
        int day = DateUtil.getDay(t);

        int nyear = DateUtil.getYear(n);
        int nmonth = DateUtil.getMonth(n);
        int nday = DateUtil.getDay(n);
        //下一天订单如果有暂停期满的，先恢复暂停订单
        Calendar c2 = DateUtil.getCronDayMaxCalendar(c);
        orderChangeRecordService.doEndPauseOrder(c2.getTime());

        //查找所有奶站
        List<SysOranizagion> nzhs = sysOranizagionService.findAllWorkerOrg();
        //需补充第三方配送
        List<SysOranizagion> dlvs = sysOranizagionService.findAllDeliveryOrg();
        if (null != dlvs && dlvs.size() > 0) {
            nzhs.addAll(dlvs);
        }
        List<UserProduct> pals = userProductService.getAll();
        for (SysOranizagion nz : nzhs) {
            EveryDayEveryOrgReport everyRpt = everyDayEveryOrgReportService.findByOrgidAndDate(nz.getId(), year, month, day);
            if (null != everyRpt && !EveryDayEveryOrgReportService.REPORT_STATE_UNREPORT.equals(everyRpt.getReportstate())) {
                continue;
            }

            if (null == everyRpt) {
                everyRpt = everyDayEveryOrgReportService.createEveryReportnumber(nz, year, month, day);
            }
            long totalNum = 0;
            UserInfo nzmanager = userInfoService.findNaizhaiManager(nz);
            Map<Long, UserProduct> ps = getAll(pals);
            List<ReportSubscribeNumber> list = new ArrayList<ReportSubscribeNumber>();
            List<AutoReportLastNum> nlist = reportSubscribeNumberDao.findByAutoLastReportNumYearmonthday(nz.getId(), nyear, nmonth, nday);
            if (null == nlist || nlist.size() <= 0) {//前一天没有报量
                boolean isworkday = DateUtil.isWorkDay(c);
                Date oneday = c.getTime();
                List<ReportNumberItemResp> respItems;
                if (isworkday) {
                    respItems = userOrderService.findWorkdayReportNumberByWorkstation(nz, oneday);
                } else {
                    respItems = userOrderService.findWorkendReportNumberByWorkstation(nz, oneday);
                }

                for (ReportNumberItemResp rsp : respItems) {
                    ReportSubscribeNumber r = createReportSubscribeNumber(rsp.getProduct_id(), rsp.getAmount(), year, month, day, nz, nzmanager, ps);
                    r.setEveryDayEveryOrgReport(everyRpt);
                    totalNum += r.getReportnum();
                    list.add(r);
                }
            } else {//使用上一天的报量数据
                for (AutoReportLastNum an : nlist) {
                    ReportSubscribeNumber r = createReportnumber(an, year, month, day, nz, nzmanager, ps);
                    list.add(r);
                    totalNum += r.getReportnum();
                    r.setEveryDayEveryOrgReport(everyRpt);
                }
            }
            everyRpt.setTotalnum(totalNum);
            everyRpt.setReportstate(EveryDayEveryOrgReportService.REPORT_STATE_REPORTTED);
            everyRpt.setReporttime(t.getTime());
            everyDayEveryOrgReportService.save(everyRpt);
            reportSubscribeNumberDao.save(list);
        }
    }

    private Map<Long, UserProduct> getAll(List<UserProduct> pals) {
        Map<Long, UserProduct> map = new HashMap<Long, UserProduct>();
        Iterator<UserProduct> it = pals.iterator();
        while (it.hasNext()) {
            UserProduct p = it.next();
            map.put(p.getId(), p);
        }
        return map;
    }

    private ReportSubscribeNumber createReportSubscribeNumber(Long pid, long amount, int year, int month, int day, SysOranizagion nz, UserInfo nzmanager, Map<Long, UserProduct> ps) {
        ReportSubscribeNumber r = new ReportSubscribeNumber();
        UserProduct product = ps.get(pid);
        if (null != product) {
            r.setProduct(product);
            r.setProductname(product.getName());
        }
        r.setDistrutenum(amount);
        r.setReportnum(r.getDistrutenum() - r.getMinusnum() + r.getPlusnum());
        r.setYear(year);
        r.setQuarter(DateUtil.getQuarterByMonth(month));
        r.setMonth(month);
        r.setDay(day);
        r.setOrg(nz);
        if (null != nz.getDealer()) {
            r.setDealer(nz.getDealer());
            r.setDealer_name(nz.getDealer().getName());
        }
        r.setOrgname(nz.getName());
        if (null != nzmanager) {
            r.setReportman(nzmanager);
            r.setReportname(nzmanager.getRealname());
        }
        r.setReportstate(EveryDayEveryOrgReportService.REPORT_STATE_REPORTTED);
        return r;
    }

    private ReportSubscribeNumber createReportnumber(AutoReportLastNum an, int year, int month, int day, SysOranizagion nz, UserInfo nzmanager, Map<Long, UserProduct> ps) {
        return createReportSubscribeNumber(an.getProductid(), an.getReportnum(), year, month, day, nz, nzmanager, ps);
    }

    @Override
    @Transactional(readOnly = true)
    public BaseMsgBean caculateOnedayReportNumber(UserInfo nzmanager, Calendar c) {
        SysOranizagion nz = nzmanager.getOrg();
        if (null == nz) {
            throw new RuntimeException("该用户没有权限");
        }
        int year = DateUtil.getYear(c);
        int month = DateUtil.getMonth(c);
        int day = DateUtil.getDay(c);
        boolean isworkday = DateUtil.isWorkDay(c);
        Date oneday = c.getTime();
        EveryDayEveryOrgReport erp = everyDayEveryOrgReportService.findByOrgidAndDate(nz.getId(), year, month, day);
        if (null == erp) {
            erp = everyDayEveryOrgReportService.createEveryReportnumber(nz, year, month, day);
            everyDayEveryOrgReportService.save(erp);
        }
        List<ReportNumberItemResp> respItems;
        ReportNumberResp rresp = new ReportNumberResp();
        //报量时间和报量状态需补充
        if (EveryDayEveryOrgReportService.REPORT_STATE_UNREPORT.equals(erp.getReportstate())) {
            if (isworkday) {
                respItems = userOrderService.findWorkdayReportNumberByWorkstation(nz, oneday);
            } else {
                respItems = userOrderService.findWorkendReportNumberByWorkstation(nz, oneday);
            }
            rresp.setStatus(erp.getReportstate());
        } else {
            respItems = reportSubscribeNumberDao.findByReportNumYearmonthday(nz.getId(), year, month, day);
        }

        Map<Long, ProductItem> pmap = getProductAll();
        ReportnumberTime rt = reportnumberTimeService.get();
        rresp.setData(respItems);
        if (null != rt) {
            rresp.setExpired_time(rt.getTime());
        }
        rresp.setStatus(erp.getReportstate());
        long tt = 0;
        if (null != respItems) {
            for (ReportNumberItemResp item : respItems) {
                tt += item.getAmount();
                pmap.remove(item.getProduct_id());
            }
            addReportProduct(respItems, pmap);
            rresp.setTotal_amount(tt);
        } else {
            respItems = new ArrayList<ReportNumberItemResp>();
            addReportProduct(respItems, pmap);
        }
        return rresp;
    }

    private Map<Long, ProductItem> getProductAll() {
        List<ProductItem> plist = userProductService.findAllProduct();
        Map<Long, ProductItem> pmap = new HashMap<Long, ProductItem>();
        for (ProductItem p : plist) {
            pmap.put(p.getPid(), p);
        }
        return pmap;
    }

    private void addReportProduct(List<ReportNumberItemResp> respItems, Map<Long, ProductItem> pmap) {
        Iterator<ProductItem> it = pmap.values().iterator();
        while (it.hasNext()) {
            ProductItem pim = it.next();
            ReportNumberItemResp rir = new ReportNumberItemResp(pim.getPid(), pim.getPname(), 0);
            respItems.add(rir);
        }
    }

    @Override
    public void saveOnedayReportNumber(ReportNumberPostReq req, UserInfo nzmanager, Calendar c) {
        Calendar ct = Calendar.getInstance();
        SysOranizagion nz = nzmanager.getOrg();
        if (null == nz) {
            throw new RuntimeException("该用户没有权限");
        }
        int year = DateUtil.getYear(c);
        int month = DateUtil.getMonth(c);
        int day = DateUtil.getDay(c);

        ReportnumberTime rt = reportnumberTimeService.get();
        if (null != rt && null != rt.getTime()) {
            ct = DateUtil.getReportTime(rt.getTime(), ct);
            if (c.after(ct)) {
                throw new RuntimeException("报量时间已过，如果没报量，将会自动报量！");
            }
        }
        int data = reportSubscribeNumberDao.findByYearAndMonthAndDay(nz.getId(), year, month, day);
        if (data > 0) {
            throw new RuntimeException("今日报量已经报告");
        }
        EveryDayEveryOrgReport everyRpt = everyDayEveryOrgReportService.findByOrgidAndDate(nz.getId(), year, month, day);
        if (null == everyRpt) {
            everyRpt = everyDayEveryOrgReportService.createEveryReportnumber(nz, year, month, day);
            String reportNo = createReportNo(year, month, day, nz.getId());
            everyRpt.setReportno(reportNo);
        }
        everyRpt.setReporttime(c.getTime());
        everyRpt.setReportstate(EveryDayEveryOrgReportService.REPORT_STATE_REPORTTED);
        long totalNum = 0;
        List<ReportNumberItemResp> respItems = req.getData();
        List<ReportSubscribeNumber> list = new ArrayList<ReportSubscribeNumber>();
        for (ReportNumberItemResp rsp : respItems) {
            ReportSubscribeNumber r = new ReportSubscribeNumber();
            r.setDistrutenum(rsp.getAmount());
            r.setMinusnum(rsp.getMinus());
            r.setPlusnum(rsp.getPlus());
            r.setReportnum(r.getDistrutenum() - r.getMinusnum() + r.getPlusnum());
//            if(r.getReportnum() <= 0) {
//                continue;
//            }
            UserProduct product = userProductService.read(rsp.getProduct_id());
            r.setProduct(product);
            r.setProductname(product.getName());
            totalNum += r.getReportnum();
            r.setYear(year);
            r.setQuarter(DateUtil.getQuarter(c));
            r.setMonth(month);
            r.setDay(day);
            r.setOrg(nz);
            r.setOrgname(nz.getName());
            if (null != nz.getDealer()) {
                r.setDealer(nz.getDealer());
                r.setDealer_name(nz.getDealer().getName());
            }
            r.setReportman(nzmanager);
            r.setReportname(nzmanager.getRealname());
            r.setReportstate(EveryDayEveryOrgReportService.REPORT_STATE_REPORTTED);
            r.setReportno(everyRpt.getReportno());

            r.setEveryDayEveryOrgReport(everyRpt);
            list.add(r);
        }
        everyRpt.setTotalnum(totalNum);
        everyDayEveryOrgReportService.save(everyRpt);
        reportSubscribeNumberDao.save(list);
    }

    @Override
    @Transactional(readOnly = true)
    public WebReportnumberDetailResp getEveryDetailById(long everyId) {
        WebReportnumberDetailResp resp = new WebReportnumberDetailResp();
        resp.setData(reportSubscribeNumberDao.findByEveryDayEveryOrgReport(everyId));
        if (null != resp.getData()) {
            long tmp = 0;
            for (WebReportnumberDetailItem item : resp.getData()) {
                tmp += item.getReport_num();
            }
            resp.setTotal_num(everyDayEveryOrgReportService.findReportnumTotalnumById(everyId));
        }
        return resp;
    }

    private String createReportNo(int year, int month, int day, long orgId) {
        StringBuilder sb = new StringBuilder(year);
        sb.append(month).append(day).append(orgId);
        return sb.toString();
    }

    @Override
    public Page<LineDataTmp> queryLinebyYear(Pageable page, Integer year, String orgname) {
        return reportSubscribeNumberDao.queryLinebyYear(page, year, orgname);
    }

    @Override
    public Page<LineDataTmp> queryLinebyYear(Pageable page, Integer year) {
        return reportSubscribeNumberDao.queryLinebyYear(page, year);
    }

    @Override
    public Page<LineDataTmp> queryLinebyYearmonthday(Pageable page, Integer year, Integer month, Integer day, String orgname) {
        return reportSubscribeNumberDao.queryLinebyYearmonthday(page, year, month, day, orgname);
    }

    @Override
    public Page<LineDataTmp> queryLinebyYearmonthday(Pageable page, Integer year, Integer month, Integer day) {
        return reportSubscribeNumberDao.queryLinebyYearmonthday(page, year, month, day);
    }
}
