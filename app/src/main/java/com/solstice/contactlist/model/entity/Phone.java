package com.solstice.contactlist.model.entity;

import java.io.Serializable;

/**
 * Created by arielrey on 29/08/14.
 */
public class Phone implements Serializable{
    private String work;
    private String home;
    private String mobile;

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
