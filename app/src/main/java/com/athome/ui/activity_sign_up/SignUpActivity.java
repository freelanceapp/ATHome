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
        model = new SignUpModel();
        binding.setModel(model);
        presenter = new ActivitySignUpPresenter(this,this);



    }





    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}