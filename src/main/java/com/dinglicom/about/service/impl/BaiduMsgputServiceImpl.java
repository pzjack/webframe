/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.about.service.impl;

import com.baidu.yun.channel.auth.ChannelKeyPair;
import com.baidu.yun.channel.client.BaiduChannelClient;
import com.baidu.yun.channel.exception.ChannelClientException;
import com.baidu.yun.channel.exception.ChannelServerException;
import com.baidu.yun.channel.model.PushUnicastMessageRequest;
import com.baidu.yun.channel.model.PushUnicastMessageResponse;
import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.dinglicom.about.service.BaiduMsgputService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author panzhen
 */
@Component
@Transactional
public class BaiduMsgputServiceImpl implements BaiduMsgputService {
    private final static Logger LOG = LoggerFactory.getLogger(BaiduMsgputServiceImpl.class);
    @Value("${baidu.apiKey}")
    private String apiKey;
    @Value("${baidu.secretKey}")
    private String secretKey;
    
    @Override
    public void downMsg(String title, String msg, String url, String userids) {
        ChannelKeyPair pair = new ChannelKeyPair(apiKey, secretKey);
        BaiduChannelClient channelClient = new BaiduChannelClient(pair);
        channelClient.setChannelLogHandler(new YunLogHandler() {
            @Override
            public void onHandle(YunLogEvent event) {
                LOG.info(event.getMessage());
            }
        });
        
         try {
            PushUnicastMessageRequest request = new PushUnicastMessageRequest();
            request.setDeviceType(3);
            request.setUserId(userids);
            request.setMessage("{\"title\":\"" + title + "\", \"content\":\"" + msg + "\",\"url\":\"" + url + "\"}");
            PushUnicastMessageResponse response = channelClient.pushUnicastMessage(request);
            LOG.info("Push msg success num {}", response.getSuccessAmount());

        } catch (ChannelClientException e) {
            LOG.warn("Down msg fail.", e);
        } catch (ChannelServerException e) {
            LOG.warn("Down msg fail:" + String.format(
                    "request_id: %d, error_code: %d, error_message: %s",
                    e.getRequestId(), e.getErrorCode(), e.getErrorMsg()), e);
        }
    }
}
