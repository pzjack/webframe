/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.about.service.impl;

import com.dinglicom.about.domain.DownMsgItem;
import com.dinglicom.about.domain.DownMsgPageResp;
import com.dinglicom.about.domain.DownMsgReq;
import com.dinglicom.about.entity.DownMsg;
import com.dinglicom.about.repository.DownMsgDao;
import com.dinglicom.about.service.BaiduMsgputService;
import com.dinglicom.about.service.DownMsgService;
import com.dinglicom.frame.sys.domain.AdminPageReqBase;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.dinglicom.frame.sys.service.UserInfoService;
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
public class DownMsgServiceImpl implements DownMsgService {
    @Resource
    private DownMsgDao downMsgDao;
    @Resource
    private BaiduMsgputService baiduMsgputService;
    @Resource
    private UserInfoService userInfoService;

    @Override
    public void sendMsg(DownMsgReq req, UserInfo admin) {
        DownMsg msg = new DownMsg();
        msg.setContent(req.getContent());
        msg.setPusher(admin);
        msg.setPushername(admin.getRealname());
        msg.setReceiver(req.getReceiver());
        msg.setTitle(req.getTitle());
        msg.setUrl(req.getUrl());
        
        downMsgDao.save(msg);
        List<String> bdids = userInfoService.findAllBaiduIds();
        for(String bdid : bdids) {
            baiduMsgputService.downMsg(msg.getTitle(), msg.getContent(), msg.getUrl(), bdid);
        }
    }
    
    
    @Override
    @Transactional(readOnly = true)
    public DownMsgPageResp findPageDownMsg(AdminPageReqBase req) {
        DownMsgPageResp resp = new DownMsgPageResp();
         Page<DownMsgItem> page = downMsgDao.findByFeeduserId(buildPageRequest(req.getPage(), req.getNum()));
         if(null != page && null != page.getContent()) {
             resp.setData(page.getContent());
             resp.setTotal_num(page.getTotalElements());
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
