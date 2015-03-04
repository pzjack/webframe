/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.app.repository;

import com.dinglicom.app.domain.AppListItem;
import com.dinglicom.app.entity.AppUpdate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author panzhen
 */
public interface AppupdateDao extends PagingAndSortingRepository<AppUpdate, Long> {

    @Query("select new com.dinglicom.app.domain.AppListItem(a.id, a.version, a.versioncode, a.info, a.type, a.url, a.forceupdate, a.createDate) from AppUpdate a where a.signDelete=:signDelete")
    Page<AppListItem> getAllAppupdate(Pageable page, @Param(value="signDelete")Boolean signDelete);
    
    @Query("from AppUpdate a where a.versioncode>:versioncode and a.type=:type and a.signDelete=:signDelete order by a.id DESC")
    List<AppUpdate> findMaxVersion(@Param(value="versioncode")Integer versioncode, @Param(value="type")String type, @Param(value="signDelete")Boolean signDelete);
}
