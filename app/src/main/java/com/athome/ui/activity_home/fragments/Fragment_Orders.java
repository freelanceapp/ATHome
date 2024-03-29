package com.athome.ui.activity_home.fragments;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.athome.R;
import com.athome.adapters.OrdersAdapter;
import com.athome.databinding.FragmentOrdersBinding;
import com.athome.models.OrderModel;
import com.athome.mvp.fragment_order_mvp.FragmentOrderPresenter;
import com.athome.mvp.fragment_order_mvp.FragmentOrderView;
import com.athome.ui.activity_home.HomeActivity;
import com.athome.ui.activity_order_details.OrderDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Orders extends Fragment implements FragmentOrderView {
    private FragmentOrdersBinding binding;
    private HomeActivity activity;
    private OrdersAdapter adapter;
    private List<OrderModel> orderModelList;
    private FragmentOrderPresenter presenter;


    public static Fragment_Orders newInstance(){
        return new Fragment_Orders();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_orders,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeActivity) getActivity();
        orderModelList = new ArrayList<>();
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
        binding.progBar.setVisibility(View.GONE);
        binding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        adapter = new OrdersAdapter(orderModelList,activity,this);
        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recView.setAdapter(adapter);
        presenter = new FragmentOrderPresenter(activity,this);
        presenter.getOrders();
        binding.swipeRefresh.setOnRefreshListener(() -> presenter.getOrders());
    }

    @Override
    public void onSuccess(List<OrderModel> data) {
        binding.swipeRefresh.setRefreshing(false);

        if (data.size()>0){
            binding.tvNoData.setVisibility(View.GONE);
            orderModelList.addAll(data);
            adapter.notifyDataSetChanged();
        }else {

            binding.tvNoData.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onFailed(String msg) {
        binding.swipeRefresh.setRefreshing(false);
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressShow() {
        binding.progBar.setVisibility(View.VISIBLE);
        binding.tvNoData.setVisibility(View.GONE);
        orderModelList.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onProgressHide() {
        binding.progBar.setVisibility(View.GONE);
        binding.swipeRefresh.setRefreshing(false);

    }

    public void setItemData(OrderModel orderModel){
        Intent intent = new Intent(activity, OrderDetailsActivity.class);
        intent.putExtra("data",orderModel);
        startActivity(intent);
    }
}
