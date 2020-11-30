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

import java.util.List;

public class HomeCategoriesAdapter extends RecyclerView.Adapter<HomeCategoriesAdapter.MyHolder> {

    private List<SingleCategoryModel> singleCategoryModelList;
    private Context context;
    private int i=-1;

    public HomeCategoriesAdapter(List<SingleCategoryModel> singleCategoryModelList, Context context) {
        this.singleCategoryModelList = singleCategoryModelList;
        this.context = context;


    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MainCategoryRowBinding categotyRowBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.main_category_row, parent, false);
        return new MyHolder(categotyRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
holder.categotyRowBinding.setModel(singleCategoryModelList.get(position));
      //  BankDataModel.BankModel bankModel = bankDataModelList.get(position);

    }

    @Override
    public int getItemCount() {
        return singleCategoryModelList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private MainCategoryRowBinding categotyRowBinding;

        public MyHolder(MainCategoryRowBinding categotyRowBinding) {
            super(categotyRowBinding.getRoot());
            this.categotyRowBinding = categotyRowBinding;


        }


    }
}
