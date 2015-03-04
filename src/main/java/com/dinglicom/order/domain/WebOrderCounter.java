/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.order.domain;

/**
 *
 * @author panzhen
 */
public class WebOrderCounter {
    private long init;
    private long confirm;
    private long ing;
    private long pause;
    private long cancel;
    private long done;

    /**
     * @return the init
     */
    public long getInit() {
        return init;
    }

    /**
     * @param init the init to set
     */
    public void setInit(long init) {
        this.init = init;
    }

    /**
     * @return the confirm
     */
    public long getConfirm() {
        return confirm;
    }

    /**
     * @param confirm the confirm to set
     */
    public void setConfirm(long confirm) {
        this.confirm = confirm;
    }

    /**
     * @return the ing
     */
    public long getIng() {
        return ing;
    }

    /**
     * @param ing the ing to set
     */
    public void setIng(long ing) {
        this.ing = ing;
    }

    /**
     * @return the pause
     */
    public long getPause() {
        return pause;
    }

    /**
     * @param pause the pause to set
     */
    public void setPause(long pause) {
        this.pause = pause;
    }

    /**
     * @return the cancel
     */
    public long getCancel() {
        return cancel;
    }

    /**
     * @param cancel the cancel to set
     */
    public void setCancel(long cancel) {
        this.cancel = cancel;
    }

    /**
     * @return the done
     */
    public long getDone() {
        return done;
    }

    /**
     * @param done the done to set
     */
    public void setDone(long done) {
        this.done = done;
    }
}
