/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.dep.service;

import com.dinglicom.dep.domain.CompanyDetailResp;
import com.dinglicom.dep.domain.CompanyReq;
import com.dinglicom.dep.domain.DepDetailResp;
import com.dinglicom.dep.domain.DepListResp;
import com.dinglicom.dep.domain.DepReq;
import com.dinglicom.frame.sys.entity.SysOranizagion;

/**
 *
 * @author panzhen
 */
public interface DepService {

    SysOranizagion save(SysOranizagion org);
    
    void update(SysOranizagion org);
    
    SysOranizagion read(Long id);
    
    void addCompany(CompanyReq req);
    
    void updateCompany(CompanyReq req);
    
    SysOranizagion addDep(DepReq req);
    
    void updateDep(DepReq req);
    
    void deleteDep(DepReq req);
    
    DepListResp getDepList();
    
    DepDetailResp getDepDetail(Long did);
    
    CompanyDetailResp getCompanyDetail();
}
