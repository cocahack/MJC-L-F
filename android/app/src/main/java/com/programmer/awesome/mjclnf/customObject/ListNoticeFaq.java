package com.programmer.awesome.mjclnf.customObject;

/**
 * Created by MJC on 2016-10-25.
 */

public class ListNoticeFaq {
    private String title, hit, regitDate,no;

    public ListNoticeFaq(String title, String hit, String regitDate, String no) {
        this.title = title;
        this.hit = hit;
        this.regitDate = regitDate;
        this.no = no;
    }

    public ListNoticeFaq() {

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String gethit() {
        return hit;
    }

    public void sethit(String hit) {
        this.hit = hit;
    }

    public String getRegitDate() {
        return regitDate;
    }

    public void setRegitDate(String regitDate) {
        this.regitDate = regitDate;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
