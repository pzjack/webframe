/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.product.domain;

/**
 *
 * @author panzhen
 */
public class ProductItem {
    private Long pid;
    private String pname;
    
    public ProductItem() {}
    public ProductItem(Long productId, String productName) {
        this.pid = productId;
        this.pname = productName;
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
}
