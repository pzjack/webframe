/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.reportnum.service.impl;

import com.dinglicom.frame.sys.entity.SysOranizagion;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.frame.sys.service.SysOranizagionService;
import com.dinglicom.frame.sys.util.DateUtil;
import com.dinglicom.order.domain.WebQueryOrderitemReq;
import com.dinglicom.product.entity.UserProduct;
import com.dinglicom.product.service.UserProductService;
import com.dinglicom.reportnum.demain.LineDataItem;
import com.dinglicom.reportnum.demain.LineDataTmp;
import com.dinglicom.reportnum.demain.LineDistributionQuery;
import com.dinglicom.reportnum.demain.LineProductItem;
import com.dinglicom.reportnum.demain.LineProductNum;
import com.dinglicom.reportnum.demain.LineResp;
import com.dinglicom.reportnum.demain.WebEverydayorgItemResp;
import com.dinglicom.reportnum.demain.WebEverydayorgListResp;
import com.dinglicom.reportnum.demain.WebReportNumberQueryItemResp;
import com.dinglicom.reportnum.demain.WebReportNumberReq;
import com.dinglicom.reportnum.demain.WebReportlistDetailItem;
import com.dinglicom.reportnum.demain.WebReportlistItemResp;
import com.dinglicom.reportnum.demain.WebReportlistReq;
import com.dinglicom.reportnum.demain.WebReportlistResp;
import com.dinglicom.reportnum.demain.WebReportlistResult;
import com.dinglicom.reportnum.demain.WebReportnumQueryResp;
import com.dinglicom.reportnum.demain.WebReportnumberCountDetail;
import com.dinglicom.reportnum.entity.EveryDayEveryOrgReport;
import com.dinglicom.reportnum.entity.ReportSubscribeNumber;
import com.dinglicom.reportnum.repository.EveryDayEveryOrgReportDao;
import com.dinglicom.reportnum.service.EveryDayEveryOrgReportService;
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
 *
 * @author panzhen
 */
@Component
@Transactional
public class EveryDayEveryOrgReportServiceImpl implements EveryDayEveryOrgReportService {

    private final static Logger LOG = LoggerFactory.getLogger(EveryDayEveryOrgReportServiceImpl.class);

//    @PersistenceContext
//    private EntityManager entityManager;
    @Resource
    private EveryDayEveryOrgReportDao everyDayEveryOrgReportDao;
    @Resource
    private ReportSubscribeNumberService reportSubscribeNumberService;
    @Resource
    private SysOranizagionService sysOranizagionService;
    @Resource
    private UserProductService userProductService;

    @Override
    public EveryDayEveryOrgReport save(EveryDayEveryOrgReport everyDayEveryOrgReport) {
        return everyDayEveryOrgReportDao.save(everyDayEveryOrgReport);
    }

    @Override
    public Iterable<EveryDayEveryOrgReport> save(List<EveryDayEveryOrgReport> list) {
        return everyDayEveryOrgReportDao.save(list);
    }

    @Override
    @Transactional(readOnly = true)
    public WebEverydayorgListResp getReportnumberList(WebReportNumberReq req) {
        WebEverydayorgListResp resp = new WebEverydayorgListResp();
        Page<WebEverydayorgItemResp> page;
        PageRequest pageRequest = buildPageRequest(req.getPage(), req.getNum());

        Calendar c = Calendar.getInstance();
        int year = DateUtil.getYear(c);
        int month = DateUtil.getMonth(c);
        int day = DateUtil.getDay(c);
        if (null != req.getStatus()) {
            page = everyDayEveryOrgReportDao.findByReportstate(pageRequest, year, month, day, req.getStatus(), Boolean.FALSE);
        } else {
            page = everyDayEveryOrgReportDao.findList(pageRequest, year, month, day, Boolean.FALSE);
        }
        if (null != page) {
            resp.setTotal_num(page.getTotalElements());
            resp.setData(page.getContent());
        }
        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public WebReportnumQueryResp queryEveryDayOrgReport(WebQueryOrderitemReq req) {
        WebReportnumQueryResp resp = new WebReportnumQueryResp();
        Page<WebReportNumberQueryItemResp> page;
        PageRequest pageRequest = buildPageRequest(1, 20);
        if (null != req.getQuery()) {
            page = everyDayEveryOrgReportDao.queryByOrgname(pageRequest, req.getQuery(), Boolean.FALSE);
        } else {
            page = everyDayEveryOrgReportDao.query(pageRequest, Boolean.FALSE);
        }
        if (null != page) {
            resp.setData(page.getContent());
        }
        return resp;
    }

    @Override
    public void doShipByid(long id, UserInfo admin) {
        EveryDayEveryOrgReport r = everyDayEveryOrgReportDao.findOne(id);
        if (null == r) {
            throw new RuntimeException("Not found reportnumber data for id:" + id);
        }
        r.setShiptime(new Date());
        r.setShipnum(r.getTotalnum());
        r.setShipname(admin.getRealname());
        r.setShipphone(admin.getPhone());

        r.setReportstate(EveryDayEveryOrgReportService.REPORT_STATE_SHIPPED);

        everyDayEveryOrgReportDao.save(r);

        List<ReportSubscribeNumber> list = reportSubscribeNumberService.findByEveryid(id);
        if (null != list && list.size() > 0) {
            for (ReportSubscribeNumber rn : list) {
                rn.setReportstate(EveryDayEveryOrgReportService.REPORT_STATE_SHIPPED);
            }
            reportSubscribeNumberService.save(list);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public WebReportnumberCountDetail countByDate(Calendar c) {
        int year = DateUtil.getYear(c);
        int month = DateUtil.getMonth(c);
        int day = DateUtil.getDay(c);

        WebReportnumberCountDetail d = new WebReportnumberCountDetail();
        d.setUnreport(everyDayEveryOrgReportDao.countByReportstate(year, month, day, EveryDayEveryOrgReportService.REPORT_STATE_UNREPORT, Boolean.FALSE));
        d.setReported(everyDayEveryOrgReportDao.countByReportstate(year, month, day, EveryDayEveryOrgReportService.REPORT_STATE_REPORTTED, Boolean.FALSE));
        d.setShipped(everyDayEveryOrgReportDao.countByReportstate(year, month, day, EveryDayEveryOrgReportService.REPORT_STATE_SHIPPED, Boolean.FALSE));
        return d;
    }

    // @Scheduled(cron = "0/05 * * * * ?")
    @Override
    public void doCreateReportnumberNextday() {
        Calendar c = Calendar.getInstance();
//        c.add(Calendar.DAY_OF_MONTH, 1);
        LOG.info("start schedule caculate report number...");
        createReportnumberByWorkerstation(c);
    }

    @Override
    @Transactional(readOnly = true)
    public EveryDayEveryOrgReport findByOrgidAndDate(long orgId, int year, int month, int day) {
        List<EveryDayEveryOrgReport> list = everyDayEveryOrgReportDao.findByDateAndOrgid(orgId, year, month, day);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }

        return null;
    }

    /**
     * 按单位生成指定时间的总的报量信息
     *
     * @param c
     */
    @Override
    public void createReportnumberByWorkerstation(Calendar c) {
        int year = DateUtil.getYear(c);
        int month = DateUtil.getMonth(c);
        int day = DateUtil.getDay(c);
        //查找所有奶站
        List<SysOranizagion> nzhs = sysOranizagionService.findAllWorkerOrg();
        List<EveryDayEveryOrgReport> rptList = new ArrayList<EveryDayEveryOrgReport>();
        for (SysOranizagion nz : nzhs) {
            EveryDayEveryOrgReport rpt = findByOrgidAndDate(nz.getId(), year, month, day);
            if (null != rpt) {//首先判断单位报量信息是否生成
                continue;
            }
            EveryDayEveryOrgReport everyOrgReport = createEveryReportnumber(nz, year, month, day);
            rptList.add(everyOrgReport);
        }

        //第三方配送报量
        nzhs = sysOranizagionService.findOrgByType(SysOranizagionService.ORG_TYPE_DLV);
        for (SysOranizagion dlv : nzhs) {
            EveryDayEveryOrgReport rpt = findByOrgidAndDate(dlv.getId(), year, month, day);
            if (null != rpt) {//首先判断单位报量信息是否生成
                continue;
            }
            EveryDayEveryOrgReport everyOrgReport = createEveryReportnumber(dlv, year, month, day);
            rptList.add(everyOrgReport);
        }
        if (rptList.size() > 0) {
            everyDayEveryOrgReportDao.save(rptList);
        }
    }

    @Override
    public EveryDayEveryOrgReport createEveryReportnumber(SysOranizagion nz, int year, int month, int day) {
        EveryDayEveryOrgReport everyOrgReport = new EveryDayEveryOrgReport();
        everyOrgReport.setYear(year);
        everyOrgReport.setMonth(month);
        everyOrgReport.setDay(day);
        everyOrgReport.setQuarter(DateUtil.getQuarterByMonth(everyOrgReport.getMonth()));

        everyOrgReport.setReportstate(EveryDayEveryOrgReportService.REPORT_STATE_UNREPORT);

        everyOrgReport.setOrg(nz);
        everyOrgReport.setOrgname(nz.getName());
        everyOrgReport.setOrgtype(nz.getType());
        everyOrgReport.setResponsibleman(nz.getResponsible_man());
        everyOrgReport.setResponsiblephone(nz.getPhone());
        return everyOrgReport;
    }

    @Override
    @Transactional(readOnly = true)
    public WebReportlistResp queryReportlist(WebReportlistReq req) {
        WebReportlistResp resp = new WebReportlistResp();
        resp.setProducts(userProductService.findAllProduct());
        Page<WebReportlistResult> page;
        page = everyDayEveryOrgReportDao.queryReportlist(buildPageRequest(req.getPage(), req.getNum()), req.getYear(), req.getMonth(), req.getDay(), Boolean.FALSE);
        if (null != page && null != page.getContent()) {
            Map<Long, WebReportlistItemResp> cache = new HashMap<Long, WebReportlistItemResp>();
            List<WebReportlistItemResp> result = new ArrayList<WebReportlistItemResp>();
            List<WebReportlistResult> data = page.getContent();

            for (WebReportlistResult r : data) {
                WebReportlistItemResp orgItem = cache.get(r.getId());
                if (null == orgItem) {
                    orgItem = new WebReportlistItemResp();
                    orgItem.setOrg_name(r.getOrgname());
                    cache.put(r.getId(), orgItem);
                    result.add(orgItem);
                }
                orgItem.setTotal_amount((null == orgItem.getTotal_amount() ? 0 : orgItem.getTotal_amount()) + (null == r.getProductnum() ? 0 : r.getProductnum()));

                if (null != r.getId() || null != r.getProductnum()) {
                    List<WebReportlistDetailItem> detail = orgItem.getDetail();
                    if (null == detail) {
                        detail = new ArrayList<WebReportlistDetailItem>();
                        orgItem.setDetail(detail);
                    }
                    WebReportlistDetailItem di = new WebReportlistDetailItem(r.getProductid(), r.getProductnum());
                    detail.add(di);
                }
            }
            resp.setData(result);
            resp.setTotal_num(page.getTotalElements());
        }
        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public Long findReportnumTotalnumById(long id) {
        return everyDayEveryOrgReportDao.findReportnumtotalnumById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public LineResp queryLinelist(LineDistributionQuery req) {
        LineResp resp = new LineResp();
//        Specification<EveryDayEveryOrgReport> s = getLineWhere(req);
//        everyDayEveryOrgReportDao.findAll(s, buildPageRequest(req.getPage(), req.getNum()));
        PageRequest pr = buildPageRequest(req.getPage(), req.getNum());
        Page<LineDataTmp> page = null;
        if (null != req.getTimetype() && !req.getTimetype().isEmpty()) {
            if (null == req.getDate() || req.getDate().isEmpty()) {
                throw new RuntimeException("Not found query date value.");
            }
            if ("year".equalsIgnoreCase(req.getTimetype())) {
                String role = req.getRole();
                if (null != role && !role.isEmpty()) {
                    if ("STATION".equalsIgnoreCase(req.getRole())) {
                        if (null != req.getQuery() && !req.getQuery().isEmpty()) {
                            page = reportSubscribeNumberService.queryLinebyYear(pr, new Integer(req.getDate().trim()), req.getQuery());
                        }
//                        else {
//                            page = reportSubscribeNumberService.queryLinebyYear(pr, new Integer(req.getDate().trim()));
//                        }
                    }
                }
                page = reportSubscribeNumberService.queryLinebyYear(pr, new Integer(req.getDate().trim()));
            } else if ("quater".equalsIgnoreCase(req.getTimetype())) {
                int p = req.getDate().indexOf("-");
                if (p > 0) {
                    Integer year = new Integer(req.getDate().substring(0, p));
                    Integer quarter = new Integer(req.getDate().substring(p + 1));
                    String role = req.getRole();
                    if (null != role && !role.isEmpty()) {
                        if ("STATION".equalsIgnoreCase(req.getRole()) && null != req.getQuery()) {

                        }
                    }
                }
            } else if ("month".equalsIgnoreCase(req.getTimetype())) {
                int p = req.getDate().indexOf("-");
                if (p > 0) {
                    Integer year = new Integer(req.getDate().substring(0, p));
                    Integer month = new Integer(req.getDate().substring(p + 1));
                    String role = req.getRole();
                    if (null != role && !role.isEmpty()) {
                        if ("STATION".equalsIgnoreCase(req.getRole()) && null != req.getQuery()) {

                        }
                    }
                }
            } else if ("day".equalsIgnoreCase(req.getTimetype())) {
                int p = req.getDate().indexOf("-");
                int p2 = req.getDate().indexOf("-", p + 1);
                if (p > 0 && p2 > 0) {
                    Integer year = new Integer(req.getDate().substring(0, p));
                    Integer month = new Integer(req.getDate().substring(p + 1, p2));
                    Integer day = new Integer(req.getDate().substring(p2 + 1));
                    String role = req.getRole();
                    if (null != role && !role.isEmpty()) {
                        if ("STATION".equalsIgnoreCase(req.getRole())) {
                            if (null != req.getQuery() && !req.getQuery().isEmpty()) {
                                page = reportSubscribeNumberService.queryLinebyYearmonthday(pr, year, month, day, req.getQuery());
                            }
                        }
                    }
                    page = reportSubscribeNumberService.queryLinebyYearmonthday(pr, year, month, day);
                }
            }
        }
        List<UserProduct> allprd = userProductService.getAll();
        List<LineProductItem> gnrprd = new ArrayList<LineProductItem>();
        List<LineProductItem> lowprd = new ArrayList<LineProductItem>();
        Map<Long, LineProductItem> gmap = new HashMap<Long, LineProductItem>();
        Map<Long, LineProductItem> lowmap = new HashMap<Long, LineProductItem>();
        for (UserProduct p : allprd) {
            LineProductItem pm = new LineProductItem();
            pm.setPid(p.getId());
            pm.setPname(p.getShortname());
            if (UserProductService.P_TYPE_GERERAL == p.getProducttype()) {
                gnrprd.add(pm);
                gmap.put(pm.getPid(), pm);
            } else {
                lowprd.add(pm);
                lowmap.put(pm.getPid(), pm);
            }
        }
        resp.setGeneral_type(gnrprd);
        resp.setLow_type(lowprd);
        
        if (null != page) {
            resp.setTotal_num(page.getTotalElements());
            List<LineDataTmp> rnn = page.getContent();
            List<LineDataItem> list = new ArrayList<LineDataItem>();
            Map<Long, LineDataItem> cache = new HashMap<Long, LineDataItem>();
            Map<Long, List<LineProductNum>> orgrpn = new HashMap<Long, List<LineProductNum>>();
            Map<Long, List<LineProductNum>> orglp = new HashMap<Long, List<LineProductNum>>();
            for(LineDataTmp t : rnn) {
                LineDataItem ldit = cache.get(t.getOrgid());
                if(null == ldit) {
                    ldit = new LineDataItem();
                    list.add(ldit);
                    cache.put(t.getOrgid(), ldit);
                }
                ldit.setOrg_id(t.getOrgid());
                ldit.setOrg_name(t.getOrgname());
                LineProductItem ptm = gmap.get(t.getPid());
                LineProductNum pn = new LineProductNum();
                pn.setPid(t.getPid());
                pn.setPnum(t.getRpnum());
                if(null != ptm) {
                    List<LineProductNum> gp = orgrpn.get(t.getOrgid());
                    if(null == gp) {
                        gp = new ArrayList<LineProductNum>();
                        orgrpn.put(t.getOrgid(), gp);
                        ldit.setGeneral_type(gp);
                    }
                    gp.add(pn);
                } else {
                    List<LineProductNum> lp = orglp.get(t.getOrgid());
                    if(null == lp) {
                        lp = new ArrayList<LineProductNum>();
                        orglp.put(t.getOrgid(), lp);
                        ldit.setLow_type(lp);
                    }
                    lp.add(pn);
                }
            }
            resp.setData(list);
        }
        return resp;
    }

//    @Transactional(readOnly = true)
//    public LineResp queryLinelistSQL(LineDistributionQuery req) {
//        LineResp resp = new LineResp();
////        Specification<EveryDayEveryOrgReport> s = getLineWhere(req);
////        everyDayEveryOrgReportDao.findAll(s, buildPageRequest(req.getPage(), req.getNum()));
//        Query query = entityManager.createQuery("select new () from ReportSubscribeNumber", );
//        List listExpected = query.getResultList();
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
//        Root from = criteriaQuery.from(EveryDayEveryOrgReport.class);
//
//        Expression minExpression = criteriaBuilder.min(from.get("pint"));
//        Path pbytePath = from.get("pbyte");
//        CriteriaQuery<Object> select = criteriaQuery.multiselect(minExpression, pbytePath);
//
//        CriteriaQuery<Object> groupBy = select.groupBy(pbytePath);
//        TypedQuery<Object> typedQuery = entityManager.createQuery(select);
//        List listActual = typedQuery.getResultList();
//        if (null != req.getTimetype() && !req.getTimetype().isEmpty()) {
//            if (null == req.getDate() || req.getDate().isEmpty()) {
//                throw new RuntimeException("Not found query date value.");
//            }
//            if ("year".equalsIgnoreCase(req.getTimetype())) {
//                String role = req.getRole();
//                if (null != role && !role.isEmpty()) {
//                    if ("STATION".equalsIgnoreCase(req.getRole()) && null != req.getQuery()) {
//
//                    }
//                }
//            } else if ("quater".equalsIgnoreCase(req.getTimetype())) {
//                int p = req.getDate().indexOf("-");
//                if (p > 0) {
//                    Integer year = new Integer(req.getDate().substring(0, p));
//                    Integer quarter = new Integer(req.getDate().substring(p + 1));
//                    String role = req.getRole();
//                    if (null != role && !role.isEmpty()) {
//                        if ("STATION".equalsIgnoreCase(req.getRole()) && null != req.getQuery()) {
//
//                        }
//                    }
//                }
//            } else if ("month".equalsIgnoreCase(req.getTimetype())) {
//                int p = req.getDate().indexOf("-");
//                if (p > 0) {
//                    Integer year = new Integer(req.getDate().substring(0, p));
//                    Integer month = new Integer(req.getDate().substring(p + 1));
//                    String role = req.getRole();
//                    if (null != role && !role.isEmpty()) {
//                        if ("STATION".equalsIgnoreCase(req.getRole()) && null != req.getQuery()) {
//
//                        }
//                    }
//                }
//            } else if ("day".equalsIgnoreCase(req.getTimetype())) {
//                int p = req.getDate().indexOf("-");
//                int p2 = req.getDate().indexOf("-", p + 1);
//                if (p > 0 && p2 > 0) {
//                    Integer year = new Integer(req.getDate().substring(0, p));
//                    Integer month = new Integer(req.getDate().substring(p + 1, p2));
//                    Integer day = new Integer(req.getDate().substring(p2 + 1));
//                    String role = req.getRole();
//                    if (null != role && !role.isEmpty()) {
//                        if ("STATION".equalsIgnoreCase(req.getRole()) && null != req.getQuery()) {
//
//                        }
//                    }
//                }
//            }
//        }
//        return resp;
//    }
//
//    private Specification<EveryDayEveryOrgReport> getLineWhere(final LineDistributionQuery req) {
//        return new Specification<EveryDayEveryOrgReport>() {
//            @Override
//            public Predicate toPredicate(Root<EveryDayEveryOrgReport> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                List<Predicate> predicate = new ArrayList<Predicate>();
////                List<CriteriaQuery<?>> grplist = new ArrayList<CriteriaQuery<?>>();
//                if (null != req.getTimetype() && !req.getTimetype().isEmpty()) {
//                    if (null == req.getDate() || req.getDate().isEmpty()) {
//                        throw new RuntimeException("Not found query date value.");
//                    }
//                    if ("year".equalsIgnoreCase(req.getTimetype())) {
//                        Path gp = root.get("year");
////                        grplist.add(query.select(gp));
//                        predicate.add(cb.equal(gp.as(Integer.class), new Integer(req.getDate())));
////                       select = query.multiselect(date, grpPath);
////                       select.groupBy(grpPath);
//                        query.select(gp).groupBy(gp);
//                    } else if ("quater".equalsIgnoreCase(req.getTimetype())) {
//                        Path gp = root.get("year");
//                        Path gp2 = root.get("quarter");
//                        int p = req.getDate().indexOf("-");
//                        if (p > 0) {
//                            Integer year = new Integer(req.getDate().substring(0, p));
//                            Integer quarter = new Integer(req.getDate().substring(p + 1));
////                            grplist.add(query.select(gp));
////                            grplist.add(query.select(gp2));
//                            query.multiselect(gp, gp2).groupBy(gp, gp2);
//                            predicate.add(cb.equal(gp.as(Integer.class), year));
//                            predicate.add(cb.equal(gp2.as(Integer.class), quarter));
//                        }
//                    }
//                }
//                String role = req.getRole();
//                if (null != role && !role.isEmpty()) {
//                    if ("STATION".equalsIgnoreCase(req.getRole()) && null != req.getQuery()) {
//                        predicate.add(cb.like(root.get("orgname").as(String.class), "%" + req.getQuery() + "%"));
//                    }
//                }
//                Predicate[] pre = new Predicate[predicate.size()];
//                query.groupBy();
//                return query.where(predicate.toArray(pre)).getRestriction();
//            }
//        };
//    }
    /**
     * 创建分页请求.
     */
    private PageRequest buildPageRequest(final int page, final int size) {
        return new PageRequest(page - 1, size);
    }
}
