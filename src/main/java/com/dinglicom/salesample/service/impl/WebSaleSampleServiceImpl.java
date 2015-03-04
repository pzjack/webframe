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
import com.dinglicom.salesample.domain.WebSaleSampleQueryResp;
import com.dinglicom.salesample.domain.WebSaleSampleReq;
import com.dinglicom.salesample.domain.WebSaleSampleResp;
import com.dinglicom.salesman.domain.ProductSaleSampleRespItem;
import com.dinglicom.salesample.service.WebSaleSampleService;
import java.util.ArrayList;
import java.util.Calendar;
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
public class WebSaleSampleServiceImpl implements WebSaleSampleService {

    private final static Logger LOG = LoggerFactory.getLogger(WebSaleSampleServiceImpl.class);
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
    public WebProductSaleResp queryByAllProductsample(WebProductSaleReq req) {
        WebProductSaleResp resp = new WebProductSaleResp();
        List<ProductSaleSampleRespItem> data;
        Calendar c = Calendar.getInstance();
        if (null == req.getType() || "month".equalsIgnoreCase(req.getType())) {
            data = reportSubscribeNumberDao.queryProductAllByMonth(DateUtil.getYear(c), DateUtil.getMonth(c));
        } else if ("quarter".equalsIgnoreCase(req.getType())) {
            data = reportSubscribeNumberDao.queryProducAllByQuater(DateUtil.getYear(c), DateUtil.getQuarter(c));
        } else {
            data = reportSubscribeNumberDao.queryProductAllByYear(DateUtil.getYear(c));
        }
        if (null != data && data.size() > 0) {
            resp.setData(data);
        }
        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public WebSaleSampleResp queryByRolesample(WebSaleSampleReq req) {
        WebSaleSampleResp resp = new WebSaleSampleResp();
        if (null == req.getRole() || null == req.getType()) {
            return resp;
        }

        Calendar c = Calendar.getInstance();
        Page<WebSaleSampleItem> page;
        if (UserInfoService.USER_ROLE_SALESMAN.equalsIgnoreCase(req.getRole())) {//销售人员
            if ("month".equalsIgnoreCase(req.getType())) {
                page = everyDayEveryOrgReportDao.queryAllSalemanByMonth(buildPageRequest(req.getPage(), req.getNum()), DateUtil.getYear(c), DateUtil.getMonth(c));
            } else if ("quarter".equalsIgnoreCase(req.getType())) {
                page = everyDayEveryOrgReportDao.queryAllSalemanByQuater(buildPageRequest(req.getPage(), req.getNum()), DateUtil.getYear(c), DateUtil.getQuarter(c));
            } else {
                page = everyDayEveryOrgReportDao.queryAllSalemanByYear(buildPageRequest(req.getPage(), req.getNum()), DateUtil.getYear(c));
            }

            processResult(page, resp, req, UserInfoService.USER_ROLE_SALESMAN);
            return resp;
        } else if (UserInfoService.USER_ROLE_STATION.equalsIgnoreCase(req.getRole()) || UserInfoService.USER_ROLE_DEALER.equalsIgnoreCase(req.getRole())) {//奶站//经销商
            if ("month".equalsIgnoreCase(req.getType())) {
                page = everyDayEveryOrgReportDao.queryAllStationByMonth(buildPageRequest(req.getPage(), req.getNum()), DateUtil.getYear(c), DateUtil.getMonth(c));
            } else if ("quarter".equalsIgnoreCase(req.getType())) {
                page = everyDayEveryOrgReportDao.queryAllStationByQuater(buildPageRequest(req.getPage(), req.getNum()), DateUtil.getYear(c), DateUtil.getQuarter(c));
            } else {
                page = everyDayEveryOrgReportDao.queryAllStationByYear(buildPageRequest(req.getPage(), req.getNum()), DateUtil.getYear(c));
            }
            if (null != page && null != page.getContent() && page.getContent().size() > 0) {
                processResult(page, resp, req, UserInfoService.USER_ROLE_STATION);
                return resp;
            }
        } else if (UserInfoService.USER_ROLE_MANAGER.equalsIgnoreCase(req.getRole())) {//销售主管
            if ("month".equalsIgnoreCase(req.getType())) {
                page = everyDayEveryOrgReportDao.queryAllDepByMonth(buildPageRequest(req.getPage(), req.getNum()), DateUtil.getYear(c), DateUtil.getMonth(c));
            } else if ("quarter".equalsIgnoreCase(req.getType())) {
                page = everyDayEveryOrgReportDao.queryAllDepByQuater(buildPageRequest(req.getPage(), req.getNum()), DateUtil.getYear(c), DateUtil.getQuarter(c));
            } else {
                page = everyDayEveryOrgReportDao.queryAllDepByYear(buildPageRequest(req.getPage(), req.getNum()), DateUtil.getYear(c));
            }
            if (null != page && null != page.getContent() && page.getContent().size() > 0) {
                processResult(page, resp, req, UserInfoService.USER_ROLE_MANAGER);
                return resp;
            }
        } else if (UserInfoService.USER_ROLE_CHIEF.equalsIgnoreCase(req.getRole())) {//销售总监
            if ("month".equalsIgnoreCase(req.getType())) {
                page = everyDayEveryOrgReportDao.queryAllComByMonth(buildPageRequest(req.getPage(), req.getNum()), DateUtil.getYear(c), DateUtil.getMonth(c));
            } else if ("quarter".equalsIgnoreCase(req.getType())) {
                page = everyDayEveryOrgReportDao.queryAllComByQuater(buildPageRequest(req.getPage(), req.getNum()), DateUtil.getYear(c), DateUtil.getQuarter(c));
            } else {
                page = everyDayEveryOrgReportDao.queryAllComByYear(buildPageRequest(req.getPage(), req.getNum()), DateUtil.getYear(c));
            }
            //补充销售总监的信息
            if (null != page && null != page.getContent() && page.getContent().size() > 0) {
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
        } else {
            return resp;
        }
        if (null != page && null != page.getContent()) {
            resp.setData(page.getContent());
            resp.setTotal_num(page.getTotalElements());
            //补充排名
            int i = 1;
            for (WebSaleSampleItem item : page.getContent()) {
                item.setRank((req.getPage() - 1) * req.getNum() + i);
            }
        }

        return resp;
    }

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
        if (SysOranizagionService.ORG_TYPE_NZH.equalsIgnoreCase(org.getType()) || SysOranizagionService.ORG_TYPE_DLV.equalsIgnoreCase(org.getType())) {//奶站、经销商
            LOG.info("Query station or dealer product sale count.");
            if ("month".equalsIgnoreCase(req.getType())) {
                data = reportSubscribeNumberDao.queryOrgDetailsSampleByMonth(org.getId(), DateUtil.getYear(c), DateUtil.getMonth(c));
            } else if ("quarter".equalsIgnoreCase(req.getType())) {
                data = reportSubscribeNumberDao.queryOrgDetailsSampleByQuarter(org.getId(), DateUtil.getYear(c), DateUtil.getQuarter(c));
            } else {
                data = reportSubscribeNumberDao.queryOrgDetailsSampleByYear(org.getId(), DateUtil.getYear(c));
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

    @Override
    @Transactional(readOnly = true)
    public WebSaleSampleQueryResp queryByQueryfieldsample(WebSaleSampleReq req) {
        WebSaleSampleQueryResp resp = new WebSaleSampleQueryResp();
        if (null == req.getRole() || null == req.getType()) {
            return resp;
        }

        Calendar c = Calendar.getInstance();
        Page<WebSaleSampleItem> page;
        if (UserInfoService.USER_ROLE_SALESMAN.equalsIgnoreCase(req.getRole())) {//销售人员
            LOG.info("Saleman Query role[" + req.getRole() + "] query[" + req.getQuery() + "] type[" + req.getType() + "]");
            if ("month".equalsIgnoreCase(req.getType())) {
                page = everyDayEveryOrgReportDao.queryFieldSalemanByMonth(buildPageRequest(req.getPage(), req.getNum()), req.getQuery(), DateUtil.getYear(c), DateUtil.getMonth(c));
            } else if ("quarter".equalsIgnoreCase(req.getType())) {
                page = everyDayEveryOrgReportDao.queryFieldSalemanByQuater(buildPageRequest(req.getPage(), req.getNum()), req.getQuery(), DateUtil.getYear(c), DateUtil.getQuarter(c));
            } else {
                page = everyDayEveryOrgReportDao.queryFieldSalemanByYear(buildPageRequest(req.getPage(), req.getNum()), req.getQuery(), DateUtil.getYear(c));
            }
        } else if (UserInfoService.USER_ROLE_STATION.equalsIgnoreCase(req.getRole()) || UserInfoService.USER_ROLE_DEALER.equalsIgnoreCase(req.getRole())) {//奶站//经销商
            LOG.info("Station or Dealer Query role[" + req.getRole() + "] query[" + req.getQuery() + "] type[" + req.getType() + "]");
            List<WebSaleSampleItem> list = userInfoDao.findUserByOrgType(req.getQuery(), req.getRole().toUpperCase(), UserInfoService.USER_ROLE_ADMINISTRATOR, Boolean.FALSE);
            List<Long> orgs = new ArrayList<Long>();
            Map<Long, WebSaleSampleItem> orgSaleCount = new HashMap<Long, WebSaleSampleItem>();
            for (WebSaleSampleItem itm : list) {
                orgs.add(itm.getAmount());
                orgSaleCount.put(itm.getAmount(), itm);
            }
            if (orgs.size() <= 0) {
                return resp;
            }
            if ("month".equalsIgnoreCase(req.getType())) {
                page = everyDayEveryOrgReportDao.queryFieldStationByMonth(buildPageRequest(req.getPage(), req.getNum()), orgs, DateUtil.getYear(c), DateUtil.getMonth(c));
            } else if ("quarter".equalsIgnoreCase(req.getType())) {
                page = everyDayEveryOrgReportDao.queryFieldStationByQuater(buildPageRequest(req.getPage(), req.getNum()), orgs, DateUtil.getYear(c), DateUtil.getQuarter(c));
            } else {
                page = everyDayEveryOrgReportDao.queryFieldStationByYear(buildPageRequest(req.getPage(), req.getNum()), orgs, DateUtil.getYear(c));
            }
            if (null != page && null != page.getContent() && page.getContent().size() > 0) {
                int i = 1;
                for (WebSaleSampleItem item : page.getContent()) {
                    item.setRank((req.getPage() - 1) * req.getNum() + i);
                    WebSaleSampleItem user = orgSaleCount.get(item.getUid());
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
        } else if (UserInfoService.USER_ROLE_MANAGER.equalsIgnoreCase(req.getRole())) {//销售主管
            LOG.info("Dep Query role[" + req.getRole() + "] query[" + req.getQuery() + "] type[" + req.getType() + "]");
            List<WebSaleSampleItem> list = userInfoDao.findUserByOrgType(req.getQuery(), SysOranizagionService.ORG_TYPE_DEP, UserInfoService.USER_ROLE_MANAGER, Boolean.FALSE);
            List<Long> orgs = new ArrayList<Long>();
            Map<Long, WebSaleSampleItem> orgSaleCount = new HashMap<Long, WebSaleSampleItem>();
            for (WebSaleSampleItem itm : list) {
                orgs.add(itm.getAmount());
                orgSaleCount.put(itm.getAmount(), itm);
            }
            if (orgs.size() <= 0) {
                return resp;
            }
            if ("month".equalsIgnoreCase(req.getType())) {
                page = everyDayEveryOrgReportDao.queryFieldDepByMonth(buildPageRequest(req.getPage(), req.getNum()), orgs, DateUtil.getYear(c), DateUtil.getMonth(c));
            } else if ("quarter".equalsIgnoreCase(req.getType())) {
                page = everyDayEveryOrgReportDao.queryFieldDepByQuater(buildPageRequest(req.getPage(), req.getNum()), orgs, DateUtil.getYear(c), DateUtil.getQuarter(c));
            } else {
                page = everyDayEveryOrgReportDao.queryFieldDepByYear(buildPageRequest(req.getPage(), req.getNum()), orgs, DateUtil.getYear(c));
            }
            if (null != page && null != page.getContent() && page.getContent().size() > 0) {
                int i = 1;
                for (WebSaleSampleItem item : page.getContent()) {
                    item.setRank((req.getPage() - 1) * req.getNum() + i);
                    WebSaleSampleItem user = orgSaleCount.get(item.getUid());
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
        } else if (UserInfoService.USER_ROLE_CHIEF.equalsIgnoreCase(req.getRole())) {//销售总监
            LOG.info("Com Query role[" + req.getRole() + "] query[" + req.getQuery() + "] type[" + req.getType() + "]");
            List<WebSaleSampleItem> list = userInfoDao.findUserByOrgType(req.getQuery(), SysOranizagionService.ORG_TYPE_COM, UserInfoService.USER_ROLE_CHIEF, Boolean.FALSE);
            Map<Long, WebSaleSampleItem> orgSaleCount = new HashMap<Long, WebSaleSampleItem>();
            WebSaleSampleItem user = null;
            for (WebSaleSampleItem itm : list) {
                if (req.getQuery().equalsIgnoreCase(itm.getManager()) || req.getQuery().equalsIgnoreCase(itm.getTel())) {
                    orgSaleCount.put(itm.getAmount(), itm);
                    user = itm;
                    break;
                }
            }
            if (orgSaleCount.size() <= 0 || null == user) {
                return resp;
            }
            if ("month".equalsIgnoreCase(req.getType())) {
                page = everyDayEveryOrgReportDao.queryAllComByMonth(buildPageRequest(req.getPage(), req.getNum()), DateUtil.getYear(c), DateUtil.getMonth(c));
            } else if ("quarter".equalsIgnoreCase(req.getType())) {
                page = everyDayEveryOrgReportDao.queryAllComByQuater(buildPageRequest(req.getPage(), req.getNum()), DateUtil.getYear(c), DateUtil.getQuarter(c));
            } else {
                page = everyDayEveryOrgReportDao.queryAllComByYear(buildPageRequest(req.getPage(), req.getNum()), DateUtil.getYear(c));
            }
            //补充销售总监的信息
            if (null != page && null != page.getContent() && page.getContent().size() > 0) {
                int i = 1;
                for (WebSaleSampleItem item : page.getContent()) {
                    item.setRank((req.getPage() - 1) * req.getNum() + i);
                    item.setUid(user.getUid());
                    item.setManager(user.getManager());
                    item.setTel(user.getTel());
                }
                resp.setData(page.getContent());
                resp.setTotal_num(page.getTotalElements());
                return resp;
            }
        } else {
            return resp;
        }
        if (null != page && null != page.getContent()) {
            resp.setData(page.getContent());
            resp.setTotal_num(page.getTotalElements());
            //补充排名
            int i = 1;
            for (WebSaleSampleItem item : page.getContent()) {
                item.setRank((req.getPage() - 1) * req.getNum() + i);
            }
        }

        return resp;
    }

    /**
     * 创建分页请求.
     */
    private PageRequest buildPageRequest(final int page, final int size) {
        return new PageRequest(page - 1, size);
    }
}
