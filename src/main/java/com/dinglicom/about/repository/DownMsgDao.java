/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.about.repository;

import com.dinglicom.about.domain.DownMsgItem;
import com.dinglicom.about.entity.DownMsg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author panzhen
 */
public interface DownMsgDao  extends PagingAndSortingRepository<DownMsg, Long>, JpaSpecificationExecutor<DownMsg> {

    
    @Query("select new com.dinglicom.about.domain.DownMsgItem(id, createDate, pushername, receiver, title, content, url) from DownMsg a")
    Page<DownMsgItem> findByFeeduserId(Pageable page);
}
