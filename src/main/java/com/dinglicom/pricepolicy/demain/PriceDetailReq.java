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

import com.dinglicom.frame.sys.domain.AdminReqBase;
import java.util.List;

/**
 *
 * @author panzhen
 */
public class PriceDetailReq extends AdminReqBase {
    private Long tid;
    private List<PriceDetailReqItem> data;

    /**
     * @return the tid
     */
    public Long getTid() {
        return tid;
    }

    /**
     * @param tid the tid to set
     */
    public void setTid(Long tid) {
        this.tid = tid;
    }

    /**
     * @return the data
     */
    public List<PriceDetailReqItem> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<PriceDetailReqItem> data) {
        this.data = data;
    }
}
