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
public class UserInfoUpdateReq extends AppRequestBase {
    private String realname;
    private String manager;
    private String tel;
    private Long pid;
    private Long cid;
    private Long rid;
    private String address;
    private String old_pwd;
    private String pwd;

    /**
     * @return the realname
     */
    public String getRealname() {
        return realname;
    }

    /**
     * @param realname the realname to set
     */
    public void setRealname(String realname) {
        this.realname = realname;
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
     * @return the pid
     */
    public Long getPid() {
        return pid;
    }

    /**
     * @param pid the pid to set
     */
    public void setPid(Long pid) {
        this.pid = pid;
    }

    /**
     * @return the cid
     */
    public Long getCid() {
        return cid;
    }

    /**
     * @param cid the cid to set
     */
    public void setCid(Long cid) {
        this.cid = cid;
    }

    /**
     * @return the rid
     */
    public Long getRid() {
        return rid;
    }

    /**
     * @param rid the rid to set
     */
    public void setRid(Long rid) {
        this.rid = rid;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the old_pwd
     */
    public String getOld_pwd() {
        return old_pwd;
    }

    /**
     * @param old_pwd the old_pwd to set
     */
    public void setOld_pwd(String old_pwd) {
        this.old_pwd = old_pwd;
    }

    /**
     * @return the pwd
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * @param pwd the pwd to set
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
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
}
