/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.reportnum.demain;

import com.dinglicom.frame.sys.domain.AdminPageReqBase;
import com.dinglicom.frame.sys.util.DateUtil;
import java.util.Calendar;

/**
 *
 * @author panzhen
 */
public class WebReportlistReq extends AdminPageReqBase {

    private String role;
    private String date;
    private int year;
    private int month;
    private int day;

    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        if (null != date) {
            date = date.trim();
            this.date = date;
            int p = date.indexOf("-");
            if (p > 0) {
                month = new Integer(date.substring(0, p));
                day = new Integer(date.substring(p + 1));
            }
            year = DateUtil.getYear(Calendar.getInstance());
        }
    }

    /**
     * @return the year
     */
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
     * @return the month
     */
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
    public int getDay() {
        return day;
    }

    /**
     * @param day the day to set
     */
    public void setDay(int day) {
        this.day = day;
    }
}
