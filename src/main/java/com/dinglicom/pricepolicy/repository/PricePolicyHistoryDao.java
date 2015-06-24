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

import com.dinglicom.pricepolicy.entity.PricePolicyHistory;
import com.dinglicom.product.domain.ProductItem;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author panzhen
 */
public interface PricePolicyHistoryDao extends PagingAndSortingRepository<PricePolicyHistory, Long>, JpaSpecificationExecutor<PricePolicyHistory> {

//    @Query("from PricePolicyHistory t where t.dealarStation.id in (:ids) and t.template.apply=:apply  and t.signDelete=:signDelete")
//    List<PricePolicyHistory> findByTemplate(@Param(value = "ids") List<Long> ids, @Param(value = "apply") Boolean apply, @Param(value = "signDelete") Boolean signDelete);

    @Modifying
    @Query("update PricePolicyHistory t set t.signDelete=:signDelete, t.lastUpdateDate=:updatedate where t.template.id=:templateId")
    int deleteByTemplateId(@Param(value = "templateId")Long templateId, @Param(value = "updatedate") Date updatedate, @Param(value = "signDelete") Boolean signDelete);

    @Modifying
    @Query("update PricePolicyHistory t set t.currentPolicy=:currentPolicy, t.lastUpdateDate=:updatedate where t.dealarStation.id in (:ids) and t.signDelete=:signDelete")
    int updateByDealarIds(@Param(value = "ids") List<Long> ids, @Param(value = "currentPolicy") Boolean currentPolicy, @Param(value = "updatedate") Date updatedate, @Param(value = "signDelete") Boolean signDelete); 

    @Modifying
    @Query("update PricePolicyHistory t set t.currentPolicy=:currentPolicy, t.lastUpdateDate=:updatedate where t.template.id=:templateId and t.signDelete=:signDelete")
    int updateByTemplateId(@Param(value = "templateId") Long templateId, @Param(value = "currentPolicy") Boolean currentPolicy, @Param(value = "updatedate") Date updatedate, @Param(value = "signDelete") Boolean signDelete);
    
    @Query("select new com.dinglicom.product.domain.ProductItem(t.product.id, t.product.shortname) from PricePolicyHistory t where t.currentPolicy=true and t.dealarStation.id=:dealarId and t.signDelete=:signDelete")
    List<ProductItem> findDealarProduct(@Param(value = "dealarId") Long dealarId, @Param(value = "signDelete") Boolean signDelete);
    
    @Query("from PricePolicyHistory t where t.currentPolicy=true and t.dealarStation.id=:dealarId and t.signDelete=:signDelete")
    List<PricePolicyHistory> findDealarCurrentPricePolicy(@Param(value = "dealarId") Long dealarId, @Param(value = "signDelete") Boolean signDelete);
}
