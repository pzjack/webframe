/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.reportnum.demain;

/**
 *
 * @author panzhen
 */
public class LineDataTmp {
    private Long orgid;
    private String orgname;
    private Long pid;
    private String pname;
    private Integer ptype;
    private Long rpnum;
    private String derlar;
    
    public LineDataTmp() {}
    public LineDataTmp(Long orgid, String orgname, Long pid, String pname, Integer ptype, Long rpnum) {
        this.orgid = orgid;
        this.orgname = orgname;
        this.pid = pid;
        this.pname = pname;
        this.ptype = ptype;
        this.rpnum = rpnum;
    }
    public LineDataTmp(Long orgid, String orgname, String dealer, Long pid, String pname, Integer ptype, Long rpnum) {
        this.orgid = orgid;
        this.orgname = orgname;
        this.derlar = dealer;
        this.pid = pid;
        this.pname = pname;
        this.ptype = ptype;
        this.rpnum = rpnum;
    }

    /**
     * @return the orgid
     */
    public Long getOrgid() {
        return orgid;
    }

    /**
     * @param orgid the orgid to set
     */
    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    /**
     * @return the orgname
     */
    public String getOrgname() {
        return orgname;
    }

    /**
     * @param orgname the orgname to set
     */
    public void setOrgname(String orgname) {
        this.orgname = orgname;
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
     * @return the pname
     */
    public String getPname() {
        return pname;
    }

    /**
     * @param pname the pname to set
     */
    public void setPname(String pname) {
        this.pname = pname;
    }

    /**
     * @return the ptype
     */
    public Integer getPtype() {
        return ptype;
    }

    /**
     * @param ptype the ptype to set
     */
    public void setPtype(Integer ptype) {
        this.ptype = ptype;
    }

    /**
     * @return the rpnum
     */
    public Long getRpnum() {
        return rpnum;
    }

    /**
     * @param rpnum the rpnum to set
     */
    public void setRpnum(Long rpnum) {
        this.rpnum = rpnum;
    }

    /**
     * @return the derlar
     */
    public String getDerlar() {
        return derlar;
    }

    /**
     * @param derlar the derlar to set
     */
    public void setDerlar(String derlar) {
        this.derlar = derlar;
    }
}
