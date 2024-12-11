package com.pro.huyfood.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pro.huyfood.constant.Constant;
import com.pro.huyfood.databinding.ItemAdminFoodBinding;
import com.pro.huyfood.listener.IOnManagerFoodListener;
import com.pro.huyfood.model.Food;
import com.pro.huyfood.utils.GlideUtils;

import java.util.List;

public class AdminFoodAdapter extends RecyclerView.Adapter<AdminFoodAdapter.AdminFoodViewHolder> {

    private final List<Food> mListFoods;
    public final IOnManagerFoodListener iOnManagerFoodListener;

    public AdminFoodAdapter(List<Food> mListFoods, IOnManagerFoodListener listener) {
        this.mListFoods = mListFoods;
        this.iOnManagerFoodListener = listener;
    }

    @NonNull
    @Override
    public AdminFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAdminFoodBinding itemAdminFoodBinding = ItemAdminFoodBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AdminFoodViewHolder(itemAdminFoodBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminFoodViewHolder holder, int position) {
        Food food = mListFoods.get(position);
        if (food == null) {
            return;
        }
        GlideUtils.loadUrl(food.getImage(), holder.mItemAdminFoodBinding.imgFood);
        if (food.getSale() <= 0) {
            holder.mItemAdminFoodBinding.tvSaleOff.setVisibility(View.GONE);
            holder.mItemAdminFoodBinding.tvPrice.setVisibility(View.GONE);

            String strPrice = food.getPrice() + Constant.CURRENCY;
            holder.mItemAdminFoodBinding.tvPriceSale.setText(strPrice);
        } else {
            holder.mItemAdminFoodBinding.tvSaleOff.setVisibility(View.VISIBLE);
            holder.mItemAdminFoodBinding.tvPrice.setVisibility(View.VISIBLE);

            String strSale = "Giảm " + food.getSale() + "%";
            holder.mItemAdminFoodBinding.tvSaleOff.setText(strSale);

            String strOldPrice = food.getPrice() + Constant.CURRENCY;
            holder.mItemAdminFoodBinding.tvPrice.setText(strOldPrice);
            holder.mItemAdminFoodBinding.tvPrice.setPaintFlags(holder.mItemAdminFoodBinding.tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            String strRealPrice = food.getRealPrice() + Constant.CURRENCY;
            holder.mItemAdminFoodBinding.tvPriceSale.setText(strRealPrice);
        }
        holder.mItemAdminFoodBinding.tvFoodName.setText(food.getName());
        holder.mItemAdminFoodBinding.tvFoodDescription.setText(food.getDescription());
        if (food.isPopular()) {
            holder.mItemAdminFoodBinding.tvPopular.setText("Có");
        } else {
            holder.mItemAdminFoodBinding.tvPopular.setText("Không");
        }

        holder.mItemAdminFoodBinding.imgEdit.setOnClickListener(v -> iOnManagerFoodListener.onClickUpdateFood(food));
        holder.mItemAdminFoodBinding.imgDelete.setOnClickListener(v -> iOnManagerFoodListener.onClickDeleteFood(food));
    }

    @Override
    public int getItemCount() {
        return null == mListFoods ? 0 : mListFoods.size();
    }

    public static class AdminFoodViewHolder extends RecyclerView.ViewHolder {

        private final ItemAdminFoodBinding mItemAdminFoodBinding;

        public AdminFoodViewHolder(ItemAdminFoodBinding itemAdminFoodBinding) {
            super(itemAdminFoodBinding.getRoot());
            this.mItemAdminFoodBinding = itemAdminFoodBinding;
        }
    }
}
