/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.about.service.impl;

import com.dinglicom.about.entity.ContinueOrderPush;
import com.dinglicom.about.repository.ContinueOrderPushDao;
import com.dinglicom.about.service.ContinueOrderService;
import java.util.Iterator;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author panzhen
 */
@Component
@Transactional
public class ContinueOrderServiceImpl implements ContinueOrderService {
    @Resource
    private ContinueOrderPushDao continueOrderPushDao;
    
    @Override
    public ContinueOrderPush save(Integer days) {
        ContinueOrderPush cop;
        cop = get();
        if(null == cop) {
            cop = new ContinueOrderPush();
        }
        cop.setDays(days);
        continueOrderPushDao.save(cop);
        
        return cop;
    }
    
    @Override
    @Transactional(readOnly = true)
    public ContinueOrderPush get() {
        Iterable<ContinueOrderPush> list = continueOrderPushDao.findAll();
        if(null != list) {
            Iterator<ContinueOrderPush> it = list.iterator();
            if(it.hasNext()) {
                return it.next();
            }
        }
        return null;
    }
}
