/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.frame.sys.web;

import com.dinglicom.frame.sys.domain.BaseMsgBean;
import com.dinglicom.frame.sys.domain.CityAddReq;
import com.dinglicom.frame.sys.domain.CityAddResp;
import com.dinglicom.frame.sys.domain.DlvryPageReq;
import com.dinglicom.frame.sys.domain.OrgRegionReq;
import com.dinglicom.frame.sys.domain.ProvinceAddReq;
import com.dinglicom.frame.sys.domain.ProvinceAddResp;
import com.dinglicom.frame.sys.domain.RegionAddReq;
import com.dinglicom.frame.sys.domain.RegionAddResponse;
import com.dinglicom.frame.sys.domain.WebAddDlvryReq;
import com.dinglicom.frame.sys.domain.WebAddDlvryResp;
import com.dinglicom.frame.sys.entity.SysOranizagion;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.frame.sys.service.SysOranizagionService;
import com.dinglicom.frame.sys.service.SysTokenService;
import com.dinglicom.frame.web.AppControllerBase;
import com.dinglicom.salesman.domain.StationQueryReq;
import com.dinglicom.salesman.domain.StationUpldateReq;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author panzhen
 */
@Controller
@RequestMapping(value = "/api/v1/org")
public class SysOranizagionController extends AppControllerBase {

    private final static Logger LOG = LoggerFactory.getLogger(SysOranizagionController.class);
    @Resource
    private SysTokenService sysTokenService;
    @Resource
    private SysOranizagionService sysOranizagionService;

    /**
     * 创建省一级行政区域
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/createprovince")
    public @ResponseBody
    ProvinceAddResp createProvince(ProvinceAddReq req) {
        ProvinceAddResp msg = new ProvinceAddResp();
        LOG.info("Mid:{},Token:{},create province:{}", req.getMid(), req.getToken(), req.getProvince());
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 == req.getMid() || null == req.getProvince()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        SysOranizagion province = new SysOranizagion();
        province.setName(req.getProvince());
        province.setType(SysOranizagionService.ORG_TYPE_RPR);
        try {
            province = sysOranizagionService.save(province);
            msg.setPid(province.getId());
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Create province record fail.", e);
        }
        return msg;
    }

    /**
     * 创建城市
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/createcity")
    public @ResponseBody
    CityAddResp createCity(CityAddReq req) {
        CityAddResp msg = new CityAddResp();
        LOG.info("Mid:{},Token:{},parent id:{},create city org:{}", req.getMid(), req.getToken(), req.getPid(), req.getCity());
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 == req.getMid() || null == req.getCity() || 0 >= req.getPid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        SysOranizagion province = sysOranizagionService.read(req.getPid());
        if (null == province) {
            msg.setCode(1);
            msg.setResult("没有有效的省编号");
            return msg;
        }
        SysOranizagion city = new SysOranizagion();
        city.setParent(province);
        city.setName(req.getCity());
        city.setType(SysOranizagionService.ORG_TYPE_RCT);
//        List<SysOranizagion> list = new ArrayList<SysOranizagion>();
//        String[] nms = req.getCity().split(",");
//        for (String name : nms) {
//            SysOranizagion subOrg = new SysOranizagion();
//            subOrg.setParent(parent);
//            subOrg.setName(name);
//            subOrg.setType(SysOranizagionService.ORG_TYPE_RCT);
//            switch(parent.getType()) {
//                case SysOranizagionService.ORG_TYPE_RPR:
//                    subOrg.setType(SysOranizagionService.ORG_TYPE_RCT);
//                    break;
//                case SysOranizagionService.ORG_TYPE_RCT:
//                    subOrg.setType(SysOranizagionService.ORG_TYPE_RRG);
//                    break;
//                case SysOranizagionService.ORG_TYPE_COR:
//                    subOrg.setType(SysOranizagionService.ORG_TYPE_COM);
//                    break;
//                case SysOranizagionService.ORG_TYPE_DEP:
//                    subOrg.setType(SysOranizagionService.ORG_TYPE_COM);
//                    break;
//            }
//            list.add(subOrg);
//        }
        try {
            city = sysOranizagionService.save(city);
            msg.setCid(city.getId());
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Create city record fail.", e);
            msg.setCode(1);
            msg.setResult("保存失败");
        }
        return msg;
    }

    /**
     * 创建区
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/createregion")
    public @ResponseBody
    RegionAddResponse createRegion(RegionAddReq req) {
        RegionAddResponse msg = new RegionAddResponse();
        LOG.info("Mid:{},Token:{},city id:{},create region org:{}", req.getMid(), req.getToken(), req.getCid(), req.getRegion());
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 == req.getMid() || null == req.getRegion() || 0 >= req.getCid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        SysOranizagion city = sysOranizagionService.read(req.getCid());
        if (null == city) {
            msg.setCode(1);
            msg.setResult("没有有效的上级组织编号");
            return msg;
        }
        SysOranizagion region = new SysOranizagion();
        region.setParent(city);
        region.setName(req.getRegion());
        region.setType(SysOranizagionService.ORG_TYPE_RRG);
//        List<SysOranizagion> list = new ArrayList<SysOranizagion>();
//        String[] nms = req.getRegion().split(",");
//        for (String name : nms) {
//            SysOranizagion subOrg = new SysOranizagion();
//            subOrg.setParent(city);
//            subOrg.setName(name);
//            subOrg.setType(SysOranizagionService.ORG_TYPE_RRG);
//            list.add(subOrg);
//        }
        try {
            region = sysOranizagionService.save(region);
            msg.setRid(region.getId());
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Create region record fail.", e);
            msg.setCode(1);
            msg.setResult("保存失败");
        }
        return msg;
    }

    /**
     * 修改区
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/updateregion")
    public @ResponseBody
    BaseMsgBean updateRegion(RegionAddReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        LOG.info("Mid:{},Token:{},update region org:{}", req.getMid(), req.getToken(), req.getRegion());
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 == req.getMid() || null == req.getRegion() || 0 >= req.getRid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        SysOranizagion region = sysOranizagionService.read(req.getRid());
        if (null == region) {
            msg.setCode(1);
            msg.setResult("没有对应的区域");
            return msg;
        }
        region.setName(req.getRegion());
        try {
            sysOranizagionService.save(region);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Update region record fail.", e);
            msg.setCode(1);
            msg.setResult("保存失败");
        }
        return msg;
    }

    /**
     * 删除省
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/deleteprovince")
    public @ResponseBody
    BaseMsgBean deleteProvince(ProvinceAddReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        LOG.info("Mid:{},Token:{},Delete province id:{}", req.getMid(), req.getToken(), req.getPid());
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 == req.getMid() || 0 >= req.getPid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        SysOranizagion province = sysOranizagionService.read(req.getPid());
        if (null == province) {
            msg.setCode(1);
            msg.setResult("没有对应的区域");
            return msg;
        }
        province.setSignDelete(Boolean.TRUE);
        try {
            sysOranizagionService.save(province);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Delete province record fail.", e);
            msg.setCode(1);
            msg.setResult("删除失败");
        }
        return msg;
    }

    /**
     * 删除城市
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/deletecity")
    public @ResponseBody
    BaseMsgBean deleteCity(CityAddReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        LOG.info("Mid:{},Token:{},delete city org:{}", req.getMid(), req.getToken(), req.getCid());
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 == req.getMid() || 0 >= req.getCid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        SysOranizagion city = sysOranizagionService.read(req.getCid());
        if (null == city) {
            msg.setCode(1);
            msg.setResult("没有有效的城市编号");
            return msg;
        }
        city.setSignDelete(Boolean.TRUE);

        try {
            sysOranizagionService.save(city);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Delete city record fail.", e);
            msg.setCode(1);
            msg.setResult("保存失败");
        }
        return msg;
    }

    /**
     * 删除区
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/deleteregion")
    public @ResponseBody
    BaseMsgBean deleteRegion(RegionAddReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        LOG.info("Mid:{},Token:{},delete region org:{}", req.getMid(), req.getToken(), req.getRid());
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 == req.getMid() || 0 >= req.getRid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        SysOranizagion region = sysOranizagionService.read(req.getRid());
        if (null == region) {
            msg.setCode(1);
            msg.setResult("没有对应的区域");
            return msg;
        }
        region.setSignDelete(Boolean.TRUE);
        try {
            sysOranizagionService.save(region);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Update region record fail.", e);
            msg.setCode(1);
            msg.setResult("保存失败");
        }
        return msg;
    }

    @RequestMapping(value = "/allprovince")
    public @ResponseBody
    BaseMsgBean getProvince(OrgRegionReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        LOG.info("Get all province, Uid:{},Token:{}", req.getUid(), req.getToken());
        try {
            msg = sysOranizagionService.findAllProvince();
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Create province record fail.", e);
        }
        return msg;
    }

    @RequestMapping(value = "/citybyprovince")
    public @ResponseBody
    BaseMsgBean getCityByProvince(OrgRegionReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        LOG.info("Get city by Province:{}, Uid:{},Token:{},", req.getPid(), req.getUid(), req.getToken());
        try {
            msg = sysOranizagionService.findCityByProvince(req.getPid());
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Create province record fail.", e);
        }
        return msg;
    }

    @RequestMapping(value = "/regionbycity")
    public @ResponseBody
    BaseMsgBean getRegionByCity(OrgRegionReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        LOG.info("Get region by city:{}, Uid:{},Token:{},Province:{}", req.getCid(), req.getUid(), req.getToken());
        try {
            msg = sysOranizagionService.findRegionByCity(req.getCid());
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Create province record fail.", e);
        }
        return msg;
    }

    @RequestMapping(value = "/adddlvry")
    public @ResponseBody
    BaseMsgBean addDelivery(WebAddDlvryReq req) {
        WebAddDlvryResp msg = new WebAddDlvryResp();
        LOG.info("Add Delivery, mid:{},Token:{},name:{}", req.getMid(), req.getToken(), req.getName());
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 == req.getMid() || null == req.getName()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            SysOranizagion dlvry = sysOranizagionService.addDelivery(req);
            if (null != dlvry) {
                msg.setDid(dlvry.getId());
            }
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Create Delivery record fail.", e);
        }
        return msg;
    }

    @RequestMapping(value = "/updatedlvry")
    public @ResponseBody
    BaseMsgBean updateDelivery(WebAddDlvryReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        LOG.info("Update Delivery, mid:{},Token:{},id:{}", req.getMid(), req.getToken(), req.getDid());
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 == req.getMid() || 0 >= req.getDid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            sysOranizagionService.updateDelivery(req);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Update Delivery record fail.", e);
        }
        return msg;
    }

    @RequestMapping(value = "/deletedlvry")
    public @ResponseBody
    BaseMsgBean deleteDelivery(WebAddDlvryReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        LOG.info("Delete Delivery, mid:{},Token:{},id:{}", req.getMid(), req.getToken(), req.getDid());
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 == req.getMid() || 0 >= req.getDid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            sysOranizagionService.deleteDelivery(req.getDid());
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Delete Delivery record fail.", e);
        }
        return msg;
    }

    @RequestMapping(value = "/pagedlvry")
    public @ResponseBody
    BaseMsgBean pageDelivery(DlvryPageReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        LOG.info("Delete Delivery, mid:{},Token:{},pid:{}", req.getMid(), req.getToken(), req.getPid());
        UserInfo admin = validateToken(sysTokenService, msg, req.getMid(), req.getToken());
        if (null == admin || 0 == req.getMid() || 0 >= req.getPid() || 0 >= req.getNum() || 0 >= req.getPage() || 0 >= req.getCid() || 0 >= req.getRid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = sysOranizagionService.getPageDlvry(req);
            msg.setResult("成功");
        } catch (Exception e) {
            LOG.warn("Query Delivery by  fail.", e);
        }
        return msg;
    }

    /**
     * 业务员添加奶站或者经销商
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/addstation")
    public @ResponseBody
    BaseMsgBean addStation(StationUpldateReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        LOG.info("Add Station, uid:{},Token:{},orgname:{}", req.getUid(), req.getToken(), req.getName());
        UserInfo admin = validateToken(sysTokenService, req, msg);
        if (null == admin || 0 >= req.getUid() || null == req.getName() || null == req.getMobile_phone() || null == req.getPwd()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            sysOranizagionService.addStation(req, admin);
            msg.setResult("成功");
        } catch (Exception e) {
            msg.setCode(1);
            msg.setResult(e.getMessage());
            LOG.warn("Add Station  fail.", e);
        }
        return msg;
    }

    /**
     * 业务员修改奶站或者经销商
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/updatestation")
    public @ResponseBody
    BaseMsgBean updateStation(StationUpldateReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        LOG.info("Update Station, uid:{},Token:{}", req.getUid(), req.getToken());
        UserInfo admin = validateToken(sysTokenService, req, msg);
        if (null == admin || 0 >= req.getUid() || null == req.getUser_id() || 0 >= req.getUser_id()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            sysOranizagionService.updateStation(req);
            msg.setResult("成功");
        } catch (Exception e) {
            msg.setCode(1);
            msg.setResult(e.getMessage());
            LOG.warn("Update Station  fail.", e);
        }
        return msg;
    }

    /**
     * 业务员删除奶站或者经销商
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/deletestation")
    public @ResponseBody
    BaseMsgBean deleteStation(StationUpldateReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        LOG.info("Delete Station, uid:{},Token:{},orgId:{}", req.getUid(), req.getToken(), req.getUser_id());
        UserInfo admin = validateToken(sysTokenService, req, msg);
        if (null == admin || 0 >= req.getUid() || null == req.getUser_id() || 0 >= req.getUser_id()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            SysOranizagion org = sysOranizagionService.read(req.getUser_id());
            if (null != org) {
                sysOranizagionService.deleteOrg(org);
                msg.setResult("成功");
            } else {
                msg.setCode(1);
                msg.setResult("Not found station by id.");
            }
        } catch (Exception e) {
            msg.setCode(1);
            msg.setResult(e.getMessage());
            LOG.warn("Delete Station  fail.", e);
        }
        return msg;
    }

    /**
     * 业务员查看奶站或者经销商详细信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/stationdetail")
    public @ResponseBody
    BaseMsgBean stationDetail(StationUpldateReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        LOG.info("Delete Station, uid:{},Token:{},orgId:{}", req.getUid(), req.getToken(), req.getUser_id());
        UserInfo admin = validateToken(sysTokenService, req, msg);
        if (null == admin || 0 >= req.getUid() || null == req.getUser_id() || 0 >= req.getUser_id()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = sysOranizagionService.findById(req.getUser_id());
            msg.setResult("成功");
        } catch (Exception e) {
            msg.setCode(1);
            msg.setResult(e.getMessage());
            LOG.warn("Delete Station  fail.", e);
        }
        return msg;
    }

    /**
     * 业务员查看奶站或者经销商详细信息
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/stationlist")
    public @ResponseBody
    BaseMsgBean stationList(StationQueryReq req) {
        BaseMsgBean msg = new BaseMsgBean();
        LOG.info("Query Station, uid:{},Token:{}", req.getUid(), req.getToken());
        UserInfo admin = validateToken(sysTokenService, req, msg);
        if (null == admin || 0 >= req.getUid()) {
            msg.setCode(1);
            msg.setResult("未输入必须字段或者无有效权限");
            return msg;
        }
        try {
            msg = sysOranizagionService.findStationList(req, admin.getId());
            msg.setResult("成功");
        } catch (Exception e) {
            msg.setCode(1);
            msg.setResult(e.getMessage());
            LOG.warn("Query Station  fail.", e);
        }
        return msg;
    }
}
