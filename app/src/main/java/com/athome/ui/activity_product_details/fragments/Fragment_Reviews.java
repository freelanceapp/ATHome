package com.athome.ui.activity_product_details.fragments;

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
import com.athome.databinding.FragmentReviewsBinding;
import com.athome.models.BankDataModel;
import com.athome.ui.activity_home.HomeActivity;
import com.athome.ui.activity_product_details.ProductDetailsActivity;

import java.util.ArrayList;

public class Fragment_Reviews extends Fragment {
    private FragmentReviewsBinding binding;
    private ProductDetailsActivity activity;

    public static Fragment_Reviews newInstance(){
        return new Fragment_Reviews();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reviews,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (ProductDetailsActivity) getActivity();


    }
}
