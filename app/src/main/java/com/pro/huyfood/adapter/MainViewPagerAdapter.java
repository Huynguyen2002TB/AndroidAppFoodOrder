package com.pro.huyfood.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.pro.huyfood.fragment.AccountFragment;
import com.pro.huyfood.fragment.CartFragment;
import com.pro.huyfood.fragment.ContactFragment;
import com.pro.huyfood.fragment.FeedbackFragment;
import com.pro.huyfood.fragment.HomeFragment;

public class MainViewPagerAdapter extends FragmentStateAdapter {

    public MainViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();

            case 1:
                return new CartFragment();

            case 2:
                return new FeedbackFragment();

            case 3:
                return new ContactFragment();

            case 4:
                return new AccountFragment();

            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
