package com.thundersoft.javademo.base;

import com.thundersoft.model.base.ObservableUseCase;

public interface IBaseView {
    /**
     * 初始化界面元素
     */
    void initView();

    /**
     * 初始化界面观察者的监听
     */
    void initViewObservable();
}
