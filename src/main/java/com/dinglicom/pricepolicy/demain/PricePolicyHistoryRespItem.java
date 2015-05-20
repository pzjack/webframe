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

package com.dinglicom.pricepolicy.demain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author panzhen
 */
public class PricePolicyHistoryRespItem {
    private Long id;
    private String tname;
    private  String pname;
    private Double price;
    private Boolean cpolicy;
    private String dname;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createdate;
    
    public PricePolicyHistoryRespItem () {}
    public PricePolicyHistoryRespItem (Long id, String tname, String pname, Double price, Boolean cpolicy, String dname, Date createdate) {
        this.id = id;
        this.tname = tname;
        this.pname = pname;
        this.price = price;
        this.cpolicy = cpolicy;
        this.dname = dname;
        this.createdate = createdate;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the tname
     */
    public String getTname() {
        return tname;
    }

    /**
     * @param tname the tname to set
     */
    public void setTname(String tname) {
        this.tname = tname;
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
     * @return the cpolicy
     */
    public Boolean getCpolicy() {
        return cpolicy;
    }

    /**
     * @param cpolicy the cpolicy to set
     */
    public void setCpolicy(Boolean cpolicy) {
        this.cpolicy = cpolicy;
    }

    /**
     * @return the dname
     */
    public String getDname() {
        return dname;
    }

    /**
     * @param dname the dname to set
     */
    public void setDname(String dname) {
        this.dname = dname;
    }

    /**
     * @return the createdate
     */
    public Date getCreatedate() {
        return createdate;
    }

    /**
     * @param createdate the createdate to set
     */
    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }
}
