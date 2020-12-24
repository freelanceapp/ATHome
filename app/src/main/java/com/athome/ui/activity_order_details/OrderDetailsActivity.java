package com.athome.ui.activity_order_details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.athome.R;
import com.athome.adapters.OrderProductAdapter;
import com.athome.databinding.ActivityOrderDetailsBinding;
import com.athome.language.Language;
import com.athome.models.OrderModel;
import com.athome.models.ProductModel;
import com.athome.models.SingleOrderModel;
import com.athome.mvp.activity_order_details_mvp.ActivityOrderDetailsPresenter;
import com.athome.mvp.activity_order_details_mvp.ActivityOrderDetailsView;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class OrderDetailsActivity extends AppCompatActivity implements ActivityOrderDetailsView {
    private ActivityOrderDetailsBinding binding;
    private ActivityOrderDetailsPresenter presenter;
    private List<OrderModel.CartProductModel> cartProductModelList;
    private OrderProductAdapter adapter;
    private OrderModel orderModel;
    private String lang;
    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_details);
        getDataFromIntent();
        initView();


    }

    private void initView() {
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        cartProductModelList = new ArrayList<>();
        binding.setLang(lang);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        adapter = new OrderProductAdapter(cartProductModelList,this);
        binding.recView.setAdapter(adapter);
        presenter = new ActivityOrderDetailsPresenter(this,this);
        presenter.getProduct(orderModel);
        binding.llBack.setOnClickListener(view -> finish());

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        orderModel = (OrderModel) intent.getSerializableExtra("data");

    }

    @Override
    public void onSuccess(SingleOrderModel data) {
        binding.view.setVisibility(View.GONE);
        orderModel = data.getOrder();
        binding.setModel(orderModel);

        if (data.getOrder().getNew_cart()!=null&&data.getOrder().getNew_cart().size()>0){
            cartProductModelList.clear();
            cartProductModelList.addAll(data.getOrder().getNew_cart());
            adapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProgressShow() {
        binding.view.setVisibility(View.VISIBLE);
        binding.progBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressHide() {
        binding.progBar.setVisibility(View.GONE);

    }
}