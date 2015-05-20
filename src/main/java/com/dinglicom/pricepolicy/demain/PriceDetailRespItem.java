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

package com.dinglicom.pricepolicy.demain;

/**
 *
 * @author panzhen
 */
public class PriceDetailRespItem extends PriceDetailReqItem {
    private String pname;
    
    public PriceDetailRespItem() {}
    public PriceDetailRespItem(Long id, Long pid, String pname, Double price) {
        this.id = id;
        this.pid = pid;
        this.pname = pname;
        this.price = price;
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
