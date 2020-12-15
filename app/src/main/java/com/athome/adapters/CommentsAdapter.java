package com.athome.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.athome.R;
import com.athome.databinding.CommentRowBinding;
import com.athome.databinding.SearchRowBinding;
import com.athome.models.CommentModel;
import com.athome.models.ProductModel;
import com.athome.ui.activity_home.fragments.Fragment_Search;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.MyHolder> {

    private List<CommentModel> list;
    private Context context;

    public CommentsAdapter(List<CommentModel> list, Context context) {
        this.list = list;
        this.context = context;


    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CommentRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.comment_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        CommentModel model = list.get(position);
        holder.binding.setModel(model);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private CommentRowBinding binding;

        public MyHolder(CommentRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }
}
