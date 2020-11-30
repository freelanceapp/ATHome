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
import com.athome.adapters.SearchAdapter;
import com.athome.databinding.FragmentSearchBinding;
import com.athome.models.BankDataModel;
import com.athome.ui.activity_home.HomeActivity;

import java.util.ArrayList;

import io.paperdb.Paper;

public class Fragment_Search extends Fragment {
    private FragmentSearchBinding binding;
    private HomeActivity activity;
    private SearchAdapter searchAdapter;
    private String lang;

    public static Fragment_Search newInstance(){
        return new Fragment_Search();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeActivity) getActivity();
        Paper.init(activity);
        lang = Paper.book().read("lang", "ar");

        binding.setLang(lang);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
        binding.progBar.setVisibility(View.GONE);
        searchAdapter = new SearchAdapter( new ArrayList<BankDataModel.BankModel>(),activity);
        binding.recView.setLayoutManager(new GridLayoutManager(activity,2));
        binding.recView.setAdapter(searchAdapter);

    }
}
