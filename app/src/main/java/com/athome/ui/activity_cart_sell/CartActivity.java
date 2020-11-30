package com.athome.ui.activity_cart_sell;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.athome.R;
import com.athome.adapters.BrandAdapter;
import com.athome.adapters.CartAdapter;
import com.athome.databinding.ActivityCartBinding;
import com.athome.language.Language;
import com.athome.models.BankDataModel;
import com.athome.mvp.activity_cart_mvp.ActivityCartPresenter;
import com.athome.mvp.activity_cart_mvp.CartActivityView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class CartActivity extends AppCompatActivity implements CartActivityView {
    private ActivityCartBinding binding;
    private ActivityCartPresenter presenter;
    private String lang;
    private CartAdapter auctionAdapter;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart);
        initView();
    }


    private void initView() {

        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);

        presenter = new ActivityCartPresenter(this, this);
        binding.llBack.setOnClickListener(view -> {
            presenter.backPress();
        });
        binding.recView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        auctionAdapter = new CartAdapter(new ArrayList<BankDataModel.BankModel>(), this);
        binding.recView.setAdapter(auctionAdapter);

    }

    @Override
    public void onBackPressed() {
        presenter.backPress();
    }


    @Override
    public void onFinished() {
        finish();
    }

    @Override
    public void onopenpay() {


    }


}