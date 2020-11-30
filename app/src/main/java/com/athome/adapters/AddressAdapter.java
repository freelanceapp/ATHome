package com.athome.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.athome.R;
import com.athome.databinding.AddressRowBinding;
import com.athome.databinding.BrandRowBinding;
import com.athome.models.BankDataModel;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyHolder> {

    private List<BankDataModel.BankModel> bankDataModelList;
    private Context context;

    public AddressAdapter(List<BankDataModel.BankModel> bankDataModelList, Context context) {
        this.bankDataModelList = bankDataModelList;
        this.context = context;


    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AddressRowBinding bankRowBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.address_row, parent, false);
        return new MyHolder(bankRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {

      //  BankDataModel.BankModel bankModel = bankDataModelList.get(position);

    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private AddressRowBinding bankRowBinding;

        public MyHolder(AddressRowBinding bankRowBinding) {
            super(bankRowBinding.getRoot());
            this.bankRowBinding = bankRowBinding;


        }


    }
}
