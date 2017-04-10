package com.programmer.awesome.mjclnf.customObject;

import java.io.Serializable;

/**
 * Created by HyunSoo on 2016-10-29.
 */

public class ItemInformation implements Serializable{
    private String title, category, regitDate, eventDate, kakao, phone, imgPath, name, explain, place, stnum,no;

    public ItemInformation(String title, String category, String regitDate, String eventDate, String no) {
        this.title = title;
        this.category = category;
        this.regitDate = regitDate;
        this.eventDate = eventDate;
        this.kakao = kakao;
        this.phone = phone;
        this.imgPath = imgPath;
        this.name = name;
        this.explain = explain;
        this.place = place;
        this.stnum = stnum;
        this.no = no;
    }

    public ItemInformation() {

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRegitDate() {
        return regitDate;
    }

    public void setRegitDate(String regitDate) {
        this.regitDate = regitDate;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getKakao() {
        return kakao;
    }

    public void setKakao(String kakao) {
        this.kakao = kakao;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {this.name = name; }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getStnum() {
        return stnum;
    }

    public void setStnum(String stnum) {
        this.stnum = stnum;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
