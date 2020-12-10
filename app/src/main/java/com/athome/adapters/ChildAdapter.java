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

import java.util.List;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.MyHolder> {

    private List<ChildModel> list;
    private Context context;
    private int parent_pos = 0;

    public ChildAdapter(List<ChildModel> list, Context context,int parent_pos) {
        this.list = list;
        this.context = context;
        this.parent_pos = parent_pos;


    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ChildRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.child_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        ChildModel model = list.get(position);
        holder.binding.setModel(model);
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
