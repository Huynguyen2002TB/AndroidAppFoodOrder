package com.pro.huyfood.activity;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pro.huyfood.R;
import com.pro.huyfood.adapter.MoreImageAdapter;
import com.pro.huyfood.constant.Constant;
import com.pro.huyfood.database.FoodDatabase;
import com.pro.huyfood.databinding.ActivityFoodDetailBinding;
import com.pro.huyfood.event.ReloadListCartEvent;
import com.pro.huyfood.model.Food;
import com.pro.huyfood.utils.GlideUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class FoodDetailActivity extends BaseActivity {

    private ActivityFoodDetailBinding mActivityFoodDetailBinding;
    private Food mFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityFoodDetailBinding = ActivityFoodDetailBinding.inflate(getLayoutInflater());
        setContentView(mActivityFoodDetailBinding.getRoot());

        getDataIntent();
        initToolbar();
        setDataFoodDetail();
        initListener();
    }

    private void getDataIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mFood = (Food) bundle.get(Constant.KEY_INTENT_FOOD_OBJECT);
        }
    }

    private void initToolbar() {
        mActivityFoodDetailBinding.toolbar.imgBack.setVisibility(View.VISIBLE);
        mActivityFoodDetailBinding.toolbar.imgCart.setVisibility(View.VISIBLE);
        mActivityFoodDetailBinding.toolbar.tvTitle.setText(getString(R.string.food_detail_title));

        mActivityFoodDetailBinding.toolbar.imgBack.setOnClickListener(v -> onBackPressed());
    }

    private void setDataFoodDetail() {
        if (mFood == null) {
            return;
        }

        GlideUtils.loadUrlBanner(mFood.getBanner(), mActivityFoodDetailBinding.imageFood);

        // Khóa phần giảm giá
        // if (mFood.getSale() <= 0) {
        //     mActivityFoodDetailBinding.tvSaleOff.setVisibility(View.GONE);
        //     mActivityFoodDetailBinding.tvPrice.setVisibility(View.GONE);
        //     String strPrice = mFood.getPrice() + Constant.CURRENCY;
        //     mActivityFoodDetailBinding.tvPriceSale.setText(strPrice);
        // } else {
        //     mActivityFoodDetailBinding.tvSaleOff.setVisibility(View.VISIBLE);
        //     mActivityFoodDetailBinding.tvPrice.setVisibility(View.VISIBLE);

        //     String strSale = "Giảm " + mFood.getSale() + "%";
        //     mActivityFoodDetailBinding.tvSaleOff.setText(strSale);

        //     String strPriceOld = mFood.getPrice() + Constant.CURRENCY;
        //     mActivityFoodDetailBinding.tvPrice.setText(strPriceOld);
        //     mActivityFoodDetailBinding.tvPrice.setPaintFlags(mActivityFoodDetailBinding.tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        //     String strRealPrice = mFood.getRealPrice() + Constant.CURRENCY;
        //     mActivityFoodDetailBinding.tvPriceSale.setText(strRealPrice);
        // }

        // Chỉ hiển thị giá thực tế
        String strPrice = mFood.getPrice() + Constant.CURRENCY;
        mActivityFoodDetailBinding.tvPriceSale.setText(strPrice);

        mActivityFoodDetailBinding.tvFoodName.setText(mFood.getName());
        mActivityFoodDetailBinding.tvFoodDescription.setText(mFood.getDescription());

        displayListMoreImages();

        setStatusButtonAddToCart();
    }

    private void displayListMoreImages() {
        // Khóa phần hiển thị hình ảnh bổ sung
        mActivityFoodDetailBinding.tvMoreImageLabel.setVisibility(View.GONE);
    }

    private void setStatusButtonAddToCart() {
        if (isFoodInCart()) {
            mActivityFoodDetailBinding.tvAddToCart.setBackgroundResource(R.drawable.bg_gray_shape_corner_6);
            mActivityFoodDetailBinding.tvAddToCart.setText(getString(R.string.added_to_cart));
            mActivityFoodDetailBinding.tvAddToCart.setTextColor(ContextCompat.getColor(this, R.color.textColorPrimary));
            mActivityFoodDetailBinding.toolbar.imgCart.setVisibility(View.GONE);
        } else {
            mActivityFoodDetailBinding.tvAddToCart.setBackgroundResource(R.drawable.bg_green_shape_corner_6);
            mActivityFoodDetailBinding.tvAddToCart.setText(getString(R.string.add_to_cart));
            mActivityFoodDetailBinding.tvAddToCart.setTextColor(ContextCompat.getColor(this, R.color.white));
            mActivityFoodDetailBinding.toolbar.imgCart.setVisibility(View.VISIBLE);
        }
    }

    private boolean isFoodInCart() {
        List<Food> list = FoodDatabase.getInstance(this).foodDAO().checkFoodInCart(mFood.getId());
        return list != null && !list.isEmpty();
    }

    private void initListener() {
        mActivityFoodDetailBinding.tvAddToCart.setOnClickListener(v -> onClickAddToCart());
        mActivityFoodDetailBinding.toolbar.imgCart.setOnClickListener(v -> onClickAddToCart());
    }

    public void onClickAddToCart() {
        if (isFoodInCart()) {
            return;
        }

        @SuppressLint("InflateParams") View viewDialog = getLayoutInflater().inflate(R.layout.layout_bottom_sheet_cart, null);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(viewDialog);

        ImageView imgFoodCart = viewDialog.findViewById(R.id.img_food_cart);
        TextView tvFoodNameCart = viewDialog.findViewById(R.id.tv_food_name_cart);
        TextView tvFoodPriceCart = viewDialog.findViewById(R.id.tv_food_price_cart);
        TextView tvSubtractCount = viewDialog.findViewById(R.id.tv_subtract);
        TextView tvCount = viewDialog.findViewById(R.id.tv_count);
        TextView tvAddCount = viewDialog.findViewById(R.id.tv_add);
        TextView tvCancel = viewDialog.findViewById(R.id.tv_cancel);
        TextView tvAddCart = viewDialog.findViewById(R.id.tv_add_cart);

        GlideUtils.loadUrl(mFood.getImage(), imgFoodCart);
        tvFoodNameCart.setText(mFood.getName());

        int totalPrice = mFood.getRealPrice();
        String strTotalPrice = totalPrice + Constant.CURRENCY;
        tvFoodPriceCart.setText(strTotalPrice);

        mFood.setCount(1);
        mFood.setTotalPrice(totalPrice);

        tvSubtractCount.setOnClickListener(v -> {
            int count = Integer.parseInt(tvCount.getText().toString());
            if (count <= 1) {
                return;
            }
            int newCount = Integer.parseInt(tvCount.getText().toString()) - 1;
            tvCount.setText(String.valueOf(newCount));

            int totalPrice1 = mFood.getRealPrice() * newCount;
            String strTotalPrice1 = totalPrice1 + Constant.CURRENCY;
            tvFoodPriceCart.setText(strTotalPrice1);

            mFood.setCount(newCount);
            mFood.setTotalPrice(totalPrice1);
        });

        tvAddCount.setOnClickListener(v -> {
            int newCount = Integer.parseInt(tvCount.getText().toString()) + 1;
            tvCount.setText(String.valueOf(newCount));

            int totalPrice2 = mFood.getRealPrice() * newCount;
            String strTotalPrice2 = totalPrice2 + Constant.CURRENCY;
            tvFoodPriceCart.setText(strTotalPrice2);

            mFood.setCount(newCount);
            mFood.setTotalPrice(totalPrice2);
        });

        tvCancel.setOnClickListener(v -> bottomSheetDialog.dismiss());

        tvAddCart.setOnClickListener(v -> {
            FoodDatabase.getInstance(FoodDetailActivity.this).foodDAO().insertFood(mFood);
            bottomSheetDialog.dismiss();
            setStatusButtonAddToCart();
            EventBus.getDefault().post(new ReloadListCartEvent());
        });

        bottomSheetDialog.show();
    }
}
