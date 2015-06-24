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
import com.dinglicom.pricepolicy.entity.PricePolicyHistory;
import com.dinglicom.pricepolicy.service.PriceTemplateService;
import com.dinglicom.product.domain.ProductItem;
import com.dinglicom.product.entity.UserProduct;
import com.dinglicom.reportform.domain.SalemanTmp;
import com.dinglicom.reportnum.demain.AutoReportLastNum;
import com.dinglicom.reportnum.demain.LineDataTmp;
import com.dinglicom.reportnum.demain.ReportDetailReq;
import com.dinglicom.reportnum.demain.ReportDetailResp;
import com.dinglicom.reportnum.demain.ReportDetailRespItem;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
//    @Resource
//    private UserOrderService userOrderService;
//    @Resource
//    private UserProductService userProductService;
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
    @Resource
    private PriceTemplateService priceTemplateService;
    @PersistenceContext
    private EntityManager em;

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
//        List<SysOranizagion> dlvs = sysOranizagionService.findAllDeliveryOrg();
//        if (null != dlvs && dlvs.size() > 0) {
//            nzhs.addAll(dlvs);
//        }
//        List<UserProduct> pals = userProductService.getAll();
        Map<Long, Map<Long, PricePolicyHistory>> pals = new HashMap<Long, Map<Long, PricePolicyHistory>>();
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
            Long dealarId = null != nz.getDealer() ? nz.getDealer().getId() : nz.getId();
            Map<Long, PricePolicyHistory> ps = pals.get(dealarId);
            if (null == ps) {
                ps = addDealarPricepolicy(dealarId, pals);
            }
            if (null == ps || ps.size() <= 0) {
                LOG.warn("StationId[{}], name[{}], dealarId[{}] is not price policy template, it's not auto report number.", nz.getId(), nz.getName(), dealarId);
                continue;
            }

//            Map<Long, UserProduct> ps = getAll(pals);
            List<ReportSubscribeNumber> list = new ArrayList<ReportSubscribeNumber>();
            List<AutoReportLastNum> nlist = reportSubscribeNumberDao.findByAutoLastReportNumYearmonthday(nz.getId(), nyear, nmonth, nday);
            if (null == nlist || nlist.size() <= 0) {//前一天没有报量
//                boolean isworkday = DateUtil.isWorkDay(c);
//                Date oneday = c.getTime();
//                List<ReportNumberItemResp> respItems;
//                if (isworkday) {
//                    respItems = userOrderService.findWorkdayReportNumberByWorkstation(nz, oneday);
//                } else {
//                    respItems = userOrderService.findWorkendReportNumberByWorkstation(nz, oneday);
//                }
//
//                for (ReportNumberItemResp rsp : respItems) {
//                    ReportSubscribeNumber r = createReportSubscribeNumber(rsp.getProduct_id(), rsp.getAmount(), year, month, day, nz, nzmanager, ps);
//                    r.setEveryDayEveryOrgReport(everyRpt);
//                    totalNum += r.getReportnum();
//                    list.add(r);
//                }
            } else {//使用上一天的报量数据
                for (AutoReportLastNum an : nlist) {
                    ReportSubscribeNumber r = createReportnumber(an, year, month, day, nz, nzmanager, ps);
                    if (null == r) {
                        continue;
                    }
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

    private Map<Long, PricePolicyHistory> addDealarPricepolicy(Long dealarId, Map<Long, Map<Long, PricePolicyHistory>> pals) {
        List<PricePolicyHistory> priceHis = priceTemplateService.findDealarCurrentPricePolicy(dealarId);
        Map<Long, PricePolicyHistory> pMap = new HashMap<Long, PricePolicyHistory>();
        for (PricePolicyHistory h : priceHis) {
            pMap.put(h.getProduct().getId(), h);
        }
        pals.put(dealarId, pMap);
        return pMap;
    }

//    private Map<Long, UserProduct> getAll(List<UserProduct> pals) {
//        Map<Long, UserProduct> map = new HashMap<Long, UserProduct>();
//        Iterator<UserProduct> it = pals.iterator();
//        while (it.hasNext()) {
//            UserProduct p = it.next();
//            map.put(p.getId(), p);
//        }
//        return map;
//    }
    private ReportSubscribeNumber createReportSubscribeNumber(Long pid, long amount, int year, int month, int day, SysOranizagion nz, UserInfo nzmanager, Map<Long, PricePolicyHistory> ps) {
        ReportSubscribeNumber r = new ReportSubscribeNumber();
        PricePolicyHistory his = ps.get(pid);
        if (null == his) {
            return null;
        }
        UserProduct product = his.getProduct();
        if (null != product) {
            r.setProduct(product);
            r.setProductname(product.getShortname());
            r.setProducttype(product.getProducttype());
        }
        r.setDistrutenum(amount);
        r.setReportnum(r.getDistrutenum() - r.getMinusnum() + r.getPlusnum());
        r.setPrice(his.getTemplateDetail().getReportPrice());
        r.setTotalPrice(r.getPrice() * r.getReportnum());
        r.setYear(year);
        r.setQuarter(DateUtil.getQuarterByMonth(month));
        r.setMonth(month);
        r.setDay(day);
        r.setOrg(nz);
        if (null != nz.getDealer()) {
            r.setDealer(nz.getDealer());
        }
        r.setOrgname(nz.getName());
        if (null != nzmanager) {
            r.setReportman(nzmanager);
            r.setReportname(nzmanager.getRealname());
        }
        r.setReportstate(EveryDayEveryOrgReportService.REPORT_STATE_REPORTTED);
        return r;
    }

    private ReportSubscribeNumber createReportnumber(AutoReportLastNum an, int year, int month, int day, SysOranizagion nz, UserInfo nzmanager, Map<Long, PricePolicyHistory> ps) {
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
//        boolean isworkday = DateUtil.isWorkDay(c);
//        Date oneday = c.getTime();
        EveryDayEveryOrgReport erp = everyDayEveryOrgReportService.findByOrgidAndDate(nz.getId(), year, month, day);
//        if (null == erp) {
//            erp = everyDayEveryOrgReportService.createEveryReportnumber(nz, year, month, day);
//            everyDayEveryOrgReportService.save(erp);
//        }
        List<ReportNumberItemResp> respItems;
        ReportNumberResp rresp = new ReportNumberResp();
        //报量时间和报量状态需补充
//        if (null != erp && EveryDayEveryOrgReportService.REPORT_STATE_UNREPORT.equals(erp.getReportstate())) {
//            if (isworkday) {
//                respItems = userOrderService.findWorkdayReportNumberByWorkstation(nz, oneday);
//            } else {
//                respItems = userOrderService.findWorkendReportNumberByWorkstation(nz, oneday);
//            }
//            rresp.setStatus(erp.getReportstate());
//        } else {
        respItems = reportSubscribeNumberDao.findByReportNumYearmonthday(nz.getId(), year, month, day);
//        }
        Map<Long, ProductItem> pmap;
        if (null != nz.getDealer()) {
            pmap = getProductAll(nz.getDealer().getId());
        } else {
            pmap = getProductAll(nz.getId());
        }
        ReportnumberTime rt = reportnumberTimeService.get();
        rresp.setData(respItems);
        if (null != rt) {
            rresp.setExpired_time(rt.getTime());
        }
        if (null != erp) {
            rresp.setStatus(erp.getReportstate());
        } else {
            rresp.setStatus(EveryDayEveryOrgReportService.REPORT_STATE_UNREPORT);
        }
        long tt = 0;
        if (null != respItems && respItems.size() > 0) {
            for (ReportNumberItemResp item : respItems) {
                tt += item.getAmount();
                pmap.remove(item.getProduct_id());
            }
            addReportProduct(respItems, pmap);
            rresp.setTotal_amount(tt);
        } else {
            respItems = new ArrayList<ReportNumberItemResp>();
            addReportProduct(respItems, pmap);
            rresp.setData(respItems);
        }
        return rresp;
    }

    private Map<Long, ProductItem> getProductAll(Long dealarId) {
        List<ProductItem> plist = priceTemplateService.findDealarProduct(dealarId);//userProductService.findAllProduct();
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
                throw new RuntimeException("报量时间已过！");
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
        List<PricePolicyHistory> priceHis;
        if (null != nz.getDealer()) {
            priceHis = priceTemplateService.findDealarCurrentPricePolicy(nz.getDealer().getId());
        } else {
            priceHis = priceTemplateService.findDealarCurrentPricePolicy(nz.getId());
        }
        Map<Long, PricePolicyHistory> pMap = new HashMap<Long, PricePolicyHistory>();
        for (PricePolicyHistory h : priceHis) {
            pMap.put(h.getProduct().getId(), h);
        }
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
            PricePolicyHistory his = pMap.get(rsp.getProduct_id());
            if (null == his) {
                continue;
            }
            r.setPrice(his.getTemplateDetail().getReportPrice());
            r.setTotalPrice(r.getPrice() * r.getReportnum());
            UserProduct product = his.getProduct();//userProductService.read(rsp.getProduct_id());
            r.setProduct(product);
            r.setProductname(product.getShortname());
            r.setProducttype(product.getProducttype());
            totalNum += r.getReportnum();
            r.setYear(year);
            r.setQuarter(DateUtil.getQuarter(c));
            r.setMonth(month);
            r.setDay(day);
            r.setOrg(nz);
            r.setOrgname(nz.getName());
            if (null != nz.getDealer()) {
                r.setDealer(nz.getDealer());
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
    @Transactional(readOnly = true)
    public Page<LineDataTmp> queryLinebyYear(Pageable page, Integer year, String orgname) {
        return reportSubscribeNumberDao.queryLinebyYear(page, year, orgname);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LineDataTmp> queryLinebyYear(Pageable page, Integer year) {
        return reportSubscribeNumberDao.queryLinebyYear(page, year);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LineDataTmp> queryLinebyYearmonthday(Pageable page, Integer year, Integer month, Integer day, String orgname) {
        return reportSubscribeNumberDao.queryLinebyYearmonthday(page, year, month, day, orgname);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LineDataTmp> queryLinebyYearmonthday(Pageable page, Integer year, Integer month, Integer day) {
        return reportSubscribeNumberDao.queryLinebyYearmonthday(page, year, month, day);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportSubscribeNumber> findByDealerAndDate(Long dealerId, Integer year, Integer month, Integer day) {
        return reportSubscribeNumberDao.findByDealerYearmonthday(dealerId, year, month, day);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LineDataTmp> queryLinebyday(Integer year, Integer month, Integer day) {
        return reportSubscribeNumberDao.queryLinebyYearmonthday(year, month, day);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LineDataTmp> queryLinebydayDepid(Integer year, Integer month, Integer day, Long depid) {
        return reportSubscribeNumberDao.queryLinebyYearmonthday(year, month, day);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LineDataTmp> queryLinebydaySalesId(Integer year, Integer month, Integer day, Long salsesid) {
        return reportSubscribeNumberDao.queryLinebyYearmonthday(year, month, day);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SalemanTmp> querySalemanbyday(Integer year, Integer month, Integer day) {
        return reportSubscribeNumberDao.querySalemansbyYearmonthday(year, month, day);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SalemanTmp> querySalemanbydayDepid(Integer year, Integer month, Integer day, Long depid) {
        return reportSubscribeNumberDao.querySalemansbyYearmonthdaydepid(year, month, day, depid);
    }

    private String createReportDetailHQL(boolean count, ReportDetailReq req) {
        StringBuilder hqlSb = new StringBuilder("select");
        if (count) {
            hqlSb.append(" count(a.id)");
        } else {
            hqlSb.append(" new com.dinglicom.reportnum.demain.ReportDetailRespItem(a.orgname, a.producttype, a.product.id, a.productname, a.reportnum, a.price, a.totalPrice)");
        }
        hqlSb.append(" from ReportSubscribeNumber a where a.signDelete=:signDelete");

        if (null != req.getDate()) {
            hqlSb.append(" and a.year=:year and a.month=:month and a.day=:day");
        }
        if (null != req.getQuery() && !req.getQuery().isEmpty()) {
            hqlSb.append(" and a.orgname like :orgname");
        }

        return hqlSb.toString();
    }

    @Override
    @Transactional(readOnly = true)
    public ReportDetailResp findReportDetails(ReportDetailReq req) {
        ReportDetailResp resp = new ReportDetailResp();
        Page<ReportDetailRespItem> page;
        String likeStr = "%" + req.getQuery() + "%";
        Query qc = em.createQuery(createReportDetailHQL(true, req));
        qc.setParameter("signDelete", Boolean.FALSE);
        Calendar c = Calendar.getInstance();
        if (null != req.getDate()) {
            c.setTime(req.getDate());
            qc.setParameter("year", DateUtil.getYear(c));
            qc.setParameter("month", DateUtil.getMonth(c));
            qc.setParameter("day", DateUtil.getDay(c));
        }
        if (null != req.getQuery() && !req.getQuery().isEmpty()) {
            qc.setParameter("orgname", likeStr);
        }
        long count = (Long)qc.getSingleResult();
        long tp = (count + req.getNum() - 1) / req.getNum();
        if (req.getPage() <= tp && count > 0) {
            Query q = em.createQuery(createReportDetailHQL(false, req));
            q.setParameter("signDelete", Boolean.FALSE);
            if (null != req.getDate()) {
                c.setTime(req.getDate());
                q.setParameter("year", DateUtil.getYear(c));
                q.setParameter("month", DateUtil.getMonth(c));
                q.setParameter("day", DateUtil.getDay(c));
            }
            if (null != req.getQuery() && !req.getQuery().isEmpty()) {
                q.setParameter("orgname", likeStr);
            }
            q.setFirstResult((req.getPage() - 1) * req.getNum());
            q.setMaxResults(req.getNum());
            page = new PageImpl<ReportDetailRespItem>(q.getResultList(), buildPageRequest(req.getPage(), req.getNum()), count);
        } else {
            page = new PageImpl<ReportDetailRespItem>(new ArrayList<ReportDetailRespItem>(), buildPageRequest(req.getPage(), req.getNum()), count);
        }
        resp.setData(page.getContent());
        resp.setTotal_page(page.getTotalPages());
        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportDetailRespItem> findReportDetailAll(ReportDetailReq req) {
        Query q = em.createQuery(createReportDetailHQL(false, req));
        q.setParameter("signDelete", Boolean.FALSE);
        if (null != req.getDate()) {
            Calendar c = Calendar.getInstance();
            c.setTime(req.getDate());
            q.setParameter("year", DateUtil.getYear(c));
            q.setParameter("month", DateUtil.getMonth(c));
            q.setParameter("day", DateUtil.getDay(c));
        }
        if (null != req.getQuery() && !req.getQuery().isEmpty()) {
            String likeStr = "%" + req.getQuery() + "%";
            q.setParameter("orgname", likeStr);
        }
       return q.getResultList(); 
    }

    /**
     * 创建分页请求.
     */
    private PageRequest buildPageRequest(final int page, final int size) {
        return new PageRequest(page - 1, size);
    }
}
