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

package com.dinglicom.reportnum.demain;

import com.dinglicom.frame.sys.domain.BaseMsgBean;
import java.util.List;

/**
 *
 * @author panzhen
 */
public class ReportDetailResp extends BaseMsgBean {
    private Integer total_page;
    private List<ReportDetailRespItem> data;


    /**
     * @return the data
     */
    public List<ReportDetailRespItem> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<ReportDetailRespItem> data) {
        this.data = data;
    }

    /**
     * @return the total_page
     */
    public Integer getTotal_page() {
        return total_page;
    }

    /**
     * @param total_page the total_page to set
     */
    public void setTotal_page(Integer total_page) {
        this.total_page = total_page;
    }
}
