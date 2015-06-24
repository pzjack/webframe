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

package com.dinglicom.pricepolicy.service;

import com.dinglicom.frame.sys.domain.BaseMsgBean;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.pricepolicy.demain.PriceDetailReq;
import com.dinglicom.pricepolicy.demain.PriceDetailResp;
import com.dinglicom.pricepolicy.demain.PricePolicyHistoryReq;
import com.dinglicom.pricepolicy.demain.PricePolicyHistoryResp;
import com.dinglicom.pricepolicy.demain.PriceTemplateGetResp;
import com.dinglicom.pricepolicy.demain.PriceTemplateReq;
import com.dinglicom.pricepolicy.demain.PriceTemplateResp;
import com.dinglicom.pricepolicy.demain.PriceTemplateUpdateReq;
import com.dinglicom.pricepolicy.entity.PricePolicyHistory;
import com.dinglicom.pricepolicy.entity.PriceTemplate;
import com.dinglicom.product.domain.ProductItem;
import java.util.List;

/**
 *
 * @author panzhen
 */
public interface PriceTemplateService {

    PriceTemplate save(PriceTemplateUpdateReq req, UserInfo admin);
    
    PriceTemplate update(PriceTemplateUpdateReq req);
    
    void delete(Long id, UserInfo admin);
    
    PriceTemplateGetResp get(PriceTemplateUpdateReq req);
    
    void saveDetails(PriceDetailReq req, UserInfo admin);
    
    void deleteDetail(Long detailId, UserInfo admin);
    
    /**
     * 根据价格模板编号查询价格模板列表
     * @param tid
     * @return 
     */
    PriceDetailResp findDetalsByTemplateId(Long tid);
    
    /**
     * 查询价格模板列表
     * @param req
     * @return 
     */
    PriceTemplateResp findTemplate(PriceTemplateReq req);
    /**
     * 获取所有商品
     * @return 
     */
    BaseMsgBean getAllProducts();
    /**
     * 所有经销商或者无经销商奶站
     * @param name
     * @return 
     */
    BaseMsgBean getAllDealar(String name);
    
    /**
     * 应用价格模板到经销商
     * @param tid
     * @param dealarstr
     * @param admin 
     */
    void doApplyDealar(Long tid, String dealarstr, UserInfo admin);
    
    /**
     * 应用价格模板到所有经销商或者奶站
     * @param tid
     * @param admin 
     */
    void doApplyDealarAll(Long tid, UserInfo admin);
    
    /**
     * 使已经应用的价格模板失效
     * @param tid 
     */
    void doDisablePriceTemplate(Long tid);
    
    /**
     * 价格价格模板应用历史记录
     * @param req
     * @return 
     */
    PricePolicyHistoryResp findPricePolicyHistory(PricePolicyHistoryReq req);
    
    /**
     * 查询经销商或者奶站的产品列表
     * @param dealarId
     * @return 
     */
    List<ProductItem> findDealarProduct(Long dealarId);
     
    /**
     * 查询经销商或奶站的价格策略
     * @param dealarId
     * @return 
     */
    List<PricePolicyHistory> findDealarCurrentPricePolicy(Long dealarId);
}
