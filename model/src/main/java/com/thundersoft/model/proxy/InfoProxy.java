package com.thundersoft.model.proxy;

import com.thundersoft.model.LoginBean;
import com.thundersoft.model.Movie;

import java.util.List;

import io.reactivex.Observable;

public interface InfoProxy {
    Observable<Movie> getInfo(int start, int count);
}
