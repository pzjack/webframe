/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.frame.sys.domain;

/**
 *
 * @author panzhen
 */
public class WebAddDlvryReq extends AdminReqBase {

    private long did;
    private String name;
    private String manager;
    private String tel;
    private String desc;
    private long pid;
    private long cid;
    private long rid;

    /**
     * @return the did
     */
    public long getDid() {
        return did;
    }

    /**
     * @param did the did to set
     */
    public void setDid(long did) {
        this.did = did;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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

    /**
     * @return the pid
     */
    public long getPid() {
        return pid;
    }

    /**
     * @param pid the pid to set
     */
    public void setPid(long pid) {
        this.pid = pid;
    }

    /**
     * @return the cid
     */
    public long getCid() {
        return cid;
    }

    /**
     * @param cid the cid to set
     */
    public void setCid(long cid) {
        this.cid = cid;
    }

    /**
     * @return the rid
     */
    public long getRid() {
        return rid;
    }

    /**
     * @param rid the rid to set
     */
    public void setRid(long rid) {
        this.rid = rid;
    }
}
