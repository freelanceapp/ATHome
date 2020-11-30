package com.athome.ui.activity_home.fragments;

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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.athome.R;
import com.athome.adapters.CategoriesAdapter;
import com.athome.adapters.SubCategoriesAdapter;
import com.athome.databinding.FragmentCategoriesBinding;
import com.athome.models.AllCategoryModel;
import com.athome.models.BankDataModel;
import com.athome.models.SingleCategoryModel;
import com.athome.remote.Api;
import com.athome.tags.Tags;
import com.athome.ui.activity_home.HomeActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Categories extends Fragment {
    private FragmentCategoriesBinding binding;
    private HomeActivity activity;
    private SubCategoriesAdapter auctionAdapter;
    private CategoriesAdapter categoriesAdapter;
    private List<SingleCategoryModel> singleCategoryModelList;

    public static Fragment_Categories newInstance(){
        return new Fragment_Categories();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_categories,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        singleCategoryModelList=new ArrayList<>();
        activity = (HomeActivity) getActivity();
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
        binding.progBar.setVisibility(View.GONE);
        auctionAdapter = new SubCategoriesAdapter( new ArrayList<BankDataModel.BankModel>(),activity);
        binding.recView.setAdapter(auctionAdapter);
        binding.progBarcategories.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.recViewcategories.setLayoutManager(new LinearLayoutManager(activity));
        categoriesAdapter = new CategoriesAdapter( singleCategoryModelList,activity);
        binding.recViewcategories.setAdapter(categoriesAdapter);
        getCategory();
    }
    private void getCategory() {

        Api.getService(Tags.base_url)
                .getCategory()
                .enqueue(new Callback<AllCategoryModel>() {
                    @Override
                    public void onResponse(Call<AllCategoryModel> call, Response<AllCategoryModel> response) {
                        binding.progBarcategories.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            singleCategoryModelList.addAll(response.body().getData());
                            categoriesAdapter.notifyDataSetChanged();

                        } else {
                            binding.progBarcategories.setVisibility(View.GONE);

                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (response.code() == 500) {
                                Toast.makeText(activity, "Server Error", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(activity, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AllCategoryModel> call, Throwable t) {
                        try {
                            binding.progBarcategories.setVisibility(View.GONE);

                            if (t.getMessage() != null) {
                                Log.e("error_not_code", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    Toast.makeText(activity, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(activity, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });
    }

}
