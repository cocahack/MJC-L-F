package com.programmer.awesome.mjclnf.customObject;

/**
 * Created by HyunSoo on 2016-11-13.
 */

public class NoticeFaq {
    private String title, hit, regitDate, no, content, imgPath, writer;

    public NoticeFaq(String title, String hit, String regitDate, String no) {
        this.title = title;
        this.hit = hit;
        this.regitDate = regitDate;
        this.no = no;
        this.content = content;
        this.imgPath = imgPath;
        this.writer = writer;
    }

    public NoticeFaq() {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }


    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

}

