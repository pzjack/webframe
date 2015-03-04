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
public class DepDetail extends DepItem {
    private String manager;
    private String tel;
    private String desc;
    
    public DepDetail() {}
    public DepDetail(Long id, String name, String manager, String tel, String desc) {
        this.did = id;
        this.department = name;
        this.manager = manager;
        this.tel = tel;
        this.desc = desc;
    }

    /**
     * @return the manager
     */
    public String getManager() {
        return manager;
    }

    /**
     * @param manager the manager to set
     */
    public void setManager(String manager) {
        this.manager = manager;
    }

    /**
     * @return the tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel the tel to set
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

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
}
