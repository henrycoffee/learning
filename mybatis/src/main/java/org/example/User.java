package org.example;

import java.util.Date;


public class User {
    private int id;
    private String name;
    private int password;
    private String city;
    private Date birthday;
    private Date resgistTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getResgistTime() {
        return resgistTime;
    }

    public void setResgistTime(Date resgistTime) {
        this.resgistTime = resgistTime;
    }
}
