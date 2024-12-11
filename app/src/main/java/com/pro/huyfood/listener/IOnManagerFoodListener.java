package com.pro.huyfood.listener;

import com.pro.huyfood.model.Food;

public interface IOnManagerFoodListener {
    void onClickUpdateFood(Food food);
    void onClickDeleteFood(Food food);
}
