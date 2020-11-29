package com.athome.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.athome.R;
import com.athome.databinding.CategotyRowBinding;
import com.athome.databinding.SubcategoryRowBinding;
import com.athome.models.BankDataModel;

import java.util.ArrayList;
import java.util.List;

public class SubCategoriesAdapter extends RecyclerView.Adapter<SubCategoriesAdapter.MyHolder> {

    private List<BankDataModel.BankModel> bankDataModelList;
    private Context context;
    private BrandAdapter auctionAdapter;

    public SubCategoriesAdapter(List<BankDataModel.BankModel> bankDataModelList, Context context) {
        this.bankDataModelList = bankDataModelList;
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
        holder.bankRowBinding.recView.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));
        auctionAdapter = new BrandAdapter( new ArrayList<BankDataModel.BankModel>(),context);
        holder.bankRowBinding.recView.setAdapter(auctionAdapter);
      //  BankDataModel.BankModel bankModel = bankDataModelList.get(position);

    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private SubcategoryRowBinding bankRowBinding;

        public MyHolder(SubcategoryRowBinding bankRowBinding) {
            super(bankRowBinding.getRoot());
            this.bankRowBinding = bankRowBinding;


        }


    }
}
