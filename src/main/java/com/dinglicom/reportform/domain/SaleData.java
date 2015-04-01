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
public class SaleData {
    private String salesman;
    private List<SaleStation> detail;

    /**
     * @return the salesman
     */
    public String getSalesman() {
        return salesman;
    }

    /**
     * @param salesman the salesman to set
     */
    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    /**
     * @return the detail
     */
    public List<SaleStation> getDetail() {
        return detail;
    }

    /**
     * @param detail the detail to set
     */
    public void setDetail(List<SaleStation> detail) {
        this.detail = detail;
    }
}
