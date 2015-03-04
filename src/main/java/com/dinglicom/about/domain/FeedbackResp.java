/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.about.domain;

import com.dinglicom.frame.sys.domain.BaseMsgBean;
import java.util.List;

/**
 *
 * @author panzhen
 */
public class FeedbackResp extends BaseMsgBean {
    private long unanswernum;
    private long answerednum;
    
    private List<FeedRespItem> data;

    /**
     * @return the unanswernum
     */
    public long getUnanswernum() {
        return unanswernum;
    }

    /**
     * @param unanswernum the unanswernum to set
     */
    public void setUnanswernum(long unanswernum) {
        this.unanswernum = unanswernum;
    }

    /**
     * @return the answerednum
     */
    public long getAnswerednum() {
        return answerednum;
    }

    /**
     * @param answerednum the answerednum to set
     */
    public void setAnswerednum(long answerednum) {
        this.answerednum = answerednum;
    }

    /**
     * @return the data
     */
    public List<FeedRespItem> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<FeedRespItem> data) {
        this.data = data;
    }
}
