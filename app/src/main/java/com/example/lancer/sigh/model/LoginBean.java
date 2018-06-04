package com.example.lancer.sigh.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by Lancer on 2018/6/4.
 */

public class LoginBean extends BmobObject {
    private String phone;
    private String pwd;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
