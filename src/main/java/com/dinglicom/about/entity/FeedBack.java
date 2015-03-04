/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.about.entity;

import com.dinglicom.frame.entity.EntityExt;
import com.dinglicom.frame.sys.entity.UserInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 用户反馈及回复表
 * @author panzhen
 */
@Entity
@Table(name = "feed_back")
public class FeedBack  extends EntityExt implements Serializable {
    private UserInfo feeduser;//反馈人
    private String feedname;//反馈人姓名
    private String feedtel;//反馈人联系电话
    private String content;//反馈内容
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date feedtime;//反馈是否回复
    private boolean feedback;//是否已经应答
    
    private UserInfo backuser;//应答人
    private String backusername;//应答人姓名
    private String backcontent;//应答内容
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date backtime;//应答时间
    

    /**
     * @return the feeduser
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    public UserInfo getFeeduser() {
        return feeduser;
    }

    /**
     * @param feeduser the feeduser to set
     */
    public void setFeeduser(UserInfo feeduser) {
        this.feeduser = feeduser;
    }

    /**
     * @return the feedname
     */
    @Column(name = "feed_name")
    public String getFeedname() {
        return feedname;
    }

    /**
     * @param feedname the feedname to set
     */
    public void setFeedname(String feedname) {
        this.feedname = feedname;
    }

    /**
     * @return the feedtel
     */
    @Column(name = "feed_tel")
    public String getFeedtel() {
        return feedtel;
    }

    /**
     * @param feedtel the feedtel to set
     */
    public void setFeedtel(String feedtel) {
        this.feedtel = feedtel;
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
     * @return the feedtime
     */
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getFeedtime() {
        return feedtime;
    }

    /**
     * @param feedtime the feedtime to set
     */
    public void setFeedtime(Date feedtime) {
        this.feedtime = feedtime;
    }

    /**
     * @return the feedback
     */
    public boolean isFeedback() {
        return feedback;
    }

    /**
     * @param feedback the feedback to set
     */
    public void setFeedback(boolean feedback) {
        this.feedback = feedback;
    }

    /**
     * @return the backuser
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "backuser_id")
    public UserInfo getBackuser() {
        return backuser;
    }

    /**
     * @param backuser the backuser to set
     */
    public void setBackuser(UserInfo backuser) {
        this.backuser = backuser;
    }

    /**
     * @return the backusername
     */
    @Column(name = "back_username")
    public String getBackusername() {
        return backusername;
    }

    /**
     * @param backusername the backusername to set
     */
    public void setBackusername(String backusername) {
        this.backusername = backusername;
    }

    /**
     * @return the backcontent
     */
    @Column(name = "back_content")
    public String getBackcontent() {
        return backcontent;
    }

    /**
     * @param backcontent the backcontent to set
     */
    public void setBackcontent(String backcontent) {
        this.backcontent = backcontent;
    }

    /**
     * @return the backtime
     */
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getBacktime() {
        return backtime;
    }

    /**
     * @param backtime the backtime to set
     */
    public void setBacktime(Date backtime) {
        this.backtime = backtime;
    }
    
}
