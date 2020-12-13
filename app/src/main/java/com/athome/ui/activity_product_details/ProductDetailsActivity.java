package com.athome.ui.activity_product_details;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.athome.R;
import com.athome.adapters.SliderAdapter;
import com.athome.adapters.ViewPagerAdapter;
import com.athome.databinding.ActivityProductDetailsBinding;
import com.athome.language.Language;
import com.athome.models.ProductModel;
import com.athome.mvp.activity_product_details_mvp.ActivityProductDetailsPresenter;
import com.athome.mvp.activity_product_details_mvp.ActivityProductDetailsView;
import com.athome.ui.activity_product_details.fragments.Fragment_Comments;
import com.athome.ui.activity_product_details.fragments.Fragment_Details;
import com.athome.ui.activity_product_details.fragments.Fragment_Reviews;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class ProductDetailsActivity extends AppCompatActivity implements ActivityProductDetailsView {
    private ActivityProductDetailsBinding binding;
    private ActivityProductDetailsPresenter presenter;
    private String lang;
    private ViewPagerAdapter adapter;
    private List<Fragment> fragmentList;
    private List<String> titles;
    private SliderAdapter sliderAdapter;
    private ProductModel productModel;
    private int amount = 1;


    @Override
    protected void attachBaseContext(Context newBase)
    {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_details);
        getDataFromIntent();
        initView();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        productModel = (ProductModel) intent.getSerializableExtra("data");

    }

    private void initView()
    {
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        presenter = new ActivityProductDetailsPresenter(this,this);
        SliderAdapter sliderAdapter = new SliderAdapter(new ArrayList<>(), this);
        binding.tab.setupWithViewPager(binding.pager);
        binding.pager.setAdapter(sliderAdapter);
        titles = new ArrayList<>();
        fragmentList = new ArrayList<>();

        presenter = new ActivityProductDetailsPresenter(this,this);
        presenter.getProductById(String.valueOf(productModel.getId()));

        binding.imageIncrease.setOnClickListener(view -> {
            amount++;
            binding.tvAmount.setText(String.valueOf(amount));
        });

        binding.imageDecrease.setOnClickListener(view -> {
            if (amount>1){
                amount--;
                binding.tvAmount.setText(String.valueOf(amount));
            }
        });

    }


    @Override
    public void onSuccess(ProductModel data) {
        binding.setModel(data);

        fragmentList.add(Fragment_Details.newInstance(data));
        fragmentList.add(Fragment_Comments.newInstance(data));
        fragmentList.add(Fragment_Reviews.newInstance());

        titles.add(getString(R.string.descriptions));
        titles.add(getString(R.string.comments));
        titles.add(getString(R.string.reviews));

        binding.tabLayout.setupWithViewPager(binding.pager2);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragments_Titles(fragmentList, titles);
        binding.pager2.setAdapter(adapter);
        binding.pager2.setOffscreenPageLimit(fragmentList.size());
        binding.pager2.setCurrentItem(0,true);

        binding.view.setVisibility(View.GONE);

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