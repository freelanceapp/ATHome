package com.athome.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.athome.R;
import com.athome.databinding.CategotyRowBinding;
import com.athome.databinding.MainCategoryRowBinding;
import com.athome.models.SingleCategoryModel;
import com.athome.ui.activity_home.fragments.Fragment_Home;

import java.util.List;

public class HomeCategoriesAdapter extends RecyclerView.Adapter<HomeCategoriesAdapter.MyHolder> {

    private List<SingleCategoryModel> singleCategoryModelList;
    private Context context;
    private int i = -1;
    private Fragment_Home fragment_home;

    public HomeCategoriesAdapter(List<SingleCategoryModel> singleCategoryModelList, Context context, Fragment_Home fragment_home) {
        this.singleCategoryModelList = singleCategoryModelList;
        this.context = context;
        this.fragment_home = fragment_home;


    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MainCategoryRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.main_category_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        holder.binding.setModel(singleCategoryModelList.get(position));

        holder.itemView.setOnClickListener(view -> {
            fragment_home.setItemData(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return singleCategoryModelList.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private MainCategoryRowBinding binding;

        public MyHolder(MainCategoryRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }
}
