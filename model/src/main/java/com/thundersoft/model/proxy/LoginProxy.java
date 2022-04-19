package com.thundersoft.model.proxy;

import com.thundersoft.model.LoginBean;

import io.reactivex.Observable;

public interface LoginProxy {
    /**
     * 登录接口
     * @param name
     * @param pwd
     * @return
     */
    Observable<LoginBean> login(String name, String pwd);
}
