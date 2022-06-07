package com.thundersoft.model.usecase;

import com.thundersoft.model.Movie;
import com.thundersoft.model.base.ObservableUseCase;
import com.thundersoft.model.proxy.InfoProxy;
import com.thundersoft.model.proxy.LoginProxy;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class InfoObserUseCase extends ObservableUseCase<Movie> {

    private InfoProxy mInfoProxy;
    private int start;
    private int count;

    public InfoObserUseCase(Scheduler backgroundExecutor, Scheduler scheduledExecutor, InfoProxy dataSource) {
        super(backgroundExecutor, scheduledExecutor);
        this.mInfoProxy = dataSource;
    }

    @Override
    protected Observable<Movie> setupObservable() {
        return mInfoProxy.getInfo(start, count);
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
