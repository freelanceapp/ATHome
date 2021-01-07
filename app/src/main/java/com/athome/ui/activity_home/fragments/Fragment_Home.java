package com.athome.ui.activity_home.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.athome.R;
import com.athome.adapters.HomeCategoriesAdapter;
import com.athome.adapters.OfferProductAdapter;
import com.athome.adapters.ProductAdapter;
import com.athome.adapters.SliderAdapter;
import com.athome.databinding.FragmentHomeBinding;

import com.athome.models.ProductModel;
import com.athome.models.SingleCategoryModel;
import com.athome.models.SliderDataModel;
import com.athome.mvp.fragment_home_mvp.FragmentHomePresenter;
import com.athome.mvp.fragment_home_mvp.FragmentHomeView;
import com.athome.ui.activity_home.HomeActivity;
import com.athome.ui.activity_product_details.ProductDetailsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.paperdb.Paper;


public class Fragment_Home extends Fragment implements FragmentHomeView {
    private FragmentHomeBinding binding;
    private double lat = 0.0, lng = 0.0;
    private HomeActivity activity;
    private FragmentHomePresenter presenter;
    private SliderAdapter sliderAdapter;
    private ProductAdapter featuredProductAdapter,mostSellerAdapter,otherProductAdapter;
    private OfferProductAdapter offerProductAdapter;
    private List<ProductModel> featuredProductList,mostSellerProductList,offerProductList,otherProductList;
    private HomeCategoriesAdapter categoriesAdapter;
    private List<SingleCategoryModel> singleCategoryModelList;
    private Timer timer;
    private TimerTask timerTask;
    private String lang="ar";


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

    private void initView()
    {
        mostSellerProductList = new ArrayList<>();
        featuredProductList = new ArrayList<>();
        singleCategoryModelList = new ArrayList<>();
        offerProductList = new ArrayList<>();
        otherProductList = new ArrayList<>();
        activity = (HomeActivity) getActivity();

        Paper.init(activity);
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
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
        binding.progBarOtherProducts.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        featuredProductAdapter = new ProductAdapter(featuredProductList, activity,this,"1");
        binding.recViewFeaturedProducts.setLayoutManager(new GridLayoutManager(activity,2,RecyclerView.HORIZONTAL, false));
        binding.recViewFeaturedProducts.setAdapter(featuredProductAdapter);


        mostSellerAdapter = new ProductAdapter(mostSellerProductList, activity,this,"2");
        binding.recViewMostSeller.setLayoutManager(new GridLayoutManager(activity,2,RecyclerView.HORIZONTAL, false));
        binding.recViewMostSeller.setAdapter(mostSellerAdapter);


        offerProductAdapter = new OfferProductAdapter(offerProductList, activity,this);
        binding.recViewOffer.setLayoutManager(new GridLayoutManager(activity,2,RecyclerView.HORIZONTAL, false));
        binding.recViewOffer.setAdapter(offerProductAdapter);


        otherProductAdapter = new ProductAdapter(otherProductList, activity,this,"3");
        binding.recViewOtherProducts.setLayoutManager(new LinearLayoutManager(activity));
        binding.recViewOtherProducts.setAdapter(otherProductAdapter);


        binding.recViewCategories.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
        categoriesAdapter = new HomeCategoriesAdapter(singleCategoryModelList, activity,this);
        binding.recViewCategories.setAdapter(categoriesAdapter);
        binding.tab.setupWithViewPager(binding.pager);




        binding.recViewCategories.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager manager = (LinearLayoutManager) binding.recViewCategories.getLayoutManager();
                int firstPos = manager.findFirstCompletelyVisibleItemPosition();
                int lastPos = manager.findLastCompletelyVisibleItemPosition();

                try {
                    if (lastPos == singleCategoryModelList.size()-1){
                        binding.card2.setVisibility(View.GONE);
                    }else {
                        binding.card2.setVisibility(View.VISIBLE);

                    }

                    if (firstPos==0){
                        binding.card1.setVisibility(View.GONE);
                        binding.card2.setVisibility(View.VISIBLE);
                    }else {
                        binding.card1.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){}






            }
        });

        binding.recViewOffer.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager manager = (GridLayoutManager) binding.recViewOffer.getLayoutManager();
                int firstPos = manager.findFirstCompletelyVisibleItemPosition();
                int lastPos = manager.findLastCompletelyVisibleItemPosition();

                if (firstPos==0){
                    binding.card5.setVisibility(View.GONE);
                }else {
                    binding.card5.setVisibility(View.VISIBLE);
                }


                try {
                    if (lastPos == offerProductList.size()||lastPos == offerProductList.size()-1){
                        binding.card6.setVisibility(View.GONE);
                    }else {
                        binding.card6.setVisibility(View.VISIBLE);

                    }
                }catch (Exception e){}




            }
        });

        binding.recViewFeaturedProducts.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager manager = (GridLayoutManager) binding.recViewFeaturedProducts.getLayoutManager();
                int firstPos = manager.findFirstCompletelyVisibleItemPosition();
                int lastPos = manager.findLastCompletelyVisibleItemPosition();

                if (firstPos==0){
                    binding.card3.setVisibility(View.GONE);
                }else {
                    binding.card3.setVisibility(View.VISIBLE);
                }

                try {

                    if (lastPos == featuredProductList.size()||lastPos == featuredProductList.size()-1){
                        binding.card4.setVisibility(View.GONE);
                    }else {
                        binding.card4.setVisibility(View.VISIBLE);

                    }
                }catch (Exception e){}




            }
        });


        binding.recViewMostSeller.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager manager = (GridLayoutManager) binding.recViewMostSeller.getLayoutManager();
                int firstPos = manager.findFirstCompletelyVisibleItemPosition();
                int lastPos = manager.findLastCompletelyVisibleItemPosition();

                if (firstPos==0){
                    binding.card7.setVisibility(View.GONE);
                }else {
                    binding.card7.setVisibility(View.VISIBLE);
                }

                try {
                    if (lastPos == mostSellerProductList.size()||lastPos == mostSellerProductList.size()-1){
                        binding.card8.setVisibility(View.GONE);
                    }else {
                        binding.card8.setVisibility(View.VISIBLE);

                    }
                }catch (Exception e){}




            }
        });


        presenter = new FragmentHomePresenter(activity,this,lat,lng);
        presenter.getSlider();
        presenter.getCategory();
        presenter.getFeaturedProducts();
        presenter.getMostSellerProducts();
        presenter.getOfferProducts();
        presenter.getOtherProducts();


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
            binding.card1.setVisibility(View.VISIBLE);
            binding.card2.setVisibility(View.VISIBLE);

        }else {
            binding.tvNoDataCategories.setVisibility(View.VISIBLE);
            binding.card1.setVisibility(View.GONE);
            binding.card2.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFeaturedProductSuccess(List<ProductModel> data) {
        if (data.size()>0){
            featuredProductList.clear();
            featuredProductList.addAll(data);
            featuredProductAdapter.notifyDataSetChanged();
            binding.tvNoDataFeaturedProducts.setVisibility(View.GONE);
            binding.card3.setVisibility(View.VISIBLE);
            binding.card4.setVisibility(View.VISIBLE);
        }else {
            binding.tvNoDataFeaturedProducts.setVisibility(View.VISIBLE);
            binding.card3.setVisibility(View.GONE);
            binding.card4.setVisibility(View.GONE);
        }
    }

    @Override
    public void onMostSellerSuccess(List<ProductModel> data) {
        if (data.size()>0){
            mostSellerProductList.clear();
            mostSellerProductList.addAll(data);
            mostSellerAdapter.notifyDataSetChanged();
            binding.tvNoDataMostSeller.setVisibility(View.GONE);
            binding.card7.setVisibility(View.VISIBLE);
            binding.card8.setVisibility(View.VISIBLE);
        }else {
            binding.tvNoDataMostSeller.setVisibility(View.VISIBLE);
            binding.card7.setVisibility(View.GONE);
            binding.card8.setVisibility(View.GONE);

        }
    }

    @Override
    public void onOtherProductSuccess(List<ProductModel> data) {
        if (data.size()>0){
            otherProductList.clear();
            otherProductList.addAll(data);
            otherProductAdapter.notifyDataSetChanged();
            binding.tvNoDataOtherProducts.setVisibility(View.GONE);

        }else {
            binding.tvNoDataOtherProducts.setVisibility(View.VISIBLE);


        }
    }

    @Override
    public void onOfferSuccess(List<ProductModel> data) {
        if (data.size()>0){
            offerProductList.clear();
            offerProductList.addAll(data);
            offerProductAdapter.notifyDataSetChanged();
            binding.tvNoDataOffer.setVisibility(View.GONE);
            binding.card5.setVisibility(View.VISIBLE);
            binding.card6.setVisibility(View.VISIBLE);

        }else {
            binding.tvNoDataOffer.setVisibility(View.VISIBLE);
            binding.card5.setVisibility(View.GONE);
            binding.card6.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserNotRegister(String msg, ProductModel productModel, int position,String type) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();

        if (type.equals("1")){
            featuredProductList.set(position,productModel);
            featuredProductAdapter.notifyItemChanged(position);
        }else if (type.equals("2")){
            mostSellerProductList.set(position,productModel);
            mostSellerAdapter.notifyItemChanged(position);
        }else if (type.equals("3")){
            offerProductList.set(position,productModel);
            offerProductAdapter.notifyItemChanged(position);
        }

    }

    @Override
    public void onFavoriteActionSuccess(ProductModel productModel, int position,String type) {

        if (type.equals("1")){
            featuredProductList.set(position,productModel);
            featuredProductAdapter.notifyItemChanged(position);
        }else if (type.equals("2")){
            mostSellerProductList.set(position,productModel);
            mostSellerAdapter.notifyItemChanged(position);
        }else if (type.equals("3")){
            offerProductList.set(position,productModel);
            offerProductAdapter.notifyItemChanged(position);
        }
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
        binding.tvNoDataFeaturedProducts.setVisibility(View.GONE);
        featuredProductList.clear();
        featuredProductAdapter.notifyDataSetChanged();
        binding.progBarFeaturedProducts.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressFeaturedProductsHide() {
        binding.progBarFeaturedProducts.setVisibility(View.GONE);

    }

    @Override
    public void onProgressMostSellerShow() {
        binding.progBarMostSeller.setVisibility(View.VISIBLE);
        binding.tvNoDataMostSeller.setVisibility(View.GONE);
        mostSellerProductList.clear();
        mostSellerAdapter.notifyDataSetChanged();

    }

    @Override
    public void onProgressMostSellerHide() {
        binding.progBarMostSeller.setVisibility(View.GONE);

    }

    @Override
    public void onProgressOfferShow() {
        binding.progBarOffer.setVisibility(View.VISIBLE);
        binding.tvNoDataOffer.setVisibility(View.GONE);
        offerProductList.clear();
        offerProductAdapter.notifyDataSetChanged();
    }

    @Override
    public void onProgressOfferHide() {
        binding.progBarOffer.setVisibility(View.GONE);

    }

    @Override
    public void onProgressOtherProductsShow() {
        binding.progBarOtherProducts.setVisibility(View.VISIBLE);

    }

    @Override
    public void onProgressOtherProductsHide() {
        binding.progBarOtherProducts.setVisibility(View.GONE);

    }

    public void setItemData(int pos) {
        activity.displayFragmentCategory(pos);
    }

    public void setProductItemModel(ProductModel model) {
        Intent intent = new Intent(activity, ProductDetailsActivity.class);
        intent.putExtra("data",model);
        startActivityForResult(intent,100);
    }

    public void add_remove_favorite(ProductModel productModel, int adapterPosition, String type){

        presenter.add_remove_favorite(productModel,adapterPosition,type);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode== Activity.RESULT_OK){
            refreshData();
        }
    }

    public void refreshData() {
        presenter.getOfferProducts();
        presenter.getMostSellerProducts();
        presenter.getFeaturedProducts();
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
