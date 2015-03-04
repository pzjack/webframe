/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.reportnum.service.impl;

import com.dinglicom.reportnum.entity.ReportnumberTime;
import com.dinglicom.reportnum.repository.ReportnumberTimeDao;
import com.dinglicom.reportnum.service.ReportnumberTimeService;
import java.util.Iterator;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author panzhen
 */
@Component
@Transactional
public class ReportnumberTimeServiceImpl implements ReportnumberTimeService {

    @Resource
    private ReportnumberTimeDao reportnumberTimeDao;

    @Override
    public ReportnumberTime save(ReportnumberTime reportnumberTime) {
        return reportnumberTimeDao.save(reportnumberTime);
    }

    @Override
    public ReportnumberTime update(ReportnumberTime reportnumberTime) {
        return reportnumberTimeDao.save(reportnumberTime);
    }

    @Override
    @Transactional(readOnly = true)
    public ReportnumberTime read(long id) {
        return reportnumberTimeDao.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ReportnumberTime get() {
        Iterable<ReportnumberTime> tab = reportnumberTimeDao.findAll();
        if (null != tab) {
            Iterator<ReportnumberTime> it = tab.iterator();
            if (it.hasNext()) {
                return it.next();
            }
        }
        return null;
    }
}
