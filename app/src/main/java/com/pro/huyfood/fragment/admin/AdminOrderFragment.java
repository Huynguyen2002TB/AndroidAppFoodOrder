package com.pro.huyfood.fragment.admin;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.pro.huyfood.ControllerApplication;
import com.pro.huyfood.R;
import com.pro.huyfood.activity.AdminMainActivity;
import com.pro.huyfood.adapter.AdminOrderAdapter;
import com.pro.huyfood.databinding.FragmentAdminOrderBinding;
import com.pro.huyfood.fragment.BaseFragment;
import com.pro.huyfood.model.Order;

import java.util.ArrayList;
import java.util.List;

public class AdminOrderFragment extends BaseFragment {

    private FragmentAdminOrderBinding mFragmentAdminOrderBinding;
    private List<Order> mListOrder;
    private AdminOrderAdapter mAdminOrderAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mFragmentAdminOrderBinding = FragmentAdminOrderBinding.inflate(inflater, container, false);
        initView();
        getListOrders();
        return mFragmentAdminOrderBinding.getRoot();
    }

    @Override
    protected void initToolbar() {
        if (getActivity() != null) {
            ((AdminMainActivity) getActivity()).setToolBar(getString(R.string.order));
        }
    }

    private void initView() {
        if (getActivity() == null) {
            return;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mFragmentAdminOrderBinding.rcvOrder.setLayoutManager(linearLayoutManager);
        mListOrder = new ArrayList<>();
        mAdminOrderAdapter = new AdminOrderAdapter(getActivity(), mListOrder,
                this::handleUpdateStatusOrder);
        mFragmentAdminOrderBinding.rcvOrder.setAdapter(mAdminOrderAdapter);
    }

    public void getListOrders() {
        if (getActivity() == null) {
            return;
        }
        ControllerApplication.get(getActivity()).getBookingDatabaseReference()
                .addChildEventListener(new ChildEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                        Order order = dataSnapshot.getValue(Order.class);
                        if (order == null || mListOrder == null || mAdminOrderAdapter == null) {
                            return;
                        }
                        mListOrder.add(0, order);
                        mAdminOrderAdapter.notifyDataSetChanged();
                    }

                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {
                        Order order = dataSnapshot.getValue(Order.class);
                        if (order == null || mListOrder == null
                                || mListOrder.isEmpty() || mAdminOrderAdapter == null) {
                            return;
                        }
                        for (int i = 0; i < mListOrder.size(); i++) {
                            if (order.getId() == mListOrder.get(i).getId()) {
                                mListOrder.set(i, order);
                                break;
                            }
                        }
                        mAdminOrderAdapter.notifyDataSetChanged();
                    }

                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                        Order order = dataSnapshot.getValue(Order.class);
                        if (order == null || mListOrder == null
                                || mListOrder.isEmpty() || mAdminOrderAdapter == null) {
                            return;
                        }
                        for (Order orderObject : mListOrder) {
                            if (order.getId() == orderObject.getId()) {
                                mListOrder.remove(orderObject);
                                break;
                            }
                        }
                        mAdminOrderAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }

    private void handleUpdateStatusOrder(Order order) {
        if (getActivity() == null) {
            return;
        }
        ControllerApplication.get(getActivity()).getBookingDatabaseReference()
                .child(String.valueOf(order.getId())).child("completed").setValue(!order.isCompleted());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mAdminOrderAdapter != null) {
            mAdminOrderAdapter.release();
        }
    }
}
