/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.dep.domain;

import com.dinglicom.frame.sys.domain.AdminReqBase;

/**
 *
 * @author panzhen
 */
public class DepReq extends AdminReqBase {
    private Long did;
    private String department;
//    private String manager;
    private String desc;
//    private String tel;

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

//    /**
//     * @return the manager
//     */
//    public String getManager() {
//        return manager;
//    }
//
//    /**
//     * @param manager the manager to set
//     */
//    public void setManager(String manager) {
//        this.manager = manager;
//    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

//    /**
//     * @return the tel
//     */
//    public String getTel() {
//        return tel;
//    }
//
//    /**
//     * @param tel the tel to set
//     */
//    public void setTel(String tel) {
//        this.tel = tel;
//    }

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
