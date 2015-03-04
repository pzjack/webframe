/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.about.service;

import com.dinglicom.about.entity.Aboutus;

/**
 *
 * @author panzhen
 */
public interface AboutusServcice {

    Aboutus save(Aboutus aboutus);
    
    Aboutus update(Aboutus aboutus);
    
    Aboutus get();
}
