package com.athome.ui.activity_home.fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.athome.R;
import com.athome.adapters.DataAdapter;
import com.athome.adapters.HomeCategoriesAdapter;
import com.athome.adapters.OfferProductAdapter;
import com.athome.adapters.ProductAdapter;
import com.athome.adapters.SliderAdapter;
import com.athome.databinding.FragmentHomeBinding;

import com.athome.models.BankDataModel;
import com.athome.models.ProductModel;
import com.athome.models.SingleCategoryModel;
import com.athome.models.SliderDataModel;
import com.athome.mvp.fragment_home_mvp.FragmentHomePresenter;
import com.athome.mvp.fragment_home_mvp.FragmentHomeView;
import com.athome.ui.activity_home.HomeActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class Fragment_Home extends Fragment implements FragmentHomeView {
    private FragmentHomeBinding binding;
    private double lat = 0.0, lng = 0.0;
    private HomeActivity activity;
    private FragmentHomePresenter presenter;
    private SliderAdapter sliderAdapter;
    private ProductAdapter featuredProductAdapter,mostSellerAdapter;
    private OfferProductAdapter offerProductAdapter;
    private List<ProductModel> featuredProductList,mostSellerProductList,offerProductList;
    private HomeCategoriesAdapter categoriesAdapter;
    private List<SingleCategoryModel> singleCategoryModelList;
    private Timer timer;
    private TimerTask timerTask;


    public static Fragment_Home newInstance(double lat, double lng) {
        Bundle bundle = new Bundle();
        bundle.putDouble("lat", lat);
        bundle.putDouble("lng", lng);
        Fragment_Home fragment_home = new Fragment_Home();
        fragment_home.setArguments(bundle);
        return fragment_home;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        mostSellerProductList = new ArrayList<>();
        featuredProductList = new ArrayList<>();
        singleCategoryModelList = new ArrayList<>();
        offerProductList = new ArrayList<>();
        activity = (HomeActivity) getActivity();
        Bundle bundle = getArguments();
        if (bundle != null) {
            lat = bundle.getDouble("lat");
            lng = bundle.getDouble("lng");
        }
        binding.progBarCategories.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.progBarSlider.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.progBarFeaturedProducts.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.progBarMostSeller.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.progBarOffer.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        featuredProductAdapter = new ProductAdapter(featuredProductList, activity);
        binding.recViewFeaturedProducts.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
        binding.recViewFeaturedProducts.setAdapter(featuredProductAdapter);


        mostSellerAdapter = new ProductAdapter(mostSellerProductList, activity);
        binding.recViewMostSeller.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
        binding.recViewMostSeller.setAdapter(mostSellerAdapter);


        offerProductAdapter = new OfferProductAdapter(offerProductList, activity);
        binding.recViewOffer.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
        binding.recViewOffer.setAdapter(offerProductAdapter);


        binding.recViewCategories.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
        categoriesAdapter = new HomeCategoriesAdapter(singleCategoryModelList, activity);
        binding.recViewCategories.setAdapter(categoriesAdapter);
        binding.tab.setupWithViewPager(binding.pager);



        presenter = new FragmentHomePresenter(activity,this,lat,lng);
        presenter.getSlider();
        presenter.getCategory();
        presenter.getFeaturedProducts();
        presenter.getMostSellerProducts();
        presenter.getOfferProducts();

    }



    @Override
    public void onSliderSuccess(List<SliderDataModel.SliderModel> sliderModelList) {
        if (sliderModelList.size()>0){
            binding.flSlider.setVisibility(View.VISIBLE);
            sliderAdapter = new SliderAdapter(sliderModelList,activity);
            binding.pager.setAdapter(sliderAdapter);
            if (sliderModelList.size()>1)
            {
                timer = new Timer();
                timerTask = new MyTask();
                timer.scheduleAtFixedRate(timerTask,6000,6000);
            }

        }else {
            binding.flSlider.setVisibility(View.GONE);
        }


    }

    @Override
    public void onSuccess(List<SingleCategoryModel> data) {
        if (data.size()>0){
            singleCategoryModelList.clear();
            singleCategoryModelList.addAll(data);
            categoriesAdapter.notifyDataSetChanged();
            binding.tvNoDataCategories.setVisibility(View.GONE);

        }else {
            binding.tvNoDataCategories.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFeaturedProductSuccess(List<ProductModel> data) {
        if (data.size()>0){
            featuredProductList.clear();
            featuredProductList.addAll(data);
            featuredProductAdapter.notifyDataSetChanged();
            binding.tvNoDataFeaturedProducts.setVisibility(View.GONE);
        }else {
            binding.tvNoDataFeaturedProducts.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onMostSellerSuccess(List<ProductModel> data) {
        if (data.size()>0){
            mostSellerProductList.clear();
            mostSellerProductList.addAll(data);
            mostSellerAdapter.notifyDataSetChanged();
            binding.tvNoDataMostSeller.setVisibility(View.GONE);
        }else {
            binding.tvNoDataMostSeller.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onOfferSuccess(List<ProductModel> data) {
        if (data.size()>0){
            offerProductList.clear();
            offerProductList.addAll(data);
            offerProductAdapter.notifyDataSetChanged();
            binding.tvNoDataOffer.setVisibility(View.GONE);

        }else {
            binding.tvNoDataOffer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressSliderShow() {
        binding.progBarSlider.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressSliderHide() {
        binding.progBarSlider.setVisibility(View.GONE);

    }

    @Override
    public void onProgressCategoryShow() {
        binding.progBarCategories.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressCategoryHide() {
        binding.progBarCategories.setVisibility(View.GONE);

    }

    @Override
    public void onProgressFeaturedProductsShow() {
        binding.progBarFeaturedProducts.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressFeaturedProductsHide() {
        binding.progBarFeaturedProducts.setVisibility(View.GONE);

    }

    @Override
    public void onProgressMostSellerShow() {
        binding.progBarMostSeller.setVisibility(View.VISIBLE);

    }

    @Override
    public void onProgressMostSellerHide() {
        binding.progBarMostSeller.setVisibility(View.GONE);

    }

    @Override
    public void onProgressOfferShow() {
        binding.progBarOffer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressOfferHide() {
        binding.progBarOffer.setVisibility(View.GONE);

    }

    public class MyTask extends TimerTask{
        @Override
        public void run() {
            activity.runOnUiThread(() -> {
                int current_page = binding.pager.getCurrentItem();
                if (current_page<sliderAdapter.getCount()-1){
                    binding.pager.setCurrentItem(binding.pager.getCurrentItem()+1);
                }else {
                    binding.pager.setCurrentItem(0);

                }
            });

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (timer!=null){
            timer.purge();
            timer.cancel();
        }
        if (timerTask!=null){
            timerTask.cancel();
        }

    }
}
