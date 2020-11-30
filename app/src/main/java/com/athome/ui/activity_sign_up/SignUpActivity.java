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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;


import com.athome.models.UserModel;
import com.athome.preferences.Preferences;
import com.athome.ui.activity_home.HomeActivity;
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

    private  AlertDialog dialog;
private Preferences preference;
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        initView();

    }

    private void initView()
    {
        preference= Preferences.getInstance();
        model = new SignUpModel();
        binding.setModel(model);
        presenter = new ActivitySignUpPresenter(this,this);
binding.btnLogin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        presenter.checkData(model);
    }
});


    }




    @Override
    public void onSignupValid(UserModel userModel) {
        preference.create_update_userdata(SignUpActivity.this, userModel);


            Intent intent = new Intent(this, HomeActivity.class);

            startActivity(intent);
            finish();

    }



    @Override
    public void onServer() {
        Toast.makeText(SignUpActivity.this, "Server Error", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLoad() {
        dialog = Common.createProgressDialog(this, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void onFinishload() {
        dialog.dismiss();
    }

    @Override
    public void onnotconnect(String msg) {
        Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onFailed(String msg) {
        Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

}