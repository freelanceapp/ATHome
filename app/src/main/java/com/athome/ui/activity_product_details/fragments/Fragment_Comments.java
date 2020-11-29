package com.athome.ui.activity_product_details.fragments;

import android.os.Bundle;
import android.os.Handler;
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
import com.athome.adapters.DataAdapter;
import com.athome.adapters.SliderAdapter;
import com.athome.databinding.FragmentCommentsBinding;
import com.athome.databinding.FragmentHomeBinding;
import com.athome.models.BankDataModel;
import com.athome.ui.activity_home.HomeActivity;
import com.athome.ui.activity_product_details.ProductDetailsActivity;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class Fragment_Comments extends Fragment {
    private FragmentCommentsBinding binding;
    private ProductDetailsActivity activity;


    public static Fragment_Comments newInstance(){
        return new Fragment_Comments();
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


    }


}
