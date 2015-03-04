/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.dep.service.impl;

import com.dinglicom.dep.domain.CompanyDetail;
import com.dinglicom.dep.domain.CompanyDetailResp;
import com.dinglicom.dep.domain.CompanyReq;
import com.dinglicom.dep.domain.DepDetail;
import com.dinglicom.dep.domain.DepDetailResp;
import com.dinglicom.dep.domain.DepItem;
import com.dinglicom.dep.domain.DepListResp;
import com.dinglicom.dep.domain.DepReq;
import com.dinglicom.dep.service.DepService;
import com.dinglicom.frame.sys.entity.SysOranizagion;
import com.dinglicom.frame.sys.repository.SysOranizagionDao;
import com.dinglicom.frame.sys.service.SysOranizagionService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author panzhen
 */
@Component
@Transactional
public class DepServiceImpl implements DepService {

    @Resource
    private SysOranizagionDao sysOranizagionDao;

    @Override
    public SysOranizagion save(SysOranizagion org) {
        return sysOranizagionDao.save(org);
    }

    @Override
    public void update(SysOranizagion org) {
        sysOranizagionDao.save(org);
    }

    @Override
    @Transactional(readOnly = true)
    public SysOranizagion read(Long id) {
        return sysOranizagionDao.findOne(id);
    }

    @Transactional(readOnly = true)
    public SysOranizagion getCompany() {
        List<SysOranizagion> coms = sysOranizagionDao.findByType(SysOranizagionService.ORG_TYPE_COM, Boolean.FALSE);
        if (coms.size() > 0) {
            return coms.get(0);
        }
        return null;
    }

    @Override
    public void addCompany(CompanyReq req) {
        if (null != getCompany()) {
            throw new RuntimeException("Company info is exists.");
        }
        SysOranizagion com = new SysOranizagion();
        com.setName(req.getCompany());
        com.setAddress(req.getAddress());
        com.setResponsible_man(req.getManager());
        com.setResponsible_phone(req.getTel());
        com.setPhone(req.getTel());
        com.setType(SysOranizagionService.ORG_TYPE_COM);

        save(com);
    }

    @Override
    public void updateCompany(CompanyReq req) {
        SysOranizagion com = getCompany();
        if (null == com) {
            com = new SysOranizagion();
//            throw new RuntimeException("Company info is not exists.");
        }
        if (null != req.getCompany()) {
            com.setName(req.getCompany());
        }
        if (null != req.getAddress()) {
            com.setAddress(req.getAddress());
        }
        if (null != req.getTel()) {
            com.setPhone(req.getTel());
            com.setResponsible_phone(req.getTel());
        }
        if (null != req.getManager()) {
            com.setResponsible_man(req.getManager());
        }

        update(com);
    }

    @Override
    public SysOranizagion addDep(DepReq req) {
        SysOranizagion company = getCompany();
        if (null == company) {
            throw new RuntimeException("Company info is exists.");
        }
        SysOranizagion dep = new SysOranizagion();
        dep.setParent(company);
        dep.setName(req.getDepartment());
//        dep.setResponsible_man(req.getManager());
//        dep.setResponsible_phone(req.getTel());
//        dep.setPhone(req.getTel());
        dep.setType(SysOranizagionService.ORG_TYPE_DEP);
        dep.setDesc(req.getDesc());
        return save(dep);
    }

    @Override
    public void updateDep(DepReq req) {
        SysOranizagion dep = read(req.getDid());
        if (null == dep) {
            throw new RuntimeException("Department info is not exists for id:" + req.getDid());
        }
        if (null != req.getDepartment()) {
            dep.setName(req.getDepartment());
        }
//        if (null != req.getManager()) {
//            dep.setResponsible_man(req.getManager());
//        }
//        if (null != req.getTel()) {
//            dep.setResponsible_phone(req.getTel());
//            dep.setPhone(req.getTel());
//        }
        if(null != req.getDesc()) {
            dep.setDesc(req.getDesc());
        }
        update(dep);
    }

    @Override
    public void deleteDep(DepReq req) {
        SysOranizagion dep = read(req.getDid());
        if (null == dep) {
            throw new RuntimeException("Department info is not exists for id:" + req.getDid());
        }
        dep.setSignDelete(Boolean.TRUE);
        update(dep);
    }
    
    @Override
    @Transactional(readOnly = true)
    public DepListResp getDepList() {
        DepListResp resp = new DepListResp();
        List<DepItem> data = sysOranizagionDao.findAllDep(SysOranizagionService.ORG_TYPE_DEP, Boolean.FALSE);
        resp.setData(data);
        return resp;
    }
    
    @Override
    @Transactional(readOnly = true)
    public DepDetailResp getDepDetail(Long did) {
        DepDetailResp resp = new DepDetailResp();
        DepDetail data = sysOranizagionDao.findDepDetailById(did);
        resp.setData(data);
        return resp;
    }
    
    @Override
    @Transactional(readOnly = true)
    public CompanyDetailResp getCompanyDetail() {
        CompanyDetailResp resp = new CompanyDetailResp();
        List<CompanyDetail> list = sysOranizagionDao.findAllCompany(SysOranizagionService.ORG_TYPE_COM, Boolean.FALSE);
        if(list.size() > 0) {
            resp.setData(list.get(0));
        }
        return resp;
    }
}
