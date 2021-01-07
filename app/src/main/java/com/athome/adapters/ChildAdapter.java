package com.athome.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.athome.R;
import com.athome.databinding.ChildRowBinding;
import com.athome.databinding.ProductRowBinding;
import com.athome.models.ChildModel;
import com.athome.models.ProductModel;
import com.athome.ui.activity_home.fragments.Fragment_Categories;

import java.util.List;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.MyHolder> {

    private List<ProductModel> list;
    private Context context;
    private int parent_pos = 0;
    private Fragment_Categories fragment_categories;

    public ChildAdapter(List<ProductModel> list, Context context,int parent_pos,Fragment_Categories fragment_categories) {
        this.list = list;
        this.context = context;
        this.parent_pos = parent_pos;
        this.fragment_categories = fragment_categories;


    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ChildRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.child_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        ProductModel model = list.get(position);
        holder.binding.setModel(model);
        holder.itemView.setOnClickListener(view -> {
            ProductModel model2 = list.get(holder.getAdapterPosition());
            fragment_categories.setChildItemData(model2,parent_pos);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private ChildRowBinding binding;

        public MyHolder(ChildRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }
}
