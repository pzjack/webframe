/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.about.entity;

import com.dinglicom.frame.entity.EntityExt;
import com.dinglicom.frame.sys.entity.UserInfo;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author panzhen
 */
@Entity
@Table(name = "down_msg")
public class DownMsg extends EntityExt implements Serializable {
    private String title;
    private String content;
    private String receiver;
    private String url;
    private UserInfo pusher;
    private String pushername;
    private Integer sendNum;
    private Integer successNum;
    private Integer deleteNum;

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the receiver
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * @param receiver the receiver to set
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the pusher
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pusher_id")
    public UserInfo getPusher() {
        return pusher;
    }

    /**
     * @param pusher the pusher to set
     */
    public void setPusher(UserInfo pusher) {
        this.pusher = pusher;
    }

    /**
     * @return the pushername
     */
    public String getPushername() {
        return pushername;
    }

    /**
     * @param pushername the pushername to set
     */
    public void setPushername(String pushername) {
        this.pushername = pushername;
    }

    /**
     * @return the sendNum
     */
    public Integer getSendNum() {
        return sendNum;
    }

    /**
     * @param sendNum the sendNum to set
     */
    public void setSendNum(Integer sendNum) {
        this.sendNum = sendNum;
    }

    /**
     * @return the successNum
     */
    public Integer getSuccessNum() {
        return successNum;
    }

    /**
     * @param successNum the successNum to set
     */
    public void setSuccessNum(Integer successNum) {
        this.successNum = successNum;
    }

    /**
     * @return the deleteNum
     */
    public Integer getDeleteNum() {
        return deleteNum;
    }

    /**
     * @param deleteNum the deleteNum to set
     */
    public void setDeleteNum(Integer deleteNum) {
        this.deleteNum = deleteNum;
    }
}
