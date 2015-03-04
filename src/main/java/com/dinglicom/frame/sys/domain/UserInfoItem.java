/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinglicom.frame.sys.domain;

import com.dinglicom.frame.sys.service.UserInfoService;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author panzhen
 */
public class UserInfoItem {

    private Long uid;
    private String account;
    private String role;
    private String sex;
    private String id_number;
    private String realname;
    private String manager;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;
    private String province;
    private String city;
    private String region;
    private String address;
    private String desc;
    private String tel;
    private Long pid;
    private Long cid;
    private Long rid;
    private Long sup_id;
    private String sup_name;

    public UserInfoItem() {
    }

    public UserInfoItem(Long uid, String account, String role, String sex, String id_number, String realname, Date birthday, Long pid, String province, Long cid, String city, Long rid, String region, String address, String desc, String tel, Long sup_id, String sup_name) {
        this.uid = uid;
        this.account = account;
        this.role = role;
        this.sex = sex;
        this.id_number = id_number;
        this.realname = realname;
        this.birthday = birthday;
        this.pid = pid;
        this.province = province;
        this.cid = cid;
        this.city = city;
        this.rid = rid;
        this.region = region;
        this.address = address;
        this.desc = desc;
        this.tel = tel;
        this.sup_id = sup_id;
        this.sup_name = sup_name;
    }

    public UserInfoItem(Long uid, String account, String role, String sex, String id_number, String realname, Date birthday, Long pid, String province, Long cid, String city, Long rid, String region, String address, String desc, String tel, Long sup_id, String sup_name, String nickname) {
        this(uid, account, role, sex, id_number, realname, birthday, pid, province, cid, city, rid, region, address, desc, tel, sup_id, sup_name);
        if (UserInfoService.USER_ROLE_DEALER.equalsIgnoreCase(role) || UserInfoService.USER_ROLE_STATION.equalsIgnoreCase(role)) {
            this.sup_id = sup_id;
            this.sup_name = sup_name;
            this.manager = nickname;
        }
    }
    
    public UserInfoItem(Long uid, String account, String role, String sex, String id_number, String realname, Date birthday, Long pid, String province, Long cid, String city, Long rid, String region, String address, String desc, String tel, Long sup_id, String sup_name, Long managerid, String manager, String nickname) {
        this(uid, account, role, sex, id_number, realname, birthday, pid, province, cid, city, rid, region, address, desc, tel, sup_id, sup_name);
        if (UserInfoService.USER_ROLE_DEALER.equalsIgnoreCase(role) || UserInfoService.USER_ROLE_STATION.equalsIgnoreCase(role)) {
            this.sup_id = managerid;
            this.sup_name = manager;
            this.manager = nickname;
        }
    }

    /**
     * @return the uid
     */
    public Long getUid() {
        return uid;
    }

    /**
     * @param uid the uid to set
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * @return the account
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return the sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return the id_number
     */
    public String getId_number() {
        return id_number;
    }

    /**
     * @param id_number the id_number to set
     */
    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    /**
     * @return the realname
     */
    public String getRealname() {
        return realname;
    }

    /**
     * @param realname the realname to set
     */
    public void setRealname(String realname) {
        this.realname = realname;
    }

    /**
     * @return the birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * @return the province
     */
    public String getProvince() {
        return province;
    }

    /**
     * @param province the province to set
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the region
     */
    public String getRegion() {
        return region;
    }

    /**
     * @param region the region to set
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return the tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel the tel to set
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * @return the pid
     */
    public Long getPid() {
        return pid;
    }

    /**
     * @param pid the pid to set
     */
    public void setPid(Long pid) {
        this.pid = pid;
    }

    /**
     * @return the cid
     */
    public Long getCid() {
        return cid;
    }

    /**
     * @param cid the cid to set
     */
    public void setCid(Long cid) {
        this.cid = cid;
    }

    /**
     * @return the rid
     */
    public Long getRid() {
        return rid;
    }

    /**
     * @param rid the rid to set
     */
    public void setRid(Long rid) {
        this.rid = rid;
    }

    /**
     * @return the sup_id
     */
    public Long getSup_id() {
        return sup_id;
    }

    /**
     * @param sup_id the sup_id to set
     */
    public void setSup_id(Long sup_id) {
        this.sup_id = sup_id;
    }

    /**
     * @return the sup_name
     */
    public String getSup_name() {
        return sup_name;
    }

    /**
     * @param sup_name the sup_name to set
     */
    public void setSup_name(String sup_name) {
        this.sup_name = sup_name;
    }

    /**
     * @return the manager
     */
    public String getManager() {
        return manager;
    }

    /**
     * @param manager the manager to set
     */
    public void setManager(String manager) {
        this.manager = manager;
    }
}
