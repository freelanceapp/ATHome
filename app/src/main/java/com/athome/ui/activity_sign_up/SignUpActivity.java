package com.athome.ui.activity_sign_up;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;


import com.athome.models.UserModel;
import com.athome.preferences.Preferences;
import com.athome.ui.activity_confirm_code.ConfirmCodeActivity;
import com.athome.ui.activity_home.HomeActivity;
import com.athome.ui.activity_login.LoginActivity;
import com.squareup.picasso.Picasso;
import com.athome.R;
import com.athome.databinding.ActivitySignUpBinding;
import com.athome.databinding.DialogSelectImageBinding;
import com.athome.language.Language;
import com.athome.models.SignUpModel;
import com.athome.mvp.activity_sign_up_mvp.ActivitySignUpPresenter;
import com.athome.mvp.activity_sign_up_mvp.ActivitySignUpView;
import com.athome.share.Common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class SignUpActivity extends AppCompatActivity implements ActivitySignUpView {
    private ActivitySignUpBinding binding;
    private ActivitySignUpPresenter presenter;
    private SignUpModel model;
    private Preferences preference;
    private double lat = 0.0, lng = 0.0;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        getDataFromIntent();
        initView();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        lat = intent.getDoubleExtra("lat", 0.0);
        lng = intent.getDoubleExtra("lng", 0.0);

    }

    private void initView() {
        preference = Preferences.getInstance();
        model = new SignUpModel();
        binding.setModel(model);
        presenter = new ActivitySignUpPresenter(this, this);
        binding.btnSignUp.setOnClickListener(view -> {
            if (model.isDataValid(this)) {
                Common.CloseKeyBoard(this, binding.edtPhone);
                navigateToConfirmCodeActivity();
            }
        });
        binding.tvLogin.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("lat", lat);
            intent.putExtra("lng", lng);
            startActivity(intent);
            finish();
        });

    }

    private void navigateToConfirmCodeActivity() {
        Intent intent = new Intent(this, ConfirmCodeActivity.class);
        intent.putExtra("phone_code", model.getPhone_code());
        intent.putExtra("phone", model.getPhone());
        startActivityForResult(intent, 100);
    }


    @Override
    public void onSuccess(UserModel userModel) {
        preference.create_update_userdata(this, userModel);
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("lat", lat);
        intent.putExtra("lng", lng);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("lat", lat);
        intent.putExtra("lng", lng);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            presenter.sign_up(model);

        }
    }
}