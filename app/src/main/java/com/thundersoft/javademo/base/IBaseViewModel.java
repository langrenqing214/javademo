package com.thundersoft.javademo.base;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface IBaseViewModel extends LifecycleObserver {
    /**
     * 同activity的onCreate
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    /**
     * 同activity的onStart
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStart();

    /**
     * 同activity的onResume
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume();

    /**
     * 同activity的onPause
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause();

    /**
     * 同activity的onStop
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop();

    /**
     * 同activity的onDestory
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestory();
}
