package com.athome.ui.activity_order_checkout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.athome.R;
import com.athome.adapters.CartAdapter;
import com.athome.databinding.ActivityOrderCheckoutBinding;
import com.athome.databinding.ActivityOrderStepsBinding;
import com.athome.language.Language;
import com.athome.mvp.activity_order_checkout_mvp.ActivityOrderCheckoutPresenter;
import com.athome.mvp.activity_order_checkout_mvp.OrderCheckoutActivityView;
import com.athome.mvp.activity_order_steps_mvp.ActivityOrderStepsPresenter;
import com.athome.mvp.activity_order_steps_mvp.OrderStepsActivityView;

import io.paperdb.Paper;

public class OrderCheckoutActivity extends AppCompatActivity implements OrderCheckoutActivityView {
    private ActivityOrderCheckoutBinding binding;
    private ActivityOrderCheckoutPresenter presenter;
    private String lang;
    private int delivery_type =0,packaging_type=0;



    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_checkout);
        initView();
    }


    private void initView() {

        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);

        presenter = new ActivityOrderCheckoutPresenter(this, this);

        binding.rbDeliveryFree.setOnClickListener(view -> {
            delivery_type = 0;
        });

        binding.rbDeliveryPaid.setOnClickListener(view -> {
            delivery_type = 1;
        });

        binding.rbPackagingFree.setOnClickListener(view -> {
            packaging_type = 0;
        });

        binding.rbPackagingPaid.setOnClickListener(view -> {
            packaging_type = 1;
        });
        binding.btnPay.setOnClickListener(view -> {
            Intent intent = getIntent();
            intent.putExtra("delivery",delivery_type);
            intent.putExtra("packaging",packaging_type);
            setResult(RESULT_OK,intent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        presenter.backPress();
    }


    @Override
    public void onFinished() {
        finish();
    }



}