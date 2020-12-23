package com.athome.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.athome.R;
import com.athome.databinding.BrandRowBinding;
import com.athome.databinding.CartRowBinding;
import com.athome.models.BankDataModel;
import com.athome.models.CartDataModel;
import com.athome.ui.activity_cart.CartActivity;

import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyHolder> {

    private List<CartDataModel.CartModel> list;
    private Context context;
    private CartActivity activity;

    public CartAdapter(List<CartDataModel.CartModel> list, Context context) {
        this.list = list;
        this.context = context;
        activity = (CartActivity) context;


    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CartRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.cart_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {

       CartDataModel.CartModel model = list.get(position);
       holder.binding.setModel(model);
        holder.binding.iconDelete.setOnClickListener(view -> {
           CartDataModel.CartModel model2 = list.get(holder.getAdapterPosition());
           activity.removeCartItem(model2);
       });

       holder.binding.imgIncrease.setOnClickListener(view -> {
           CartDataModel.CartModel model2 = list.get(holder.getAdapterPosition());
           int amount = model2.getAmount();
           amount++;
           model2.setAmount(amount);
           list.set(holder.getAdapterPosition(),model2);
           activity.increase_decrease_item_count(model2,amount);
           notifyItemChanged(holder.getAdapterPosition());
       });

        holder.binding.imgDecrease.setOnClickListener(view -> {
            CartDataModel.CartModel model2 = list.get(holder.getAdapterPosition());
            int amount = model2.getAmount();
            if (amount>1){
                amount--;
                model2.setAmount(amount);
                list.set(holder.getAdapterPosition(),model2);
                activity.increase_decrease_item_count(model2,amount);
                notifyItemChanged(holder.getAdapterPosition());
            }

        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private CartRowBinding binding;

        public MyHolder(CartRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }
}
