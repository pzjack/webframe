/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.reportnum.entity;

import com.dinglicom.frame.entity.EntityExt;
import com.dinglicom.frame.sys.entity.SysOranizagion;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 * 每天每单位的上报及发货情况
 *
 * @author panzhen
 */
@Entity
@Table(name = "everyday_everyorg_report")
public class EveryDayEveryOrgReport extends EntityExt implements Serializable {

    private int year;//年
    private int quarter;//季度
    private int month;//月
    private int day;//日

    private String reportno;//报量编号
    private String reportstate;//上报状态
    private long totalnum;//上报总数量
    private Date reporttime;//报量时间

    private SysOranizagion org;//上报单位（经销商、奶站）
    private String orgname;//上报单位名称
    private String orgtype;//奶站/经销商
    private String responsibleman;//上报单位的负责人
    private String responsiblephone;//上报单位的负责人联系电话

    private Date shiptime;//出货时间
    private long shipnum;//出货量
    private String shipname;//出货人姓名
    private String shipphone;//出货人联系电话

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
     * @return the totalnum
     */
    @Column(name = "total_num")
    public long getTotalnum() {
        return totalnum;
    }

    /**
     * @param totalnum the totalnum to set
     */
    public void setTotalnum(long totalnum) {
        this.totalnum = totalnum;
    }

    /**
     * @return the reporttime
     */
    @Column(name = "report_time")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getReporttime() {
        return reporttime;
    }

    /**
     * @param reporttime the reporttime to set
     */
    public void setReporttime(Date reporttime) {
        this.reporttime = reporttime;
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
     * @return the orgtype
     */
    @Column(name = "org_type")
    public String getOrgtype() {
        return orgtype;
    }

    /**
     * @param orgtype the orgtype to set
     */
    public void setOrgtype(String orgtype) {
        this.orgtype = orgtype;
    }

    /**
     * @return the responsibleman
     */
    @Column(name = "responsible_man")
    public String getResponsibleman() {
        return responsibleman;
    }

    /**
     * @param responsibleman the responsibleman to set
     */
    public void setResponsibleman(String responsibleman) {
        this.responsibleman = responsibleman;
    }

    /**
     * @return the responsiblephone
     */
    @Column(name = "responsible_phone")
    public String getResponsiblephone() {
        return responsiblephone;
    }

    /**
     * @param responsiblephone the responsiblephone to set
     */
    public void setResponsiblephone(String responsiblephone) {
        this.responsiblephone = responsiblephone;
    }

    /**
     * @return the shiptime
     */
    @Column(name = "ship_time")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getShiptime() {
        return shiptime;
    }

    /**
     * @param shiptime the shiptime to set
     */
    public void setShiptime(Date shiptime) {
        this.shiptime = shiptime;
    }

    /**
     * @return the shipnum
     */
    @Column(name = "ship_num")
    public long getShipnum() {
        return shipnum;
    }

    /**
     * @param shipnum the shipnum to set
     */
    public void setShipnum(long shipnum) {
        this.shipnum = shipnum;
    }

    /**
     * @return the shipname
     */
    @Column(name = "ship_name")
    public String getShipname() {
        return shipname;
    }

    /**
     * @param shipname the shipname to set
     */
    public void setShipname(String shipname) {
        this.shipname = shipname;
    }

    /**
     * @return the shipphone
     */
    @Column(name = "ship_phone")
    public String getShipphone() {
        return shipphone;
    }

    /**
     * @param shipphone the shipphone to set
     */
    public void setShipphone(String shipphone) {
        this.shipphone = shipphone;
    }
}
