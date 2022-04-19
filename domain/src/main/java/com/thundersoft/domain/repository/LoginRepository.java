package com.thundersoft.domain.repository;

import com.thundersoft.domain.Singleton;
import com.thundersoft.model.LoginBean;
import com.thundersoft.model.proxy.LoginProxy;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class LoginRepository implements LoginProxy {

    private static final Singleton<LoginRepository> INSTANCE = new Singleton<LoginRepository>() {
        @Override
        protected LoginRepository create() {
            return new LoginRepository();
        }
    };

    public static LoginRepository getInstance() {
        return INSTANCE.get();
    }

    @Override
    public Observable<LoginBean> login(final String name, final String pwd) {
        return Observable.create(new ObservableOnSubscribe<LoginBean>() {
            @Override
            public void subscribe(ObservableEmitter<LoginBean> emitter) throws Exception {
                LoginBean bean = new LoginBean();
                if ("test".equals(name) && "111".equals(pwd)) {
                    bean.setName(name);
                    bean.setPwd(pwd);
                    bean.setLogin(true);
                } else {
                    bean.setLogin(false);
                }
                emitter.onNext(bean);
            }
        });
    }
}
