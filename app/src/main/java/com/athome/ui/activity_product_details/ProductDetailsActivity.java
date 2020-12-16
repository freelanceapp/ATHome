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
import com.athome.adapters.ProductSliderAdapter;
import com.athome.adapters.ViewPagerAdapter;
import com.athome.databinding.ActivityProductDetailsBinding;
import com.athome.language.Language;
import com.athome.models.GalleryModel;
import com.athome.models.ProductModel;
import com.athome.mvp.activity_product_details_mvp.ActivityProductDetailsPresenter;
import com.athome.mvp.activity_product_details_mvp.ActivityProductDetailsView;
import com.athome.ui.activity_product_details.fragments.Fragment_Comments;
import com.athome.ui.activity_product_details.fragments.Fragment_Details;
import com.athome.ui.activity_product_details.fragments.Fragment_Policy;

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
    private ProductSliderAdapter sliderAdapter;
    private ProductModel productModel;
    private int amount = 1;
    private boolean isDataChanged = false;


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

        binding.llBack.setOnClickListener(view -> {
           onBackPressed();
        });

        binding.checkbox.setOnClickListener(view -> {
            if (binding.checkbox.isChecked()){
                productModel.setIs_wishlist(new ProductModel.IsWishList());

            }else {
                productModel.setIs_wishlist(null);

            }

            presenter.add_remove_favorite(productModel);
        });

    }


    @Override
    public void onSuccess(ProductModel data) {
        this.productModel = data;
        binding.setModel(data);

        binding.tab.setupWithViewPager(binding.pager);
        if (productModel.getGalleries().size() > 0) {
            List<GalleryModel> galleryModelList = new ArrayList<>(productModel.getGalleries());
            sliderAdapter = new ProductSliderAdapter(galleryModelList,this);
            binding.pager.setAdapter(sliderAdapter);
            binding.flSlider.setVisibility(View.VISIBLE);
            binding.image.setVisibility(View.GONE);

        }else {
            binding.image.setVisibility(View.VISIBLE);
            binding.flSlider.setVisibility(View.GONE);

        }


        fragmentList.add(Fragment_Details.newInstance(data));
        fragmentList.add(Fragment_Comments.newInstance(data));
        fragmentList.add(Fragment_Policy.newInstance(data));

        titles.add(getString(R.string.descriptions));
        titles.add(getString(R.string.comments));
        titles.add(getString(R.string.policy));




        binding.tabLayout.setupWithViewPager(binding.pager2);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragments_Titles(fragmentList, titles);
        binding.pager2.setAdapter(adapter);
        binding.pager2.setOffscreenPageLimit(fragmentList.size());
        binding.pager2.setCurrentItem(0,true);

        binding.view.setVisibility(View.GONE);
        binding.setCommentsCount(String.valueOf(data.getComments().size()));

    }

    public void updateCommentsCount(int count){
        binding.setCommentsCount(String.valueOf(count));

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

    @Override
    public void onUserNotRegister(String msg, ProductModel productModel) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        this.productModel = productModel;
        binding.setModel(productModel);
        isDataChanged = false;


    }

    @Override
    public void onFavoriteActionSuccess(ProductModel productModel) {
        this.productModel = productModel;
        binding.setModel(productModel);
        isDataChanged = true;
    }

    @Override
    public void onBackPressed() {
        if (isDataChanged){
            setResult(RESULT_OK);
        }
        finish();
    }
}