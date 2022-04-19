package com.thundersoft.javademo.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<VM extends BaseViewModel> extends AppCompatActivity implements IBaseView {
    protected Unbinder mUnbinder;
    protected VM mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideContentView());
        mUnbinder = ButterKnife.bind(this);

        if (mViewModel == null) {
            mViewModel = initViewModel();
            if (mViewModel == null) {
                Class modelClass;
                Type type = getClass().getGenericSuperclass();
                if (type instanceof ParameterizedType) {
                    modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
                } else {
                    modelClass = BaseViewModel.class;
                }
                mViewModel = (VM) createViewModel(this, modelClass);
            }
            getLifecycle().addObserver(mViewModel);
            initView();
            initViewObservable();
        }
    }

    public VM getViewModel() {
        return mViewModel;
    }

    /**
     * 传递自定义的ViewModel，当需要自定义ViewModel才重写此方法
     *
     * @return 返回自定义的ViewModel，默认为空。
     */
    public VM initViewModel() {
        return null;
    }

    @Override
    public abstract void initView();

    /**
     * 提供R.layout.xx
     */
    public abstract int provideContentView();

    /**
     * Activity监听ViewModel中的数据源，统一在此函数
     */
    @Override
    public abstract void initViewObservable();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
        if (mViewModel != null) {
            mViewModel = null;
        }
    }

    /**
     * 创建ViewModel
     *
     * @param cls Activity对象
     * @param <T> ViewModel类
     * @return ViewModel具体实现类
     */
    private <T extends ViewModel> T createViewModel(FragmentActivity activity, Class<T> cls) {
        return ViewModelProviders.of(activity).get(cls);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
