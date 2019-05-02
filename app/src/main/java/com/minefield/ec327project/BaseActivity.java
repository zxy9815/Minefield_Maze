package com.minefield.ec327project;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Package    : com.example.ec327project
 * ClassName  : BaseActivity
 * Description: ${TODO}
 *
 */
abstract class BaseActivity extends AppCompatActivity {
    protected BaseActivity  mActivity;
    protected MyApplication mApplication;
    private   Unbinder      bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mApplication = (MyApplication) getApplication();
        mActivity = this;
        bind = ButterKnife.bind(this);
        initView();
    }

    protected abstract int getLayoutId();


    protected abstract void initView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
