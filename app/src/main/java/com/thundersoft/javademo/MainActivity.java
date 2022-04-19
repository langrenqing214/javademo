package com.thundersoft.javademo;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.Observer;

import com.thundersoft.javademo.base.BaseActivity;
import com.thundersoft.javademo.base.BaseViewModel;
import com.thundersoft.javademo.viewmodel.LoginViewModel;
import com.thundersoft.model.LoginBean;

import butterknife.BindView;

public class MainActivity extends BaseActivity<LoginViewModel> {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    public void initView() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getViewModel().login(etName.getText().toString(), etPwd.getText().toString());
            }
        });
    }

    @Override
    public LoginViewModel initViewModel() {
        return new LoginViewModel();
    }

    @Override
    public int provideContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initViewObservable() {
        getViewModel().getLoginLiveData().observe(this, new Observer<LoginBean>() {
            @Override
            public void onChanged(LoginBean loginBean) {
                if (loginBean.isLogin()) {
                    Toast.makeText(MainActivity.this, "登录成功，name == " + loginBean.getName() + " pwd == " + loginBean.getPwd(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}