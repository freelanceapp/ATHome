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
import com.athome.models.SingleCategoryModel;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyHolder> {

    private List<SingleCategoryModel> singleCategoryModelList;
    private Context context;
    private int i=-1;

    public CategoriesAdapter(List<SingleCategoryModel> singleCategoryModelList, Context context) {
        this.singleCategoryModelList = singleCategoryModelList;
        this.context = context;


    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CategotyRowBinding categotyRowBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.categoty_row, parent, false);
        return new MyHolder(categotyRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
holder.categotyRowBinding.setModel(singleCategoryModelList.get(position));
      //  BankDataModel.BankModel bankModel = bankDataModelList.get(position);
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        i=position;
        notifyDataSetChanged();
    }
});
if(i==position){
    holder.categotyRowBinding.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.white));
}
else {
    holder.categotyRowBinding.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.gray3));


}
    }

    @Override
    public int getItemCount() {
        return singleCategoryModelList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private CategotyRowBinding categotyRowBinding;

        public MyHolder(CategotyRowBinding categotyRowBinding) {
            super(categotyRowBinding.getRoot());
            this.categotyRowBinding = categotyRowBinding;


        }


    }
}
