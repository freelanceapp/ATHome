package com.athome.ui.activity_home;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.athome.tags.Tags;
import com.athome.ui.activity_cart.CartActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.athome.R;
import com.athome.databinding.ActivityHomeBinding;
import com.athome.language.Language;
import com.athome.mvp.activity_home_mvp.ActivityHomePresenter;
import com.athome.mvp.activity_home_mvp.HomeActivityView;
import com.athome.ui.activity_login.LoginActivity;

import java.util.List;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements HomeActivityView {
    private ActivityHomeBinding binding;
    private FragmentManager fragmentManager;
    private ActivityHomePresenter presenter;
    private double lat = 0.0, lng = 0.0;
    private boolean onCategorySelected = false;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }


    @Override
    protected void onResume() {
        super.onResume();
        presenter.getCartItemCount();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        lat = intent.getDoubleExtra("lat", 0.0);
        lng = intent.getDoubleExtra("lng", 0.0);
    }

    private void initView()
    {
        fragmentManager = getSupportFragmentManager();
        presenter = new ActivityHomePresenter(this, this, fragmentManager, lat, lng);
        binding.navigationView.setOnNavigationItemSelectedListener(item -> {
            if (!onCategorySelected){
                presenter.manageFragments(item);

            }
            onCategorySelected= false;
            return true;
        });

        binding.flCart.setOnClickListener(view -> {
            presenter.cart();
        });


    }

    public void displayFragmentCategory(int pos){
        presenter.displayFragmentCategories(pos);
    }

    public void refreshFragmentHomeData(){
        presenter.refreshFragmentHomeData();
    }

    public void logout(){

        presenter.logout();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    @Override
    public void onBackPressed() {
        presenter.backPress();
    }

    @Override
    public void onHomeFragmentSelected() {
        onCategorySelected = false;
        binding.navigationView.setSelectedItemId(R.id.home);
    }

    @Override
    public void onCategoryFragmentSelected() {
        onCategorySelected = true;
        binding.navigationView.setSelectedItemId(R.id.categories);

    }


    @Override
    public void onNavigateToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onNavigateToCartActivity() {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);

    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCartCountUpdate(int count) {
        binding.setCartcount(count);
    }


    @Override
    public void onFinished() {
        finish();
    }

    public void refreshActivity(String lang) {
        Paper.init(this);
        Paper.book().write("lang",lang);
        Language.updateResources(this,lang);
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.updateCartModel();
    }
}