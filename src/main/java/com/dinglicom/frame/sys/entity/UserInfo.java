/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.frame.sys.entity;

import com.dinglicom.frame.entity.EntityExt;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 * 用户信息
 * @author panzhen
 */
@Entity
@Table(name = "sys_user_info")
public class UserInfo extends EntityExt  implements java.io.Serializable {
    private SysOranizagion org;
    private String orgname;
    private String nickname;
    private String realname;
    private String sex;
    private Date birthday;
    private String docType;
    private String idNumber;
    private String userType;
    private String mail;
    private String phone;
    private String provincename;
    private String cityname;
    private String regionname;
    private String address;
    private String desc;
    private SysOranizagion province;
    private SysOranizagion city;
    private SysOranizagion region;
    private String account;
    private String baiduid;
    private Long managerid;
    private String manager;
    
    public UserInfo() {}
    public UserInfo(long id, String name, String phone) {
        this.id = id;
        this.realname = name;
        this.phone = phone;
    }

    /**
     * @return the org
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id")
    public SysOranizagion getOrg() {
        return org;
    }

    /**
     * @param org the org to set
     */
    public void setOrg(SysOranizagion org) {
        this.org = org;
    }

    /**
     * @return the orgname
     */
    @Column(name = "orgname")
    public String getOrgname() {
        return orgname;
    }

    /**
     * @param orgname the orgname to set
     */
    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    /**
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname the nickname to set
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
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
     * @return the birthday
     */
    @Column(name = "birthday")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
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
     * @return the docType
     */
    @Column(name="doc_type")
    public String getDocType() {
        return docType;
    }

    /**
     * @param docType the docType to set
     */
    public void setDocType(String docType) {
        this.docType = docType;
    }

    /**
     * @return the idNumber
     */
     @Column(name="id_number")
    public String getIdNumber() {
        return idNumber;
    }

    /**
     * @param idNumber the idNumber to set
     */
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    /**
     * @return the userType
     */
    @Column(name="user_type")
    public String getUserType() {
        return userType;
    }

    /**
     * @param userType the userType to set
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the province
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id")
    public SysOranizagion getProvince() {
        return province;
    }

    /**
     * @param province the province to set
     */
    public void setProvince(SysOranizagion province) {
        this.province = province;
    }

    /**
     * @return the city
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    public SysOranizagion getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(SysOranizagion city) {
        this.city = city;
    }

    /**
     * @return the region
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    public SysOranizagion getRegion() {
        return region;
    }

    /**
     * @param region the region to set
     */
    public void setRegion(SysOranizagion region) {
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
    @Column(name = "description")
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
     * @return the provincename
     */
    public String getProvincename() {
        return provincename;
    }

    /**
     * @param provincename the provincename to set
     */
    public void setProvincename(String provincename) {
        this.provincename = provincename;
    }

    /**
     * @return the cityname
     */
    public String getCityname() {
        return cityname;
    }

    /**
     * @param cityname the cityname to set
     */
    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    /**
     * @return the regionname
     */
    public String getRegionname() {
        return regionname;
    }

    /**
     * @param regionname the regionname to set
     */
    public void setRegionname(String regionname) {
        this.regionname = regionname;
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
     * @return the baiduid
     */
    public String getBaiduid() {
        return baiduid;
    }

    /**
     * @param baiduid the baiduid to set
     */
    public void setBaiduid(String baiduid) {
        this.baiduid = baiduid;
    }

    /**
     * @return the managerid
     */
    public Long getManagerid() {
        return managerid;
    }

    /**
     * @param managerid the managerid to set
     */
    public void setManagerid(Long managerid) {
        this.managerid = managerid;
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
