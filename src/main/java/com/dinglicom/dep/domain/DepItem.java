/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.dep.domain;

/**
 *
 * @author panzhen
 */
public class DepItem {
    protected Long did;
    protected String department;
    
    public DepItem() {}
    public DepItem(Long id, String name) {
        this.did = id;
        this.department = name;
    }

    /**
     * @return the did
     */
    public Long getDid() {
        return did;
    }

    /**
     * @param did the did to set
     */
    public void setDid(Long did) {
        this.did = did;
    }

    /**
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }
}
