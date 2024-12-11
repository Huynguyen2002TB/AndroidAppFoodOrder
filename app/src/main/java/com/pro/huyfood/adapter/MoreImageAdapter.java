package com.pro.huyfood.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pro.huyfood.databinding.ItemMoreImageBinding;
import com.pro.huyfood.model.Image;
import com.pro.huyfood.utils.GlideUtils;

import java.util.List;

public class MoreImageAdapter extends RecyclerView.Adapter<MoreImageAdapter.MoreImageViewHolder> {

    private final List<Image> mListImages;

    public MoreImageAdapter(List<Image> mListImages) {
        this.mListImages = mListImages;
    }

    @NonNull
    @Override
    public MoreImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMoreImageBinding itemMoreImageBinding = ItemMoreImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MoreImageViewHolder(itemMoreImageBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MoreImageViewHolder holder, int position) {
        Image image = mListImages.get(position);
        if (image == null) {
            return;
        }
        GlideUtils.loadUrl(image.getUrl(), holder.mItemMoreImageBinding.imageFood);
    }

    @Override
    public int getItemCount() {
        return null == mListImages ? 0 : mListImages.size();
    }

    public static class MoreImageViewHolder extends RecyclerView.ViewHolder {

        private final ItemMoreImageBinding mItemMoreImageBinding;

        public MoreImageViewHolder(ItemMoreImageBinding itemMoreImageBinding) {
            super(itemMoreImageBinding.getRoot());
            this.mItemMoreImageBinding = itemMoreImageBinding;
        }
    }
}
