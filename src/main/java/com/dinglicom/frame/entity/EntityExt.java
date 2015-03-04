/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.frame.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *给实体附加删除标记和创建日期
 * @author panzhen
 */
@MappedSuperclass
public class EntityExt extends IdEntity {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected Date createDate = new Date();
    protected boolean signDelete = false;
    /**
     * @return the signDelete
     */
    @Column(name = "sign_delete")
    public boolean getSignDelete() {
        return signDelete;
    }

    /**
     * @param signDelete the delete to set
     */
    public void setSignDelete(boolean signDelete) {
        this.signDelete = signDelete;
    }

    /**
     * @return the createDate
     */
    @Column(name = "createdate")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
