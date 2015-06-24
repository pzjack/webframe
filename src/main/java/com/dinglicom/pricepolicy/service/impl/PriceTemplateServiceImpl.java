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
package com.dinglicom.pricepolicy.service.impl;

import com.dinglicom.frame.sys.domain.BaseMsgBean;
import com.dinglicom.frame.sys.entity.SysOranizagion;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.frame.sys.service.SysOranizagionService;
import com.dinglicom.pricepolicy.demain.OrgDealarResp;
import com.dinglicom.pricepolicy.demain.PriceDetailReq;
import com.dinglicom.pricepolicy.demain.PriceDetailReqItem;
import com.dinglicom.pricepolicy.demain.PriceDetailResp;
import com.dinglicom.pricepolicy.demain.PricePolicyHistoryReq;
import com.dinglicom.pricepolicy.demain.PricePolicyHistoryResp;
import com.dinglicom.pricepolicy.demain.PricePolicyHistoryRespItem;
import com.dinglicom.pricepolicy.demain.PriceTemplateGetResp;
import com.dinglicom.pricepolicy.demain.PriceTemplateReq;
import com.dinglicom.pricepolicy.demain.PriceTemplateResp;
import com.dinglicom.pricepolicy.demain.PriceTemplateRespItem;
import com.dinglicom.pricepolicy.demain.PriceTemplateUpdateReq;
import com.dinglicom.pricepolicy.demain.ProductResp;
import com.dinglicom.pricepolicy.demain.ProductRespItem;
import com.dinglicom.pricepolicy.entity.PricePolicyHistory;
import com.dinglicom.pricepolicy.entity.PriceTemplate;
import com.dinglicom.pricepolicy.entity.PriceTemplateDetail;
import com.dinglicom.pricepolicy.repository.PricePolicyHistoryDao;
import com.dinglicom.pricepolicy.repository.PriceTemplateDao;
import com.dinglicom.pricepolicy.repository.PriceTemplateDetailDao;
import com.dinglicom.pricepolicy.service.PriceTemplateService;
import com.dinglicom.product.domain.ProductItem;
import com.dinglicom.product.entity.UserProduct;
import com.dinglicom.product.service.UserProductService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author panzhen
 */
@Component
@Transactional
public class PriceTemplateServiceImpl implements PriceTemplateService {

    @Resource
    private PriceTemplateDao priceTemplateDao;
    @Resource
    private PriceTemplateDetailDao priceTemplateDetailDao;
    @Resource
    private PricePolicyHistoryDao pricePolicyHistoryDao;
    @Resource
    private UserProductService userProductService;
    @Resource
    private SysOranizagionService sysOranizagionService;
    @PersistenceContext
    private EntityManager em;

    private void doSave(PriceTemplate priceTemplate) {
        priceTemplateDao.save(priceTemplate);
    }

    @Override
    public PriceTemplate save(PriceTemplateUpdateReq req, UserInfo admin) {
        PriceTemplate priceTemplate = new PriceTemplate();
        priceTemplate.setName(req.getName());
        priceTemplate.setDesc(req.getDesc());
        priceTemplate.setCreateman(admin.getRealname());
        priceTemplate.setApply(Boolean.FALSE);
        doSave(priceTemplate);
        return priceTemplate;
    }

    @Override
    public PriceTemplate update(PriceTemplateUpdateReq req) {
        PriceTemplate priceTemplate = priceTemplateDao.findOne(req.getId());
        if (null != priceTemplate) {
            priceTemplate.setName(req.getName());
            priceTemplate.setDesc(req.getDesc());
            doSave(priceTemplate);
        } else {
            throw new RuntimeException("");
        }
        return priceTemplate;
    }

    @Override
    public void delete(Long id, UserInfo admin) {
        PriceTemplate priceTemplate = priceTemplateDao.findOne(id);
        if (null != priceTemplate && !priceTemplate.getApply()) {
            priceTemplate.setSignDelete(Boolean.TRUE);
            doSave(priceTemplate);
            //-- 需要增补逻辑 --
            pricePolicyHistoryDao.deleteByTemplateId(id, new Date(), Boolean.TRUE);
        } else {
            throw new RuntimeException("check data or price template is applied.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PriceTemplateGetResp get(PriceTemplateUpdateReq req) {
        PriceTemplate priceTemplate = priceTemplateDao.findOne(req.getId());
        PriceTemplateGetResp resp = new PriceTemplateGetResp();
        if (null != priceTemplate) {
            resp.setId(priceTemplate.getId());
            resp.setName(priceTemplate.getName());
            resp.setDesc(priceTemplate.getDesc());
            resp.setCreatedate(priceTemplate.getCreateDate());
            resp.setApply(priceTemplate.getApply());
        } else {
            throw new RuntimeException("not found price template by id:" + req.getId());
        }
        return resp;
    }

    @Override
    public void saveDetails(PriceDetailReq req, UserInfo admin) {
        PriceTemplate priceTemplate = priceTemplateDao.findOne(req.getTid());
        Date date = new Date();
        if (null != priceTemplate) {
            List<PriceTemplateDetail> details = priceTemplateDetailDao.findByTemplateId(req.getTid(), Boolean.FALSE);
            Map<Long, PriceTemplateDetail> mapDetails = new HashMap<Long, PriceTemplateDetail>();
            for (PriceTemplateDetail d : details) {
                mapDetails.put(d.getId(), d);
            }
            if (null != req.getData()) {
                List<UserProduct> plist = userProductService.getAll();
                Map<Long, UserProduct> pmap = new HashMap<Long, UserProduct>();
                for (UserProduct p : plist) {
                    pmap.put(p.getId(), p);
                }

                Set<Long> mapProducts = new HashSet<Long>();
                List<PriceTemplateDetail> targets = new ArrayList<PriceTemplateDetail>();
                for (PriceDetailReqItem item : req.getData()) {
                    UserProduct product = pmap.get(item.getPid());
                    if (null == product) {
                        throw new RuntimeException("Not found product by id:" + item.getPid());
                    }
                    if (!mapProducts.contains(item.getPid())) {
                        mapProducts.add(item.getPid());
                    } else {
                        throw new RuntimeException("In price template,must only one product id:" + item.getPid());
                    }
                    PriceTemplateDetail dt;
                    if (null != item.getId() && item.getId() > 0) {//update
                        dt = mapDetails.get(item.getId());
                        if (null == dt) {
                            throw new RuntimeException("Not found price template detail by id:" + item.getId());
                        }
                        if (req.getTid().longValue() != dt.getTemplate().getId()) {
                            throw new RuntimeException("Price template detail by tid:" + dt.getTemplate().getId() + " !=" + req.getTid());
                        }
                    } else {//new add
                        dt = new PriceTemplateDetail();
                        dt.setTemplate(priceTemplate);
                        dt.setCreateDate(date);
                    }
                    dt.setProduct(product);
                    dt.setProductName(product.getShortname());
                    dt.setProducType(product.getProducttype());
                    dt.setReportPrice(item.getPrice());

                    dt.setLastUpdateDate(date);
                    dt.setLastUpdateMan(admin.getRealname());
                    targets.add(dt);
                }
                priceTemplateDetailDao.save(targets);
            }
        } else {
            throw new RuntimeException("not found price template by id:" + req.getTid());
        }
    }

    @Override
    public void deleteDetail(Long detailId, UserInfo admin) {
        PriceTemplateDetail item = priceTemplateDetailDao.findOne(detailId);
        if (null != item && !item.getTemplate().getApply()) {
            item.setSignDelete(Boolean.TRUE);
            item.setLastUpdateDate(new Date());
            item.setLastUpdateMan(admin.getRealname());
            priceTemplateDetailDao.save(item);
        } else {
            throw new RuntimeException("Price template detail id not found or price template is applied.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PriceDetailResp findDetalsByTemplateId(Long tid) {
        PriceDetailResp resp = new PriceDetailResp();
        resp.setData(priceTemplateDetailDao.findDetailsByTemplateId(tid, Boolean.FALSE));
        return resp;
    }

    private String createPriceTemplateHQL(boolean count, PriceTemplateReq req) {
        StringBuilder hqlSb = new StringBuilder("select");
        if (count) {
            hqlSb.append(" count(a.id)");
        } else {
            hqlSb.append(" new com.dinglicom.pricepolicy.demain.PriceTemplateRespItem(a.id, a.name, a.desc, a.apply, a.createDate)");
        }
        hqlSb.append(" from PriceTemplate a where a.signDelete=:signDelete");

        if (null != req.getId() && req.getId() > 0) {
            hqlSb.append(" and a.id=:id");
        }
        if (null != req.getTname() && !req.getTname().isEmpty()) {
            hqlSb.append(" and a.name like :name");
        }

        return hqlSb.toString();
    }

    @Override
    @Transactional(readOnly = true)
    public PriceTemplateResp findTemplate(PriceTemplateReq req) {
        PriceTemplateResp resp = new PriceTemplateResp();
        Page<PriceTemplateRespItem> page;
        String likeStr = "%" + req.getTname() + "%";
        Query qc = em.createQuery(createPriceTemplateHQL(true, req));
        qc.setParameter("signDelete", Boolean.FALSE);
        if (null != req.getId() && req.getId() > 0) {
            qc.setParameter("id", req.getId());
        }
        if (null != req.getTname() && !req.getTname().isEmpty()) {
            qc.setParameter("name", likeStr);
        }
        long count = (Long)qc.getSingleResult();
        long tp = (count + req.getNum() - 1) / req.getNum();
        if (req.getPage() <= tp && count > 0) {
            Query q = em.createQuery(createPriceTemplateHQL(false, req));
            q.setParameter("signDelete", Boolean.FALSE);
            if (null != req.getId() && req.getId() > 0) {
                q.setParameter("id", req.getId());
            }
            if (null != req.getTname() && !req.getTname().isEmpty()) {
                q.setParameter("name", likeStr);
            }
            q.setFirstResult((req.getPage() - 1) * req.getNum());
            q.setMaxResults(req.getNum());
            page = new PageImpl<PriceTemplateRespItem>(q.getResultList(), buildPageRequest(req.getPage(), req.getNum()), count);
        } else {
            page = new PageImpl<PriceTemplateRespItem>(new ArrayList<PriceTemplateRespItem>(), buildPageRequest(req.getPage(), req.getNum()), count);
        }
        resp.setData(page.getContent());
        resp.setTotal_page(page.getTotalPages());
        return resp;
    }
    
    @Override
    @Transactional(readOnly = true)
    public BaseMsgBean getAllProducts() {
        ProductResp resp = new ProductResp();
        List<UserProduct> plist = userProductService.getAll();
        List<ProductRespItem> data = new ArrayList<ProductRespItem>();
        for(UserProduct p : plist) {
            ProductRespItem item = new ProductRespItem();
            item.setId(p.getId());
            item.setName(p.getShortname());
            data.add(item);
        }
        resp.setData(data);
        return resp;
    }
    
    @Override
    @Transactional(readOnly = true)
    public BaseMsgBean getAllDealar(String name) {
        OrgDealarResp resp = new OrgDealarResp();
        resp.setData(sysOranizagionService.findAllDealarAndNoDealarStation(name));
        return resp;
    }
    
    @Override
    public void doApplyDealarAll(Long tid, UserInfo admin) {
        doApplyPriceTemplateToDealar(tid, null, admin);
    }
    
    private void doApplyPriceTemplateToDealar(Long tid, List<Long> dealarList, UserInfo admin) {
        PriceTemplate priceTemplate = priceTemplateDao.findOne(tid);
        Date date = new Date();
        if (null == priceTemplate) {
            throw new RuntimeException("not found price template by id:" + tid);
        }
        List<PriceTemplateDetail> details = priceTemplateDetailDao.findByTemplateId(tid, Boolean.FALSE);
        if (null == details || details.size() <= 0) {
            throw new RuntimeException("The price template id(" + tid + ") not found any price details.");
        }
        List<SysOranizagion> orgs = sysOranizagionService.findDealarAndNoDealarStation(dealarList);
        if (null == orgs || orgs.size() <= 0) {
            throw new RuntimeException("Not found any dealar or station.");
        }
        Map<Long, SysOranizagion> dealarMap = new HashMap<Long, SysOranizagion>();
        List<Long> ids = new ArrayList<Long>();
        for (SysOranizagion org : orgs) {
            ids.add(org.getId());
            dealarMap.put(org.getId(), org);
        }
        pricePolicyHistoryDao.updateByDealarIds(ids, Boolean.FALSE, date, Boolean.FALSE);
        priceTemplate.setApply(Boolean.TRUE);
        priceTemplateDao.save(priceTemplate);

        List<PricePolicyHistory> data = new ArrayList<PricePolicyHistory>();
        for (SysOranizagion dealar : orgs) {
            for (PriceTemplateDetail d : details) {
                PricePolicyHistory history = new PricePolicyHistory();
                history.setTemplate(priceTemplate);
                history.setTemplateDetail(d);
                history.setProduct(d.getProduct());
                history.setDealarStation(dealar);

                history.setCreateDate(date);
                history.setCreateman(admin.getRealname());
                history.setCurrentPolicy(Boolean.TRUE);

                data.add(history);
            }
        }
        pricePolicyHistoryDao.save(data);
    }

    @Override
    public void doApplyDealar(Long tid, String dealarstr, UserInfo admin) {
        String[] tem = dealarstr.split(",");
        List<Long> dealarList = new ArrayList<Long>();
        for(String t : tem) {
            dealarList.add(new Long(t.trim()));
        }
        doApplyPriceTemplateToDealar(tid, dealarList, admin);
    }
    
    @Override
    public void doDisablePriceTemplate(Long tid) {
        PriceTemplate priceTemplate = priceTemplateDao.findOne(tid);
        Date date = new Date();
        if (null == priceTemplate) {
            throw new RuntimeException("not found price template by id:" + tid);
        }
        if(!priceTemplate.getApply()) {
            throw new RuntimeException("Price template state is not apply. id:" + tid);
        }
        priceTemplate.setApply(Boolean.FALSE);
        priceTemplateDao.save(priceTemplate);
        pricePolicyHistoryDao.updateByTemplateId(tid, Boolean.FALSE, date, Boolean.FALSE);
    }
    
    private String createPricePolicyHistoryHQL(boolean count, PricePolicyHistoryReq req) {
        StringBuilder hqlSb = new StringBuilder("select");
        if (count) {
            hqlSb.append(" count(a.id)");
        } else {
            hqlSb.append(" new com.dinglicom.pricepolicy.demain.PricePolicyHistoryRespItem(a.id, a.template.name, a.product.shortname, a.templateDetail.reportPrice, a.currentPolicy, a.dealarStation.name, a.createDate)");
        }
        hqlSb.append(" from PricePolicyHistory a where a.signDelete=:signDelete");
        if (null != req.getId() && req.getId() > 0) {
            hqlSb.append(" and a.template.id=:tid");
        }
        if (null != req.getQuery() && !req.getQuery().isEmpty()) {
            hqlSb.append(" and (a.template.name like :name or a.dealarStation.name like :name)");
        }

        return hqlSb.toString();
    }
    
    
    @Override
    @Transactional(readOnly = true)
    public PricePolicyHistoryResp findPricePolicyHistory(PricePolicyHistoryReq req) {
        PricePolicyHistoryResp resp = new PricePolicyHistoryResp();
        Page<PricePolicyHistoryRespItem> page;
        Query qc = em.createQuery(createPricePolicyHistoryHQL(true, req));
        qc.setParameter("signDelete", Boolean.FALSE);
        if (null != req.getId() && req.getId() > 0) {
            qc.setParameter("tid", req.getId());
        }
        String likeStr = "%" + req.getQuery() + "%";
        if (null != req.getQuery() && !req.getQuery().isEmpty()) {
            qc.setParameter("name", likeStr);
        }
        long count = (Long)qc.getSingleResult();
        long tp = (count + req.getNum() - 1) / req.getNum();
        if (req.getPage() <= tp && count > 0) {
            Query q = em.createQuery(createPricePolicyHistoryHQL(false, req));
            q.setParameter("signDelete", Boolean.FALSE);
            if (null != req.getId() && req.getId() > 0) {
                q.setParameter("tid", req.getId());
            }
            if (null != req.getQuery() && !req.getQuery().isEmpty()) {
                q.setParameter("name", likeStr);
            }
            q.setFirstResult((req.getPage() - 1) * req.getNum());
            q.setMaxResults(req.getNum());
            page = new PageImpl<PricePolicyHistoryRespItem>(q.getResultList(), buildPageRequest(req.getPage(), req.getNum()), count);
        } else {
            page = new PageImpl<PricePolicyHistoryRespItem>(new ArrayList<PricePolicyHistoryRespItem>(), buildPageRequest(req.getPage(), req.getNum()), count);
        }
        resp.setData(page.getContent());
        resp.setTotal_page(page.getTotalPages());
        return resp;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ProductItem> findDealarProduct(Long dealarId) {
         return pricePolicyHistoryDao.findDealarProduct(dealarId, Boolean.FALSE);
     }

    @Override
    @Transactional(readOnly = true)
    public List<PricePolicyHistory> findDealarCurrentPricePolicy(Long dealarId) {
        return pricePolicyHistoryDao.findDealarCurrentPricePolicy(dealarId, Boolean.FALSE);
    }

    /**
     * 创建分页请求.
     */
    private PageRequest buildPageRequest(final int page, final int size) {
        return new PageRequest(page - 1, size);
    }
}
