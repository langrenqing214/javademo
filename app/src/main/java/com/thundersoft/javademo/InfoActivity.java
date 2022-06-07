package com.thundersoft.javademo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.thundersoft.javademo.base.BaseActivity;
import com.thundersoft.javademo.viewmodel.InfoViewModel;
import com.thundersoft.javademo.viewmodel.LoginViewModel;
import com.thundersoft.model.Movie;

import java.util.List;

import butterknife.BindView;

public class InfoActivity extends BaseActivity<InfoViewModel> {

    @BindView(R.id.tv_info)
    TextView tvInfo;

    @Override
    public void initView() {
        mViewModel.getInfo(0, 10);
    }

    @Override
    public InfoViewModel initViewModel() {
        return new InfoViewModel();
    }

    @Override
    public int provideContentView() {
        return R.layout.activity_info;
    }

    @Override
    public void initViewObservable() {
        mViewModel.getinfoLiveData().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movies) {
                Log.d("haha", "movies === " + movies.toString());
                tvInfo.setText(movies.toString());
            }
        });
    }
}