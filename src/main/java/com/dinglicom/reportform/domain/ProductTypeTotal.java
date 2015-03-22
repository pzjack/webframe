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

/**
 *
 * @author panzhen
 */
public class ProductTypeTotal extends ProductTypeTotalNoTotal {
    private Long total_cnum = 0L;

    /**
     * @return the total_cnum
     */
    public Long getTotal_cnum() {
        return total_cnum;
    }

    /**
     * @param total_cnum the total_cnum to set
     */
    public void setTotal_cnum(Long total_cnum) {
        this.total_cnum = total_cnum;
    }
}
