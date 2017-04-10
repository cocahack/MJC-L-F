package com.programmer.awesome.mjclnf.customObject;

import java.io.Serializable;

/**
 * Created by USER on 2016-10-27.
 */

public class Member implements Serializable{
    private String stnum;
    private String name;

    public Member(String stnum, String name){
        this.stnum = stnum;
        this.name = name;
    }
    public String getStnum() {
        return stnum;
    }

    public void setStnum(String stnum) {
        this.stnum = stnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
