package com.athome.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.athome.R;
import com.athome.databinding.BrandRowBinding;
import com.athome.databinding.CategotyRowBinding;
import com.athome.models.BankDataModel;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyHolder> {

    private List<BankDataModel.BankModel> bankDataModelList;
    private Context context;

    public CategoriesAdapter(List<BankDataModel.BankModel> bankDataModelList, Context context) {
        this.bankDataModelList = bankDataModelList;
        this.context = context;


    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CategotyRowBinding bankRowBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.categoty_row, parent, false);
        return new MyHolder(bankRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
if(position==0){
    holder.bankRowBinding.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.white));
}
      //  BankDataModel.BankModel bankModel = bankDataModelList.get(position);

    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private CategotyRowBinding bankRowBinding;

        public MyHolder(CategotyRowBinding bankRowBinding) {
            super(bankRowBinding.getRoot());
            this.bankRowBinding = bankRowBinding;


        }


    }
}
