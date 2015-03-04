/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.product.domain;

import com.dinglicom.frame.sys.domain.AdminReqBase;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 *
 * @author panzhen
 */
public class ProductPicReq extends AdminReqBase {
    private CommonsMultipartFile filename;

    /**
     * @return the filename
     */
    public CommonsMultipartFile getFilename() {
        return filename;
    }

    /**
     * @param filename the filename to set
     */
    public void setFilename(CommonsMultipartFile filename) {
        this.filename = filename;
    }
}
