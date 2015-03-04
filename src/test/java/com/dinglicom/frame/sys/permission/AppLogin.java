/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.frame.sys.permission;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author panzhen
 */
public class AppLogin {
    
    public void login() {
        String inputJsonStr = "{\"account\":\"admin\",\"pwd\":\"admin\"}";
        HttpClient httpclient = new DefaultHttpClient(); // 13800510543
        try {
            HttpEntityWrapper en = new HttpEntityWrapper(new StringEntity(inputJsonStr, ContentType.create("application/json", "UTF-8")));
            HttpPost post = new HttpPost("http://localhost:8080/webframe-1.0-SNAPSHOT//api/v1/app/login");
            post.setEntity(en);
            HttpResponse response = httpclient.execute(post);
            HttpEntity resEntity = response.getEntity();
            System.out.println(EntityUtils.toString(resEntity));
        } catch (IOException ex) {
            Logger.getLogger(RoleCRUD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
    }
    
    public static void main(String[] args) {
        AppLogin app = new AppLogin();
        app.login();
    }

}
