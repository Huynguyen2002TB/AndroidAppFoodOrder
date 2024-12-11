package com.pro.huyfood.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pro.huyfood.constant.Constant;
import com.pro.huyfood.databinding.ItemRevenueBinding;
import com.pro.huyfood.model.Order;
import com.pro.huyfood.utils.DateTimeUtils;

import java.util.List;

public class RevenueAdapter extends RecyclerView.Adapter<RevenueAdapter.RevenueViewHolder> {

    private final List<Order> mListOrder;

    public RevenueAdapter(List<Order> mListOrder) {
        this.mListOrder = mListOrder;
    }

    @NonNull
    @Override
    public RevenueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRevenueBinding itemRevenueBinding = ItemRevenueBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new RevenueViewHolder(itemRevenueBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RevenueViewHolder holder, int position) {
        Order order = mListOrder.get(position);
        if (order == null) {
            return;
        }
        holder.mItemRevenueBinding.tvId.setText(String.valueOf(order.getId()));
        holder.mItemRevenueBinding.tvDate.setText(DateTimeUtils.convertTimeStampToDate_2(order.getId()));

        String strAmount = order.getAmount() + Constant.CURRENCY;
        holder.mItemRevenueBinding.tvTotalAmount.setText(strAmount);
    }

    @Override
    public int getItemCount() {
        if (mListOrder != null) {
            return mListOrder.size();
        }
        return 0;
    }

    public static class RevenueViewHolder extends RecyclerView.ViewHolder {

        private final ItemRevenueBinding mItemRevenueBinding;

        public RevenueViewHolder(@NonNull ItemRevenueBinding itemRevenueBinding) {
            super(itemRevenueBinding.getRoot());
            this.mItemRevenueBinding = itemRevenueBinding;
        }
    }
}
