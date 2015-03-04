/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.about.service.impl;

import com.dinglicom.about.entity.Aboutus;
import com.dinglicom.about.repository.AboutusDao;
import com.dinglicom.about.service.AboutusServcice;
import java.util.Iterator;
import javax.annotation.Resource;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author panzhen
 */
@CacheConfig(cacheNames = {"aboutusCache"})
@Component
@Transactional
public class AboutusServciceImpl implements AboutusServcice {
    @Resource
    private AboutusDao aboutusDao;
    
    @Override
    @CachePut(key = "#aboutus.id")
    public Aboutus save(Aboutus aboutus) {
        return aboutusDao.save(aboutus);
    }
    
    @Override
    @CacheEvict(key = "#aboutus.id")
    public Aboutus update(Aboutus aboutus) {
        return aboutusDao.save(aboutus);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable
    public Aboutus get() {
        Iterable<Aboutus> ita = aboutusDao.findAll();
        Iterator<Aboutus> it = ita.iterator();
        if(it.hasNext()) {
            return it.next();
        }
        return null;
    }
}
