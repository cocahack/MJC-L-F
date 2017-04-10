package com.programmer.awesome.mjclnf.customObject;

/**
 * Created by MJC on 2016-10-25.
 */

public class ListItem {
    private String title, category, regitDate, eventDate,no;

    public ListItem(String title, String category, String regitDate, String eventDate, String no) {
        this.title = title;
        this.category = category;
        this.regitDate = regitDate;
        this.eventDate = eventDate;
        this.no = no;
    }

    public ListItem() {

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

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
