/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.frame.sys.repository;

import com.dinglicom.dep.domain.CompanyDetail;
import com.dinglicom.dep.domain.DepDetail;
import com.dinglicom.dep.domain.DepItem;
import com.dinglicom.frame.sys.domain.CityRespItem;
import com.dinglicom.frame.sys.domain.DeliveryOrgRespItem;
import com.dinglicom.frame.sys.domain.DlvryPageItem;
import com.dinglicom.frame.sys.domain.ProvinceRespItem;
import com.dinglicom.frame.sys.domain.RegionRespItem;
import com.dinglicom.frame.sys.domain.WorkerOrgRespItem;
import com.dinglicom.frame.sys.entity.SysOranizagion;
import com.dinglicom.pricepolicy.demain.OrgDealarRespItem;
import com.dinglicom.salesman.domain.StationRespItem;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author panzhen
 */
public interface SysOranizagionDao extends PagingAndSortingRepository<SysOranizagion, Long> {
    /**
     * 查找特定类型的组织机构
     * @param type
     * @param signDelete
     * @return 
     */
    @Query("from SysOranizagion a where a.type=:type and a.signDelete=:signDelete")
    List<SysOranizagion> findByType(@Param(value = "type")String type, @Param(value = "signDelete")Boolean signDelete);

    /**
     * 查找特定父机构特定类型的组织机构
     * @param parentid
     * @param type
     * @param signDelete
     * @return 
     */
    @Query("from SysOranizagion a where a.parent.id=:parentid and a.type=:type and a.signDelete=:signDelete")
    List<SysOranizagion> findByParentAndType(@Param(value = "parentid")long parentid, @Param(value = "type")String type, @Param(value = "signDelete")Boolean signDelete);
    
    /**
     * 查询奶站
     * @param type
     * @param province
     * @param city
     * @param region
     * @param signDelete
     * @return 
     */
    @Query("select new com.dinglicom.frame.sys.domain.WorkerOrgRespItem(id, name, address) from SysOranizagion a where a.type=:type and a.province.id=:province and a.city.id=:city and a.region.id=:region and a.signDelete=:signDelete")
    List<WorkerOrgRespItem> findWorkerStation(@Param(value = "type")String type, @Param(value = "province")Long province, @Param(value = "city")Long city, @Param(value = "region")Long region, @Param(value = "signDelete")Boolean signDelete);

    /**
     * 查询第三方配送
     * @param type
     * @param province
     * @param city
     * @param region
     * @param signDelete
     * @return 
     */
    @Query("select new com.dinglicom.frame.sys.domain.DeliveryOrgRespItem(id, name) from SysOranizagion a where a.type=:type and a.province.id=:province and a.city.id=:city and a.region.id=:region and a.signDelete=:signDelete")
    List<DeliveryOrgRespItem> findDeliveryStation(@Param(value = "type")String type, @Param(value = "province")Long province, @Param(value = "city")Long city, @Param(value = "region")Long region, @Param(value = "signDelete")Boolean signDelete);
    
    /**
     * 所有省
     * @param type
     * @param signDelete
     * @return 
     */
    @Query("select new com.dinglicom.frame.sys.domain.ProvinceRespItem(id,name) from SysOranizagion a where a.type=:type and a.signDelete=:signDelete")
    List<ProvinceRespItem> findByProvince(@Param(value = "type")String type, @Param(value = "signDelete")Boolean signDelete);
    
    /**
     * 省对应的所有市
     * @param parentid
     * @param type
     * @param signDelete
     * @return 
     */
    @Query("select new com.dinglicom.frame.sys.domain.CityRespItem(id,name) from SysOranizagion a where a.parent.id=:parentid and a.type=:type and a.signDelete=:signDelete")
    List<CityRespItem> findByCity(@Param(value = "parentid")long parentid, @Param(value = "type")String type, @Param(value = "signDelete")Boolean signDelete);
    
    /**
     * 城市所包含的所有区县
     * @param parentid
     * @param type
     * @param signDelete
     * @return 
     */
    @Query("select new com.dinglicom.frame.sys.domain.RegionRespItem(id,name) from SysOranizagion a where a.parent.id=:parentid and a.type=:type and a.signDelete=:signDelete")
    List<RegionRespItem> findByRegion(@Param(value = "parentid")long parentid, @Param(value = "type")String type, @Param(value = "signDelete")Boolean signDelete);
    
    /**
     * 按照行政区域及类型查询组织结构
     * 查询配送方信息
     * @param page
     * @param type
     * @param pid
     * @param cid
     * @param rid
     * @param signDelete
     * @return 
     */
    @Query("select new com.dinglicom.frame.sys.domain.DlvryPageItem(id,name,responsible_man,phone,a.desc,province_name,city_name,region_name) from SysOranizagion a where a.province.id=:pid and a.city.id=:cid and a.region.id=:rid and a.type=:type and a.signDelete=:signDelete")
    Page<DlvryPageItem> findByProvinceCityRegion(Pageable page, @Param(value = "type")String type, @Param(value = "pid")long pid, @Param(value = "cid")long cid, @Param(value = "rid")long rid, @Param(value = "signDelete")Boolean signDelete);
    
    /**
     * 查找奶站或者经销商
     * @param page
     * @param salesid
     * @param type
     * @param signDelete
     * @return 
     */
    @Query("select new com.dinglicom.salesman.domain.StationRespItem(id, name, responsible_man, phone) from SysOranizagion a where a.userinfo.id=:salesid and a.type=:type and a.signDelete=:signDelete")
    Page<StationRespItem> findByStationType(Pageable page, @Param(value = "salesid")long salesid, @Param(value = "type")String type, @Param(value = "signDelete")Boolean signDelete);
    
    
    /**
     * 查找奶站和经销商
     * @param page
     * @param salesid
     * @param type1
     * @param type2
     * @param signDelete
     * @return 
     */
    @Query("select new com.dinglicom.salesman.domain.StationRespItem(id, name, responsible_man, phone) from SysOranizagion a where a.userinfo.id=:salesid and a.type in(:type1, :type2) and a.signDelete=:signDelete")
    Page<StationRespItem> findByStationAllType(Pageable page, @Param(value = "salesid")long salesid, @Param(value = "type1")String type1, @Param(value = "type2")String type2, @Param(value = "signDelete")Boolean signDelete);
    
//    @Query("select a.id from SysOranizagion a where a.userinfo.id=:salesid and a.type=:type and a.signDelete=:signDelete")
//    List<Long> findOrgidBySalesmanid(@Param(value = "salesid")long salesid, @Param(value = "type")String type, @Param(value = "signDelete")Boolean signDelete);
//    
//    
//    @Query("select a.id from SysOranizagion a where a.userinfo.id=:salesid and a.type in (:type1, :type2) and a.signDelete=:signDelete")
//    List<Long> findOrgidBySalesmanid(@Param(value = "salesid")long salesid,@Param(value = "type1")String type1, @Param(value = "type2")String type2, @Param(value = "signDelete")Boolean signDelete);
    
    @Query("select new com.dinglicom.dep.domain.DepItem(id, name) from SysOranizagion a where a.type =:type and a.signDelete=:signDelete")
    List<DepItem> findAllDep(@Param(value = "type")String type, @Param(value = "signDelete")Boolean signDelete);
    
    
    @Query("select new com.dinglicom.dep.domain.DepDetail(id, name, responsible_man, phone, a.desc) from SysOranizagion a where a.id=:id")
    DepDetail findDepDetailById(@Param(value = "id")Long id);
    
    
    @Query("select new com.dinglicom.dep.domain.CompanyDetail(name, responsible_man, phone, address) from SysOranizagion a where a.type =:type and a.signDelete=:signDelete")
    List<CompanyDetail> findAllCompany(@Param(value = "type")String type, @Param(value = "signDelete")Boolean signDelete);
    
    
    @Query("select new com.dinglicom.pricepolicy.demain.OrgDealarRespItem(id, name) from SysOranizagion a where (a.type =:dealar or (a.type =:station and a.dealer is null)) and a.signDelete=:signDelete")
    List<OrgDealarRespItem> findAllDealarAndStation(@Param(value = "station")String station, @Param(value = "dealar")String dealar, @Param(value = "signDelete")Boolean signDelete);
    
    
    @Query("select new com.dinglicom.pricepolicy.demain.OrgDealarRespItem(id, name) from SysOranizagion a where (a.type =:dealar or (a.type =:station and a.dealer is null)) and a.name like :name and a.signDelete=:signDelete")
    List<OrgDealarRespItem> findAllDealarAndStation(@Param(value = "station")String station, @Param(value = "dealar")String dealar, @Param(value = "name")String name, @Param(value = "signDelete")Boolean signDelete);
    
    @Query("from SysOranizagion a where (a.type =:dealar or (a.type =:station and a.dealer is null)) and a.signDelete=:signDelete")
    List<SysOranizagion> findDealarAndStation(@Param(value = "station")String station, @Param(value = "dealar")String dealar, @Param(value = "signDelete")Boolean signDelete);
    
    
    @Query("from SysOranizagion a where (a.type =:dealar or (a.type =:station and a.dealer is null)) and a.id in (:ids) and a.signDelete=:signDelete")
    List<SysOranizagion> findDealarAndStation(@Param(value = "station")String station, @Param(value = "dealar")String dealar, @Param(value = "ids")List<Long> ids, @Param(value = "signDelete")Boolean signDelete);
    
}
