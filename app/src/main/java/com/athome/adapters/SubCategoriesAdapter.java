package com.athome.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.athome.R;
import com.athome.databinding.SubcategoryRowBinding;
import com.athome.models.BankDataModel;
import com.athome.models.SubCategoryModel;

import java.util.ArrayList;
import java.util.List;

public class SubCategoriesAdapter extends RecyclerView.Adapter<SubCategoriesAdapter.MyHolder> {

    private List<SubCategoryModel> list;
    private Context context;

    public SubCategoriesAdapter(List<SubCategoryModel> list, Context context) {
        this.list = list;
        this.context = context;


    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SubcategoryRowBinding bankRowBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.subcategory_row, parent, false);
        return new MyHolder(bankRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        SubCategoryModel model = list.get(position);
        holder.binding.setModel(model);

        holder.binding.recView.setLayoutManager(new GridLayoutManager(context,3));
        if (model.getChilds()!=null&&model.getChilds().size()>0){
            ChildAdapter childAdapter = new ChildAdapter(model.getChilds(),context,position);
            holder.binding.recView.setAdapter(childAdapter);
            holder.binding.tvNoData.setVisibility(View.GONE);

        }else {
            ChildAdapter childAdapter = new ChildAdapter(new ArrayList<>(),context,position);
            holder.binding.recView.setAdapter(childAdapter);
            holder.binding.tvNoData.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private SubcategoryRowBinding binding;

        public MyHolder(SubcategoryRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }
}
