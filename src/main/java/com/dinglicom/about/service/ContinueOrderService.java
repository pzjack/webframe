/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.about.service;

import com.dinglicom.about.entity.ContinueOrderPush;

/**
 *
 * @author panzhen
 */
public interface ContinueOrderService {

    ContinueOrderPush save(Integer days);

    ContinueOrderPush get();
}
