/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.reportnum.demain;

import com.dinglicom.frame.sys.domain.AdminPageReqBase;

/**
 *
 * @author panzhen
 */
public class LineDistributionQuery extends AdminPageReqBase {
    private String timetype;
    private String date;
    private String role;
    private String query;

    /**
     * @return the timetype
     */
    public String getTimetype() {
        return timetype;
    }

    /**
     * @param timetype the timetype to set
     */
    public void setTimetype(String timetype) {
        this.timetype = timetype;
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
        this.date = date;
    }

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
     * @return the query
     */
    public String getQuery() {
        return query;
    }

    /**
     * @param query the query to set
     */
    public void setQuery(String query) {
        this.query = query;
    }

}
