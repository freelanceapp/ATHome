package com.athome.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.athome.R;
import com.athome.databinding.ProductFavoriteRowBinding;
import com.athome.databinding.ProductRow2Binding;
import com.athome.models.ProductModel;
import com.athome.ui.activity_favorite.FavoriteActivity;

import java.util.List;

public class FavoriteProductAdapter extends RecyclerView.Adapter<FavoriteProductAdapter.MyHolder> {

    private List<ProductModel> list;
    private Context context;
    private FavoriteActivity activity;

    public FavoriteProductAdapter(List<ProductModel> list, Context context) {
        this.list = list;
        this.context = context;
        activity = (FavoriteActivity) context;


    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductFavoriteRowBinding bankRowBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.product_favorite_row, parent, false);
        return new MyHolder(bankRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        ProductModel model = list.get(position);
        holder.binding.setModel(model);
        holder.itemView.setOnClickListener(view -> {
            ProductModel model2 = list.get(holder.getAdapterPosition());
            activity.setProductItemModel(model2);

        });
        holder.binding.imageFavorite.setOnClickListener(view -> {
            ProductModel model2 = list.get(holder.getAdapterPosition());
            activity.removeFavorite(model2,holder.getAdapterPosition());

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private ProductFavoriteRowBinding binding;

        public MyHolder(ProductFavoriteRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }
}
