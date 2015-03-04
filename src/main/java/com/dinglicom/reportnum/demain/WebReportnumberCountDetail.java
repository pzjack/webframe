/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.reportnum.demain;

/**
 *
 * @author panzhen
 */
public class WebReportnumberCountDetail {
    private long unreport;
    private long reported;
    private long shipped;

    /**
     * @return the unreport
     */
    public long getUnreport() {
        return unreport;
    }

    /**
     * @param unreport the unreport to set
     */
    public void setUnreport(long unreport) {
        this.unreport = unreport;
    }

    /**
     * @return the reported
     */
    public long getReported() {
        return reported;
    }

    /**
     * @param reported the reported to set
     */
    public void setReported(long reported) {
        this.reported = reported;
    }

    /**
     * @return the shipped
     */
    public long getShipped() {
        return shipped;
    }

    /**
     * @param shipped the shipped to set
     */
    public void setShipped(long shipped) {
        this.shipped = shipped;
    }
}
