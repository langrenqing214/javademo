package com.thundersoft.model.usecase;

import com.thundersoft.model.LoginBean;
import com.thundersoft.model.base.ObservableUseCase;
import com.thundersoft.model.proxy.LoginProxy;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;

public class LoginObserUseCase extends ObservableUseCase<LoginBean> {

    private LoginProxy mDataSourceProxy;
    private String name;
    private String pwd;

    public LoginObserUseCase(Scheduler backgroundExecutor, Scheduler scheduledExecutor, LoginProxy dataSource) {
        super(backgroundExecutor, scheduledExecutor);
        this.mDataSourceProxy = dataSource;
    }

    @Override
    protected Observable<LoginBean> setupObservable() {
        return mDataSourceProxy.login(name, pwd);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
