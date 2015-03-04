/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.about.repository;

import com.dinglicom.about.domain.FeedRespItem;
import com.dinglicom.about.domain.FeedSelfRespItem;
import com.dinglicom.about.entity.FeedBack;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author panzhen
 */
public interface FeedbackDao  extends PagingAndSortingRepository<FeedBack, Long>, JpaSpecificationExecutor<FeedBack> {
    /**
     * 查询特定用户的反馈及回答信息
     * @param page
     * @param feeduserId
     * @param signDelete
     * @return 
     */
    @Query("select new com.dinglicom.about.domain.FeedSelfRespItem(feeduser.id, feedtime, content, feedback, backusername, backtime, backcontent) from FeedBack a where a.feeduser.id=:feeduserId and a.signDelete=:signDelete")
    Page<FeedSelfRespItem> findByFeeduserId(Pageable page, @Param(value="feeduserId")long feeduserId, @Param(value="signDelete")Boolean signDelete);
    /**
     * 查询所有的反馈及回答信息
     * @param page
     * @param signDelete
     * @return 
     */
    @Query("select new com.dinglicom.about.domain.FeedRespItem(id, feedname, feedtel, feedtime, content, feedback, backusername, backtime, backcontent) from FeedBack a where a.signDelete=:signDelete")
    Page<FeedRespItem> findByPage(Pageable page, @Param(value="signDelete")Boolean signDelete);
    
    @Query("select count(*) from FeedBack a where a.signDelete=:signDelete")
    int findCount(@Param(value="signDelete")Boolean signDelete);
    
    /**
     * 查询所有的反馈及回答信息
     * 按照是否反馈及应答的状态
     * @param page
     * @param feedback
     * @param signDelete
     * @return 
     */
    @Query("select new com.dinglicom.about.domain.FeedRespItem(id, feedname, feedtel, feedtime, content, feedback, backusername, backtime, backcontent) from FeedBack a where a.feedback=:feedback and a.signDelete=:signDelete")
    Page<FeedRespItem> findByPageByFeedback(Pageable page, @Param(value="feedback")Boolean feedback, @Param(value="signDelete")Boolean signDelete);
    
    @Query("select count(*) from FeedBack a where a.feedback=:feedback and a.signDelete=:signDelete")
    int findFeedbackCount(@Param(value="feedback")Boolean feedback, @Param(value="signDelete")Boolean signDelete);
}
