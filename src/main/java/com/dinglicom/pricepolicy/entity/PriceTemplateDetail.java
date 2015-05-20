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
package com.dinglicom.pricepolicy.entity;

import com.dinglicom.frame.entity.EntityExt;
import com.dinglicom.product.entity.UserProduct;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author panzhen
 */
@Entity
@Table(name = "price_template_detail")
public class PriceTemplateDetail extends EntityExt implements java.io.Serializable {

    private UserProduct product;
    private String productName;
    private Integer producType;
    private Double reportPrice;
    private PriceTemplate template;
    private String lastUpdateMan;
    private Date lastUpdateDate;

    /**
     * @return the product
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    public UserProduct getProduct() {
        return product;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(UserProduct product) {
        this.product = product;
    }

    /**
     * @return the productName
     */
    @Column(name = "product_name")
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName the productName to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * @return the producType
     */
    @Column(name = "product_type")
    public Integer getProducType() {
        return producType;
    }

    /**
     * @param producType the producType to set
     */
    public void setProducType(Integer producType) {
        this.producType = producType;
    }

    /**
     * @return the reportPrice
     */
    @Column(name = "report_price")
    public Double getReportPrice() {
        return reportPrice;
    }

    /**
     * @param reportPrice the reportPrice to set
     */
    public void setReportPrice(Double reportPrice) {
        this.reportPrice = reportPrice;
    }

    /**
     * @return the template
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    public PriceTemplate getTemplate() {
        return template;
    }

    /**
     * @param template the template to set
     */
    public void setTemplate(PriceTemplate template) {
        this.template = template;
    }

    /**
     * @return the lastUpdateMan
     */
    @Column(name = "last_update_man")
    public String getLastUpdateMan() {
        return lastUpdateMan;
    }

    /**
     * @param lastUpdateMan the lastUpdateMan to set
     */
    public void setLastUpdateMan(String lastUpdateMan) {
        this.lastUpdateMan = lastUpdateMan;
    }

    /**
     * @return the lastUpdateDate
     */
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "last_update_date")
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * @param lastUpdateDate the lastUpdateDate to set
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}
