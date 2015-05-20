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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author panzhen
 */
@Entity
@Table(name = "price_template")
public class PriceTemplate extends EntityExt implements java.io.Serializable {

    private String name;
    private String desc;
    private Boolean apply;
    private String createman;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the desc
     */
    @Column(name = "temptable_desc")
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return the apply
     */
    public Boolean getApply() {
        return apply;
    }

    /**
     * @param apply the apply to set
     */
    public void setApply(Boolean apply) {
        this.apply = apply;
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
