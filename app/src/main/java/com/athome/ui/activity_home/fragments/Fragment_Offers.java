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
import androidx.recyclerview.widget.LinearLayoutManager;


import com.athome.R;
import com.athome.adapters.OfferAdapter;
import com.athome.databinding.FragmentOffersBinding;
import com.athome.models.BankDataModel;
import com.athome.ui.activity_home.HomeActivity;

import java.util.ArrayList;

public class Fragment_Offers extends Fragment {
    private FragmentOffersBinding binding;
    private HomeActivity activity;
    private OfferAdapter auctionAdapter;

    public static Fragment_Offers newInstance(){
        return new Fragment_Offers();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_offers,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeActivity) getActivity();
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
        binding.progBar.setVisibility(View.GONE);
        auctionAdapter = new OfferAdapter( new ArrayList<BankDataModel.BankModel>(),activity);
        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recView.setAdapter(auctionAdapter);

    }
}
