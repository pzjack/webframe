/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.about.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author panzhen
 */
public class FeedSelfRespItem {
    protected long fid;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected Date time;
    protected String content;
    protected Answer answer;
    
    public FeedSelfRespItem() {}
    public FeedSelfRespItem(long feeduserid, Date feedtime, String content, boolean feedback, String backusername, Date backtime, String backcontent) {
        this.fid = feeduserid;
        this.time = feedtime;
        this.content = content;
        if(feedback) {
            answer = new Answer(backusername, backtime, backcontent);
        }
    }

    /**
     * @return the fid
     */
    public long getFid() {
        return fid;
    }

    /**
     * @param fid the fid to set
     */
    public void setFid(long fid) {
        this.fid = fid;
    }

    /**
     * @return the time
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Date time) {
        this.time = time;
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
     * @return the answer
     */
    public Answer getAnswer() {
        return answer;
    }

    /**
     * @param answer the answer to set
     */
    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
