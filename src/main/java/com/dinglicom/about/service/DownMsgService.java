/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.about.service;

import com.dinglicom.about.domain.DownMsgPageResp;
import com.dinglicom.about.domain.DownMsgReq;
import com.dinglicom.frame.sys.domain.AdminPageReqBase;
import com.dinglicom.frame.sys.entity.UserInfo;

/**
 *
 * @author panzhen
 */
public interface DownMsgService {

    void sendMsg(DownMsgReq req, UserInfo admin);
    
    DownMsgPageResp findPageDownMsg(AdminPageReqBase req);
}
