/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.frame.sys.repository;

import com.dinglicom.frame.sys.domain.AdminUserInfoRespItem;
import com.dinglicom.frame.sys.domain.AppUserBeanBase;
import com.dinglicom.frame.sys.domain.UserInfoItem;
import com.dinglicom.frame.sys.entity.SysOranizagion;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.salesample.domain.WebSaleSampleItem;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author panzhen
 */
public interface UserInfoDao extends PagingAndSortingRepository<UserInfo, Long>, JpaSpecificationExecutor<UserInfo> {

    @Query("select new com.dinglicom.frame.sys.domain.AppUserBeanBase(id, realname, phone) from UserInfo a where a.userType=:userType and (a.phone=:qrystr or a.realname=:qrystr) and a.signDelete=:signDelete")
    List<AppUserBeanBase> findWorkerQueryString(@Param(value = "userType") String userType, @Param(value = "qrystr") String qrystr, @Param(value = "signDelete") boolean signDelete);

    @Query("select new com.dinglicom.frame.sys.domain.AppUserBeanBase(id, realname, phone) from UserInfo a where a.id > :id and a.phone=:phone and a.signDelete=:signDelete")
    Page<AppUserBeanBase> findWorkerQueryPhone(Pageable page, @Param(value = "id") long id, @Param(value = "phone") String phone, @Param(value = "signDelete") boolean signDelete);

    @Query("select new com.dinglicom.frame.sys.domain.AppUserBeanBase(id, realname, phone) from UserInfo a where a.org.id = :orgId and a.userType=:userType and a.signDelete=:signDelete")
    Page<AppUserBeanBase> findWorkerQueryCustomPage(Pageable page, @Param(value = "orgId") long orgId, @Param(value = "userType") String userType, @Param(value = "signDelete") boolean signDelete);

    @Query("from UserInfo a where a.org=:org and a.signDelete=:signDelete")
    List<UserInfo> findUserByOrg(@Param(value = "org") SysOranizagion org, @Param(value = "signDelete") boolean signDelete);
    
    @Query("from UserInfo a where a.userType=:userType and a.org=:org and a.signDelete=:signDelete")
    List<UserInfo> findUserByOrgAndRole(@Param(value = "org")SysOranizagion org, @Param(value = "userType")String userType, @Param(value = "signDelete") boolean signDelete);

    @Query("select new com.dinglicom.frame.sys.domain.AdminUserInfoRespItem(id, userType, sex, idNumber, realname, birthday, provincename, cityname, regionname, a.desc, phone) from UserInfo a where a.signDelete=:signDelete")
    Page<AdminUserInfoRespItem> findUserInfoPage(Pageable page, @Param(value = "signDelete") boolean signDelete);

//    @Query("select new com.dinglicom.frame.sys.domain.UserInfoItem(a.userInfo.id, a.account, a.userInfo.userType, a.userInfo.sex, a.userInfo.idNumber, a.userInfo.realname, a.userInfo.birthday, a.userInfo.province.id, a.userInfo.provincename, a.userInfo.city.id, a.userInfo.cityname,a.userInfo.region.id, a.userInfo.regionname, a.userInfo.address, a.userInfo.desc, a.userInfo.phone, a.userInfo.org.id, a.userInfo.orgname) from SysUserAccount a where a.userInfo.userType = :userRole and a.userInfo.signDelete = :signDelete")
//    Page<UserInfoItem> findUserAndAccountPage(Pageable page, @Param(value = "userRole") String userRole, @Param(value = "signDelete") boolean signDelete);
//
//    @Query("select new com.dinglicom.frame.sys.domain.UserInfoItem(a.userInfo.id, a.account, a.userInfo.userType, a.userInfo.sex, a.userInfo.idNumber, a.userInfo.realname, a.userInfo.birthday, a.userInfo.province.id, a.userInfo.provincename, a.userInfo.city.id, a.userInfo.cityname,a.userInfo.region.id, a.userInfo.regionname, a.userInfo.address, a.userInfo.desc, a.userInfo.phone, a.userInfo.org.id, a.userInfo.orgname) from SysUserAccount a where a.userInfo.signDelete = :signDelete")
//    Page<UserInfoItem> findUserAndAccountPage(Pageable page, @Param(value = "signDelete") boolean signDelete);
//
//    @Query("select new com.dinglicom.frame.sys.domain.UserInfoItem(a.userInfo.id, a.account, a.userInfo.userType, a.userInfo.sex, a.userInfo.idNumber, a.userInfo.realname, a.userInfo.birthday, a.userInfo.province.id, a.userInfo.provincename, a.userInfo.city.id, a.userInfo.cityname,a.userInfo.region.id, a.userInfo.regionname, a.userInfo.address, a.userInfo.desc, a.userInfo.phone, a.userInfo.org.id, a.userInfo.orgname) from SysUserAccount a where a.userInfo.id = :uid and a.userInfo.signDelete = :signDelete")
//    List<UserInfoItem> findUserByUserId(@Param(value = "uid") long uid, @Param(value = "signDelete") boolean signDelete);
    @Query("select new com.dinglicom.frame.sys.domain.UserInfoItem(id, a.account, a.userType, a.sex, a.idNumber, a.realname, a.birthday, a.province.id, a.provincename, a.city.id, a.cityname,a.region.id, a.regionname, a.address, a.desc, a.phone, a.org.id, a.orgname, a.did, a.dname) from UserInfo a where a.userType = :userRole and a.signDelete = :signDelete")
    Page<UserInfoItem> findUserAndAccountPage(Pageable page, @Param(value = "userRole") String userRole, @Param(value = "signDelete") boolean signDelete);

    @Query("select new com.dinglicom.frame.sys.domain.UserInfoItem(id, a.account, a.userType, a.sex, a.idNumber, a.realname, a.birthday, a.province.id, a.provincename, a.city.id, a.cityname,a.region.id, a.regionname, a.address, a.desc, a.phone, a.managerid, a.manager, a.nickname, a.did, a.dname) from UserInfo a where a.userType = :userRole and a.signDelete = :signDelete")
    Page<UserInfoItem> findUserAndAccountStationPage(Pageable page, @Param(value = "userRole") String userRole, @Param(value = "signDelete") boolean signDelete);
    
    @Query("select new com.dinglicom.frame.sys.domain.UserInfoItem(a.id, a.account, a.userType, a.sex, a.idNumber, a.realname, a.birthday, a.province.id, a.provincename, a.city.id, a.cityname,a.region.id, a.regionname, a.address, a.desc, a.phone, a.org.id, a.orgname, a.managerid, a.manager, a.nickname, a.did, a.dname) from UserInfo a where a.signDelete = :signDelete")
    Page<UserInfoItem> findUserAndAccountPage(Pageable page, @Param(value = "signDelete") boolean signDelete);

    @Query("select new com.dinglicom.frame.sys.domain.UserInfoItem(a.id, a.account, a.userType, a.sex, a.idNumber, a.realname, a.birthday, a.province.id, a.provincename, a.city.id, a.cityname,a.region.id, a.regionname, a.address, a.desc, a.phone, a.org.id, a.orgname, a.managerid, a.manager, a.nickname, a.did, a.dname) from UserInfo a where a.id = :uid and a.signDelete = :signDelete")
    List<UserInfoItem> findUserByUserId(@Param(value = "uid") long uid, @Param(value = "signDelete") boolean signDelete);

    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(a.org.id, a.id, realname, phone) from UserInfo a where a.org.id in :orgs and a.userType=:userType and a.signDelete = :signDelete")
    List<WebSaleSampleItem> findUserByOrgAndType(@Param(value = "orgs") List<Long> orgs, @Param(value = "userType") String userType, @Param(value = "signDelete") boolean signDelete);
    
    
    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(a.org.id, a.id, realname, phone) from UserInfo a where a.org.id in :orgs and a.userType=:userType and a.signDelete = :signDelete")
    List<WebSaleSampleItem> findUserType(@Param(value = "userType") String userType, @Param(value = "signDelete") boolean signDelete);
    
    
    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(a.org.id, a.id, realname, phone) from UserInfo a where (a.realname=:name or a.phone=:name) and a.org.type=:orgtype and a.userType=:userType and a.signDelete = :signDelete")
    List<WebSaleSampleItem> findUserByOrgType(@Param(value = "name") String name, @Param(value = "orgtype") String orgtype, @Param(value = "userType") String userType, @Param(value = "signDelete") boolean signDelete);
    
    
    @Query("select new com.dinglicom.salesample.domain.WebSaleSampleItem(a.org.id, a.id, realname, phone) from UserInfo a where a.org.id in (:ids)")
    List<WebSaleSampleItem> findUserByOrgIds(@Param(value = "ids") List<Long> ids);

    @Query("select baiduid from UserInfo a where a.baiduid is not null and a.signDelete = :signDelete")
    List<String> findAllBaiduid(@Param(value = "signDelete") boolean signDelete);
}
