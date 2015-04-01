/*
 * Copyright 2015 Jack.Alexander
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dinglicom.reportform.domain;

/**
 *
 * @author panzhen
 */
public class SalemanTmp {
    private Long orgid;
    private String orgname;
    private Long salemanid;
    private String salemanname;
    private Long pid;
    private String pname;
    private Long rpnum;
    
    public SalemanTmp() {}
    
    public SalemanTmp(Long stationid, String stationname, Long salemanid, String salemanname, Long pid, String pname, Long num) {
        this.orgid = stationid;
        this.orgname = stationname;
        this.salemanid = salemanid;
        this.salemanname = salemanname;
        this.pid = pid;
        this.pname = pname;
        this.rpnum = num;
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
     * @return the salemanid
     */
    public Long getSalemanid() {
        return salemanid;
    }

    /**
     * @param salemanid the salemanid to set
     */
    public void setSalemanid(Long salemanid) {
        this.salemanid = salemanid;
    }

    /**
     * @return the salemanname
     */
    public String getSalemanname() {
        return salemanname;
    }

    /**
     * @param salemanname the salemanname to set
     */
    public void setSalemanname(String salemanname) {
        this.salemanname = salemanname;
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

}
