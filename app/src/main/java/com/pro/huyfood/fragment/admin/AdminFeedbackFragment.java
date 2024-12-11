package com.pro.huyfood.fragment.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.pro.huyfood.ControllerApplication;
import com.pro.huyfood.R;
import com.pro.huyfood.activity.AdminMainActivity;
import com.pro.huyfood.adapter.FeedbackAdapter;
import com.pro.huyfood.databinding.FragmentAdminFeedbackBinding;
import com.pro.huyfood.fragment.BaseFragment;
import com.pro.huyfood.model.Feedback;

import java.util.ArrayList;
import java.util.List;

public class AdminFeedbackFragment extends BaseFragment {

    private FragmentAdminFeedbackBinding mFragmentAdminFeedbackBinding;
    private List<Feedback> mListFeedback;
    private FeedbackAdapter mFeedbackAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentAdminFeedbackBinding = FragmentAdminFeedbackBinding.inflate(inflater, container, false);

        initView();
        getListFeedback();
        return mFragmentAdminFeedbackBinding.getRoot();
    }

    @Override
    protected void initToolbar() {
        if (getActivity() != null) {
            ((AdminMainActivity) getActivity()).setToolBar(getString(R.string.feedback));
        }
    }

    private void initView() {
        if (getActivity() == null) {
            return;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mFragmentAdminFeedbackBinding.rcvFeedback.setLayoutManager(linearLayoutManager);
    }

    public void getListFeedback() {
        if (getActivity() == null) {
            return;
        }
        ControllerApplication.get(getActivity()).getFeedbackDatabaseReference()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (mListFeedback != null) {
                            mListFeedback.clear();
                        } else {
                            mListFeedback = new ArrayList<>();
                        }
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Feedback feedback = dataSnapshot.getValue(Feedback.class);
                            if (feedback != null) {
                                mListFeedback.add(0, feedback);
                            }
                        }
                        mFeedbackAdapter = new FeedbackAdapter(mListFeedback);
                        mFragmentAdminFeedbackBinding.rcvFeedback.setAdapter(mFeedbackAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }
}
