/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.salesman.service.impl;

import com.dinglicom.frame.sys.entity.SysOranizagion;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.frame.sys.util.DateUtil;
import com.dinglicom.reportnum.repository.EveryDayEveryOrgReportDao;
import com.dinglicom.reportnum.repository.ReportSubscribeNumberDao;
import com.dinglicom.salesman.service.SalesmanService;
import com.dinglicom.salesman.domain.DepsaleResp;
import com.dinglicom.salesman.domain.DepsaleRespItem;
import com.dinglicom.salesman.domain.OrgSaleDetailSampleReq;
import com.dinglicom.salesman.domain.OrgSaleSampleReq;
import com.dinglicom.salesman.domain.OrgSaleSampleResp;
import com.dinglicom.salesman.domain.OrgSaleSampleRespItem;
import com.dinglicom.salesman.domain.ProductSaleSampleReq;
import com.dinglicom.salesman.domain.ProductSaleSampleResp;
import com.dinglicom.salesman.domain.ProductSaleSampleRespItem;
import java.util.Calendar;
import java.util.List;
import javax.annotation.Resource;
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
public class SalesmanServiceImpl implements SalesmanService {

    @Resource
    private ReportSubscribeNumberDao reportSubscribeNumberDao;
    @Resource
    private EveryDayEveryOrgReportDao everyDayEveryOrgReportDao;

    @Override
    @Transactional(readOnly = true)
    public ProductSaleSampleResp queryByProductsample(ProductSaleSampleReq req, UserInfo salesman) {
        ProductSaleSampleResp resp = new ProductSaleSampleResp();
        Page<ProductSaleSampleRespItem> page;
        Calendar c = Calendar.getInstance();
        PageRequest pagereq = buildPageRequest(req.getPage(), req.getNum());
        Long saleCount;
        if (null == req.getType() || "month".equalsIgnoreCase(req.getType())) {
            page = reportSubscribeNumberDao.queryProductSampleByMonth(pagereq, salesman.getId(), DateUtil.getYear(c), DateUtil.getMonth(c), Boolean.FALSE);
            saleCount = reportSubscribeNumberDao.queryProductSampleByMonth(salesman.getId(), DateUtil.getYear(c), DateUtil.getMonth(c), Boolean.FALSE);
        } else if ("quarter".equalsIgnoreCase(req.getType())) {
            page = reportSubscribeNumberDao.queryProductSampleByQuarter(pagereq, salesman.getId(), DateUtil.getYear(c), DateUtil.getQuarter(c), Boolean.FALSE);
            saleCount = reportSubscribeNumberDao.queryProductSampleByQuarter(salesman.getId(), DateUtil.getYear(c), DateUtil.getQuarter(c), Boolean.FALSE);
        } else {
            page = reportSubscribeNumberDao.queryProductSampleByYear(pagereq, salesman.getId(), DateUtil.getYear(c), Boolean.FALSE);
            saleCount = reportSubscribeNumberDao.queryProductSampleByYear(salesman.getId(), DateUtil.getYear(c), Boolean.FALSE);
        }
        if (null != page && null != page.getContent()) {
            resp.setData(page.getContent());
            resp.setTotal_sales(saleCount);
        }
        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public OrgSaleSampleResp queryByOrgsample(OrgSaleSampleReq req, UserInfo salesman) {
        OrgSaleSampleResp resp = new OrgSaleSampleResp();
        Page<OrgSaleSampleRespItem> page;
        Calendar c = Calendar.getInstance();
        PageRequest pagereq = buildPageRequest(req.getPage(), req.getNum());
        if (null == req.getType() || "month".equalsIgnoreCase(req.getType())) {
            if (null == req.getRole() || "ALL".equalsIgnoreCase(req.getRole())) {
                page = everyDayEveryOrgReportDao.queryOrgSampleByMonth(pagereq, salesman.getId(), DateUtil.getYear(c), DateUtil.getMonth(c), Boolean.FALSE);
            } else {
                page = everyDayEveryOrgReportDao.queryOrgSampleByMonth(pagereq, salesman.getId(), req.getRole(), DateUtil.getYear(c), DateUtil.getMonth(c), Boolean.FALSE);
            }
        } else if ("quarter".equalsIgnoreCase(req.getType())) {
            if (null == req.getRole() || "ALL".equalsIgnoreCase(req.getRole())) {
                page = everyDayEveryOrgReportDao.queryOrgSampleByQuarter(pagereq, salesman.getId(), DateUtil.getYear(c), DateUtil.getQuarter(c), Boolean.FALSE);
            } else {
                page = everyDayEveryOrgReportDao.queryOrgSampleByMonth(pagereq, salesman.getId(), req.getRole(), DateUtil.getYear(c), DateUtil.getQuarter(c), Boolean.FALSE);
            }
        } else {
            if (null == req.getRole() || "ALL".equalsIgnoreCase(req.getRole())) {
                page = everyDayEveryOrgReportDao.queryOrgSampleByYear(pagereq, salesman.getId(), DateUtil.getYear(c), Boolean.FALSE);
            } else {
                page = everyDayEveryOrgReportDao.queryOrgSampleByYear(pagereq, salesman.getId(), req.getRole(), DateUtil.getYear(c), Boolean.FALSE);
            }
        }
        if (null != page && null != page.getContent()) {
            resp.setData(page.getContent());
        }
        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public ProductSaleSampleResp queryByOrgDetailsample(OrgSaleDetailSampleReq req) {
        ProductSaleSampleResp resp = new ProductSaleSampleResp();
        List<ProductSaleSampleRespItem> data;
        Calendar c = Calendar.getInstance();
        Long saleCount;
        if (null == req.getType() || "month".equalsIgnoreCase(req.getType())) {
            data = reportSubscribeNumberDao.queryOrgDetailsSampleByMonth(req.getUser_id(), DateUtil.getYear(c), DateUtil.getMonth(c));
            saleCount = reportSubscribeNumberDao.queryOrgDetailsSampleByMonthNumber(req.getUser_id(), DateUtil.getYear(c), DateUtil.getMonth(c));
        } else if ("quarter".equalsIgnoreCase(req.getType())) {
            data = reportSubscribeNumberDao.queryOrgDetailsSampleByQuarter(req.getUser_id(), DateUtil.getYear(c), DateUtil.getQuarter(c));
            saleCount = reportSubscribeNumberDao.queryOrgDetailsSampleByQuarterNumber(req.getUser_id(), DateUtil.getYear(c), DateUtil.getQuarter(c));
        } else {
            data = reportSubscribeNumberDao.queryOrgDetailsSampleByYear(req.getUser_id(), DateUtil.getYear(c));
            saleCount = reportSubscribeNumberDao.queryOrgDetailsSampleByYearNumber(req.getUser_id(), DateUtil.getYear(c));
        }
        if (null != data) {
            resp.setData(data);
            resp.setTotal_sales(saleCount);
        }
        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public ProductSaleSampleResp queryByDepProductsample(ProductSaleSampleReq req, UserInfo depAdmin) {
        if (null == depAdmin.getOrg()) {
            throw new RuntimeException("Current user not department.");
        }
        SysOranizagion dep = depAdmin.getOrg();
        ProductSaleSampleResp resp = new ProductSaleSampleResp();
        List<ProductSaleSampleRespItem> data;
        Calendar c = Calendar.getInstance();
        Long saleCount;
        if (null == req.getType() || "month".equalsIgnoreCase(req.getType())) {
            data = reportSubscribeNumberDao.queryProductDepByMonth(dep.getId(), DateUtil.getYear(c), DateUtil.getMonth(c), Boolean.FALSE);
            saleCount = reportSubscribeNumberDao.queryProductDepSampleByMonth(dep.getId(), DateUtil.getYear(c), DateUtil.getMonth(c), Boolean.FALSE);
        } else if ("quarter".equalsIgnoreCase(req.getType())) {
            data = reportSubscribeNumberDao.queryProductDepByQuater(dep.getId(), DateUtil.getYear(c), DateUtil.getQuarter(c), Boolean.FALSE);
            saleCount = reportSubscribeNumberDao.queryProductDepSampleByQuater(dep.getId(), DateUtil.getYear(c), DateUtil.getQuarter(c), Boolean.FALSE);
        } else {
            data = reportSubscribeNumberDao.queryProductDepByYear(dep.getId(), DateUtil.getYear(c), Boolean.FALSE);
            saleCount = reportSubscribeNumberDao.queryProductDepSampleByYear(dep.getId(), DateUtil.getYear(c), Boolean.FALSE);
        }
        if (null != data && data.size() > 0) {
            resp.setData(data);
            resp.setTotal_sales(saleCount);
        }
        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public DepsaleResp queryDepBySalesman(OrgSaleSampleReq req, UserInfo depman) {
        if (null == depman.getOrg()) {
            throw new RuntimeException("Current user not department.");
        }
        SysOranizagion dep = depman.getOrg();
        DepsaleResp resp = new DepsaleResp();
        List<DepsaleRespItem> data;
        Calendar c = Calendar.getInstance();
        if (null == req.getType() || "month".equalsIgnoreCase(req.getType())) {
            data = everyDayEveryOrgReportDao.queryDepOrgSampleByMonth(dep.getId(), DateUtil.getYear(c), DateUtil.getMonth(c), Boolean.FALSE);
        } else if ("quarter".equalsIgnoreCase(req.getType())) {
            data = everyDayEveryOrgReportDao.queryDepOrgSampleByQuater(dep.getId(), DateUtil.getYear(c), DateUtil.getQuarter(c), Boolean.FALSE);
        } else {
            data = everyDayEveryOrgReportDao.queryDepOrgSampleByYear(dep.getId(), DateUtil.getYear(c), Boolean.FALSE);
        }
        if (null != data && data.size() > 0) {
            resp.setData(data);
        }
        return resp;
    }
    
    

    @Override
    @Transactional(readOnly = true)
    public ProductSaleSampleResp queryDepBySalemansample(OrgSaleDetailSampleReq req) {
        ProductSaleSampleResp resp = new ProductSaleSampleResp();
        List<ProductSaleSampleRespItem> data;
        Calendar c = Calendar.getInstance();
        Long saleCount;
        if (null == req.getType() || "month".equalsIgnoreCase(req.getType())) {
            data = reportSubscribeNumberDao.queryDepSalemanByMonth(req.getUser_id(), DateUtil.getYear(c), DateUtil.getMonth(c));
            saleCount = reportSubscribeNumberDao.queryDepSalemanByMonthNumber(req.getUser_id(), DateUtil.getYear(c), DateUtil.getMonth(c));
        } else if ("quarter".equalsIgnoreCase(req.getType())) {
            data = reportSubscribeNumberDao.queryDepSalemanByQuarter(req.getUser_id(), DateUtil.getYear(c), DateUtil.getQuarter(c));
            saleCount = reportSubscribeNumberDao.queryDepSalemanByQuarterNumber(req.getUser_id(), DateUtil.getYear(c), DateUtil.getQuarter(c));
        } else {
            data = reportSubscribeNumberDao.queryDepSalemanByYear(req.getUser_id(), DateUtil.getYear(c));
            saleCount = reportSubscribeNumberDao.queryDepSalemanByYearNumber(req.getUser_id(), DateUtil.getYear(c));
        }
        if (null != data) {
            resp.setData(data);
            resp.setTotal_sales(saleCount);
        }
        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public ProductSaleSampleResp queryByAllProductsample(ProductSaleSampleReq req) {
        ProductSaleSampleResp resp = new ProductSaleSampleResp();
        List<ProductSaleSampleRespItem> data;
        Calendar c = Calendar.getInstance();
        Long saleCount;
        if (null == req.getType() || "month".equalsIgnoreCase(req.getType())) {
            data = reportSubscribeNumberDao.queryProductAllByMonth(DateUtil.getYear(c), DateUtil.getMonth(c));
            saleCount = reportSubscribeNumberDao.queryProductAllSampleByMonth(DateUtil.getYear(c), DateUtil.getMonth(c));
        } else if ("quarter".equalsIgnoreCase(req.getType())) {
            data = reportSubscribeNumberDao.queryProducAllByQuater(DateUtil.getYear(c), DateUtil.getQuarter(c));
            saleCount = reportSubscribeNumberDao.queryProductAllSampleByQuater(DateUtil.getYear(c), DateUtil.getQuarter(c));
        } else {
            data = reportSubscribeNumberDao.queryProductAllByYear(DateUtil.getYear(c));
            saleCount = reportSubscribeNumberDao.queryProductAllSampleByYear(DateUtil.getYear(c));
        }
        if (null != data && data.size() > 0) {
            resp.setData(data);
            resp.setTotal_sales(saleCount);
        }
        return resp;
    }
    
    @Override
    @Transactional(readOnly = true)
    public DepsaleResp querySaleManagerSample(OrgSaleSampleReq req) {
        DepsaleResp resp = new DepsaleResp();
        List<DepsaleRespItem> data;
        Calendar c = Calendar.getInstance();
        if (null == req.getType() || "month".equalsIgnoreCase(req.getType())) {
            data = everyDayEveryOrgReportDao.querySaleManagerSampleByMonth(DateUtil.getYear(c), DateUtil.getMonth(c));
        } else if ("quarter".equalsIgnoreCase(req.getType())) {
            data = everyDayEveryOrgReportDao.querySaleManagerSampleByQuater(DateUtil.getYear(c), DateUtil.getQuarter(c));
        } else {
            data = everyDayEveryOrgReportDao.querySaleManagerSampleByYear(DateUtil.getYear(c));
        }
        if (null != data && data.size() > 0) {
            resp.setData(data);
        }
        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public ProductSaleSampleResp querySaleManagerDetailSample(OrgSaleDetailSampleReq req) {
        ProductSaleSampleResp resp = new ProductSaleSampleResp();
        List<ProductSaleSampleRespItem> data;
        Calendar c = Calendar.getInstance();
        Long saleCount;
        if (null == req.getType() || "month".equalsIgnoreCase(req.getType())) {
            data = reportSubscribeNumberDao.queryProductByDepMonth(req.getUser_id(), DateUtil.getYear(c), DateUtil.getMonth(c));
            saleCount = reportSubscribeNumberDao.queryProductSampleByDepMonth(req.getUser_id(), DateUtil.getYear(c), DateUtil.getMonth(c));
        } else if ("quarter".equalsIgnoreCase(req.getType())) {
            data = reportSubscribeNumberDao.queryProductByDepQuater(req.getUser_id(), DateUtil.getYear(c), DateUtil.getQuarter(c));
            saleCount = reportSubscribeNumberDao.queryProductSampleByDepQuater(req.getUser_id(), DateUtil.getYear(c), DateUtil.getQuarter(c));
        } else {
            data = reportSubscribeNumberDao.queryProductByDepYear(req.getUser_id(), DateUtil.getYear(c));
            saleCount = reportSubscribeNumberDao.queryProductSampleByDepYear(req.getUser_id(), DateUtil.getYear(c));
        }
        if (null != data && data.size() > 0) {
            resp.setData(data);
            resp.setTotal_sales(saleCount);
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
