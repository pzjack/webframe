/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.about.domain;

import com.dinglicom.frame.sys.domain.AppPageReqBase;

/**
 *
 * @author panzhen
 */
public class AnswerReq extends AppPageReqBase {
    private long fid;
    private String content;

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
}
