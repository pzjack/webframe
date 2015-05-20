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

package com.dinglicom.pricepolicy.repository;

import com.dinglicom.pricepolicy.demain.PriceDetailRespItem;
import com.dinglicom.pricepolicy.entity.PriceTemplateDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author panzhen
 */
public interface PriceTemplateDetailDao extends PagingAndSortingRepository<PriceTemplateDetail, Long>, JpaSpecificationExecutor<PriceTemplateDetail> {

    @Query("from PriceTemplateDetail t where t.template.id=:templateId and t.signDelete=:signDelete")
    List<PriceTemplateDetail> findByTemplateId(@Param(value = "templateId") Long templateId, @Param(value = "signDelete") Boolean signDelete);
    
    @Query("select new com.dinglicom.pricepolicy.demain.PriceDetailRespItem(id, product.id, productName, reportPrice) from PriceTemplateDetail t where t.template.id=:templateId and t.signDelete=:signDelete")
    List<PriceDetailRespItem> findDetailsByTemplateId(@Param(value = "templateId") Long templateId, @Param(value = "signDelete") Boolean signDelete);
    
}
