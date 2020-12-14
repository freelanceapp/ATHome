package com.athome.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.athome.R;
import com.athome.databinding.SearchRowBinding;
import com.athome.models.BankDataModel;
import com.athome.models.ProductModel;
import com.athome.ui.activity_home.fragments.Fragment_Search;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyHolder> {

    private List<ProductModel> list;
    private Context context;
    private Fragment_Search fragment_search;

    public SearchAdapter(List<ProductModel> list, Context context,Fragment_Search fragment_search) {
        this.list = list;
        this.context = context;
        this.fragment_search = fragment_search;


    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SearchRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.search_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        ProductModel model = list.get(position);
        holder.binding.setModel(model);
        holder.itemView.setOnClickListener(view -> {
            ProductModel model2 = list.get(holder.getAdapterPosition());
            fragment_search.setProductItemModel(model2,holder.getAdapterPosition());

        });
        holder.binding.checkbox.setOnClickListener(view -> {
            ProductModel model2 = list.get(holder.getAdapterPosition());
            if (holder.binding.checkbox.isChecked()){
                model2.setIs_wishlist(new ProductModel.IsWishList());

            }else {
                model2.setIs_wishlist(null);

            }
            list.set(holder.getAdapterPosition(),model2);
            notifyItemChanged(holder.getAdapterPosition());
            fragment_search.add_remove_favorite(model2,holder.getAdapterPosition());
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private SearchRowBinding binding;

        public MyHolder(SearchRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }
}
