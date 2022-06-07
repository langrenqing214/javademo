package com.thundersoft.domain.net.api;

import com.thundersoft.domain.net.service.InfoService;
import com.thundersoft.model.Movie;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class InfoApi {
    private static InfoApi instance ;
    private InfoService mService ;

    private InfoApi(InfoService mService){
        this.mService = mService ;
    }

    public static synchronized InfoApi getInstance(InfoService mService){
        if (instance == null){
            instance = new InfoApi(mService);
        }
        return instance ;
    }

    public Observable<Movie> getInfo(int start, int count){
        return mService.getInfo();
    }
}
