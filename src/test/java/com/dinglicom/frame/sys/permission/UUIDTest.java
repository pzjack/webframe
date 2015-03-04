/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.frame.sys.permission;

import java.util.UUID;

/**
 *
 * @author panzhen
 */
public class UUIDTest {

    public void createUUID() {
        System.out.println(UUID.randomUUID().toString());
    }
    public static void main(String[] args) {
        UUIDTest t = new UUIDTest();
        t.createUUID();
    }
}
