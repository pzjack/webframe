/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.reportnum.entity;

import com.dinglicom.frame.entity.EntityExt;
import com.dinglicom.frame.sys.entity.SysOranizagion;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.product.entity.UserProduct;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author panzhen
 */
@Entity
@Table(name = "report_subscribe_number")
public class ReportSubscribeNumber extends EntityExt implements Serializable {
    private int year;//年
    private int quarter;//季度
    private int month;//月
    private int day;//日
    private SysOranizagion org;//上报单位（经销商、奶站）
    private String orgname;//上报单位名称
    private UserProduct product;//产品
    private String productname;//产品名称
    private long distrutenum;//计算上报量
    private long minusnum;//减少报量（退订报量）
    private long plusnum;//增加报量
    private long reportnum;//实际报量
    private String reportstate;//上报状态
    private UserInfo reportman;//上报人
    private String reportname;//上报人姓名
    private String reportno;//报量编号
    private EveryDayEveryOrgReport everyDayEveryOrgReport;//报量单位信息
    
    
    public ReportSubscribeNumber () {};
    public ReportSubscribeNumber(UserProduct product, long distrutenum) {
        this.product = product;
        this.distrutenum = distrutenum;
    }

    /**
     * @return the year
     */
    @Column(name = "f_year")
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return the quarter
     */
    @Column(name = "f_quarter")
    public int getQuarter() {
        return quarter;
    }

    /**
     * @param quarter the quarter to set
     */
    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    /**
     * @return the month
     */
    @Column(name = "f_month")
    public int getMonth() {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * @return the day
     */
    @Column(name = "f_day")
    public int getDay() {
        return day;
    }

    /**
     * @param day the day to set
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * @return the org
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id")
    public SysOranizagion getOrg() {
        return org;
    }

    /**
     * @param org the org to set
     */
    public void setOrg(SysOranizagion org) {
        this.org = org;
    }

    /**
     * @return the orgname
     */
    @Column(name = "org_name")
    public String getOrgname() {
        return orgname;
    }

    /**
     * @param orgname the orgmame to set
     */
    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    /**
     * @return the product
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    public UserProduct getProduct() {
        return product;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(UserProduct product) {
        this.product = product;
    }

    /**
     * @return the productname
     */
    @Column(name = "product_name")
    public String getProductname() {
        return productname;
    }

    /**
     * @param productname the productname to set
     */
    public void setProductname(String productname) {
        this.productname = productname;
    }

    /**
     * @return the distrutenum
     */
    @Column(name = "distrute_num")
    public long getDistrutenum() {
        return distrutenum;
    }

    /**
     * @param distrutenum the distrutenum to set
     */
    public void setDistrutenum(long distrutenum) {
        this.distrutenum = distrutenum;
    }

    /**
     * @return the reportstate
     */
    @Column(name = "report_state")
    public String getReportstate() {
        return reportstate;
    }

    /**
     * @param reportstate the reportstate to set
     */
    public void setReportstate(String reportstate) {
        this.reportstate = reportstate;
    }

    /**
     * @return the reportman
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reportman_id")
    public UserInfo getReportman() {
        return reportman;
    }

    /**
     * @param reportman the reportman to set
     */
    public void setReportman(UserInfo reportman) {
        this.reportman = reportman;
    }

    /**
     * @return the reportname
     */
    @Column(name = "report_name")
    public String getReportname() {
        return reportname;
    }

    /**
     * @param reportname the reportname to set
     */
    public void setReportname(String reportname) {
        this.reportname = reportname;
    }

    /**
     * @return the minusnum
     */
    @Column(name = "minus_num")
    public long getMinusnum() {
        return minusnum;
    }

    /**
     * @param minusnum the minusnum to set
     */
    public void setMinusnum(long minusnum) {
        this.minusnum = minusnum;
    }

    /**
     * @return the plusnum
     */
    @Column(name = "plus_num")
    public long getPlusnum() {
        return plusnum;
    }

    /**
     * @param plusnum the plusnum to set
     */
    public void setPlusnum(long plusnum) {
        this.plusnum = plusnum;
    }

    /**
     * @return the reportnum
     */
    @Column(name = "report_num")
    public long getReportnum() {
        return reportnum;
    }

    /**
     * @param reportnum the reportnum to set
     */
    public void setReportnum(long reportnum) {
        this.reportnum = reportnum;
    }

    /**
     * @return the reportno
     */
    @Column(name = "report_no")
    public String getReportno() {
        return reportno;
    }

    /**
     * @param reportno the reportno to set
     */
    public void setReportno(String reportno) {
        this.reportno = reportno;
    }

    /**
     * @return the everyDayEveryOrgReport
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "everyday_org_id")
    public EveryDayEveryOrgReport getEveryDayEveryOrgReport() {
        return everyDayEveryOrgReport;
    }

    /**
     * @param everyDayEveryOrgReport the everyDayEveryOrgReport to set
     */
    public void setEveryDayEveryOrgReport(EveryDayEveryOrgReport everyDayEveryOrgReport) {
        this.everyDayEveryOrgReport = everyDayEveryOrgReport;
    }
}
