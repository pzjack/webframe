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

package com.dinglicom.reportnum.demain;

/**
 *
 * @author panzhen
 */
public class ReportDetailRespItem {
    private String org_name;
    private Integer ptype;
    private Long pid;
    private String pname;
    private Long pnum;
    private Double price;
    private Double total_price;
    
    public ReportDetailRespItem() {}
    public ReportDetailRespItem(String orgName, Integer ptype, Long pid, String pname, Long pnum, Double price, Double totalPrice) {
        this.org_name = orgName;
        this.ptype = ptype;
        this.pid = pid;
        this.pname = pname;
        this.pnum = pnum;
        this.price = price;
        this.total_price = totalPrice;
    }

    /**
     * @return the org_name
     */
    public String getOrg_name() {
        return org_name;
    }

    /**
     * @param org_name the org_name to set
     */
    public void setOrg_name(String org_name) {
        this.org_name = org_name;
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
     * @return the pnum
     */
    public Long getPnum() {
        return pnum;
    }

    /**
     * @param pnum the pnum to set
     */
    public void setPnum(Long pnum) {
        this.pnum = pnum;
    }

    /**
     * @return the price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * @return the total_price
     */
    public Double getTotal_price() {
        return total_price;
    }

    /**
     * @param total_price the total_price to set
     */
    public void setTotal_price(Double total_price) {
        this.total_price = total_price;
    }
}
