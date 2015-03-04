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
public class WorkerOrgReq extends AppRequestBase {
    private Long province;
    private Long city;
    private Long region;

    /**
     * @return the province
     */
    public Long getProvince() {
        return province;
    }

    /**
     * @param province the province to set
     */
    public void setProvince(Long province) {
        this.province = province;
    }

    /**
     * @return the city
     */
    public Long getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(Long city) {
        this.city = city;
    }

    /**
     * @return the region
     */
    public Long getRegion() {
        return region;
    }

    /**
     * @param region the region to set
     */
    public void setRegion(Long region) {
        this.region = region;
    }
}
