/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.frame.sys.service;

import java.util.UUID;

/**
 *
 * @author panzhen
 */
public class GeneratorUUID {

    private final static String UUID_SPLITER = "-";

    public static String getRadomUUID() {
        String idStr = UUID.randomUUID().toString();
        int p1 = 0;
        int p2 = idStr.indexOf(UUID_SPLITER);
        StringBuilder sb = new StringBuilder();
        while (p2 >= 0) {
            if (p2 > 0) {
                sb.append(idStr.substring(p1, p2));
                p1 = p2 + 1;
                p2 = idStr.indexOf(UUID_SPLITER, p1);
            } else {
                sb.append(idStr.substring(p1));
                p2 = -1;
            }
        }
        if (p2 < 0) {
                sb.append(idStr.substring(p1));
        }
        return sb.toString().toUpperCase();
    }
    
    public static void main(String[] args) {
        System.out.println(GeneratorUUID.getRadomUUID());
    }
}
