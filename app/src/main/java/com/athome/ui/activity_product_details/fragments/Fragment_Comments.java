package com.athome.ui.activity_product_details.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.athome.R;
import com.athome.adapters.CommentsAdapter;
import com.athome.adapters.DataAdapter;
import com.athome.adapters.SliderAdapter;
import com.athome.databinding.FragmentCommentsBinding;
import com.athome.databinding.FragmentHomeBinding;
import com.athome.models.BankDataModel;
import com.athome.models.ProductModel;
import com.athome.ui.activity_home.HomeActivity;
import com.athome.ui.activity_product_details.ProductDetailsActivity;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import io.paperdb.Paper;


public class Fragment_Comments extends Fragment {
    private FragmentCommentsBinding binding;
    private ProductDetailsActivity activity;
    private ProductModel productModel;
    private String lang;
    private CommentsAdapter adapter;


    public static Fragment_Comments newInstance(ProductModel productModel) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", productModel);
        Fragment_Comments fragment_comments = new Fragment_Comments();
        fragment_comments.setArguments(bundle);
        return fragment_comments;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comments, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (ProductDetailsActivity) getActivity();
        Paper.init(activity);
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
        Bundle bundle = getArguments();
        if (bundle != null) {
            productModel = (ProductModel) bundle.getSerializable("data");
        }

        if (productModel.getComments().size()>0){
            adapter = new CommentsAdapter(productModel.getComments(),activity);
            binding.recView.setLayoutManager(new LinearLayoutManager(activity));
            binding.recView.setAdapter(adapter);
            binding.tvNoData.setVisibility(View.GONE);

        }else {
            binding.tvNoData.setVisibility(View.VISIBLE);
        }


    }


}
