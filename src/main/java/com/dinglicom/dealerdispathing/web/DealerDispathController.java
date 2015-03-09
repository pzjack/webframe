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

package com.dinglicom.dealerdispathing.web;

import com.dinglicom.about.domain.AboutusItemResp;
import com.dinglicom.about.entity.Aboutus;
import com.dinglicom.dealerdispathing.domain.DealerDispatchResp;
import com.dinglicom.dealerdispathing.domain.DealerDispathReq;
import com.dinglicom.dealerdispathing.service.DealerDispatchServcie;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.frame.sys.service.SysTokenService;
import com.dinglicom.frame.web.AppControllerBase;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author panzhen
 */
public class DealerDispathController extends AppControllerBase {
    private final static Logger LOG = LoggerFactory.getLogger(DealerDispathController.class);
    @Resource
    private SysTokenService sysTokenService;
    @Resource
    private DealerDispatchServcie dealerDispatchServcie;
    
    
    @RequestMapping(value = "/get")
    public @ResponseBody
    DealerDispatchResp get(DealerDispathReq req) {
        DealerDispatchResp msg = new DealerDispatchResp();
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 >= req.getMid() || 0 >= req.getPage() || 0 >= req.getNum() || null == req.getStatus()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Dealer query dispatching list page fail:", e);
            msg.setCode(1);
            msg.setResult("查询失败");
            return msg;
        }
        return msg;
    }
}
