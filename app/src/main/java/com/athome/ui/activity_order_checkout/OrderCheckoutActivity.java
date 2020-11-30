package com.athome.ui.activity_order_checkout;

import android.content.Context;
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
    private CartAdapter auctionAdapter;
    private String payment_type;


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

        binding.cardCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payment_type = "cash";
                binding.img1.setVisibility(View.GONE);
                binding.img2.setVisibility(View.GONE);
                binding.img3.setVisibility(View.VISIBLE);

            }
        });
        binding.cardCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payment_type = "online";
                binding.img1.setVisibility(View.VISIBLE);
                binding.img2.setVisibility(View.GONE);
                binding.img3.setVisibility(View.GONE);

            }
        });
        binding.cardWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payment_type = "wallet";
                binding.img1.setVisibility(View.GONE);
                binding.img2.setVisibility(View.VISIBLE);
                binding.img3.setVisibility(View.GONE);
            }
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