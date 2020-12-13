package com.athome.ui.activity_products;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.athome.R;
import com.athome.adapters.ProductAdapter2;
import com.athome.databinding.ActivityProductsBinding;
import com.athome.language.Language;
import com.athome.models.ChildModel;
import com.athome.models.ProductModel;
import com.athome.models.SingleCategoryModel;
import com.athome.models.SubCategoryModel;
import com.athome.mvp.activity_product_mvp.ActivityProductPresenter;
import com.athome.mvp.activity_product_mvp.ActivityProductView;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class ProductsActivity extends AppCompatActivity implements ActivityProductView {
    private ActivityProductsBinding binding;
    private String lang;
    private SingleCategoryModel singleCategoryModel;
    private SubCategoryModel subCategoryModel;
    private ChildModel childModel;
    private List<ProductModel> productModelList;
    private ProductAdapter2 adapter;
    private ActivityProductPresenter presenter;
    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_products);
        getDataFromIntent();
        initView();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        singleCategoryModel = (SingleCategoryModel) intent.getSerializableExtra("data1");
        childModel = (ChildModel) intent.getSerializableExtra("data2");
        subCategoryModel = (SubCategoryModel) intent.getSerializableExtra("data3");

    }

    private void initView() {
        productModelList = new ArrayList<>();
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
        binding.setModel(singleCategoryModel);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter2(productModelList,this);
        binding.recView.setAdapter(adapter);

        presenter= new ActivityProductPresenter(this,this);
        presenter.getProducts(String.valueOf(singleCategoryModel.getId()),String.valueOf(subCategoryModel.getId()),String.valueOf(childModel.getId()));

        binding.imageBack.setOnClickListener(view -> finish());

    }


    @Override
    public void onSuccess(List<ProductModel> data) {
        productModelList.clear();
        if (data.size()>0){
            productModelList.addAll(data);
            adapter.notifyDataSetChanged();
            binding.tvNoData.setVisibility(View.GONE);
        }else {
            binding.tvNoData.setVisibility(View.VISIBLE);

        }

        adapter.notifyDataSetChanged();

    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressShow() {
        binding.progBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressHide() {
        binding.progBar.setVisibility(View.GONE);

    }
}