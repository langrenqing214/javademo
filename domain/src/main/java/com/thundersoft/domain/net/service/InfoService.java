package com.thundersoft.domain.net.service;

import com.thundersoft.model.Movie;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface InfoService {
    @GET("api/news/feed/v51/")
    Observable<Movie> getInfo();
}
