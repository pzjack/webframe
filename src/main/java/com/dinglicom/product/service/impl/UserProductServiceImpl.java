/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.product.service.impl;

import com.dinglicom.product.domain.AppProductDetail;
import com.dinglicom.product.domain.AppUserProduct;
import com.dinglicom.product.domain.ProductItem;
import com.dinglicom.product.domain.WebProductDetailResp;
import com.dinglicom.product.domain.WebUserProduct;
import com.dinglicom.product.entity.UserProduct;
import com.dinglicom.product.repository.UserProductDao;
import com.dinglicom.product.service.UserProductService;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author panzhen
 */
@Component
@Transactional
@CacheConfig(cacheNames = "productCache")
public class UserProductServiceImpl implements UserProductService {
    @Resource
    private UserProductDao userProductDao;

    @Override
    @CacheEvict(allEntries=true)
    public UserProduct save(UserProduct userProduct) {
        return userProductDao.save(userProduct);
    }
    @Override
    @CacheEvict(allEntries=true)
    public UserProduct update(UserProduct userProduct) {
        return userProductDao.save(userProduct);
    }
    
    @Override
    @Transactional(readOnly = true)
    @CachePut(key = "#id")
    public UserProduct read(long id) {
        return userProductDao.findOne(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public AppProductDetail getAppDetail(long id) {
        UserProduct userProduct = read(id);
        AppProductDetail apd = new AppProductDetail();
        if(null != userProduct) {
            apd.setProduct_id(userProduct.getId());
            apd.setProduct_name(userProduct.getName());
            apd.setBig_pic(userProduct.getBigPic());
            apd.setBrand(userProduct.getBrand());
            apd.setModel(userProduct.getModel());
            apd.setHome_price(userProduct.getHomePrice());
            apd.setPrice(userProduct.getPrice());
            apd.setWeight(userProduct.getWeight());
            apd.setShort_desc(userProduct.getShortDesc());
            apd.setCode(0);
            apd.setResult("成功");
        } else {
            apd.setCode(1);
            apd.setResult("请求的商品不存在");
        }
        return apd;
    }
    
    @Override
    @Transactional(readOnly = true)
    public WebProductDetailResp getAppDetailWeb(long id) {
        UserProduct userProduct = read(id);
        WebProductDetailResp apd = new WebProductDetailResp();
        if(null != userProduct) {
            apd.setProduct_id(userProduct.getId());
            apd.setProduct_name(userProduct.getName());
            apd.setBig_pic(userProduct.getBigPic());
            apd.setSmall_pic(userProduct.getSmallPic());
            apd.setBrand(userProduct.getBrand());
            apd.setModel(userProduct.getModel());
            apd.setHome_price(userProduct.getHomePrice());
            apd.setPrice(userProduct.getPrice());
            apd.setWeight(userProduct.getWeight());
            apd.setShort_desc(userProduct.getShortDesc());
            apd.setShort_name(userProduct.getShortname());
            apd.setType_desc(userProduct.getProducttype());
            apd.setView_range(userProduct.getViewrange());
            apd.setCode(0);
            apd.setResult("成功");
        } else {
            apd.setCode(1);
            apd.setResult("请求的商品不存在");
        }
        return apd;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable
    public Page<AppUserProduct> findByDefinePage(int page, int size) {
        return userProductDao.findBySignDelete(buildPageRequest(page, size), UserProductService.P_VIEW_ALL, Boolean.FALSE);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable
    public Page<WebUserProduct> findByAllPage(int page, int size) {
        return userProductDao.findByNotDelete(buildPageRequest(page, size), Boolean.FALSE);
    }

//    @Override
    @Transactional(readOnly = true)
//    @Cacheable
    public Page<UserProduct> findByPage(int size, int number) {
        return userProductDao.findAll(new Specification<UserProduct>() {
            @Override
            public Predicate toPredicate(Root<UserProduct> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                List<Predicate> condition = Lists.newArrayList();
                Path<Boolean> delete = root.get("signDelete");
//                Path<String> name = root.get("name");
//                return cb.and(cb.equal(delete, false), cb.like(name, "%00%"));
                return cb.equal(delete, false);
            }
        }, buildPageRequest(number, size));
    }

    @Override
    public List<AppUserProduct> coverAppOut(Page<UserProduct> page) {
        List<AppUserProduct> list = Lists.newArrayList();
        Iterator<UserProduct> it = page.getContent().iterator();
        while (it.hasNext()) {
            UserProduct p = it.next();
            AppUserProduct ap = new AppUserProduct();
            ap.setProduct_id(p.getId());
            ap.setProduct_name(p.getName());
            ap.setPrice(p.getPrice());
            ap.setWeight(p.getWeight());
            ap.setSmall_pic(p.getSmallPic());
            list.add(ap);
        }
        return list;
    }

    @Transactional(readOnly = true)
//    @Cacheable
//    @Override
    public Iterable<UserProduct> findByPage() {
        return userProductDao.findAll(
                new Specification<UserProduct>() {
                    @Override
                    public Predicate toPredicate(Root<UserProduct> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                        List<Predicate> condition = Lists.newArrayList();
                        Path path = root.get("delete");
                        condition.add(cb.equal(path, Boolean.FALSE));
                        path = root.get("name");
                        condition.add(cb.like(path, "00"));
                        if (!condition.isEmpty()) {
                            cb.and(condition.toArray(new Predicate[condition.size()]));
                        }
                        return cb.conjunction();
                    }
                }
        );
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ProductItem> findAllProduct() {
        return userProductDao.findByAllProduct(Boolean.FALSE);
    }

    /**
     * 创建分页请求.
     */
    private PageRequest buildPageRequest(final int page, final int size) {
        return new PageRequest(page - 1, size);
    }

    @Override
    public List<UserProduct> getAll() {
        return userProductDao.findByAll(Boolean.FALSE);
    }

//    /**
//     * 创建动态查询条件组合.
//     */
//    private Specification<UserProduct> buildSpecification(Long userId, Map<String, Object> searchParams) {
//        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
//        filters.put("user.id", new SearchFilter("user.id", Operator.EQ, userId));
//        Specification<UserProduct> spec = DynamicSpecifications.bySearchFilter(filters.values(), UserProduct.class);
//        return spec;
//    }
}
