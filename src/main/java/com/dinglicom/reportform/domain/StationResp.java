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

import com.dinglicom.frame.sys.domain.BaseMsgBean;
import java.util.List;

/**
 *
 * @author panzhen
 */
public class StationResp  extends BaseMsgBean {
    private List<StationProductType> products;
    private List<StationData> data;
    private List<ProductTypeTotalNoTotal> total;
    private Long total_num;

    /**
     * @return the products
     */
    public List<StationProductType> getProducts() {
        return products;
    }

    /**
     * @param products the products to set
     */
    public void setProducts(List<StationProductType> products) {
        this.products = products;
    }

    /**
     * @return the data
     */
    public List<StationData> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<StationData> data) {
        this.data = data;
    }

    /**
     * @return the total
     */
    public List<ProductTypeTotalNoTotal> getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(List<ProductTypeTotalNoTotal> total) {
        this.total = total;
    }

    /**
     * @return the total_num
     */
    public Long getTotal_num() {
        return total_num;
    }

    /**
     * @param total_num the total_num to set
     */
    public void setTotal_num(Long total_num) {
        this.total_num = total_num;
    }
}
