/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.app.service;

import com.dinglicom.app.domain.AppupdateCheckReq;
import com.dinglicom.app.domain.AppupdateCheckResp;
import com.dinglicom.app.domain.AppupdateReq;
import com.dinglicom.app.domain.AppupdatelistResp;
import com.dinglicom.app.entity.AppUpdate;

/**
 *
 * @author panzhen
 */
public interface AppupdateService {

    AppUpdate save(AppUpdate app);
    
    AppUpdate addAppupdate(AppupdateReq req);
    
    void delete(Long id);
    
    void updateAppupdate(AppupdateReq req);
    
    AppupdatelistResp getAllAppupdate();
    
    AppupdateCheckResp checkVersion(AppupdateCheckReq req);
}
