package com.thundersoft.model;

public class LoginBean {

    private String name;
    private String pwd;
    private boolean isLogin;

    @Override
    public String toString() {
        return "LoginBean{" +
                "name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", isLogin=" + isLogin +
                '}';
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

}
