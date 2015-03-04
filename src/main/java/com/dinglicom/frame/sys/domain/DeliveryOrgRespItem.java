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
public class DeliveryOrgRespItem {
    private long deliverer_id;
    private String deliverer_name;
    
    public DeliveryOrgRespItem() {}
    public DeliveryOrgRespItem(long id, String name) {
        this.deliverer_id = id;
        this.deliverer_name = name;
    }

    /**
     * @return the deliverer_id
     */
    public long getDeliverer_id() {
        return deliverer_id;
    }

    /**
     * @param deliverer_id the deliverer_id to set
     */
    public void setDeliverer_id(long deliverer_id) {
        this.deliverer_id = deliverer_id;
    }

    /**
     * @return the deliverer_name
     */
    public String getDeliverer_name() {
        return deliverer_name;
    }

    /**
     * @param deliverer_name the deliverer_name to set
     */
    public void setDeliverer_name(String deliverer_name) {
        this.deliverer_name = deliverer_name;
    }
}
