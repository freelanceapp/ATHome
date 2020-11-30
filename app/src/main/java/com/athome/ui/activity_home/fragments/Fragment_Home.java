package com.athome.ui.activity_home.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.athome.R;
import com.athome.adapters.DataAdapter;
import com.athome.adapters.SliderAdapter;
import com.athome.databinding.FragmentHomeBinding;

import com.athome.models.BankDataModel;
import com.athome.models.Slider_Model;
import com.athome.remote.Api;
import com.athome.tags.Tags;
import com.athome.ui.activity_home.HomeActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_Home extends Fragment {
    private FragmentHomeBinding binding;
    private double lat = 0.0, lng = 0.0;
    private HomeActivity activity;
    private SliderAdapter sliderAdapter;
    private DataAdapter auctionAdapter;
    private int current_page = 0, NUM_PAGES;

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
        change_slide_image();
        return binding.getRoot();
    }

    private void initView() {

        activity = (HomeActivity) getActivity();
        Bundle bundle = getArguments();
        auctionAdapter = new DataAdapter(new ArrayList<BankDataModel.BankModel>(), activity);
        binding.recViewAccessories.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
        binding.recViewAccessories.setAdapter(auctionAdapter);
        binding.recViewFavoriteOffers.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
        binding.recViewFavoriteOffers.setAdapter(auctionAdapter);
        binding.tab.setupWithViewPager(binding.pager);

        if (bundle != null) {
            lat = bundle.getDouble("lat");
            lng = bundle.getDouble("lng");
        }
get_slider();
    }

    private void change_slide_image() {
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (current_page == NUM_PAGES) {
                    current_page = 0;
                }
                binding.pager.setCurrentItem(current_page++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);
    }
    private void get_slider() {

        Api.getService(Tags.base_url).get_slider().enqueue(new Callback<Slider_Model>() {
            @Override
            public void onResponse(Call<Slider_Model> call, Response<Slider_Model> response) {
                binding.progBarSlider.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    if (response.body().getData().size() > 0) {
                        NUM_PAGES = response.body().getData().size();
                        sliderAdapter = new SliderAdapter( response.body().getData(),activity);
                        binding.pager.setAdapter(sliderAdapter);

                    } else {

                        binding.pager.setVisibility(View.GONE);
                    }
                } else if (response.code() == 404) {
                    binding.pager.setVisibility(View.GONE);
                } else {
                    binding.pager.setVisibility(View.GONE);
                    try {
                        Log.e("Error_code", response.code() + "_" + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<Slider_Model> call, Throwable t) {
                try {
                    binding.progBarSlider.setVisibility(View.GONE);
                    binding.pager.setVisibility(View.GONE);

                    Log.e("Error", t.getMessage());

                } catch (Exception e) {

                }

            }
        });

    }


}
