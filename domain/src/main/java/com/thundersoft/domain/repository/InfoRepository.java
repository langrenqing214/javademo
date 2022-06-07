package com.thundersoft.domain.repository;

import com.thundersoft.domain.Singleton;
import com.thundersoft.domain.net.RetrofitHelper;
import com.thundersoft.domain.net.service.InfoService;
import com.thundersoft.model.Movie;
import com.thundersoft.model.proxy.InfoProxy;

import java.util.List;

import io.reactivex.Observable;

public class InfoRepository implements InfoProxy {
    private static final Singleton<InfoRepository> INSTANCE = new Singleton<InfoRepository>() {
        @Override
        protected InfoRepository create() {
            return new InfoRepository();
        }
    };

    public static InfoRepository getInstance() {
        return INSTANCE.get();
    }

    @Override
    public Observable<Movie> getInfo(int start, int count) {
        return RetrofitHelper.getInstance().getRetrofit().create(InfoService.class).getInfo();
    }
}
