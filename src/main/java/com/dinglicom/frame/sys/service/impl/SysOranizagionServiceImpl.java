/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.frame.sys.service.impl;

import com.dinglicom.frame.sys.domain.CityResp;
import com.dinglicom.frame.sys.domain.DeliveryOrgRespItem;
import com.dinglicom.frame.sys.domain.DlvryPageItem;
import com.dinglicom.frame.sys.domain.DlvryPageReq;
import com.dinglicom.frame.sys.domain.DlvryPageResp;
import com.dinglicom.frame.sys.domain.ProvinceResp;
import com.dinglicom.frame.sys.domain.RegionResp;
import com.dinglicom.frame.sys.domain.WebAddDlvryReq;
import com.dinglicom.frame.sys.domain.WorkerOrgReq;
import com.dinglicom.frame.sys.domain.WorkerOrgRespItem;
import com.dinglicom.frame.sys.entity.SysOranizagion;
import com.dinglicom.frame.sys.entity.SysUserAccount;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.frame.sys.repository.SysOranizagionDao;
import com.dinglicom.frame.sys.service.SysOranizagionService;
import com.dinglicom.frame.sys.service.UserInfoService;
import com.dinglicom.salesman.domain.StationDetailResp;
import com.dinglicom.salesman.domain.StationQueryReq;
import com.dinglicom.salesman.domain.StationQueryResp;
import com.dinglicom.salesman.domain.StationRespItem;
import com.dinglicom.salesman.domain.StationUpldateReq;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author panzhen
 */
@Component
@Transactional
public class SysOranizagionServiceImpl implements SysOranizagionService {

    @Resource
    private SysOranizagionDao sysOranizagionDao;
    @Resource
    private UserInfoService userInfoService;

    @Override
    @Transactional(readOnly = true)
    public SysOranizagion read(long id) {
        return sysOranizagionDao.findOne(id);
    }

    @Override
    public SysOranizagion save(SysOranizagion org) {
        return sysOranizagionDao.save(org);
    }

    @Override
    public Iterable<SysOranizagion> save(List<SysOranizagion> orgs) {
        return sysOranizagionDao.save(orgs);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SysOranizagion> findAllWorkerOrg() {
        return sysOranizagionDao.findByType(SysOranizagionService.ORG_TYPE_NZH, Boolean.FALSE);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SysOranizagion> findAllDeliveryOrg() {
        return sysOranizagionDao.findByType(SysOranizagionService.ORG_TYPE_DLV, Boolean.FALSE);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkerOrgRespItem> findAllWorkerOrg(WorkerOrgReq req) {
        return sysOranizagionDao.findWorkerStation(SysOranizagionService.ORG_TYPE_NZH, req.getProvince(), req.getCity(), req.getRegion(), Boolean.FALSE);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeliveryOrgRespItem> findAllDeliveryOrg(WorkerOrgReq req) {
        return sysOranizagionDao.findDeliveryStation(SysOranizagionService.ORG_TYPE_DLV, req.getProvince(), req.getCity(), req.getRegion(), Boolean.FALSE);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SysOranizagion> findOrgByType(String type) {
        return sysOranizagionDao.findByType(type, Boolean.FALSE);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SysOranizagion> findOrgByParentAndType(long parentid, String type) {
        return sysOranizagionDao.findByParentAndType(parentid, type, Boolean.FALSE);
    }

    @Override
    @Transactional(readOnly = true)
    public ProvinceResp findAllProvince() {
        ProvinceResp resp = new ProvinceResp();
        resp.setData(sysOranizagionDao.findByProvince(SysOranizagionService.ORG_TYPE_RPR, Boolean.FALSE));
        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public CityResp findCityByProvince(long provinceId) {
        CityResp resp = new CityResp();
        resp.setData(sysOranizagionDao.findByCity(provinceId, SysOranizagionService.ORG_TYPE_RCT, Boolean.FALSE));
        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public RegionResp findRegionByCity(long cityId) {
        RegionResp resp = new RegionResp();
        resp.setData(sysOranizagionDao.findByRegion(cityId, SysOranizagionService.ORG_TYPE_RRG, Boolean.FALSE));
        return resp;
    }

    @Override
    public SysOranizagion addDelivery(WebAddDlvryReq req) {
        SysOranizagion org = new SysOranizagion();
        org.setName(req.getName());
        org.setType(SysOranizagionService.ORG_TYPE_DLV);
        org.setResponsible_phone(req.getTel());
        org.setResponsible_man(req.getManager());
        org.setPhone(req.getTel());
        org.setDesc(req.getDesc());
        if (req.getPid() > 0) {
            SysOranizagion province = read(req.getPid());
            if (null != province) {
                org.setProvince(province);
                org.setProvince_name(province.getName());
            }
        }
        if (req.getCid() > 0) {
            SysOranizagion city = read(req.getCid());
            if (null != city) {
                org.setCity(city);
                org.setCity_name(city.getName());
            }
        }
        if (req.getRid() > 0) {
            SysOranizagion region = read(req.getRid());
            if (null != region) {
                org.setRegion(region);
                org.setRegion_name(region.getName());
            }
        }

        return save(org);
    }

    @Override
    public SysOranizagion updateDelivery(WebAddDlvryReq req) {
        SysOranizagion org = read(req.getDid());
        if (null == org) {
            throw new RuntimeException("Not found Delivery by id:" + req.getDid());
        }
        if (null != req.getName()) {
            org.setName(req.getName());
        }
        if (null != req.getTel()) {
            org.setResponsible_phone(req.getTel());
            org.setPhone(req.getTel());
        }
        if (null != req.getManager()) {
            org.setResponsible_man(req.getManager());
        }
        if (null != req.getDesc()) {
            org.setDesc(req.getDesc());
        }
        if (req.getPid() > 0) {
            SysOranizagion province = read(req.getPid());
            if (null != province) {
                org.setProvince(province);
                org.setProvince_name(province.getName());
            }
        }
        if (req.getCid() > 0) {
            SysOranizagion city = read(req.getCid());
            if (null != city) {
                org.setCity(city);
                org.setCity_name(city.getName());
            }
        }
        if (req.getRid() > 0) {
            SysOranizagion region = read(req.getRid());
            if (null != region) {
                org.setRegion(region);
                org.setRegion_name(region.getName());
            }
        }

        return save(org);
    }

    @Override
    public void deleteDelivery(long did) {
        SysOranizagion org = read(did);
        if (null == org) {
            throw new RuntimeException("Not found Delivery by id:" + did);
        }
        org.setSignDelete(Boolean.TRUE);
        save(org);
    }

    @Override
    @Transactional(readOnly = true)
    public DlvryPageResp getPageDlvry(DlvryPageReq req) {
        DlvryPageResp resp = new DlvryPageResp();
        Page<DlvryPageItem> page = sysOranizagionDao.findByProvinceCityRegion(buildPageRequest(req.getPage(), req.getNum()), SysOranizagionService.ORG_TYPE_DLV, req.getPid(), req.getCid(), req.getRid(), Boolean.FALSE);
        if (null != page) {
            resp.setTotal_num(page.getTotalElements());
            resp.setData(page.getContent());
        }
        return resp;
    }

    @Override
    public SysOranizagion addStation(StationUpldateReq req, UserInfo salesman) {
        SysOranizagion org = new SysOranizagion();
        org.setUserinfo(salesman);
        org.setName(req.getName());
        if (SysOranizagionService.ORG_TYPE_NZH.equalsIgnoreCase(req.getRole())) {
            org.setType(SysOranizagionService.ORG_TYPE_NZH);
        } else if (SysOranizagionService.ORG_TYPE_DEALER.equalsIgnoreCase(req.getRole())) {
            org.setType(SysOranizagionService.ORG_TYPE_DEALER);
        } else {
            org.setType(SysOranizagionService.ORG_TYPE_NZH);
        }
        org.setResponsible_man(req.getManager());
        org.setResponsible_phone(req.getMobile_phone());
        org.setPhone(req.getMobile_phone());

        if (null != req.getProvince() && req.getProvince() > 0) {
            SysOranizagion province = read(req.getProvince());
            if (null != province) {
                org.setProvince(province);
                org.setProvince_name(province.getName());
            }
        }
        if (null != req.getCity() && req.getCity() > 0) {
            SysOranizagion city = read(req.getCity());
            if (null != city) {
                org.setCity(city);
                org.setCity_name(city.getName());
            }
        }
        if (null != req.getRegion() && req.getRegion() > 0) {
            SysOranizagion region = read(req.getRegion());
            if (null != region) {
                org.setRegion(region);
                org.setRegion_name(region.getName());
            }
        }
        org.setAddress(req.getAddress());

        org = save(org);
        userInfoService.addStationUser(org, req, salesman);
        return org;
    }

    @Override
    public SysOranizagion updateStation(StationUpldateReq req) {
        SysOranizagion org = read(req.getUser_id());
        if (null == org) {
            throw new RuntimeException("Not found station by id.");
        }
        SysUserAccount account = userInfoService.findByOrgAndUserType(org.getId(), org.getType());
        UserInfo userinfo;
        if (null != account) {
            userinfo = account.getUserInfo();
        } else {
            userinfo = new UserInfo();
            userinfo.setOrg(org);
            userinfo.setOrgname(org.getName());
        }
        if (null != req.getName()) {
            org.setName(req.getName());
            userinfo.setOrgname(org.getName());
        }
        if (null != req.getRole()) {
            if (SysOranizagionService.ORG_TYPE_NZH.equalsIgnoreCase(req.getRole())) {
                org.setType(SysOranizagionService.ORG_TYPE_NZH);
            } else if (SysOranizagionService.ORG_TYPE_DEALER.equalsIgnoreCase(req.getRole())) {
                org.setType(SysOranizagionService.ORG_TYPE_DEALER);
            } else {
                org.setType(SysOranizagionService.ORG_TYPE_NZH);
            }
            userinfo.setUserType(org.getType());
        }
        if (null != req.getManager()) {
            org.setResponsible_man(req.getManager());
            userinfo.setRealname(org.getResponsible_man());
        }
        if (null != req.getMobile_phone()) {
            org.setResponsible_phone(req.getMobile_phone());
            org.setPhone(req.getMobile_phone());
            userinfo.setPhone(org.getPhone());
        }

        if (null != req.getProvince() && req.getProvince() > 0) {
            SysOranizagion province = read(req.getProvince());
            if (null != province) {
                org.setProvince(province);
                org.setProvince_name(province.getName());
                userinfo.setProvince(org.getProvince());
                userinfo.setProvincename(org.getProvince_name());
            }
        }
        if (null != req.getCity() && req.getCity() > 0) {
            SysOranizagion city = read(req.getCity());
            if (null != city) {
                org.setCity(city);
                org.setCity_name(city.getName());
                userinfo.setCity(org.getCity());
                userinfo.setCityname(org.getCity_name());
            }
        }
        if (null != req.getRegion() && req.getRegion() > 0) {
            SysOranizagion region = read(req.getRegion());
            if (null != region) {
                org.setRegion(region);
                org.setRegion_name(region.getName());
                userinfo.setRegion(org.getRegion());
                userinfo.setRegionname(org.getRegion_name());
            }
        }
        if (null != req.getAddress()) {
            org.setAddress(req.getAddress());
            userinfo.setAddress(org.getAddress());
        }

        org = save(org);
        userInfoService.save(userinfo);
        if (null != req.getPwd() && null != account) {
            userInfoService.updateUserPwd(account, null, req.getPwd());
        }
        return org;
    }

    @Override
    public void deleteOrg(SysOranizagion org) {
        org.setSignDelete(Boolean.TRUE);
        save(org);
        List<UserInfo> users = userInfoService.findByOrg(org);
        for (UserInfo user : users) {
            userInfoService.deleteUserByAdmin(user.getId());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public StationQueryResp findStationList(StationQueryReq req, long salesid) {
        StationQueryResp resp = new StationQueryResp();
        Page<StationRespItem> page;
        if (null == req.getRole() || "ALL".equalsIgnoreCase(req.getRole())) {
            page = sysOranizagionDao.findByStationAllType(buildPageRequest(req.getPage(), req.getNum()), salesid, SysOranizagionService.ORG_TYPE_DEALER, SysOranizagionService.ORG_TYPE_NZH, Boolean.FALSE);
        } else {
            String type;
            if (SysOranizagionService.ORG_TYPE_DEALER.equalsIgnoreCase(req.getRole())) {
                type = SysOranizagionService.ORG_TYPE_DEALER;
            } else {
                type = SysOranizagionService.ORG_TYPE_NZH;
            }
            page = sysOranizagionDao.findByStationType(buildPageRequest(req.getPage(), req.getNum()), salesid, type, Boolean.FALSE);
        }
        if (null != page && null != page.getContent()) {
            resp.setData(page.getContent());
            resp.setTotal_num(page.getTotalElements());
        }
        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public StationDetailResp findById(long id) {
        StationDetailResp resp = new StationDetailResp();
        SysOranizagion org = read(id);
        if (null != org) {
            resp.setUser_id(id);
            resp.setName(org.getName());
            resp.setRole(org.getType());
            if (null != org.getProvince()) {
                resp.setProvince(org.getProvince().getName());
            }
            if (null != org.getCity()) {
                resp.setCity(org.getCity().getName());
            }
            if (null != org.getRegion()) {
                resp.setRegion(org.getRegion().getName());
            }
            resp.setAddress(org.getAddress());
            resp.setManager(org.getResponsible_man());
            resp.setMobile_phone(org.getPhone());
        }
        return resp;
    }

    /**
     * 创建分页请求.
     */
    private PageRequest buildPageRequest(final int page, final int size) {
        return new PageRequest(page - 1, size);
    }
}
