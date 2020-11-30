package com.athome.ui.activity_order_steps;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.athome.R;
import com.athome.adapters.CartAdapter;
import com.athome.databinding.ActivityCartBinding;
import com.athome.databinding.ActivityOrderStepsBinding;
import com.athome.language.Language;
import com.athome.models.BankDataModel;
import com.athome.mvp.activity_cart_mvp.ActivityCartPresenter;
import com.athome.mvp.activity_cart_mvp.CartActivityView;
import com.athome.mvp.activity_order_steps_mvp.ActivityOrderStepsPresenter;
import com.athome.mvp.activity_order_steps_mvp.OrderStepsActivityView;

import java.util.ArrayList;

import io.paperdb.Paper;

public class OrderStepsActivity extends AppCompatActivity implements OrderStepsActivityView {
    private ActivityOrderStepsBinding binding;
    private ActivityOrderStepsPresenter presenter;
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_steps);
        initView();
    }


    private void initView() {

        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);

        presenter = new ActivityOrderStepsPresenter(this, this);

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