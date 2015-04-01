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
import com.dinglicom.reportnum.demain.LineProductItem;
import java.util.List;

/**
 *
 * @author panzhen
 */
public class SalemanResp extends BaseMsgBean {
    private List<LineProductItem> products;
    private List<SaleData> data;
    private List<ProductCount> total;

    /**
     * @return the products
     */
    public List<LineProductItem> getProducts() {
        return products;
    }

    /**
     * @param products the products to set
     */
    public void setProducts(List<LineProductItem> products) {
        this.products = products;
    }

    /**
     * @return the data
     */
    public List<SaleData> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<SaleData> data) {
        this.data = data;
    }

    /**
     * @return the total
     */
    public List<ProductCount> getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(List<ProductCount> total) {
        this.total = total;
    }
}
