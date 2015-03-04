/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.about.domain;

import java.util.Date;

/**
 *
 * @author panzhen
 */
public class FeedRespItem extends FeedSelfRespItem {
    private String author;
    private String tel;
    
    public FeedRespItem() {}
    public FeedRespItem(long feeduserid, String author, String tel, Date feedtime, String content, boolean feedback, String backusername, Date backtime, String backcontent) {
        super(feeduserid, feedtime, content, feedback, backusername, backtime, backcontent);
        this.author = author;
        this.tel = tel;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return the tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel the tel to set
     */
    public void setTel(String tel) {
        this.tel = tel;
    }
}
