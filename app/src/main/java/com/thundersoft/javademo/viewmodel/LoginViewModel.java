package com.thundersoft.javademo.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.thundersoft.domain.repository.LoginRepository;
import com.thundersoft.javademo.base.BaseViewModel;
import com.thundersoft.model.LoginBean;
import com.thundersoft.model.usecase.LoginObserUseCase;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends BaseViewModel {

    private final LoginObserUseCase loginObserUseCase;

    private final MutableLiveData<LoginBean> loginLiveData = new MutableLiveData<>();

    public LoginViewModel() {
        loginObserUseCase = new LoginObserUseCase(Schedulers.io(),
                AndroidSchedulers.mainThread(), LoginRepository.getInstance());
    }

    public void login(String name, String pwd) {
        loginObserUseCase.setName(name);
        loginObserUseCase.setPwd(pwd);
        loginObserUseCase.execute(new DisposableObserver<LoginBean>() {

            @Override
            public void onNext(LoginBean loginBean) {
                loginLiveData.setValue(loginBean);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public MutableLiveData<LoginBean> getLoginLiveData() {
        return loginLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        loginObserUseCase.clear();
    }
}
