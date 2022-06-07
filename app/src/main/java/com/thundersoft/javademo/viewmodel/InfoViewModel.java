package com.thundersoft.javademo.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.thundersoft.domain.repository.InfoRepository;
import com.thundersoft.domain.repository.LoginRepository;
import com.thundersoft.javademo.base.BaseViewModel;
import com.thundersoft.model.LoginBean;
import com.thundersoft.model.Movie;
import com.thundersoft.model.usecase.InfoObserUseCase;
import com.thundersoft.model.usecase.LoginObserUseCase;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class InfoViewModel extends BaseViewModel {

    private final InfoObserUseCase infoObserUseCase;

    private final MutableLiveData<Movie> infoLiveData = new MutableLiveData<>();

    public InfoViewModel() {
        infoObserUseCase = new InfoObserUseCase(Schedulers.io(),
                AndroidSchedulers.mainThread(), InfoRepository.getInstance());
    }

    public void getInfo(int start, int count) {
        infoObserUseCase.setStart(start);
        infoObserUseCase.setCount(count);
        infoObserUseCase.execute(new DisposableObserver<Movie>() {

            @Override
            public void onNext(Movie info) {
                infoLiveData.setValue(info);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public MutableLiveData<Movie> getinfoLiveData() {
        return infoLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        infoObserUseCase.clear();
    }
}
