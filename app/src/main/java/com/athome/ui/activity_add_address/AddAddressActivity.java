package com.athome.ui.activity_add_address;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.athome.R;
import com.athome.adapters.CartAdapter;
import com.athome.databinding.ActivityAddAddressBinding;
import com.athome.databinding.ActivityOrderCheckoutBinding;
import com.athome.language.Language;
import com.athome.models.AddressModel;
import com.athome.models.SelectedLocation;
import com.athome.mvp.activity_order_checkout_mvp.ActivityOrderCheckoutPresenter;
import com.athome.mvp.activity_order_checkout_mvp.OrderCheckoutActivityView;

import io.paperdb.Paper;

public class AddAddressActivity extends AppCompatActivity {
    private ActivityAddAddressBinding binding;
    private String lang;
    private SelectedLocation selectedLocation;
    private AddressModel addressModel;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_address);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        selectedLocation = (SelectedLocation) intent.getSerializableExtra("location");
        addressModel = (AddressModel) intent.getSerializableExtra("data");
    }


    private void initView() {

        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        binding.setLocation(selectedLocation);


        if (addressModel==null){
            binding.btnAdd.setText(R.string.update_address);
        }else {
            binding.btnAdd.setText(R.string.add_address);
        }



    }




}