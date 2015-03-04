/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.frame.sys.service;

import com.dinglicom.frame.sys.domain.CityResp;
import com.dinglicom.frame.sys.domain.DeliveryOrgRespItem;
import com.dinglicom.frame.sys.domain.DlvryPageReq;
import com.dinglicom.frame.sys.domain.DlvryPageResp;
import com.dinglicom.frame.sys.domain.ProvinceResp;
import com.dinglicom.frame.sys.domain.RegionResp;
import com.dinglicom.frame.sys.domain.WebAddDlvryReq;
import com.dinglicom.frame.sys.domain.WorkerOrgReq;
import com.dinglicom.frame.sys.domain.WorkerOrgRespItem;
import com.dinglicom.frame.sys.entity.SysOranizagion;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.salesman.domain.StationDetailResp;
import com.dinglicom.salesman.domain.StationQueryReq;
import com.dinglicom.salesman.domain.StationQueryResp;
import com.dinglicom.salesman.domain.StationUpldateReq;
import java.util.List;

/**
 *
 * @author panzhen
 */
public interface SysOranizagionService {

    final static String ORG_TYPE_COR = "cor";//集团
    final static String ORG_TYPE_COM = "com";//公司
    final static String ORG_TYPE_DEP = "dep";//部门
    final static String ORG_TYPE_NZH = "STATION";//奶站
    final static String ORG_TYPE_DLV = "dlv";//配送商
    final static String ORG_TYPE_DEALER = "DEALER";//经销商
    final static String ORG_TYPE_RPR = "pri";//行政区省(直辖市)
    final static String ORG_TYPE_RCT = "cty";//行政区市
    final static String ORG_TYPE_RRG = "reg";//行政区区(县)

    SysOranizagion read(long id);

    SysOranizagion save(SysOranizagion org);

    Iterable<SysOranizagion> save(List<SysOranizagion> orgs);

    List<SysOranizagion> findAllWorkerOrg();
    
    List<SysOranizagion> findAllDeliveryOrg();

    /**
     * 查找所有奶站
     *
     * @param req
     * @return
     */
    List<WorkerOrgRespItem> findAllWorkerOrg(WorkerOrgReq req);

    /**
     * 查找所有第三方配送单位
     *
     * @param req
     * @return
     */
    List<DeliveryOrgRespItem> findAllDeliveryOrg(WorkerOrgReq req);
    
    /**
     * 查找特定类型的组织结构
     * @param type
     * @return 
     */
    List<SysOranizagion> findOrgByType(String type);

    /**
     * 查找特定父机构特定类型的组织结构
     * @param parentid
     * @param type
     * @return 
     */
    List<SysOranizagion> findOrgByParentAndType(long parentid, String type);
    
    /**
     * 查询所有的省
     * @return 
     */
    ProvinceResp findAllProvince();
    
    /**
     * 查询省对应的城市
     * @param provinceId
     * @return 
     */
    CityResp findCityByProvince(long provinceId);
    
    /**
     * 查询城市对应的区
     * @param cityId
     * @return 
     */
    RegionResp findRegionByCity(long cityId);
    /**
     * 添加配送方信息
     * @param req
     * @return 
     */
    SysOranizagion addDelivery(WebAddDlvryReq req);
    /**
     * 修改配送方信息
     * @param req
     * @return 
     */
    SysOranizagion updateDelivery(WebAddDlvryReq req);
    /**
     * 删除配送方，根据ID信息
     * @param did 
     */
    void deleteDelivery(long did);
    /**
     * 按照行政区查找配送方信息
     * @param req
     * @return 
     */
    DlvryPageResp getPageDlvry(DlvryPageReq req);
    
    /**
     * 添加奶站或经销商
     * @param req
     * @param salesman
     * @return 
     */
    SysOranizagion addStation(StationUpldateReq req, UserInfo salesman);
    
    /**
     * 修改奶站或者经销商
     * @param req
     * @return 
     */
    SysOranizagion updateStation(StationUpldateReq req);
    
    /**
     * 删除奶站及下属的用户账号信息
     * 需谨慎操作
     * @param org 
     */
    void deleteOrg(SysOranizagion org);
    
    /**
     * 显示该业务员的所有奶站或经销商信息
     * @param req
     * @param salesid
     * @return 
     */
    StationQueryResp findStationList(StationQueryReq req, long salesid);
    
    /**
     * 查询奶站或者经销商详情信息
     * @param id
     * @return 
     */
    StationDetailResp findById(long id);
}
