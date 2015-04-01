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

import java.util.List;

/**
 *
 * @author panzhen
 */
public class SaleStation {
    private String station;
    private List<ProductCount> products;

    /**
     * @return the station
     */
    public String getStation() {
        return station;
    }

    /**
     * @param station the station to set
     */
    public void setStation(String station) {
        this.station = station;
    }

    /**
     * @return the products
     */
    public List<ProductCount> getProducts() {
        return products;
    }

    /**
     * @param products the products to set
     */
    public void setProducts(List<ProductCount> products) {
        this.products = products;
    }
}
