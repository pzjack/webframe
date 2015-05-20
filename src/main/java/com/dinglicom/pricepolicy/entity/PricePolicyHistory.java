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
import com.dinglicom.frame.sys.entity.SysOranizagion;
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
@Table(name = "price_policy_history")
public class PricePolicyHistory extends EntityExt implements java.io.Serializable {
    private SysOranizagion dealarStation;
    private PriceTemplateDetail templateDetail;
    private PriceTemplate template;
    private UserProduct product;
    private Boolean currentPolicy;
    private Date lastUpdateDate;
    private String createman;

    /**
     * @return the dealarStation
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dealar_station_id")
    public SysOranizagion getDealarStation() {
        return dealarStation;
    }

    /**
     * @param dealarStation the dealarStation to set
     */
    public void setDealarStation(SysOranizagion dealarStation) {
        this.dealarStation = dealarStation;
    }

    /**
     * @return the templateDetail
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_detail_id")
    public PriceTemplateDetail getTemplateDetail() {
        return templateDetail;
    }

    /**
     * @param templateDetail the templateDetail to set
     */
    public void setTemplateDetail(PriceTemplateDetail templateDetail) {
        this.templateDetail = templateDetail;
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
     * @return the currentPolicy
     */
    @Column(name = "current_policy")
    public Boolean getCurrentPolicy() {
        return currentPolicy;
    }

    /**
     * @param currentPolicy the currentPolicy to set
     */
    public void setCurrentPolicy(Boolean currentPolicy) {
        this.currentPolicy = currentPolicy;
    }

    /**
     * @return the lastUpdateDate
     */
    @Column(name = "last_update_date")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * @param lastUpdateDate the lastUpdateDate to set
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    /**
     * @return the createman
     */
    public String getCreateman() {
        return createman;
    }

    /**
     * @param createman the createman to set
     */
    public void setCreateman(String createman) {
        this.createman = createman;
    }
}
