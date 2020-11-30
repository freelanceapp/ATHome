package com.athome.ui.activity_select_address;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.athome.R;
import com.athome.adapters.AddressAdapter;
import com.athome.databinding.ActivitySelectAddressBinding;
import com.athome.language.Language;

import java.util.ArrayList;

import io.paperdb.Paper;

public class SelectAddressActivity extends AppCompatActivity {
    private ActivitySelectAddressBinding binding;
    private String lang;
    private double lat=0.0,lng=0.0;
    private AddressAdapter adapter;
    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang","ar")));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_address);
        getDataFromIntent();
        initView();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        lat = intent.getDoubleExtra("lat",0.0);
        lng = intent.getDoubleExtra("lng",0.0);

    }

    private void initView() {
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        binding.recView.setAdapter(new AddressAdapter(new ArrayList<>(),this));
        binding.llBack.setOnClickListener(view -> {
            finish();
        });
    }
}