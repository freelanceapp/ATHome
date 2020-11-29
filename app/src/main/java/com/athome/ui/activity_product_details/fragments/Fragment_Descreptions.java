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
import com.athome.adapters.CategoriesAdapter;
import com.athome.adapters.SubCategoriesAdapter;
import com.athome.databinding.FragmentCategoriesBinding;
import com.athome.databinding.FragmentDescreptionsBinding;
import com.athome.models.BankDataModel;
import com.athome.ui.activity_home.HomeActivity;
import com.athome.ui.activity_product_details.ProductDetailsActivity;

import java.util.ArrayList;

public class Fragment_Descreptions extends Fragment {
    private FragmentDescreptionsBinding binding;
    private ProductDetailsActivity activity;


    public static Fragment_Descreptions newInstance(){
        return new Fragment_Descreptions();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_descreptions,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (ProductDetailsActivity) getActivity();

    }
}
