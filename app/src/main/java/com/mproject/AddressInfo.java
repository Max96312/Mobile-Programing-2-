package com.mproject;

import java.io.Serializable;

public class AddressInfo /*implements Serializable*/ {
    private String name;
    private String age;
    private String phone;
    private String job;

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public String getJob() {
        return job;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
