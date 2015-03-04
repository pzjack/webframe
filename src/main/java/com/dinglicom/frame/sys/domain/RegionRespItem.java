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
public class RegionRespItem {

    private long rid;
    private String region;

    public RegionRespItem(){}
    public RegionRespItem(long rid, String region) {
        this.rid = rid;
        this.region = region;
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

    /**
     * @return the region
     */
    public String getRegion() {
        return region;
    }

    /**
     * @param region the regions to set
     */
    public void setRegion(String region) {
        this.region = region;
    }
}
