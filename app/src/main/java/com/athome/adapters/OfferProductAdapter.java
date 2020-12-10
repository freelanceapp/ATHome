package com.athome.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.athome.R;
import com.athome.databinding.ProductOfferRowBinding;
import com.athome.databinding.ProductRowBinding;
import com.athome.models.ProductModel;

import java.util.List;

public class OfferProductAdapter extends RecyclerView.Adapter<OfferProductAdapter.MyHolder> {

    private List<ProductModel> list;
    private Context context;

    public OfferProductAdapter(List<ProductModel> list, Context context) {
        this.list = list;
        this.context = context;


    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductOfferRowBinding bankRowBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.product_offer_row, parent, false);
        return new MyHolder(bankRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        ProductModel model = list.get(position);
        holder.binding.setModel(model);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private ProductOfferRowBinding binding;

        public MyHolder(ProductOfferRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }
}
