/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.reportnum.service;

import com.dinglicom.reportnum.entity.ReportnumberTime;

/**
 *
 * @author panzhen
 */
public interface ReportnumberTimeService {

    ReportnumberTime save(ReportnumberTime reportnumberTime);
    
    ReportnumberTime update(ReportnumberTime reportnumberTime);
    
    ReportnumberTime read(long id);
    
     ReportnumberTime get();
}
