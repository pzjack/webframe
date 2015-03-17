/*
 * Copyright 2015 Jack.Alexander
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dinglicom.dealerdispathing.service.impl;

import com.dinglicom.dealerdispathing.domain.DealerDispatchOnedayItem;
import com.dinglicom.dealerdispathing.domain.DealerDispatchOnedayResp;
import com.dinglicom.dealerdispathing.domain.DealerDispatchResp;
import com.dinglicom.dealerdispathing.domain.DealerDispatchRespItem;
import com.dinglicom.dealerdispathing.domain.DealerDispathReq;
import com.dinglicom.dealerdispathing.domain.DealerOnedayTaskReq;
import com.dinglicom.dealerdispathing.domain.DispatingDetailResp;
import com.dinglicom.dealerdispathing.domain.LineProduct;
import com.dinglicom.dealerdispathing.entity.DealerDispatchItem;
import com.dinglicom.dealerdispathing.entity.DealerDispatching;
import com.dinglicom.dealerdispathing.repository.DealerDispatchItemDao;
import com.dinglicom.dealerdispathing.repository.DealerDispatchingDao;
import com.dinglicom.dealerdispathing.service.DealerDispatchServcie;
import com.dinglicom.frame.sys.entity.SysOranizagion;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.frame.sys.service.SysOranizagionService;
import com.dinglicom.frame.sys.util.DateUtil;
import com.dinglicom.product.entity.UserProduct;
import com.dinglicom.product.service.UserProductService;
import com.dinglicom.reportnum.demain.LineProductNum;
import com.dinglicom.reportnum.entity.ReportSubscribeNumber;
import com.dinglicom.reportnum.service.ReportSubscribeNumberService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 经销商做配送工作所需的逻辑处理
 *
 * @author panzhen
 */
@Component
@Transactional
public class DealerDispatchServcieImpl implements DealerDispatchServcie {

    private final static Logger LOG = LoggerFactory.getLogger(DealerDispatchServcieImpl.class);

    @Resource
    private DealerDispatchingDao dealerDispatchingDao;
    @Resource
    private DealerDispatchItemDao dealerDispatchItemDao;
    @Resource
    private ReportSubscribeNumberService reportSubscribeNumberService;
    @Resource
    private SysOranizagionService sysOranizagionService;
    @Resource
    private UserProductService userProductService;

    /**
     * 自动将前一天的报量划拨到相应的经销商
     */
    public void autoGettoDealerProduct() {
        Calendar c = Calendar.getInstance();
        c = DateUtil.getPreDay(c, 1);//前一天
        int year = DateUtil.getYear(c);
        int month = DateUtil.getMonth(c);
        int day = DateUtil.getDay(c);
        Date cur = new Date();
        Calendar ct = Calendar.getInstance();
        Date cmin = DateUtil.getOneDayMintime(ct).getTime();
        Date cmax = DateUtil.getOneDayMaxtime(ct).getTime();
        String no = DateUtil.formatToDayNum(cur);
        List<SysOranizagion> dls = sysOranizagionService.findAllDealer();//查找所有经销商
        for (SysOranizagion d : dls) {//逐个经销商处理一遍
            //首先判断该经销商是否已经处理过了
            if (dealerDispatchingDao.countDealerDataByDate(d.getId(), cmin, cmax) > 0) {
                continue;
            }
            List<ReportSubscribeNumber> rptDetails = reportSubscribeNumberService.findByDealerAndDate(d.getId(), year, month, day);
            List<DealerDispatchItem> details = new ArrayList<DealerDispatchItem>();
            Map<Long, DealerDispatching> dealerDispathings = new HashMap<Long, DealerDispatching>();
            List<DealerDispatching> dealerList = new ArrayList<DealerDispatching>();
            for (ReportSubscribeNumber rpt : rptDetails) {
                if (0 == rpt.getReportnum()) {
                    continue;
                }
                if (null == rpt.getProduct() || null == rpt.getProduct().getId() || rpt.getProduct().getId() <= 0) {
                    LOG.warn("Report detail data not product msg id({})", rpt.getId());
                    continue;
                }
                DealerDispatching dealerDispathing = dealerDispathings.get(rpt.getProduct().getId());
                if (null == dealerDispathing) {
                    dealerDispathing = createDealerDispatching(cur, d, rpt.getProduct());
                    dealerDispathing.setDispatingno(no + d.getId());
                    dealerDispathing.setProductnum(rpt.getReportnum());
                    dealerList.add(dealerDispathing);
                    dealerDispathings.put(rpt.getProduct().getId(), dealerDispathing);
                } else {
                    dealerDispathing.setProductnum(dealerDispathing.getProductnum() + rpt.getReportnum());
                }
                com.dinglicom.dealerdispathing.entity.DealerDispatchItem dpc = createDispathingRecord(cur, rpt);
                dpc.setDispatingno(no + rpt.getOrg().getId());
                details.add(dpc);
            }
            if (details.size() > 0) {
                dealerDispatchItemDao.save(details);
                dealerDispatchingDao.save(dealerList);
            }
        }
    }

    private DealerDispatching createDealerDispatching(Date cur, SysOranizagion dealer, UserProduct product) {
        DealerDispatching dealerDispathing = new DealerDispatching();
        dealerDispathing.setDealer(dealer);
        dealerDispathing.setDealermanager(dealer.getResponsible_man());
        dealerDispathing.setDealername(dealer.getName());

        dealerDispathing.setDispathstate(DealerDispatchServcie.DISPATH_STATE_NONE);
        dealerDispathing.setDispathingdate(cur);

        dealerDispathing.setProduct(product);
        dealerDispathing.setProductname(product.getShortname());
        dealerDispathing.setProducttype(product.getProducttype());

        dealerDispathing.setProductnum(0L);
        return dealerDispathing;
    }

    private com.dinglicom.dealerdispathing.entity.DealerDispatchItem createDispathingRecord(Date cur, ReportSubscribeNumber rpt) {
        DealerDispatchItem d = new DealerDispatchItem();
        d.setReportdetail(rpt);
        if (null != rpt.getDealer()) {
            d.setDealer(rpt.getDealer());
            d.setDealername(rpt.getDealer().getName());
            d.setDealermanager(rpt.getDealer().getResponsible_man());
        }
        if (null != rpt.getOrg()) {
            d.setWorkstation(rpt.getOrg());
            d.setStationname(rpt.getOrg().getName());
            d.setStationphone(rpt.getOrg().getPhone());
            d.setStationmanager(rpt.getOrg().getResponsible_man());
            d.setStationaddress(rpt.getOrg().getAddress());
        }

        if (null != rpt.getProduct()) {
            d.setProduct(rpt.getProduct());
            d.setProductname(rpt.getProductname());
            d.setProducttype(rpt.getProducttype());
            d.setDistrutenum(rpt.getReportnum());
        }

        d.setDispathingdate(cur);
        d.setDispathstate(DealerDispatchServcie.DISPATH_STATE_NONE);
        return d;
    }

    @Override
    @Transactional(readOnly = true)
    public DealerDispatchResp queryReportlist(DealerDispathReq req) {
        DealerDispatchResp resp = new DealerDispatchResp();
        resp.setProducts(userProductService.findAllProduct());
        Calendar c = Calendar.getInstance();
        Date cmin = DateUtil.getOneDayMintime(c).getTime();
        Date cmax = DateUtil.getOneDayMaxtime(c).getTime();
        Page<DealerDispatching> page = dealerDispatchingDao.findByDispathingdateBetween(buildPageRequest(req.getPage(), req.getNum()), req.getStatus(), cmin, cmax);
        if (null != page) {
            resp.setTotal_num(page.getTotalElements());
            List<DealerDispatchRespItem> list = new ArrayList<DealerDispatchRespItem>();
            resp.setData(list);
            Map<Long, DealerDispatchRespItem> map = new HashMap<Long, DealerDispatchRespItem>();
            for (DealerDispatching d : page.getContent()) {
                DealerDispatchRespItem drt;
                if (null != d.getDealer()) {
                    drt = map.get(d.getDealer().getId());
                } else {
                    continue;
                }
                if (null == drt) {
                    drt = new DealerDispatchRespItem();
                    drt.setDealer(d.getDealername());
                    drt.setId(d.getDispatingno());
                    drt.setManager(d.getDealermanager());
                    drt.setTotal_amount(d.getProductnum());
                    List<LineProductNum> plist = new ArrayList<LineProductNum>();
                    LineProductNum pn = new LineProductNum();
                    pn.setPid(d.getProduct().getId());
                    pn.setPnum(d.getProductnum());
                    plist.add(pn);
                    drt.setDetail(plist);
                    list.add(drt);
                    map.put(d.getDealer().getId(), drt);
                } else {
                    LineProductNum pn = new LineProductNum();
                    pn.setPid(d.getProduct().getId());
                    pn.setPnum(d.getProductnum());
                    drt.getDetail().add(pn);
                    drt.setTotal_amount(drt.getTotal_amount() + d.getProductnum());
                }
            }
        }
        return resp;
    }

    private PageRequest buildPageRequest(final int page, final int size) {
        return new PageRequest(page - 1, size);
    }

    @Override
    public void doShip(String id, UserInfo darler) {
        List<DealerDispatching> list = dealerDispatchingDao.findByDispatingno(id);
        List<DealerDispatchItem> listdetals = new ArrayList<DealerDispatchItem>();
        for (DealerDispatching dealerDispatching : list) {
            if (DealerDispatchServcie.DISPATH_STATE_NONE.equals(dealerDispatching.getDispathstate())) {
                dealerDispatching.setDispathstate(DealerDispatchServcie.DISPATH_STATE_INT);
                dealerDispatching.setUser(darler);
                dealerDispatching.setUsername(darler.getRealname());

                Calendar c = Calendar.getInstance();
                Date cmin = DateUtil.getOneDayMintime(c).getTime();
                Date cmax = DateUtil.getOneDayMaxtime(c).getTime();
                List<DealerDispatchItem> ds = dealerDispatchItemDao.findByDetailByDealerDay(dealerDispatching.getDealer().getId(), cmin, cmax);
                for (DealerDispatchItem d : ds) {
                    d.setDispathstate(DealerDispatchServcie.DISPATH_STATE_INT);
                    listdetals.add(d);
                }
            }
        }
        if (list.size() > 0) {
            dealerDispatchingDao.save(list);
            dealerDispatchItemDao.save(listdetals);
        }
    }

    @Override
    public void doDispatching(String id, UserInfo darler) {
//        List<DealerDispatching> list = dealerDispatchingDao.findByDispatingno(id);
        Calendar c = Calendar.getInstance();
        Date cur = c.getTime();
        Long darlerId = null;
        List<DealerDispatchItem> list = dealerDispatchItemDao.findByDispatingno(id);
        for (DealerDispatchItem d : list) {
            d.setDispathstate(DealerDispatchServcie.DISPATH_STATE_DONE);
            d.setShiptime(cur);
            d.setShipname(darler.getRealname());
            d.setShipphone(darler.getPhone());
            d.setShipnum(d.getDistrutenum());
            if (null == darlerId) {
                darlerId = d.getDealer().getId();
            }

            c.setTime(d.getDispathingdate());
        }

        if (list.size() > 0) {
            dealerDispatchItemDao.save(list);
        }

        Date cmin = DateUtil.getOneDayMintime(c).getTime();
        Date cmax = DateUtil.getOneDayMaxtime(c).getTime();
        System.out.println(DateUtil.formatToSec(cmin) + "\t\t" + DateUtil.formatToSec(cmax) + "\t" + darlerId);
        if (0 >= dealerDispatchItemDao.countByDealer(darlerId, DealerDispatchServcie.DISPATH_STATE_DONE, cmin, cmax)) {
            List<DealerDispatching> ddList = dealerDispatchingDao.findByDealerDay(darlerId, cmin, cmax);
            for (DealerDispatching d : ddList) {
                d.setDispathstate(DealerDispatchServcie.DISPATH_STATE_DONE);
            }
            if (ddList.size() > 0) {
                dealerDispatchingDao.save(ddList);
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DealerDispatchOnedayResp getCurDayTasks(DealerOnedayTaskReq req, UserInfo darler) {
        DealerDispatchOnedayResp resp = new DealerDispatchOnedayResp();
        Calendar c = Calendar.getInstance();
        Date cmin = DateUtil.getOneDayMintime(c).getTime();
        Date cmax = DateUtil.getOneDayMaxtime(c).getTime();
        Long darlerId = null;
        String state = DealerDispatchServcie.DISPATH_STATE_INT;
        if ("done".equalsIgnoreCase(req.getStatus())) {
            state = DealerDispatchServcie.DISPATH_STATE_DONE;
        }
        if (null != darler.getOrg()) {
            darlerId = darler.getOrg().getId();
        } else {
            throw new RuntimeException("Not found current user org.");
        }
        Page<DealerDispatchOnedayItem> page = dealerDispatchItemDao.findByDealerOnedayStation(buildPageRequest(req.getPage(), req.getNum()), darlerId, state, cmin, cmax);
        if (null != page) {
            resp.setTotal_page(page.getTotalPages());
            resp.setData(page.getContent());
        }
        return resp;
    }

    
    @Override
    @Transactional(readOnly = true)
    public DispatingDetailResp getDispatingDetail(String id) {
        DispatingDetailResp resp = new DispatingDetailResp();
        List<DealerDispatchItem> list = dealerDispatchItemDao.findByDispatingno(id);
        List<LineProduct> data = new ArrayList<LineProduct>();
        Map<Long, LineProduct> map = new HashMap<Long, LineProduct>();
        long num = 0;
        for (DealerDispatchItem d : list) {
            if (null == resp.getId()) {
                resp.setId(d.getDispatingno());
                resp.setStation(d.getStationname());
                resp.setManager(d.getStationmanager());
                resp.setTel(d.getStationphone());
                resp.setAddress(d.getStationaddress());
            }
            LineProduct p = map.get(d.getProduct().getId());
            if (null == p) {
                p = new LineProduct();
                map.put(d.getProduct().getId(), p);
                data.add(p);
                p.setProduct_name(d.getProductname());
                p.setDistribution_num(d.getDistrutenum());
            }
            num += p.getDistribution_num();
        }
        resp.setTotal_amount(num);
        resp.setData(data);
        return resp;
    }
}
