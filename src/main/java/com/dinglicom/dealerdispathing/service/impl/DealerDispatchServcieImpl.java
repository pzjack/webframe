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
package com.dinglicom.dealerdispathing.service.impl;

import com.dinglicom.dealerdispathing.domain.DealerDispatchItem;
import com.dinglicom.dealerdispathing.domain.DealerDispatchResp;
import com.dinglicom.dealerdispathing.domain.DealerDispathReq;
import com.dinglicom.dealerdispathing.service.DealerDispatchServcie;
import com.dinglicom.frame.sys.entity.SysOranizagion;
import com.dinglicom.frame.sys.service.SysOranizagionService;
import com.dinglicom.frame.sys.util.DateUtil;
import com.dinglicom.product.service.UserProductService;
import com.dinglicom.reportnum.entity.ReportSubscribeNumber;
import com.dinglicom.reportnum.service.ReportSubscribeNumberService;
import com.dinglicom.reportnum.service.impl.EveryDayEveryOrgReportServiceImpl;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author panzhen
 */
public class DealerDispatchServcieImpl implements DealerDispatchServcie {

    private final static Logger LOG = LoggerFactory.getLogger(EveryDayEveryOrgReportServiceImpl.class);
    @Resource
    private ReportSubscribeNumberService reportSubscribeNumberService;
    @Resource
    private SysOranizagionService sysOranizagionService;
    @Resource
    private UserProductService userProductService;

    public void autoGettoDealerProduct() {
        Calendar c = Calendar.getInstance();
        c = DateUtil.getPreDay(c, 1);//前一天
        int year = DateUtil.getYear(c);
        int month = DateUtil.getMonth(c);
        int day = DateUtil.getDay(c);
        Date cur = new Date();
        List<SysOranizagion> dls = sysOranizagionService.findAllDealer();//查找所有奶站
        for (SysOranizagion d : dls) {
            List<ReportSubscribeNumber> rptDetails = reportSubscribeNumberService.findByDealerAndDate(d.getId(), year, month, day);
            for (ReportSubscribeNumber rpt : rptDetails) {
                com.dinglicom.dealerdispathing.entity.DealerDispatchItem dpc = createDispathingRecord(cur, rpt);
            }
        }
    }

    private com.dinglicom.dealerdispathing.entity.DealerDispatchItem createDispathingRecord(Date cur, ReportSubscribeNumber rpt) {
        com.dinglicom.dealerdispathing.entity.DealerDispatchItem d = new com.dinglicom.dealerdispathing.entity.DealerDispatchItem();
        if (null != rpt.getDealer()) {
            d.setDealer(rpt.getDealer());
            d.setDealername(rpt.getDealer().getName());
            d.setDealermanager(rpt.getDealer().getResponsible_man());
        }
        if (null != rpt.getOrg()) {
            d.setWorkstation(rpt.getOrg());
            d.setStationname(rpt.getOrg().getName());
            d.setStationphone(rpt.getOrg().getPhone());
            d.setStationmanager(rpt.getOrg().getResponsible_man());
            d.setStationaddress(rpt.getOrg().getAddress());
        }
        
        if(null != rpt.getProduct()) {
            d.setProduct(rpt.getProduct());
            d.setProductname(rpt.getProductname());
            d.setProducttype(rpt.getProducttype());
        }
        
        d.setDispathingdate(cur);
        d.setDispathstate(DealerDispatchServcie.DISPATH_STATE_NONE);
        return d;
    }

    @Transactional(readOnly = true)
    public DealerDispatchResp queryReportlist(DealerDispathReq req) {
        DealerDispatchResp resp = new DealerDispatchResp();
        resp.setProducts(userProductService.findAllProduct());
        Calendar c = Calendar.getInstance();
        c = DateUtil.getPreDay(c, 1);//前一天
        int year = DateUtil.getYear(c);
        int month = DateUtil.getMonth(c);
        int day = DateUtil.getDay(c);
        if (DealerDispatchServcie.DISPATH_STATE_NONE.equalsIgnoreCase(req.getStatus())) {

        }
        return resp;
    }
}
