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

import com.dinglicom.reportnum.demain.LineProductItem;
import java.util.List;

/**
 *
 * @author panzhen
 */
public class LineProductType {
    private Integer cid;
    private String cname;
    private Boolean need_total;
    private List<LineProductItem> detail;

    /**
     * @return the cid
     */
    public Integer getCid() {
        return cid;
    }

    /**
     * @param cid the cid to set
     */
    public void setCid(Integer cid) {
        this.cid = cid;
    }

    /**
     * @return the cname
     */
    public String getCname() {
        return cname;
    }

    /**
     * @param cname the cname to set
     */
    public void setCname(String cname) {
        this.cname = cname;
    }

    /**
     * @return the need_total
     */
    public Boolean getNeed_total() {
        return need_total;
    }

    /**
     * @param need_total the need_total to set
     */
    public void setNeed_total(Boolean need_total) {
        this.need_total = need_total;
    }

    /**
     * @return the detail
     */
    public List<LineProductItem> getDetail() {
        return detail;
    }

    /**
     * @param detail the detail to set
     */
    public void setDetail(List<LineProductItem> detail) {
        this.detail = detail;
    }
}
