package com.pro.huyfood.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.pro.huyfood.R;
import com.pro.huyfood.activity.MainActivity;
import com.pro.huyfood.adapter.ContactAdapter;
import com.pro.huyfood.constant.GlobalFunction;
import com.pro.huyfood.databinding.FragmentContactBinding;
import com.pro.huyfood.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends BaseFragment {

    private ContactAdapter mContactAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentContactBinding mFragmentContactBinding = FragmentContactBinding.inflate(inflater, container, false);

        mContactAdapter = new ContactAdapter(getActivity(), getListContact(), () -> GlobalFunction.callPhoneNumber(getActivity()));
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        mFragmentContactBinding.rcvData.setNestedScrollingEnabled(false);
        mFragmentContactBinding.rcvData.setFocusable(false);
        mFragmentContactBinding.rcvData.setLayoutManager(layoutManager);
        mFragmentContactBinding.rcvData.setAdapter(mContactAdapter);

        return mFragmentContactBinding.getRoot();
    }

    public List<Contact> getListContact() {
        List<Contact> contactArrayList = new ArrayList<>();
//        contactArrayList.add(new Contact(Contact.FACEBOOK, R.drawable.ic_facebook));
//        contactArrayList.add(new Contact(Contact.HOTLINE, R.drawable.ic_hotline));
//        contactArrayList.add(new Contact(Contact.GMAIL, R.drawable.ic_gmail));
//        contactArrayList.add(new Contact(Contact.SKYPE, R.drawable.ic_skype));
//        contactArrayList.add(new Contact(Contact.YOUTUBE, R.drawable.ic_youtube));
//        contactArrayList.add(new Contact(Contact.ZALO, R.drawable.ic_zalo));

        return contactArrayList;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContactAdapter.release();
    }

    @Override
    protected void initToolbar() {
        if (getActivity() != null) {
            ((MainActivity) getActivity()).setToolBar(false, getString(R.string.contact));
        }
    }
}
