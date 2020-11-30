package com.athome.ui.activity_home.fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.athome.R;
import com.athome.adapters.BrandAdapter;
import com.athome.adapters.OrdersAdapter;
import com.athome.adapters.SearchAdapter;
import com.athome.databinding.FragmentOrdersBinding;
import com.athome.models.BankDataModel;
import com.athome.ui.activity_home.HomeActivity;

import java.util.ArrayList;

public class Fragment_Orders extends Fragment {
    private FragmentOrdersBinding binding;
    private HomeActivity activity;
    private OrdersAdapter ordersAdapter;


    public static Fragment_Orders newInstance(){
        return new Fragment_Orders();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_orders,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeActivity) getActivity();

        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
        binding.progBar.setVisibility(View.GONE);
        ordersAdapter = new OrdersAdapter( new ArrayList<BankDataModel.BankModel>(),activity);
        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recView.setAdapter(ordersAdapter);
    }
}
