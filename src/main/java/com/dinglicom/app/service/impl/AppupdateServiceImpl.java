/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.app.service.impl;

import com.dinglicom.app.domain.AppListItem;
import com.dinglicom.app.domain.AppupdateCheckReq;
import com.dinglicom.app.domain.AppupdateCheckResp;
import com.dinglicom.app.domain.AppupdateReq;
import com.dinglicom.app.domain.AppupdatelistResp;
import com.dinglicom.app.entity.AppUpdate;
import com.dinglicom.app.repository.AppupdateDao;
import com.dinglicom.app.service.AppupdateService;
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
public class AppupdateServiceImpl implements AppupdateService {

    @Resource
    private AppupdateDao appupdateDao;

    @Override
    public AppUpdate save(AppUpdate app) {
        return appupdateDao.save(app);
    }

    @Override
    public AppUpdate addAppupdate(AppupdateReq req) {
        AppUpdate app = new AppUpdate();
        app.setForceupdate(req.getForceUpdate());
        app.setInfo(req.getInfo());
        app.setType(req.getType());
        app.setUrl(req.getUrl());
        app.setVersion(req.getVersionName());
        app.setVersioncode(req.getVersionCode());

        return save(app);
    }

    @Override
    public void delete(Long id) {
        AppUpdate app = appupdateDao.findOne(id);
        app.setSignDelete(Boolean.TRUE);

        save(app);
    }

    @Override
    public void updateAppupdate(AppupdateReq req) {
        AppUpdate app = appupdateDao.findOne(req.getPid());
        if(null == req.getPid() || 0 >= req.getPid()) {
            
        }
        if(null == app) {
            throw new RuntimeException("Not found app update by id:" + req.getPid());
        }
        if (null != req.getForceUpdate()) {
            app.setForceupdate(req.getForceUpdate());
        }
        if (null != req.getInfo()) {
            app.setInfo(req.getInfo());
        }
        if (null != req.getType()) {
            app.setType(req.getType());
        }
        if (null != req.getUrl()) {
            app.setUrl(req.getUrl());
        }
        if (null != req.getVersionName()) {
            app.setVersion(req.getVersionName());
        }
        if(null != req.getVersionCode()) {
            app.setVersioncode(req.getVersionCode());
        }
        save(app);
    }

    @Override
    @Transactional(readOnly = true)
    public AppupdatelistResp getAllAppupdate() {
        AppupdatelistResp resp = new AppupdatelistResp();
        Page<AppListItem> page = appupdateDao.getAllAppupdate(new PageRequest(0, 20), Boolean.FALSE);
        if (null != page && page.getContent().size() > 0) {
            resp.setData(page.getContent());
            resp.setTotal_num(page.getTotalElements());
        }
        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public AppupdateCheckResp checkVersion(AppupdateCheckReq req) {
        List<AppUpdate> list = appupdateDao.findMaxVersion(req.getCurVersionCode(), req.getType(), Boolean.FALSE);
        AppupdateCheckResp resp = new AppupdateCheckResp();
        if(list.size() > 0) {
            AppUpdate app = list.get(0);
            resp = new AppupdateCheckResp(app.getVersioncode(), app.getInfo(), app.getUrl(), app.getForceupdate());
        } else {
            resp.setCode(1);
        }
        resp.setCurVersionCode(req.getCurVersionCode());
        return resp;
    }

}
