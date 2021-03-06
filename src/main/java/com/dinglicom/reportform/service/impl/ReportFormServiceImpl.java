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
package com.dinglicom.reportform.service.impl;

import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.frame.sys.service.UserInfoService;
import com.dinglicom.frame.sys.util.DateUtil;
import com.dinglicom.product.entity.UserProduct;
import com.dinglicom.product.service.UserProductService;
import com.dinglicom.reportform.domain.LineProductCountNoTotal;
import com.dinglicom.reportform.domain.LineProductType;
import com.dinglicom.reportform.domain.LineProductTypeCount;
import com.dinglicom.reportform.domain.LineResp;
import com.dinglicom.reportform.domain.LineStationData;
import com.dinglicom.reportform.domain.ProductCount;
import com.dinglicom.reportform.domain.ProductTotalCount;
import com.dinglicom.reportform.domain.ProductTypeTotal;
import com.dinglicom.reportform.domain.ProductTypeTotalNoTotal;
import com.dinglicom.reportform.domain.ReportformReq;
import com.dinglicom.reportform.domain.SaleData;
import com.dinglicom.reportform.domain.SaleStation;
import com.dinglicom.reportform.domain.SalemanResp;
import com.dinglicom.reportform.domain.SalemanTmp;
import com.dinglicom.reportform.domain.StationData;
import com.dinglicom.reportform.domain.StationProductType;
import com.dinglicom.reportform.domain.StationResp;
import com.dinglicom.reportform.service.ReportFormService;
import com.dinglicom.reportnum.demain.LineDataTmp;
import com.dinglicom.reportnum.demain.LineProductItem;
import com.dinglicom.reportnum.service.ReportSubscribeNumberService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author panzhen
 */
@Component
@Transactional
public class ReportFormServiceImpl implements ReportFormService {

    private final static Logger LOG = LoggerFactory.getLogger(ReportFormServiceImpl.class);

    @Resource
    private ReportSubscribeNumberService reportSubscribeNumberService;
    @Resource
    private UserProductService userProductService;

    @Override
    @Transactional(readOnly = true)
    public LineResp queryLinelist(ReportformReq req, UserInfo admin) {
        LineResp resp = new LineResp();
        Calendar c = Calendar.getInstance();
        int year = DateUtil.getYear(c);
        int month = DateUtil.getMonth(c);
        int day = DateUtil.getDay(c);
        if (null != req.getDate()) {
            int p = req.getDate().indexOf("-");
            if (p > 0) {
                month = Integer.valueOf(req.getDate().substring(0, p).trim());
                day = Integer.valueOf(req.getDate().substring(p + 1).trim());
            }
        }
        Map<Integer, LineProductType> ptmap = getAllTypeProduct();
        List<LineProductType> products = new ArrayList<LineProductType>();
        products.addAll(ptmap.values());
        resp.setProducts(products);

        Map<Long, LineStationData> datamap = new HashMap<Long, LineStationData>();
        List<LineStationData> datalist = new ArrayList<LineStationData>();
        resp.setData(datalist);
        
        Map typemap = new HashMap();
        Map<String,ProductCount> pmap = new HashMap<String,ProductCount>();

        List<ProductTypeTotalNoTotal> typetotal = new ArrayList<ProductTypeTotalNoTotal>();
        Map typetotalmap = new HashMap();
        Map<String, ProductTotalCount> ptotalmap = new HashMap<String, ProductTotalCount>();
        initTotalLineData(ptmap, typetotalmap, typetotal, ptotalmap);
        resp.setTotal(typetotal);

        List<LineDataTmp> tmpd = null;
        switch(admin.getUserType()) {
            case UserInfoService.USER_ROLE_MANAGER:
                tmpd = reportSubscribeNumberService.queryLinebydayDepid(year, month, day, admin.getOrg().getId());
                break;
            case UserInfoService.USER_ROLE_SALESMAN:
                tmpd = reportSubscribeNumberService.queryLinebydaySalesId(year, month, day, admin.getId());
                break;
            default:
                tmpd = reportSubscribeNumberService.queryLinebyday(year, month, day);
        }
        if (null != tmpd) {
            for (LineDataTmp t : tmpd) {
                LineStationData line = datamap.get(t.getOrgid());
                if (null == line) {
                    line = new LineStationData();
                    line.setStation(t.getOrgname());
                    line.setDealer(t.getDerlar());
                    
                    datalist.add(line);
                    datamap.put(t.getOrgid(), line);
                    initLineData(ptmap, line, t.getOrgid(), typemap, pmap);
                }
                Object tot = typetotalmap.get(t.getPtype());
                if(null != tot) {
                    if(tot instanceof ProductTypeTotal) {
                        ProductTypeTotal p = (ProductTypeTotal)tot;
                        String tk = t.getPtype() + "_" + t.getPid();
                        ProductTotalCount pc = ptotalmap.get(tk);
                        if(null != pc) {
                            pc.setCnum(t.getRpnum() + pc.getCnum());
                            p.setTotal_cnum(p.getTotal_cnum() + t.getRpnum());
                        }
                    } else if(tot instanceof ProductTypeTotalNoTotal) {
                        String tk = t.getPtype() + "_" + t.getPid();
                        ProductTotalCount pc = ptotalmap.get(tk);
                        if(null != pc) {
                            pc.setCnum(t.getRpnum() + pc.getCnum());
                        }
                    }
                }
                String tk = t.getOrgid() + "_" + t.getPtype();
                String tp = tk + "_" + t.getPid();
                Object ot = typemap.get(tk);
                if (null != ot) {
                    ProductCount pdc = pmap.get(tp);
                    if(null == pdc) continue;
                    pdc.setNum(t.getRpnum());
                    if (ot instanceof LineProductTypeCount) {
                        LineProductTypeCount pc = (LineProductTypeCount) ot;
                        pc.setTotal_num(pc.getTotal_num() + pdc.getNum());
                    }
                }
            }
        }
        return resp;
    }
    
    
    @Override
    @Transactional(readOnly = true)
    public StationResp queryStationlist(ReportformReq req, UserInfo admin) {
        StationResp resp = new StationResp();
        Calendar c = Calendar.getInstance();
        int year = DateUtil.getYear(c);
        int month = DateUtil.getMonth(c);
        int day = DateUtil.getDay(c);
        if (null != req.getDate()) {
            int p = req.getDate().indexOf("-");
            if (p > 0) {
                month = Integer.valueOf(req.getDate().substring(0, p).trim());
                day = Integer.valueOf(req.getDate().substring(p + 1).trim());
            }
        }
        Map<Integer, StationProductType> ptmap = getAllTypeProductForStation();
        List<StationProductType> products = new ArrayList<StationProductType>();
        products.addAll(ptmap.values());
        resp.setProducts(products);

        Map<Long, StationData> datamap = new HashMap<Long, StationData>();
        List<StationData> datalist = new ArrayList<StationData>();
        resp.setData(datalist);
        
        Map<String,LineProductCountNoTotal> typemap = new HashMap<String,LineProductCountNoTotal>();
        Map<String,ProductCount> pmap = new HashMap<String,ProductCount>();

        List<ProductTypeTotalNoTotal> typetotal = new ArrayList<ProductTypeTotalNoTotal>();
        Map<Integer, ProductTypeTotalNoTotal> typetotalmap = new HashMap<Integer, ProductTypeTotalNoTotal>();
        Map<String, ProductTotalCount> ptotalmap = new HashMap<String, ProductTotalCount>();
        initTotalStationData(ptmap, typetotalmap, typetotal, ptotalmap);
        resp.setTotal(typetotal);
        resp.setTotal_num(0L);

        List<LineDataTmp> tmpd = null;
        switch(admin.getUserType()) {
            case UserInfoService.USER_ROLE_MANAGER:
                tmpd = reportSubscribeNumberService.queryLinebydayDepid(year, month, day, admin.getOrg().getId());
                break;
            case UserInfoService.USER_ROLE_SALESMAN:
                tmpd = reportSubscribeNumberService.queryLinebydaySalesId(year, month, day, admin.getId());
                break;
            default:
                tmpd = reportSubscribeNumberService.queryLinebyday(year, month, day);
        }
        if (null != tmpd) {
            for (LineDataTmp t : tmpd) {
                StationData station = datamap.get(t.getOrgid());
                if (null == station) {
                    station = new StationData();
                    station.setTotal_num(0L);
                    station.setStation(t.getOrgname());
                    
                    datalist.add(station);
                    datamap.put(t.getOrgid(), station);
                    initStationData(ptmap, station, t.getOrgid(), typemap, pmap);
                }
                ProductTypeTotalNoTotal tot = typetotalmap.get(t.getPtype());
                if (null != tot) {
                    String tk = t.getPtype() + "_" + t.getPid();
                    ProductTotalCount pc = ptotalmap.get(tk);
                    if (null != pc) {
                        pc.setCnum(t.getRpnum() + pc.getCnum());
                    }
                }
                String tk = t.getOrgid() + "_" + t.getPtype();
                String tp = tk + "_" + t.getPid();
                LineProductCountNoTotal ot = typemap.get(tk);
                if (null != ot) {
                    ProductCount pdc = pmap.get(tp);
                    if(null == pdc) continue;
                    pdc.setNum(t.getRpnum());
                    station.setTotal_num(station.getTotal_num() + pdc.getNum());
                    resp.setTotal_num(pdc.getNum() + resp.getTotal_num());
                }
            }
        }
        return resp;
    }

    private void initStationData(Map<Integer, StationProductType> src, StationData state, Long orgid, Map<String,LineProductCountNoTotal> typemap, Map<String,ProductCount> pmap) {
        Iterator<Entry<Integer, StationProductType>> it = src.entrySet().iterator();
        List<LineProductCountNoTotal> typelist = new ArrayList<LineProductCountNoTotal>();
        while (it.hasNext()) {
            Entry<Integer, StationProductType> e = it.next();
            LineProductCountNoTotal pc = new LineProductCountNoTotal();
            List<ProductCount> detail = new ArrayList<ProductCount>();
            pc.setCid(e.getKey());
            pc.setDetail(detail);
            String tk = orgid + "_" + e.getKey();
            typemap.put(tk, pc);
            typelist.add(pc);
            state.setCat(typelist);
            if(null != e.getValue() && null != e.getValue().getDetail()) {
                List<LineProductItem> list = e.getValue().getDetail();
                for(LineProductItem item : list) {
                    ProductCount pdc = new ProductCount();
                    pdc.setPid(item.getPid());
                    pdc.setNum(0L);
                    detail.add(pdc);
                    pmap.put(tk + "_" +  pdc.getPid(), pdc);
                }
            }
        }
    }
    
    
    private void initTotalStationData(Map<Integer, StationProductType> src, Map<Integer, ProductTypeTotalNoTotal> typetotalmap, List<ProductTypeTotalNoTotal> typetotallist, Map<String,ProductTotalCount> ptotalmap) {
        Iterator<Entry<Integer, StationProductType>> it = src.entrySet().iterator();
        while (it.hasNext()) {
            Entry<Integer, StationProductType> e = it.next();
            ProductTypeTotalNoTotal p = new ProductTypeTotalNoTotal();
            p.setCid(e.getKey());
            typetotallist.add(p);
            typetotalmap.put(e.getKey(), p);
            
            List<ProductTotalCount> detail = new ArrayList<ProductTotalCount>();
            p.setDetail(detail);
            if(null != e.getValue() && null != e.getValue().getDetail()) {
                List<LineProductItem> list = e.getValue().getDetail();
                for(LineProductItem item : list) {
                    ProductTotalCount pdc = new ProductTotalCount();
                    pdc.setCnum(0L);
                    pdc.setPid(item.getPid());
                    detail.add(pdc);
                    ptotalmap.put(e.getKey() + "_" +  pdc.getPid(), pdc);
                }
            }
        }
    }
    
    private void initTotalLineData(Map<Integer, LineProductType> src, Map typetotalmap, List<ProductTypeTotalNoTotal> typetotallist, Map<String,ProductTotalCount> ptotalmap) {
        Iterator<Entry<Integer, LineProductType>> it = src.entrySet().iterator();
        while (it.hasNext()) {
            Entry<Integer, LineProductType> e = it.next();
            ProductTypeTotalNoTotal p;
            if (1 == e.getKey()) {
                p = new ProductTypeTotal();
            } else {
                p = new ProductTypeTotalNoTotal();
            }
            p.setCid(e.getKey());
            typetotallist.add(p);
            typetotalmap.put(e.getKey(), p);
            
            List<ProductTotalCount> detail = new ArrayList<ProductTotalCount>();
            p.setDetail(detail);
            if(null != e.getValue() && null != e.getValue().getDetail()) {
                List<LineProductItem> list = e.getValue().getDetail();
                for(LineProductItem item : list) {
                    ProductTotalCount pdc = new ProductTotalCount();
                    pdc.setPid(item.getPid());
                    detail.add(pdc);
                    ptotalmap.put(e.getKey() + "_" +  pdc.getPid(), pdc);
                }
            }
        }
    }

    private void initLineData(Map<Integer, LineProductType> src, LineStationData line, Long orgid, Map typemap, Map<String,ProductCount> pmap) {
        Iterator<Entry<Integer, LineProductType>> it = src.entrySet().iterator();
        List<LineProductCountNoTotal> typelist = new ArrayList<LineProductCountNoTotal>();
        while (it.hasNext()) {
            Entry<Integer, LineProductType> e = it.next();
            LineProductCountNoTotal pc;
            if (1 == e.getKey()) {
                LineProductTypeCount p = new LineProductTypeCount();
                p.setTotal_num(0L);
                pc = p;
            } else {
                pc = new LineProductCountNoTotal();
            }
            List<ProductCount> detail = new ArrayList<ProductCount>();
            pc.setCid(e.getKey());
            pc.setDetail(detail);
            String tk = orgid + "_" + e.getKey();
            typemap.put(tk, pc);
            typelist.add(pc);
            line.setCat(typelist);
            if(null != e.getValue() && null != e.getValue().getDetail()) {
                List<LineProductItem> list = e.getValue().getDetail();
                for(LineProductItem item : list) {
                    ProductCount pdc = new ProductCount();
                    pdc.setPid(item.getPid());
                    pdc.setNum(0L);
                    detail.add(pdc);
                    pmap.put(tk + "_" +  pdc.getPid(), pdc);
                }
            }
        }
    }

    private Map<Integer, LineProductType> getAllTypeProduct() {
        Map<Integer, LineProductType> ptmap = new HashMap<Integer, LineProductType>();
        List<UserProduct> allprd = userProductService.getAll();
        for (UserProduct p : allprd) {
            LineProductType t = ptmap.get(p.getProducttype());
            if (null == t) {
                t = new LineProductType();
                switch (p.getProducttype()) {
                    case 1:
                        t.setCid(p.getProducttype());
                        t.setCname("瓶装奶");
                        t.setNeed_total(true);
                        break;
                    default:
                        t.setCid(p.getProducttype());
                        t.setCname("酸奶");
                        t.setNeed_total(false);
                }
                List<LineProductItem> detail = new ArrayList<LineProductItem>();
                t.setDetail(detail);
                ptmap.put(p.getProducttype(), t);
            }
            LineProductItem ptm = new LineProductItem();
            ptm.setPid(p.getId());
            ptm.setPname(p.getShortname());
            t.getDetail().add(ptm);
        }
        return ptmap;
    }

    private Map<Integer, StationProductType> getAllTypeProductForStation() {
        Map<Integer, StationProductType> ptmap = new HashMap<Integer, StationProductType>();
        List<UserProduct> allprd = userProductService.getAll();
        for (UserProduct p : allprd) {
            StationProductType t = ptmap.get(p.getProducttype());
            if (null == t) {
                t = new StationProductType();
                switch (p.getProducttype()) {
                    case 1:
                        t.setCid(p.getProducttype());
                        t.setCname("瓶装奶");
                        break;
                    default:
                        t.setCid(p.getProducttype());
                        t.setCname("酸奶");
                }
                List<LineProductItem> detail = new ArrayList<LineProductItem>();
                t.setDetail(detail);
                ptmap.put(p.getProducttype(), t);
            }
            LineProductItem ptm = new LineProductItem();
            ptm.setPid(p.getId());
            ptm.setPname(p.getShortname());
            t.getDetail().add(ptm);
        }
        return ptmap;
    }

    @Override
    @Transactional(readOnly = true)
    public SalemanResp querySalemanlist(ReportformReq req, UserInfo admin) {
        SalemanResp resp = new SalemanResp();
        Calendar c = Calendar.getInstance();
        int year = DateUtil.getYear(c);
        int month = DateUtil.getMonth(c);
        int day = DateUtil.getDay(c);
        if (null != req.getDate()) {
            int p = req.getDate().indexOf("-");
            if (p > 0) {
                month = Integer.valueOf(req.getDate().substring(0, p).trim());
                day = Integer.valueOf(req.getDate().substring(p + 1).trim());
            }
        }
        List<LineProductItem> products = getAllProduct();
        resp.setProducts(products);

        
        Map<Long, ProductCount> ptotalmap = new HashMap<Long, ProductCount>();
        List<ProductCount> ptotaldata = new ArrayList<ProductCount>();
        initTotalSalemanData(products, ptotalmap);
        ptotaldata.addAll(ptotalmap.values());
        resp.setTotal(ptotaldata);
        
        List<SaleData> datalist = new ArrayList<SaleData>();
        resp.setData(datalist);
        Map<String,ProductCount> pmap = new HashMap<String,ProductCount>();
        Map<Long,SaleData> smap = new HashMap<Long,SaleData>();
        Map<String,SaleStation> stationmap = new HashMap<String,SaleStation>();
        
        LOG.info("Login user role[{}]", admin.getUserType());
        List<SalemanTmp> tmpd = null;
        switch(admin.getUserType()) {
            case UserInfoService.USER_ROLE_MANAGER:
                LOG.info("User id({}) dep manager, query salesman data." );
                tmpd = reportSubscribeNumberService.querySalemanbydayDepid(year, month, day, admin.getOrg().getId());
                break;
            default:
                LOG.info("User id({}) admin, query salesman data." );
                tmpd = reportSubscribeNumberService.querySalemanbyday(year, month, day);
        }
        if (null != tmpd) {
            for (SalemanTmp t : tmpd) {
                String sk = t.getSalemanid() + "_" + t.getOrgid();
                String pk = t.getSalemanid() + "_" + t.getOrgid() + "_" + t.getPid();
                SaleData line = smap.get(t.getSalemanid());
                if (null == line) {
                    line = new SaleData();
                    line.setSalesman(t.getSalemanname());
                    
                    List<SaleStation> detail = new ArrayList<SaleStation>();
                    line.setDetail(detail);
                    
                    datalist.add(line);
                    smap.put(t.getSalemanid(), line);
                }
                SaleStation stot = stationmap.get(sk);
                if(null == stot) {
                    stot = new SaleStation();
                    stot.setStation(t.getOrgname());
                    line.getDetail().add(stot);
                    //Add All product
                    stationmap.put(sk, stot);
                    initSalemanData(products, sk, stot, pmap);
                }
                ProductCount pc = pmap.get(pk);
                if (null != pc) {
                    pc.setNum(t.getRpnum());
                    ProductCount tpc = ptotalmap.get(t.getPid());
                    if (null != tpc) {
                        tpc.setNum(pc.getNum() + tpc.getNum());
                    }
                }
            }
        }
        return resp;
    }
    
    private List<LineProductItem> getAllProduct() {
        List<LineProductItem> list = new  ArrayList<LineProductItem>();
        List<UserProduct> allprd = userProductService.getAll();
        for (UserProduct p : allprd) {
            LineProductItem item = new LineProductItem();
            item.setPid(p.getId());
            item.setPname(p.getShortname());
            list.add(item);
        }
        return list;
    }
    
    
    
    private void initTotalSalemanData(List<LineProductItem> products,  Map<Long,ProductCount> ptotalmap) {
        Iterator<LineProductItem> it = products.iterator();
        while (it.hasNext()) {
            LineProductItem pm = it.next();
            ProductCount p = ptotalmap.get(pm.getPid());
            if (p == null) {
                p = new ProductCount();
                p.setPid(pm.getPid());
                ptotalmap.put(pm.getPid(), p);
            }
        }
    }
    
    
    private void initSalemanData(List<LineProductItem> src, String statinkey, SaleStation station, Map<String,ProductCount> pmap) {
        List<ProductCount> cat = new ArrayList<ProductCount>();
        Iterator<LineProductItem> it = src.iterator();
        while (it.hasNext()) {
            LineProductItem e = it.next();
            String key = statinkey + "_" + e.getPid();
            ProductCount pc = pmap.get(key);
            if(null ==  pc) {
                pc = new ProductCount();
                pc.setPid(e.getPid());
                pmap.put(key, pc);
                cat.add(pc);
            }
        }
        station.setProducts(cat);
    }
}
