/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.about.service;

/**
 *
 * @author panzhen
 */
public interface BaiduMsgputService {

    void downMsg(String title, String msg, String url, String userids);
}
