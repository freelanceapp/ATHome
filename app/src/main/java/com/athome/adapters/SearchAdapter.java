package com.athome.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.athome.R;
import com.athome.databinding.OfferRowBinding;
import com.athome.databinding.SearchRowBinding;
import com.athome.models.BankDataModel;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyHolder> {

    private List<BankDataModel.BankModel> bankDataModelList;
    private Context context;

    public SearchAdapter(List<BankDataModel.BankModel> bankDataModelList, Context context) {
        this.bankDataModelList = bankDataModelList;
        this.context = context;


    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SearchRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.search_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {

     //   BankDataModel.BankModel bankModel = bankDataModelList.get(position);
        holder.binding.tvOldprice.setPaintFlags(holder.binding.tvOldprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private SearchRowBinding binding;

        public MyHolder(SearchRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }
}
