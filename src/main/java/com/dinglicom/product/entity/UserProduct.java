/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dinglicom.product.entity;

import com.dinglicom.frame.entity.EntityExt;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户商品
 * @author panzhen
 */
@Entity
@Table(name = "user_product")
public class UserProduct extends EntityExt implements Serializable {
    private String name;//名称
    private double weight;//重量
    private String specification;//规格
    private String model;//型号、货号
    private String brand;//品牌
    private double price;//价格
    private double homePrice;//送货到家价格
    private String typeDesc;//分类描述
    private String shortDesc;//短描述
    private String details;//详细说明
    private String smallPic;//小图片路径
    private String bigPic;//大图片路径
    private String state;//状态，正常、下架
    private int productnum = 30;//商品每批的数量，也就是多少瓶
    private String shortname;
    private int producttype;
    private int viewrange;
    
    public UserProduct () {
    }
    public UserProduct(long id, String name, String smallPic, double weight, double price, double homePrice) {
        this.id = id;
        this.name = name;
        this.smallPic = smallPic;
        this.weight = weight;
        this.price = price;
        this.homePrice = homePrice;
    }

    /**商品名称
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**商品重量（单位克，整数）
     * @return the weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**规格
     * @return the specification
     */
    public String getSpecification() {
        return specification;
    }

    /**
     * @param specification the specification to set
     */
    public void setSpecification(String specification) {
        this.specification = specification;
    }

    /**型号、货号
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**品牌
     * @return the brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @param brand the brand to set
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**价格，单价
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**送货到家价格
     * @return the homePrice
     */
    @Column(name = "home_price")
    public double getHomePrice() {
        return homePrice;
    }

    /**
     * @param homePrice the homePrice to set
     */
    public void setHomePrice(double homePrice) {
        this.homePrice = homePrice;
    }

    /**分类描述
     * @return the typeDesc
     */
    @Column(name = "type_desc")
    public String getTypeDesc() {
        return typeDesc;
    }

    /**
     * @param typeDesc the typeDesc to set
     */
    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    /**短描述
     * @return the shortDesc
     */
    @Column(name = "short_desc")
    public String getShortDesc() {
        return shortDesc;
    }

    /**详细说明
     * @param shortDesc the shortDesc to set
     */
    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    /**详细说明
     * @return the details
     */
    public String getDetails() {
        return details;
    }

    /**
     * @param details the details to set
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**小图片路径
     * @return the smallPic
     */
    @Column(name = "small_pic")
    public String getSmallPic() {
        return smallPic;
    }

    /**
     * @param smallPic the smallPic to set
     */
    public void setSmallPic(String smallPic) {
        this.smallPic = smallPic;
    }

    /**大图片路径
     * @return the bigPic
     */
    @Column(name = "big_pic")
    public String getBigPic() {
        return bigPic;
    }

    /**
     * @param bigPic the bigPic to set
     */
    public void setBigPic(String bigPic) {
        this.bigPic = bigPic;
    }

    /**状态，正常、下架
     * @return the state
     */
    @Column(name = "p_state")
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the productnum
     */
    public int getProductnum() {
        return productnum;
    }

    /**
     * @param productnum the productnum to set
     */
    public void setProductnum(int productnum) {
        this.productnum = productnum;
    }

    /**
     * @return the producttype
     */
    public int getProducttype() {
        return producttype;
    }

    /**
     * @param producttype the producttype to set
     */
    public void setProducttype(int producttype) {
        this.producttype = producttype;
    }

    /**
     * @return the viewrange
     */
    public int getViewrange() {
        return viewrange;
    }

    /**
     * @param viewrange the viewrange to set
     */
    public void setViewrange(int viewrange) {
        this.viewrange = viewrange;
    }

    /**
     * @return the shortname
     */
    public String getShortname() {
        return shortname;
    }

    /**
     * @param shortname the shortname to set
     */
    public void setShortname(String shortname) {
        this.shortname = shortname;
    }
    
}