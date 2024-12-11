package com.pro.huyfood.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.pro.huyfood.R;
import com.pro.huyfood.constant.GlobalFunction;
import com.pro.huyfood.prefs.DataStoreManager;
import com.pro.huyfood.utils.StringUtil;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(this::goToNextActivity, 2000);
    }

    private void goToNextActivity() {
        if (DataStoreManager.getUser() != null && !StringUtil.isEmpty(DataStoreManager.getUser().getEmail())) {
            GlobalFunction.gotoMainActivity(this);
        } else {
            GlobalFunction.startActivity(this, SignInActivity.class);
        }
        finish();
    }
}