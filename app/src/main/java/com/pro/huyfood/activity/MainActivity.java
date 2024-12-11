package com.pro.huyfood.activity;

import android.os.Bundle;
import android.view.View;

import androidx.viewpager2.widget.ViewPager2;

import com.afollestad.materialdialogs.MaterialDialog;
import com.pro.huyfood.R;
import com.pro.huyfood.adapter.MainViewPagerAdapter;
import com.pro.huyfood.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding mActivityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mActivityMainBinding.getRoot());

        mActivityMainBinding.viewpager2.setUserInputEnabled(false);
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(this);
        mActivityMainBinding.viewpager2.setAdapter(mainViewPagerAdapter);

        mActivityMainBinding.viewpager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        mActivityMainBinding.bottomNavigation.getMenu().findItem(R.id.nav_home).setChecked(true);
                        break;

                    case 1:
                        mActivityMainBinding.bottomNavigation.getMenu().findItem(R.id.nav_cart).setChecked(true);
                        break;

                    case 2:
                        mActivityMainBinding.bottomNavigation.getMenu().findItem(R.id.nav_feedback).setChecked(true);
                        break;

                    case 3:
                        mActivityMainBinding.bottomNavigation.getMenu().findItem(R.id.nav_contact).setChecked(true);
                        break;

                    case 4:
                        mActivityMainBinding.bottomNavigation.getMenu().findItem(R.id.nav_account).setChecked(true);
                        break;
                }
            }
        });

        mActivityMainBinding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                mActivityMainBinding.viewpager2.setCurrentItem(0);
            } else if (id == R.id.nav_cart) {
                mActivityMainBinding.viewpager2.setCurrentItem(1);
            } else if (id == R.id.nav_feedback) {
                mActivityMainBinding.viewpager2.setCurrentItem(2);
            } else if (id == R.id.nav_contact) {
                mActivityMainBinding.viewpager2.setCurrentItem(3);
            } else if (id == R.id.nav_account) {
                mActivityMainBinding.viewpager2.setCurrentItem(4);
            }
            return true;
        });
    }

    @Override
    public void onBackPressed() {
        showConfirmExitApp();
    }

    private void showConfirmExitApp() {
        new MaterialDialog.Builder(this)
                .title(getString(R.string.app_name))
                .content(getString(R.string.msg_exit_app))
                .positiveText(getString(R.string.action_ok))
                .onPositive((dialog, which) -> finishAffinity())
                .negativeText(getString(R.string.action_cancel))
                .cancelable(false)
                .show();
    }

    public void setToolBar(boolean isHome, String title) {
        if (isHome) {
            mActivityMainBinding.toolbar.layoutToolbar.setVisibility(View.GONE);
            return;
        }
        mActivityMainBinding.toolbar.layoutToolbar.setVisibility(View.VISIBLE);
        mActivityMainBinding.toolbar.tvTitle.setText(title);
    }
}