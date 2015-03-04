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
public class RoleCRUD {
    
    public void addRole() {
        String inputJsonStr = "{\"role\":\"test_role1\",\"description\":\"用户角色测试\",\"available\":true}";
        HttpClient httpclient = new DefaultHttpClient(); // 13800510543
        try {
            HttpEntityWrapper en = new HttpEntityWrapper(new StringEntity(inputJsonStr, ContentType.create("application/json", "UTF-8")));
            HttpPost post = new HttpPost("http://localhost:8080/webframe-1.0-SNAPSHOT/api/v1/role/save");
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
    /*
    {
  "id" : 3,
  "delete" : false,
  "createDate" : "2014-10-30 22:36:45",
  "role" : "test_role1",
  "description" : "用户角色测试",
  "available" : true,
  "permissions" : [ {
    "id" : 1,
    "delete" : true,
    "createDate" : null,
    "permission" : "user:create",
    "description" : "user:create",
    "available" : null
  }, {
    "id" : 2,
    "delete" : false,
    "createDate" : null,
    "permission" : "user:update",
    "description" : "用户信息修改权限",
    "available" : true
  }, {
    "id" : 3,
    "delete" : false,
    "createDate" : null,
    "permission" : "user:delete",
    "description" : "用户信息删除权限",
    "available" : true
  }, {
    "id" : 4,
    "delete" : false,
    "createDate" : null,
    "permission" : "user:query",
    "description" : "用户信息查询权限",
    "available" : true
  }, {
    "id" : 5,
    "delete" : false,
    "createDate" : "2014-10-29 22:51:57",
    "permission" : "user:view",
    "description" : "用户信息查看权限",
    "available" : true
  }, {
    "id" : 6,
    "delete" : false,
    "createDate" : "2014-10-29 23:01:05",
    "permission" : "user:test",
    "description" : "用户信息test权限",
    "available" : true
  } ]
}
*/    
    public void addRolePermission() {
        String inputJsonStr = "{\"id\":3}";
        HttpClient httpclient = new DefaultHttpClient(); // 13800510543
        try {
            HttpEntityWrapper en = new HttpEntityWrapper(new StringEntity(inputJsonStr, ContentType.create("application/json", "UTF-8")));
            HttpPost post = new HttpPost("http://localhost:8080/webframe-1.0-SNAPSHOT/api/v1/role/addpms");
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
    
    public void removeRolePermission() {
        String inputJsonStr = "{\"id\":3}";
        HttpClient httpclient = new DefaultHttpClient(); // 13800510543
        try {
            HttpEntityWrapper en = new HttpEntityWrapper(new StringEntity(inputJsonStr, ContentType.create("application/json", "UTF-8")));
            HttpPost post = new HttpPost("http://localhost:8080/webframe-1.0-SNAPSHOT/api/v1/role/deletepms");
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
        RoleCRUD t = new RoleCRUD();
//        t.addRole();
//        t.addRolePermission();
        t.removeRolePermission();
    }
}
