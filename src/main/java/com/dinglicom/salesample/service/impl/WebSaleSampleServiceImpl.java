/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.salesample.service.impl;

import com.dinglicom.frame.sys.entity.SysOranizagion;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.frame.sys.repository.UserInfoDao;
import com.dinglicom.frame.sys.service.SysOranizagionService;
import com.dinglicom.frame.sys.service.UserInfoService;
import com.dinglicom.frame.sys.util.DateUtil;
import com.dinglicom.reportnum.repository.EveryDayEveryOrgReportDao;
import com.dinglicom.reportnum.repository.ReportSubscribeNumberDao;
import com.dinglicom.salesample.domain.WebProductSaleReq;
import com.dinglicom.salesample.domain.WebProductSaleResp;
import com.dinglicom.salesample.domain.WebRoleuserProductReq;
import com.dinglicom.salesample.domain.WebSaleSampleItem;
import com.dinglicom.salesample.domain.WebSaleSampleReq;
import com.dinglicom.salesample.domain.WebSaleSampleResp;
import com.dinglicom.salesman.domain.ProductSaleSampleRespItem;
import com.dinglicom.salesample.service.WebSaleSampleService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author panzhen
 */
@Component
@Transactional
public class WebSaleSampleServiceImpl implements WebSaleSampleService {

    private final static Logger LOG = LoggerFactory.getLogger(WebSaleSampleServiceImpl.class);
    @PersistenceContext
    private EntityManager em;
    @Resource
    private ReportSubscribeNumberDao reportSubscribeNumberDao;
    @Resource
    private EveryDayEveryOrgReportDao everyDayEveryOrgReportDao;
    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private SysOranizagionService sysOranizagionService;

    @Override
    @Transactional(readOnly = true)
    public WebProductSaleResp queryByAllProductsample(WebProductSaleReq req, UserInfo user) {
        WebProductSaleResp resp = new WebProductSaleResp();
        List<ProductSaleSampleRespItem> data = null;
        Calendar c = Calendar.getInstance();
        if (null != user.getUserType()) {
            switch (user.getUserType()) {
                case UserInfoService.USER_ROLE_ADMINISTRATOR:
                case UserInfoService.USER_ROLE_CHIEF:
                    if (null == req.getType() || "month".equalsIgnoreCase(req.getType())) {
                        data = reportSubscribeNumberDao.queryProductAllByMonth(DateUtil.getYear(c), DateUtil.getMonth(c));
                    } else if ("quarter".equalsIgnoreCase(req.getType())) {
                        data = reportSubscribeNumberDao.queryProducAllByQuater(DateUtil.getYear(c), DateUtil.getQuarter(c));
                    } else {
                        data = reportSubscribeNumberDao.queryProductAllByYear(DateUtil.getYear(c));
                    }
                    break;
                case UserInfoService.USER_ROLE_MANAGER:
                    if (null == req.getType() || "month".equalsIgnoreCase(req.getType())) {
                        data = reportSubscribeNumberDao.queryProductDepByMonth(DateUtil.getYear(c), DateUtil.getMonth(c), user.getOrg().getId());
                    } else if ("quarter".equalsIgnoreCase(req.getType())) {
                        data = reportSubscribeNumberDao.queryProducDepByQuater(DateUtil.getYear(c), DateUtil.getQuarter(c), user.getOrg().getId());
                    } else {
                        data = reportSubscribeNumberDao.queryProductDepByYear(DateUtil.getYear(c), user.getOrg().getId());
                    }
                    break;
                case UserInfoService.USER_ROLE_SALESMAN:
                    if (null == req.getType() || "month".equalsIgnoreCase(req.getType())) {
                        data = reportSubscribeNumberDao.queryProductSalesmanByMonth(DateUtil.getYear(c), DateUtil.getMonth(c), user.getId());
                    } else if ("quarter".equalsIgnoreCase(req.getType())) {
                        data = reportSubscribeNumberDao.queryProducSalesmanByQuater(DateUtil.getYear(c), DateUtil.getQuarter(c), user.getId());
                    } else {
                        data = reportSubscribeNumberDao.queryProductSalesmanByYear(DateUtil.getYear(c), user.getId());
                    }
                    break;
                case UserInfoService.USER_ROLE_DEALER:
                    if (null == req.getType() || "month".equalsIgnoreCase(req.getType())) {
                        data = reportSubscribeNumberDao.queryProductDealerByMonth(DateUtil.getYear(c), DateUtil.getMonth(c), user.getOrg().getId());
                    } else if ("quarter".equalsIgnoreCase(req.getType())) {
                        data = reportSubscribeNumberDao.queryProducDealerByQuater(DateUtil.getYear(c), DateUtil.getQuarter(c), user.getOrg().getId());
                    } else {
                        data = reportSubscribeNumberDao.queryProductDealerByYear(DateUtil.getYear(c), user.getOrg().getId());
                    }
                    break;
                case UserInfoService.USER_ROLE_STATION:
                    if (null == req.getType() || "month".equalsIgnoreCase(req.getType())) {
                        data = reportSubscribeNumberDao.queryProductDepByMonth(DateUtil.getYear(c), DateUtil.getMonth(c), user.getOrg().getId());
                    } else if ("quarter".equalsIgnoreCase(req.getType())) {
                        data = reportSubscribeNumberDao.queryProducDepByQuater(DateUtil.getYear(c), DateUtil.getQuarter(c), user.getOrg().getId());
                    } else {
                        data = reportSubscribeNumberDao.queryProductDepByYear(DateUtil.getYear(c), user.getOrg().getId());
                    }
                    break;
            }
        }
        if (null != data && data.size() > 0) {
            resp.setData(data);
        }
        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public WebSaleSampleResp queryByRolesample(WebSaleSampleReq req, UserInfo quser) {
        Calendar c = Calendar.getInstance();
        WebSaleSampleResp resp = new WebSaleSampleResp();
        if (null != req.getRole()) {
            switch (req.getRole()) {
                case UserInfoService.USER_ROLE_STATION:
                    //奶站
                    if (UserInfoService.USER_ROLE_ADMINISTRATOR.equals(quser.getUserType()) || UserInfoService.USER_ROLE_CHIEF.equals(quser.getUserType())
                            || UserInfoService.USER_ROLE_MANAGER.equals(quser.getUserType()) || UserInfoService.USER_ROLE_SALESMAN.equals(quser.getUserType())
                            || UserInfoService.USER_ROLE_DEALER.equals(quser.getUserType()) || UserInfoService.USER_ROLE_STATION.equals(quser.getUserType())) {
                        resp = queryStation(req, c, quser);
                    }
                    break;
                case UserInfoService.USER_ROLE_DEALER:
                    //经销商
                    if (UserInfoService.USER_ROLE_ADMINISTRATOR.equals(quser.getUserType()) || UserInfoService.USER_ROLE_CHIEF.equals(quser.getUserType())
                            || UserInfoService.USER_ROLE_MANAGER.equals(quser.getUserType()) || UserInfoService.USER_ROLE_SALESMAN.equals(quser.getUserType())
                            || UserInfoService.USER_ROLE_DEALER.equals(quser.getUserType())) {
                        resp = queryDealer(req, c, quser);
                    }
                    break;
                case UserInfoService.USER_ROLE_SALESMAN:
                    //销售人员
                    if (UserInfoService.USER_ROLE_ADMINISTRATOR.equals(quser.getUserType()) || UserInfoService.USER_ROLE_CHIEF.equals(quser.getUserType())
                            || UserInfoService.USER_ROLE_MANAGER.equals(quser.getUserType()) || UserInfoService.USER_ROLE_SALESMAN.equals(quser.getUserType())) {
                        return querySalesman(req, c, quser);
                    }
                    break;
                case UserInfoService.USER_ROLE_MANAGER:
                    //销售主管
                    if (UserInfoService.USER_ROLE_ADMINISTRATOR.equals(quser.getUserType()) || UserInfoService.USER_ROLE_CHIEF.equals(quser.getUserType())
                            || UserInfoService.USER_ROLE_MANAGER.equals(quser.getUserType())) {
                        resp = queryDep(req, c, quser);
                    }
                    break;
                case UserInfoService.USER_ROLE_CHIEF:
                    //销售总监
                    if (UserInfoService.USER_ROLE_ADMINISTRATOR.equals(quser.getUserType()) || UserInfoService.USER_ROLE_CHIEF.equals(quser.getUserType())) {
                        resp = queryCom(req, c);
                    }
                    break;
            }
        }
        return resp;
//        WebSaleSampleResp resp = new WebSaleSampleResp();
//        if (null == req.getRole() || null == req.getType()) {
//            return resp;
//        }
//        Calendar c = Calendar.getInstance();
//        resp = queryByAdminOrCom(req, c, quser);
//
//        if (null != quser.getUserType()) {
//            switch (quser.getUserType()) {
//                case UserInfoService.USER_ROLE_ADMINISTRATOR:
//                case UserInfoService.USER_ROLE_CHIEF:
////                    resp = queryByAdminOrCom(req, quser, c);
//                    resp = queryByAdminOrCom(req, c, quser);
//                    break;
//                case UserInfoService.USER_ROLE_MANAGER:
//                    resp = queryByDep(req, quser, c);
//                    break;
//                case UserInfoService.USER_ROLE_SALESMAN:
//                    resp = queryBySalesman(req, quser, c);
//                    break;
//                case UserInfoService.USER_ROLE_DEALER:
//                    resp = queryByDarler(req, quser, c);
//                    break;
//                case UserInfoService.USER_ROLE_STATION:
//                    resp = queryByStation(req, quser, c);
//                    break;
//            }
//        }
//        return resp;
    }

    private String getSalesmanHql(WebSaleSampleReq req, boolean count, UserInfo admin) {
        StringBuilder hqlSb = new StringBuilder("select ");
        if (count) {
            hqlSb.append("count(a.id)");
        } else {
            hqlSb.append("new com.dinglicom.salesample.domain.WebSaleSampleItem(a.org.userinfo.id, a.org.userinfo.realname, a.org.userinfo.phone, sum(totalnum))");
        }
        hqlSb.append(" from EveryDayEveryOrgReport a where a.year=:year");
        if ("month".equalsIgnoreCase(req.getType())) {
            hqlSb.append(" and a.month=:month");
        } else if ("quarter".equalsIgnoreCase(req.getType())) {
            hqlSb.append(" and a.quarter=:quarter");
        }
        if (null != admin.getUserType()) {
            switch (admin.getUserType()) {
                case UserInfoService.USER_ROLE_MANAGER:
                    hqlSb.append(" and a.org.parent.id=:depId");
                    break;
                case UserInfoService.USER_ROLE_SALESMAN:
                    hqlSb.append(" and a.org.userinfo.id=:salesId");
                    break;
            }
        }
        if (null != req.getQuery()) {
            hqlSb.append(" and (a.org.userinfo.realname like :qry or a.org.userinfo.phone like :qry)");
        }
        hqlSb.append(" group by a.org.userinfo.id, a.org.userinfo.realname, a.org.userinfo.phone");
        if (!count) {
            hqlSb.append(" order by sum(totalnum) desc");
        }
        return hqlSb.toString();
    }

    private String getStationHql(WebSaleSampleReq req, boolean count, UserInfo admin) {
        StringBuilder hqlSb = new StringBuilder("select ");
        if (count) {
            hqlSb.append("count(a.id)");
        } else {
            hqlSb.append("new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone)");
        }
        hqlSb.append(" from EveryDayEveryOrgReport a where a.year=:year");
        if ("month".equalsIgnoreCase(req.getType())) {
            hqlSb.append(" and a.month=:month");
        } else if ("quarter".equalsIgnoreCase(req.getType())) {
            hqlSb.append(" and a.quarter=:quarter");
        }
        if (null != admin.getUserType()) {
            switch (admin.getUserType()) {
                case UserInfoService.USER_ROLE_MANAGER:
                    hqlSb.append(" and a.org.parent.id=:depId");
                    break;
                case UserInfoService.USER_ROLE_SALESMAN:
                    hqlSb.append(" and a.org.userinfo.id=:salesId");
                    break;
                case UserInfoService.USER_ROLE_DEALER:
                    hqlSb.append(" and a.org.dealer.id=:dealerId");
                    break;
                case UserInfoService.USER_ROLE_STATION:
                    hqlSb.append(" and a.org.id=:stationId");
                    break;
            }
        }
        if (null != req.getQuery()) {
            hqlSb.append(" and (a.org.name like :qry or a.org.responsible_phone like :qry)");
        }
        hqlSb.append(" group by a.org.id, a.org.name, a.org.responsible_man, a.org.responsible_phone ");
        if (!count) {
            hqlSb.append(" order by sum(totalnum) desc");
        }
        return hqlSb.toString();
    }

    private String getDealerHql(WebSaleSampleReq req, boolean count, UserInfo admin) {
        StringBuilder hqlSb = new StringBuilder("select ");
        if (count) {
            hqlSb.append("count(a.id)");
        } else {
            hqlSb.append("new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.dealer.id, a.org.dealer.name, a.org.dealer.responsible_man, a.org.dealer.responsible_phone)");
        }
        hqlSb.append(" from EveryDayEveryOrgReport a where a.year=:year");
        if ("month".equalsIgnoreCase(req.getType())) {
            hqlSb.append(" and a.month=:month");
        } else if ("quarter".equalsIgnoreCase(req.getType())) {
            hqlSb.append(" and a.quarter=:quarter");
        }
        if (null != admin.getUserType()) {
            switch (admin.getUserType()) {
                case UserInfoService.USER_ROLE_MANAGER:
                    hqlSb.append(" and a.org.parent.id=:depId");
                    break;
                case UserInfoService.USER_ROLE_SALESMAN:
                    hqlSb.append(" and a.org.userinfo.id=:salesId");
                    break;
                case UserInfoService.USER_ROLE_DEALER:
                    hqlSb.append(" and a.org.dealer.id=:dealerId");
                    break;
            }
        }
        if (null != req.getQuery()) {
            hqlSb.append(" and (a.org.dealer.name like :qry or a.org.dealer.responsible_phone like :qry)");
        }
        hqlSb.append(" group by a.org.dealer.id, a.org.dealer.name, a.org.dealer.responsible_man, a.org.dealer.responsible_phone ");
        if (!count) {
            hqlSb.append(" order by sum(totalnum) desc");
        }
        return hqlSb.toString();
    }

    private String getDepHql(WebSaleSampleReq req, boolean count, UserInfo admin) {
        StringBuilder hqlSb = new StringBuilder("select ");
        if (count) {
            hqlSb.append("count(a.id)");
        } else {
            hqlSb.append("new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum), a.org.userinfo.org.id, a.org.userinfo.org.name, a.org.userinfo.org.responsible_man, a.org.userinfo.org.responsible_phone)");
        }
        hqlSb.append(" from EveryDayEveryOrgReport a where a.year=:year");
        if ("month".equalsIgnoreCase(req.getType())) {
            hqlSb.append(" and a.month=:month");
        } else if ("quarter".equalsIgnoreCase(req.getType())) {
            hqlSb.append(" and a.quarter=:quarter");
        }
        if (UserInfoService.USER_ROLE_MANAGER.equals(admin.getUserType())) {
            hqlSb.append(" and a.org.parent.id=:depId");
        }
        if (null != req.getQuery()) {
            hqlSb.append(" and (a.org.userinfo.org.name like :qry or a.org.userinfo.org.responsible_phone like :qry)");
        }
        hqlSb.append(" group by a.org.userinfo.org.id, a.org.userinfo.org.name, a.org.userinfo.org.responsible_man, a.org.userinfo.org.responsible_phone ");
        if (!count) {
            hqlSb.append(" order by sum(totalnum) desc");
        }
        return hqlSb.toString();
    }

    private String getComHql(WebSaleSampleReq req) {
        StringBuilder hqlSb = new StringBuilder("select new com.dinglicom.salesample.domain.WebSaleSampleItem(sum(totalnum))");
        hqlSb.append(" from EveryDayEveryOrgReport a where a.year=:year");
        if ("month".equalsIgnoreCase(req.getType())) {
            hqlSb.append(" and a.month=:month");
        } else if ("quarter".equalsIgnoreCase(req.getType())) {
            hqlSb.append(" and a.quarter=:quarter");
        }
        return hqlSb.toString();
    }

    private void setQuery(Query q, WebSaleSampleReq req, Calendar c) {
        q.setParameter("year", DateUtil.getYear(c));
        if ("month".equalsIgnoreCase(req.getType())) {
            q.setParameter("month", DateUtil.getMonth(c));
        } else if ("quarter".equalsIgnoreCase(req.getType())) {
            q.setParameter("quarter", DateUtil.getQuarter(c));
        }
        if (null != req.getQuery()) {
            q.setParameter("qry", "%" + req.getQuery() + "%");
        }
    }

    private WebSaleSampleResp querySalesman(WebSaleSampleReq req, Calendar c, UserInfo admin) {
        WebSaleSampleResp resp = new WebSaleSampleResp();
        Page<WebSaleSampleItem> page;
        Query qc = em.createQuery(getSalesmanHql(req, true, admin));
        if (null != admin.getUserType()) {
            switch (admin.getUserType()) {
                case UserInfoService.USER_ROLE_MANAGER:
                    qc.setParameter("depId", admin.getOrg().getId());
                    break;
                case UserInfoService.USER_ROLE_SALESMAN:
                    qc.setParameter("salesId", admin.getId());
                    break;
            }
        }
        setQuery(qc, req, c);
        long count = (Long)qc.getSingleResult();
        long tp = (count + req.getNum() - 1) / req.getNum();
//        System.out.println("Total element:" + count + " pages:" + tp);
        if (req.getPage() <= tp) {
            Query q = em.createQuery(getSalesmanHql(req, false, admin));
            if (null != admin.getUserType()) {
                switch (admin.getUserType()) {
                    case UserInfoService.USER_ROLE_MANAGER:
                        q.setParameter("depId", admin.getOrg().getId());
                        break;
                    case UserInfoService.USER_ROLE_SALESMAN:
                        q.setParameter("salesId", admin.getId());
                        break;
                }
            }
            setQuery(q, req, c);
            q.setFirstResult((req.getPage() - 1) * req.getNum());
            q.setMaxResults(req.getNum());
            page = new PageImpl<WebSaleSampleItem>(q.getResultList(), buildPageRequest(req.getPage(), req.getNum()), count);
        } else {
            page = new PageImpl<WebSaleSampleItem>(new ArrayList<WebSaleSampleItem>(), buildPageRequest(req.getPage(), req.getNum()), count);
        }
        processResult(page, resp, req, UserInfoService.USER_ROLE_SALESMAN);
        return resp;
    }

    private WebSaleSampleResp queryStation(WebSaleSampleReq req, Calendar c, UserInfo admin) {
        WebSaleSampleResp resp = new WebSaleSampleResp();
        Page<WebSaleSampleItem> page;
        Query qc = em.createQuery(getStationHql(req, true, admin));
        if (null != admin.getUserType()) {
            switch (admin.getUserType()) {
                case UserInfoService.USER_ROLE_MANAGER:
                    qc.setParameter("depId", admin.getOrg().getId());
                    break;
                case UserInfoService.USER_ROLE_SALESMAN:
                    qc.setParameter("salesId", admin.getId());
                    break;
                case UserInfoService.USER_ROLE_DEALER:
                    qc.setParameter("dealerId", admin.getOrg().getId());
                    break;
                case UserInfoService.USER_ROLE_STATION:
                    qc.setParameter("stationId", admin.getOrg().getId());
                    break;
            }
        }
        setQuery(qc, req, c);
        long count = (Long)qc.getSingleResult();
        long tp = (count + req.getNum() - 1) / req.getNum();
//        System.out.println("Total element:" + count + " pages:" + tp);
        if (req.getPage() <= tp) {
            Query q = em.createQuery(getStationHql(req, false, admin));
            if (null != admin.getUserType()) {
                switch (admin.getUserType()) {
                    case UserInfoService.USER_ROLE_MANAGER:
                        q.setParameter("depId", admin.getOrg().getId());
                        break;
                    case UserInfoService.USER_ROLE_SALESMAN:
                        q.setParameter("salesId", admin.getId());
                        break;
                    case UserInfoService.USER_ROLE_DEALER:
                        q.setParameter("dealerId", admin.getOrg().getId());
                        break;
                    case UserInfoService.USER_ROLE_STATION:
                        q.setParameter("stationId", admin.getOrg().getId());
                        break;
                }
            }
            setQuery(q, req, c);
            q.setFirstResult((req.getPage() - 1) * req.getNum());
            q.setMaxResults(req.getNum());
            page = new PageImpl<WebSaleSampleItem>(q.getResultList(), buildPageRequest(req.getPage(), req.getNum()), count);
        } else {
            page = new PageImpl<WebSaleSampleItem>(new ArrayList<WebSaleSampleItem>(), buildPageRequest(req.getPage(), req.getNum()), count);
        }
        processResult(page, resp, req, UserInfoService.USER_ROLE_STATION);
        return resp;
    }

    private WebSaleSampleResp queryDealer(WebSaleSampleReq req, Calendar c, UserInfo admin) {
        WebSaleSampleResp resp = new WebSaleSampleResp();
        Page<WebSaleSampleItem> page;
        Query qc = em.createQuery(getDealerHql(req, true, admin));
        if (null != admin.getUserType()) {
            switch (admin.getUserType()) {
                case UserInfoService.USER_ROLE_MANAGER:
                    qc.setParameter("depId", admin.getOrg().getId());
                    break;
                case UserInfoService.USER_ROLE_SALESMAN:
                    qc.setParameter("salesId", admin.getId());
                    break;
                case UserInfoService.USER_ROLE_DEALER:
                    qc.setParameter("dealerId", admin.getOrg().getId());
                    break;
            }
        }
        setQuery(qc, req, c);
        long count = (Long)qc.getSingleResult();
        long tp = (count + req.getNum() - 1) / req.getNum();
//        System.out.println("Total element:" + count + " pages:" + tp);
        if (req.getPage() <= tp) {
            Query q = em.createQuery(getDealerHql(req, false, admin));
            if (null != admin.getUserType()) {
                switch (admin.getUserType()) {
                    case UserInfoService.USER_ROLE_MANAGER:
                        q.setParameter("depId", admin.getOrg().getId());
                        break;
                    case UserInfoService.USER_ROLE_SALESMAN:
                        q.setParameter("salesId", admin.getId());
                        break;
                    case UserInfoService.USER_ROLE_DEALER:
                        q.setParameter("dealerId", admin.getOrg().getId());
                        break;
                }
            }
            setQuery(q, req, c);
            q.setFirstResult((req.getPage() - 1) * req.getNum());
            q.setMaxResults(req.getNum());
            page = new PageImpl<WebSaleSampleItem>(q.getResultList(), buildPageRequest(req.getPage(), req.getNum()), count);
        } else {
            page = new PageImpl<WebSaleSampleItem>(new ArrayList<WebSaleSampleItem>(), buildPageRequest(req.getPage(), req.getNum()), count);
        }
        processResult(page, resp, req, UserInfoService.USER_ROLE_DEALER);
        return resp;
    }

    private WebSaleSampleResp queryDep(WebSaleSampleReq req, Calendar c, UserInfo admin) {
        WebSaleSampleResp resp = new WebSaleSampleResp();
        Page<WebSaleSampleItem> page;
        Query qc = em.createQuery(getDepHql(req, true, admin));
        setQuery(qc, req, c);
        if (UserInfoService.USER_ROLE_MANAGER.equals(admin.getUserType())) {
            qc.setParameter("depId", admin.getOrg().getId());
        }
        long count = (Long)qc.getSingleResult();
        long tp = (count + req.getNum() - 1) / req.getNum();
//        System.out.println("Total element:" + count + " pages:" + tp);
        if (req.getPage() <= tp) {
            Query q = em.createQuery(getDepHql(req, false, admin));
            if (UserInfoService.USER_ROLE_MANAGER.equals(admin.getUserType())) {
                q.setParameter("depId", admin.getOrg().getId());
            }
            setQuery(q, req, c);
            q.setFirstResult((req.getPage() - 1) * req.getNum());
            q.setMaxResults(req.getNum());
            page = new PageImpl<WebSaleSampleItem>(q.getResultList(), buildPageRequest(req.getPage(), req.getNum()), count);
        } else {
            page = new PageImpl<WebSaleSampleItem>(new ArrayList<WebSaleSampleItem>(), buildPageRequest(req.getPage(), req.getNum()), count);
        }
        processResult(page, resp, req, UserInfoService.USER_ROLE_MANAGER);
        return resp;
    }

    private WebSaleSampleResp queryCom(WebSaleSampleReq req, Calendar c) {
        WebSaleSampleResp resp = new WebSaleSampleResp();
        Query q = em.createQuery(getComHql(req));
        setQuery(q, req, c);
        Page<WebSaleSampleItem> page = new PageImpl<WebSaleSampleItem>(q.getResultList(), buildPageRequest(req.getPage(), req.getNum()), 1);

        //补充销售总监的信息
        if (null != page.getContent() && page.getContent().size() > 0) {
            List<WebSaleSampleItem> list = userInfoDao.findUserType(UserInfoService.USER_ROLE_CHIEF, Boolean.FALSE);
            WebSaleSampleItem user = null;
            if (null != list && list.size() > 0) {
                user = list.get(0);
            }
            int i = 1;
            for (WebSaleSampleItem item : page.getContent()) {
                item.setRank((req.getPage() - 1) * req.getNum() + i);
                if (null != user) {
                    item.setUid(user.getUid());
                    item.setManager(user.getManager());
                    item.setTel(user.getTel());
                }
            }
            resp.setData(page.getContent());
            resp.setTotal_num(page.getTotalElements());
            return resp;
        }
        return resp;
    }

//    private WebSaleSampleResp queryByAdminOrCom(WebSaleSampleReq req, Calendar c, UserInfo admin) {
//        WebSaleSampleResp resp = new WebSaleSampleResp();
//        if (UserInfoService.USER_ROLE_STATION.equalsIgnoreCase(req.getRole())) {//奶站
//            if (UserInfoService.USER_ROLE_ADMINISTRATOR.equals(admin.getUserType()) || UserInfoService.USER_ROLE_CHIEF.equals(admin.getUserType())
//                    || UserInfoService.USER_ROLE_MANAGER.equals(admin.getUserType()) || UserInfoService.USER_ROLE_SALESMAN.equals(admin.getUserType())
//                    || UserInfoService.USER_ROLE_DEALER.equals(admin.getUserType()) || UserInfoService.USER_ROLE_STATION.equals(admin.getUserType())) {
//                return queryStation(req, c, admin);
//            }
//        } else if (UserInfoService.USER_ROLE_DEALER.equalsIgnoreCase(req.getRole())) {//经销商
//            if (UserInfoService.USER_ROLE_ADMINISTRATOR.equals(admin.getUserType()) || UserInfoService.USER_ROLE_CHIEF.equals(admin.getUserType())
//                    || UserInfoService.USER_ROLE_MANAGER.equals(admin.getUserType()) || UserInfoService.USER_ROLE_SALESMAN.equals(admin.getUserType())
//                    || UserInfoService.USER_ROLE_DEALER.equals(admin.getUserType())) {
//                return queryDealer(req, c, admin);
//            }
//        } else if (UserInfoService.USER_ROLE_SALESMAN.equalsIgnoreCase(req.getRole())) { //销售人员
//            if (UserInfoService.USER_ROLE_ADMINISTRATOR.equals(admin.getUserType()) || UserInfoService.USER_ROLE_CHIEF.equals(admin.getUserType())
//                    || UserInfoService.USER_ROLE_MANAGER.equals(admin.getUserType()) || UserInfoService.USER_ROLE_SALESMAN.equals(admin.getUserType())) {
//                return querySalesman(req, c, admin);
//            }
//        } else if (UserInfoService.USER_ROLE_MANAGER.equalsIgnoreCase(req.getRole())) {//销售主管
//            if (UserInfoService.USER_ROLE_ADMINISTRATOR.equals(admin.getUserType()) || UserInfoService.USER_ROLE_CHIEF.equals(admin.getUserType())
//                    || UserInfoService.USER_ROLE_MANAGER.equals(admin.getUserType())) {
//                return queryDep(req, c, admin);
//            }
//        } else if (UserInfoService.USER_ROLE_CHIEF.equalsIgnoreCase(req.getRole())) {//销售总监
//            if (UserInfoService.USER_ROLE_ADMINISTRATOR.equals(admin.getUserType()) || UserInfoService.USER_ROLE_CHIEF.equals(admin.getUserType())) {
//                return queryCom(req, c);
//            }
//        }
//        return resp;
//    }

//    private WebSaleSampleResp queryByAdminOrCom(WebSaleSampleReq req, UserInfo quser, Calendar c) {
//        WebSaleSampleResp resp = new WebSaleSampleResp();
//        Page<WebSaleSampleItem> page = null;
//        if (UserInfoService.USER_ROLE_SALESMAN.equalsIgnoreCase(req.getRole())) {//销售人员
//            if ("month".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryAllSalemanByAdminMonth(buildPageRequest(req.getPage(), req.getNum()), DateUtil.getYear(c), DateUtil.getMonth(c));
//            } else if ("quarter".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryAllSalemanByAdminQuater(buildPageRequest(req.getPage(), req.getNum()), DateUtil.getYear(c), DateUtil.getQuarter(c));
//            } else {
//                page = everyDayEveryOrgReportDao.queryAllSalemanByAdminYear(buildPageRequest(req.getPage(), req.getNum()), DateUtil.getYear(c));
//            }
//
//            processResult(page, resp, req, UserInfoService.USER_ROLE_SALESMAN);
//            return resp;
//        } else if (UserInfoService.USER_ROLE_STATION.equalsIgnoreCase(req.getRole())) {//奶站
//            if ("month".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryAllStationByAdminMonth(buildPageRequest(req.getPage(), req.getNum()), DateUtil.getYear(c), DateUtil.getMonth(c));
//            } else if ("quarter".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryAllStationByAdminQuater(buildPageRequest(req.getPage(), req.getNum()), DateUtil.getYear(c), DateUtil.getQuarter(c));
//            } else {
//                page = everyDayEveryOrgReportDao.queryAllStationByAdminYear(buildPageRequest(req.getPage(), req.getNum()), DateUtil.getYear(c));
//            }
//            if (null != page && null != page.getContent() && page.getContent().size() > 0) {
//                processResult(page, resp, req, UserInfoService.USER_ROLE_STATION);
//                return resp;
//            }
//        } else if (UserInfoService.USER_ROLE_DEALER.equalsIgnoreCase(req.getRole())) {//经销商
//            if ("month".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryAllDarlerByAdminMonth(buildPageRequest(req.getPage(), req.getNum()), DateUtil.getYear(c), DateUtil.getMonth(c));
//            } else if ("quarter".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryAllDarlerByAdminQuater(buildPageRequest(req.getPage(), req.getNum()), DateUtil.getYear(c), DateUtil.getQuarter(c));
//            } else {
//                page = everyDayEveryOrgReportDao.queryAllDarlerByAdminYear(buildPageRequest(req.getPage(), req.getNum()), DateUtil.getYear(c));
//            }
//            if (null != page && null != page.getContent() && page.getContent().size() > 0) {
//                processResult(page, resp, req, UserInfoService.USER_ROLE_DEALER);
//                return resp;
//            }
//        } else if (UserInfoService.USER_ROLE_MANAGER.equalsIgnoreCase(req.getRole())) {//销售主管
//            if ("month".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryAllDepByAdminMonth(buildPageRequest(req.getPage(), req.getNum()), DateUtil.getYear(c), DateUtil.getMonth(c));
//            } else if ("quarter".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryAllDepByAdminQuater(buildPageRequest(req.getPage(), req.getNum()), DateUtil.getYear(c), DateUtil.getQuarter(c));
//            } else {
//                page = everyDayEveryOrgReportDao.queryAllDepByAdminYear(buildPageRequest(req.getPage(), req.getNum()), DateUtil.getYear(c));
//            }
//            if (null != page && null != page.getContent() && page.getContent().size() > 0) {
//                processResult(page, resp, req, UserInfoService.USER_ROLE_MANAGER);
//                return resp;
//            }
//        } else if (UserInfoService.USER_ROLE_CHIEF.equalsIgnoreCase(req.getRole())) {//销售总监
//            if ("month".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryAllComByAdminMonth(buildPageRequest(req.getPage(), req.getNum()), DateUtil.getYear(c), DateUtil.getMonth(c));
//            } else if ("quarter".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryAllComByAdminQuater(buildPageRequest(req.getPage(), req.getNum()), DateUtil.getYear(c), DateUtil.getQuarter(c));
//            } else {
//                page = everyDayEveryOrgReportDao.queryAllComByAdminYear(buildPageRequest(req.getPage(), req.getNum()), DateUtil.getYear(c));
//            }
//            //补充销售总监的信息
//            if (null != page && null != page.getContent() && page.getContent().size() > 0) {
//                List<WebSaleSampleItem> list = userInfoDao.findUserType(UserInfoService.USER_ROLE_CHIEF, Boolean.FALSE);
//                WebSaleSampleItem user = null;
//                if (null != list && list.size() > 0) {
//                    user = list.get(0);
//                }
//                int i = 1;
//                for (WebSaleSampleItem item : page.getContent()) {
//                    item.setRank((req.getPage() - 1) * req.getNum() + i);
//                    if (null != user) {
//                        item.setUid(user.getUid());
//                        item.setManager(user.getManager());
//                        item.setTel(user.getTel());
//                    }
//                }
//                resp.setData(page.getContent());
//                resp.setTotal_num(page.getTotalElements());
//                return resp;
//            }
//        }
//        return resp;
//    }

//    private WebSaleSampleResp queryByDep(WebSaleSampleReq req, UserInfo quser, Calendar c) {
//        WebSaleSampleResp resp = new WebSaleSampleResp();
//        if (null == quser.getOrg() || null == quser.getOrg().getId()) {
//            return resp;
//        }
//        Page<WebSaleSampleItem> page;
//        if (UserInfoService.USER_ROLE_SALESMAN.equalsIgnoreCase(req.getRole())) {//销售人员
//            if ("month".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryAllSalemanByDepMonth(buildPageRequest(req.getPage(), req.getNum()), quser.getOrg().getId(), DateUtil.getYear(c), DateUtil.getMonth(c));
//            } else if ("quarter".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryAllSalemanByDepQuater(buildPageRequest(req.getPage(), req.getNum()), quser.getOrg().getId(), DateUtil.getYear(c), DateUtil.getQuarter(c));
//            } else {
//                page = everyDayEveryOrgReportDao.queryAllSalemanByDepYear(buildPageRequest(req.getPage(), req.getNum()), quser.getOrg().getId(), DateUtil.getYear(c));
//            }
//
//            processResult(page, resp, req, UserInfoService.USER_ROLE_SALESMAN);
//            return resp;
//        } else if (UserInfoService.USER_ROLE_STATION.equalsIgnoreCase(req.getRole())) {//奶站
//            if ("month".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryAllStationByDepMonth(buildPageRequest(req.getPage(), req.getNum()), quser.getOrg().getId(), DateUtil.getYear(c), DateUtil.getMonth(c));
//            } else if ("quarter".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryAllStationByDepQuater(buildPageRequest(req.getPage(), req.getNum()), quser.getOrg().getId(), DateUtil.getYear(c), DateUtil.getQuarter(c));
//            } else {
//                page = everyDayEveryOrgReportDao.queryAllStationByDepYear(buildPageRequest(req.getPage(), req.getNum()), quser.getOrg().getId(), DateUtil.getYear(c));
//            }
//            if (null != page && null != page.getContent() && page.getContent().size() > 0) {
//                processResult(page, resp, req, UserInfoService.USER_ROLE_STATION);
//                return resp;
//            }
//        } else if (UserInfoService.USER_ROLE_DEALER.equalsIgnoreCase(req.getRole())) {//经销商
//            if ("month".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryAllDarlerByDepMonth(buildPageRequest(req.getPage(), req.getNum()), quser.getOrg().getId(), DateUtil.getYear(c), DateUtil.getMonth(c));
//            } else if ("quarter".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryAllDarlerByDepQuater(buildPageRequest(req.getPage(), req.getNum()), quser.getOrg().getId(), DateUtil.getYear(c), DateUtil.getQuarter(c));
//            } else {
//                page = everyDayEveryOrgReportDao.queryAllDarlerByDepYear(buildPageRequest(req.getPage(), req.getNum()), quser.getOrg().getId(), DateUtil.getYear(c));
//            }
//            if (null != page && null != page.getContent() && page.getContent().size() > 0) {
//                processResult(page, resp, req, UserInfoService.USER_ROLE_DEALER);
//                return resp;
//            }
//        } else if (UserInfoService.USER_ROLE_MANAGER.equalsIgnoreCase(req.getRole())) {//销售主管
//            if ("month".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryAllDepByDepMonth(buildPageRequest(req.getPage(), req.getNum()), quser.getOrg().getId(), DateUtil.getYear(c), DateUtil.getMonth(c));
//            } else if ("quarter".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryAllDepByDepQuater(buildPageRequest(req.getPage(), req.getNum()), quser.getOrg().getId(), DateUtil.getYear(c), DateUtil.getQuarter(c));
//            } else {
//                page = everyDayEveryOrgReportDao.queryAllDepByDepYear(buildPageRequest(req.getPage(), req.getNum()), quser.getOrg().getId(), DateUtil.getYear(c));
//            }
//            if (null != page && null != page.getContent() && page.getContent().size() > 0) {
//                processResult(page, resp, req, UserInfoService.USER_ROLE_MANAGER);
//                return resp;
//            }
//        }
//        return resp;
//    }
//
//    private WebSaleSampleResp queryBySalesman(WebSaleSampleReq req, UserInfo quser, Calendar c) {
//        WebSaleSampleResp resp = new WebSaleSampleResp();
//
//        Page<WebSaleSampleItem> page;
//        if (UserInfoService.USER_ROLE_SALESMAN.equalsIgnoreCase(req.getRole())) {//销售人员
//            if ("month".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryAllSalemanBySalesmanMonth(buildPageRequest(req.getPage(), req.getNum()), quser.getId(), DateUtil.getYear(c), DateUtil.getMonth(c));
//            } else if ("quarter".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryAllSalemanBySalesmanQuater(buildPageRequest(req.getPage(), req.getNum()), quser.getId(), DateUtil.getYear(c), DateUtil.getQuarter(c));
//            } else {
//                page = everyDayEveryOrgReportDao.queryAllSalemanBySalesmanYear(buildPageRequest(req.getPage(), req.getNum()), quser.getId(), DateUtil.getYear(c));
//            }
//
//            processResult(page, resp, req, UserInfoService.USER_ROLE_SALESMAN);
//            return resp;
//        } else if (UserInfoService.USER_ROLE_STATION.equalsIgnoreCase(req.getRole())) {//奶站
////            if(null == quser.getOrg() || null == quser.getOrg().getUserinfo()) return resp;
//            if ("month".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryAllStationBySalesmanMonth(buildPageRequest(req.getPage(), req.getNum()), quser.getId(), DateUtil.getYear(c), DateUtil.getMonth(c));
//            } else if ("quarter".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryAllStationBySalesmanQuater(buildPageRequest(req.getPage(), req.getNum()), quser.getId(), DateUtil.getYear(c), DateUtil.getQuarter(c));
//            } else {
//                page = everyDayEveryOrgReportDao.queryAllStationBySalesmanYear(buildPageRequest(req.getPage(), req.getNum()), quser.getId(), DateUtil.getYear(c));
//            }
//            if (null != page && null != page.getContent() && page.getContent().size() > 0) {
//                processResult(page, resp, req, UserInfoService.USER_ROLE_STATION);
//                return resp;
//            }
//        } else if (UserInfoService.USER_ROLE_DEALER.equalsIgnoreCase(req.getRole())) {//经销商
//            if ("month".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryAllDarlerBySalesmanMonth(buildPageRequest(req.getPage(), req.getNum()), quser.getId(), DateUtil.getYear(c), DateUtil.getMonth(c));
//            } else if ("quarter".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryAllDarlerBySalesmanQuater(buildPageRequest(req.getPage(), req.getNum()), quser.getId(), DateUtil.getYear(c), DateUtil.getQuarter(c));
//            } else {
//                page = everyDayEveryOrgReportDao.queryAllDarlerBySalesmanYear(buildPageRequest(req.getPage(), req.getNum()), quser.getId(), DateUtil.getYear(c));
//            }
//            if (null != page && null != page.getContent() && page.getContent().size() > 0) {
//                processResult(page, resp, req, UserInfoService.USER_ROLE_DEALER);
//                return resp;
//            }
//        }
//        return resp;
//    }
//
//    private WebSaleSampleResp queryByDarler(WebSaleSampleReq req, UserInfo quser, Calendar c) {
//        WebSaleSampleResp resp = new WebSaleSampleResp();
//        if (null == quser.getOrg() || null == quser.getOrg().getDealer()) {
//            return resp;
//        }
//        Page<WebSaleSampleItem> page;
//        if (UserInfoService.USER_ROLE_STATION.equalsIgnoreCase(req.getRole())) {//奶站
//            if ("month".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryAllStationByDarlerMonth(buildPageRequest(req.getPage(), req.getNum()), quser.getOrg().getDealer().getId(), DateUtil.getYear(c), DateUtil.getMonth(c));
//            } else if ("quarter".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryAllStationByDarlerQuater(buildPageRequest(req.getPage(), req.getNum()), quser.getOrg().getDealer().getId(), DateUtil.getYear(c), DateUtil.getQuarter(c));
//            } else {
//                page = everyDayEveryOrgReportDao.queryAllStationByDarlerYear(buildPageRequest(req.getPage(), req.getNum()), quser.getOrg().getDealer().getId(), DateUtil.getYear(c));
//            }
//            if (null != page && null != page.getContent() && page.getContent().size() > 0) {
//                processResult(page, resp, req, UserInfoService.USER_ROLE_STATION);
//                return resp;
//            }
//        } else if (UserInfoService.USER_ROLE_DEALER.equalsIgnoreCase(req.getRole())) {//经销商
//            if ("month".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryAllDarlerBySalesmanMonth(buildPageRequest(req.getPage(), req.getNum()), quser.getOrg().getDealer().getId(), DateUtil.getYear(c), DateUtil.getMonth(c));
//            } else if ("quarter".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryAllDarlerBySalesmanQuater(buildPageRequest(req.getPage(), req.getNum()), quser.getOrg().getDealer().getId(), DateUtil.getYear(c), DateUtil.getQuarter(c));
//            } else {
//                page = everyDayEveryOrgReportDao.queryAllDarlerBySalesmanYear(buildPageRequest(req.getPage(), req.getNum()), quser.getOrg().getDealer().getId(), DateUtil.getYear(c));
//            }
//            if (null != page && null != page.getContent() && page.getContent().size() > 0) {
//                processResult(page, resp, req, UserInfoService.USER_ROLE_DEALER);
//                return resp;
//            }
//        }
//        return resp;
//    }
//
//    private WebSaleSampleResp queryByStation(WebSaleSampleReq req, UserInfo quser, Calendar c) {
//        WebSaleSampleResp resp = new WebSaleSampleResp();
//        if (null == quser.getOrg()) {
//            return resp;
//        }
//        Page<WebSaleSampleItem> page;
//        if (UserInfoService.USER_ROLE_STATION.equalsIgnoreCase(req.getRole())) {//奶站
//            if ("month".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryAllStationByStationMonth(buildPageRequest(req.getPage(), req.getNum()), quser.getOrg().getId(), DateUtil.getYear(c), DateUtil.getMonth(c));
//            } else if ("quarter".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryAllStationByStationQuater(buildPageRequest(req.getPage(), req.getNum()), quser.getOrg().getId(), DateUtil.getYear(c), DateUtil.getQuarter(c));
//            } else {
//                page = everyDayEveryOrgReportDao.queryAllStationByStationYear(buildPageRequest(req.getPage(), req.getNum()), quser.getOrg().getId(), DateUtil.getYear(c));
//            }
//            if (null != page && null != page.getContent() && page.getContent().size() > 0) {
//                processResult(page, resp, req, UserInfoService.USER_ROLE_STATION);
//                return resp;
//            }
//        }
//        return resp;
//    }

    private void processResult(Page<WebSaleSampleItem> page, WebSaleSampleResp resp, WebSaleSampleReq req, String role) {
//        Map<Long, WebSaleSampleItem> orgSaleCount = new HashMap<Long, WebSaleSampleItem>();
//        List<Long> orgs = new ArrayList<Long>();
        int i = 0 + (req.getPage() - 1) * req.getNum();
        for (WebSaleSampleItem item : page.getContent()) {
            i++;
            item.setRank(i);
//            orgs.add(item.getUid());
        }
//        List<WebSaleSampleItem> list = userInfoDao.findUserByOrgIds(orgs);
//        for (WebSaleSampleItem item : list) {
//            orgSaleCount.put(item.getAmount(), item);
//        }
//        for (WebSaleSampleItem item : page.getContent()) {
//            WebSaleSampleItem user = orgSaleCount.get(item.getUid());
//            if (null != user) {
//                item.setUid(user.getUid());
////                item.setName(user.getManager());
////                item.setManager(user.getManager());
////                item.setTel(user.getTel());
//            }
//        }
        resp.setData(page.getContent());
        resp.setTotal_num(page.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public WebProductSaleResp queryByRoledetail(WebRoleuserProductReq req) {
        SysOranizagion org = sysOranizagionService.read(req.getUid());
        if (null == org) {
            UserInfo admin = userInfoDao.findOne(req.getUid());
            if (null != admin) {
                org = admin.getOrg();
            }
        }

        if (null == org || null == req.getType()) {
            throw new RuntimeException("Current user not org info or not type info.");
        }
        List<ProductSaleSampleRespItem> data = null;
        Calendar c = Calendar.getInstance();
        WebProductSaleResp resp = new WebProductSaleResp();
        if (SysOranizagionService.ORG_TYPE_NZH.equalsIgnoreCase(org.getType())) {//奶站
            LOG.info("Query station  product sale count.");
            if ("month".equalsIgnoreCase(req.getType())) {
                data = reportSubscribeNumberDao.queryOrgDetailsSampleByMonth(org.getId(), DateUtil.getYear(c), DateUtil.getMonth(c));
            } else if ("quarter".equalsIgnoreCase(req.getType())) {
                data = reportSubscribeNumberDao.queryOrgDetailsSampleByQuarter(org.getId(), DateUtil.getYear(c), DateUtil.getQuarter(c));
            } else {
                data = reportSubscribeNumberDao.queryOrgDetailsSampleByYear(org.getId(), DateUtil.getYear(c));
            }
        } else if (SysOranizagionService.ORG_TYPE_DEALER.equalsIgnoreCase(org.getType())) {//经销商
            LOG.info("Query dealer product sale count.");
            if ("month".equalsIgnoreCase(req.getType())) {
                data = reportSubscribeNumberDao.queryDealarDetailsSampleByMonth(org.getId(), DateUtil.getYear(c), DateUtil.getMonth(c));
            } else if ("quarter".equalsIgnoreCase(req.getType())) {
                data = reportSubscribeNumberDao.queryDealarDetailsSampleByQuarter(org.getId(), DateUtil.getYear(c), DateUtil.getQuarter(c));
            } else {
                data = reportSubscribeNumberDao.queryDealarDetailsSampleByYear(org.getId(), DateUtil.getYear(c));
            }
        } else if (SysOranizagionService.ORG_TYPE_DEP.equalsIgnoreCase(org.getType())) {//销售部门
            if (UserInfoService.USER_ROLE_SALESMAN.equalsIgnoreCase(req.getType())) {//业务员
                LOG.info("Query sale man product sale count.");
                if ("month".equalsIgnoreCase(req.getType())) {
                    data = reportSubscribeNumberDao.queryDepSalemanByMonth(req.getUid(), DateUtil.getYear(c), DateUtil.getMonth(c));
                } else if ("quarter".equalsIgnoreCase(req.getType())) {
                    data = reportSubscribeNumberDao.queryDepSalemanByQuarter(req.getUid(), DateUtil.getYear(c), DateUtil.getQuarter(c));
                } else {
                    data = reportSubscribeNumberDao.queryDepSalemanByYear(req.getUid(), DateUtil.getYear(c));
                }
            } else {//部门
                LOG.info("Query department product sale count.");
                if ("month".equalsIgnoreCase(req.getType())) {
                    data = reportSubscribeNumberDao.queryProductDepByMonth(org.getId(), DateUtil.getYear(c), DateUtil.getMonth(c), Boolean.FALSE);
                } else if ("quarter".equalsIgnoreCase(req.getType())) {
                    data = reportSubscribeNumberDao.queryProductDepByQuater(org.getId(), DateUtil.getYear(c), DateUtil.getQuarter(c), Boolean.FALSE);
                } else {
                    data = reportSubscribeNumberDao.queryProductDepByYear(org.getId(), DateUtil.getYear(c), Boolean.FALSE);
                }
            }
        } else if (SysOranizagionService.ORG_TYPE_COM.equalsIgnoreCase(org.getType())) {//公司
            LOG.info("Query com product sale count.");
            if ("month".equalsIgnoreCase(req.getType())) {
                data = reportSubscribeNumberDao.queryProductAllByMonth(DateUtil.getYear(c), DateUtil.getMonth(c));
            } else if ("quarter".equalsIgnoreCase(req.getType())) {
                data = reportSubscribeNumberDao.queryProducAllByQuater(DateUtil.getYear(c), DateUtil.getQuarter(c));
            } else {
                data = reportSubscribeNumberDao.queryProductAllByYear(DateUtil.getYear(c));
            }
        }
        if (null != data) {
            resp.setData(data);
        }
        return resp;
    }

//    @Override
//    @Transactional(readOnly = true)
//    public WebSaleSampleQueryResp queryByQueryfieldsample(WebSaleSampleReq req) {
//        WebSaleSampleQueryResp resp = new WebSaleSampleQueryResp();
//        if (null == req.getRole() || null == req.getType()) {
//            return resp;
//        }
//
//        Calendar c = Calendar.getInstance();
//        Page<WebSaleSampleItem> page;
//        if (UserInfoService.USER_ROLE_SALESMAN.equalsIgnoreCase(req.getRole())) {//销售人员
//            LOG.info("Saleman Query role[" + req.getRole() + "] query[" + req.getQuery() + "] type[" + req.getType() + "]");
//            if ("month".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryFieldSalemanByMonth(buildPageRequest(req.getPage(), req.getNum()), req.getQuery(), DateUtil.getYear(c), DateUtil.getMonth(c));
//            } else if ("quarter".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryFieldSalemanByQuater(buildPageRequest(req.getPage(), req.getNum()), req.getQuery(), DateUtil.getYear(c), DateUtil.getQuarter(c));
//            } else {
//                page = everyDayEveryOrgReportDao.queryFieldSalemanByYear(buildPageRequest(req.getPage(), req.getNum()), req.getQuery(), DateUtil.getYear(c));
//            }
//        } else if (UserInfoService.USER_ROLE_STATION.equalsIgnoreCase(req.getRole()) || UserInfoService.USER_ROLE_DEALER.equalsIgnoreCase(req.getRole())) {//奶站//经销商
//            LOG.info("Station or Dealer Query role[" + req.getRole() + "] query[" + req.getQuery() + "] type[" + req.getType() + "]");
//            List<WebSaleSampleItem> list = userInfoDao.findUserByOrgType(req.getQuery(), req.getRole().toUpperCase(), UserInfoService.USER_ROLE_STATION, Boolean.FALSE);
//            List<Long> orgs = new ArrayList<Long>();
//            Map<Long, WebSaleSampleItem> orgSaleCount = new HashMap<Long, WebSaleSampleItem>();
//            for (WebSaleSampleItem itm : list) {
//                orgs.add(itm.getAmount());
//                orgSaleCount.put(itm.getAmount(), itm);
//            }
//            if (orgs.size() <= 0) {
//                return resp;
//            }
//            if ("month".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryFieldStationByMonth(buildPageRequest(req.getPage(), req.getNum()), orgs, DateUtil.getYear(c), DateUtil.getMonth(c));
//            } else if ("quarter".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryFieldStationByQuater(buildPageRequest(req.getPage(), req.getNum()), orgs, DateUtil.getYear(c), DateUtil.getQuarter(c));
//            } else {
//                page = everyDayEveryOrgReportDao.queryFieldStationByYear(buildPageRequest(req.getPage(), req.getNum()), orgs, DateUtil.getYear(c));
//            }
//            if (null != page && null != page.getContent() && page.getContent().size() > 0) {
//                int i = 1;
//                for (WebSaleSampleItem item : page.getContent()) {
//                    item.setRank((req.getPage() - 1) * req.getNum() + i);
//                    WebSaleSampleItem user = orgSaleCount.get(item.getUid());
//                    if (null != user) {
//                        item.setUid(user.getUid());
//                        item.setManager(user.getManager());
//                        item.setTel(user.getTel());
//                    }
//                }
//                resp.setData(page.getContent());
//                resp.setTotal_num(page.getTotalElements());
//                return resp;
//            }
//        } else if (UserInfoService.USER_ROLE_MANAGER.equalsIgnoreCase(req.getRole())) {//销售主管
//            LOG.info("Dep Query role[" + req.getRole() + "] query[" + req.getQuery() + "] type[" + req.getType() + "]");
//            List<WebSaleSampleItem> list = userInfoDao.findUserByOrgType(req.getQuery(), SysOranizagionService.ORG_TYPE_DEP, UserInfoService.USER_ROLE_MANAGER, Boolean.FALSE);
//            List<Long> orgs = new ArrayList<Long>();
//            Map<Long, WebSaleSampleItem> orgSaleCount = new HashMap<Long, WebSaleSampleItem>();
//            for (WebSaleSampleItem itm : list) {
//                orgs.add(itm.getAmount());
//                orgSaleCount.put(itm.getAmount(), itm);
//            }
//            if (orgs.size() <= 0) {
//                return resp;
//            }
//            if ("month".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryFieldDepByMonth(buildPageRequest(req.getPage(), req.getNum()), orgs, DateUtil.getYear(c), DateUtil.getMonth(c));
//            } else if ("quarter".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryFieldDepByQuater(buildPageRequest(req.getPage(), req.getNum()), orgs, DateUtil.getYear(c), DateUtil.getQuarter(c));
//            } else {
//                page = everyDayEveryOrgReportDao.queryFieldDepByYear(buildPageRequest(req.getPage(), req.getNum()), orgs, DateUtil.getYear(c));
//            }
//            if (null != page && null != page.getContent() && page.getContent().size() > 0) {
//                int i = 1;
//                for (WebSaleSampleItem item : page.getContent()) {
//                    item.setRank((req.getPage() - 1) * req.getNum() + i);
//                    WebSaleSampleItem user = orgSaleCount.get(item.getUid());
//                    if (null != user) {
//                        item.setUid(user.getUid());
//                        item.setManager(user.getManager());
//                        item.setTel(user.getTel());
//                    }
//                }
//                resp.setData(page.getContent());
//                resp.setTotal_num(page.getTotalElements());
//                return resp;
//            }
//        } else if (UserInfoService.USER_ROLE_CHIEF.equalsIgnoreCase(req.getRole())) {//销售总监
//            LOG.info("Com Query role[" + req.getRole() + "] query[" + req.getQuery() + "] type[" + req.getType() + "]");
//            List<WebSaleSampleItem> list = userInfoDao.findUserByOrgType(req.getQuery(), SysOranizagionService.ORG_TYPE_COM, UserInfoService.USER_ROLE_CHIEF, Boolean.FALSE);
//            Map<Long, WebSaleSampleItem> orgSaleCount = new HashMap<Long, WebSaleSampleItem>();
//            WebSaleSampleItem user = null;
//            for (WebSaleSampleItem itm : list) {
//                if (req.getQuery().equalsIgnoreCase(itm.getManager()) || req.getQuery().equalsIgnoreCase(itm.getTel())) {
//                    orgSaleCount.put(itm.getAmount(), itm);
//                    user = itm;
//                    break;
//                }
//            }
//            if (orgSaleCount.size() <= 0 || null == user) {
//                return resp;
//            }
//            if ("month".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryAllComByAdminMonth(buildPageRequest(req.getPage(), req.getNum()), DateUtil.getYear(c), DateUtil.getMonth(c));
//            } else if ("quarter".equalsIgnoreCase(req.getType())) {
//                page = everyDayEveryOrgReportDao.queryAllComByAdminQuater(buildPageRequest(req.getPage(), req.getNum()), DateUtil.getYear(c), DateUtil.getQuarter(c));
//            } else {
//                page = everyDayEveryOrgReportDao.queryAllComByAdminYear(buildPageRequest(req.getPage(), req.getNum()), DateUtil.getYear(c));
//            }
//            //补充销售总监的信息
//            if (null != page && null != page.getContent() && page.getContent().size() > 0) {
//                int i = 1;
//                for (WebSaleSampleItem item : page.getContent()) {
//                    item.setRank((req.getPage() - 1) * req.getNum() + i);
//                    item.setUid(user.getUid());
//                    item.setManager(user.getManager());
//                    item.setTel(user.getTel());
//                }
//                resp.setData(page.getContent());
//                resp.setTotal_num(page.getTotalElements());
//                return resp;
//            }
//        } else {
//            return resp;
//        }
//        if (null != page && null != page.getContent()) {
//            resp.setData(page.getContent());
//            resp.setTotal_num(page.getTotalElements());
//            //补充排名
//            int i = 1;
//            for (WebSaleSampleItem item : page.getContent()) {
//                item.setRank((req.getPage() - 1) * req.getNum() + i);
//            }
//        }
//
//        return resp;
//    }

    /**
     * 创建分页请求.
     */
    private PageRequest buildPageRequest(final int page, final int size) {
        return new PageRequest(page - 1, size);
    }
}
