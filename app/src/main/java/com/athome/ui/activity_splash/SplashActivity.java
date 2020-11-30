package com.athome.ui.activity_splash;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.athome.R;
import com.athome.databinding.ActivitySplashBinding;
import com.athome.language.Language;
import com.athome.models.UserSettingsModel;
import com.athome.mvp.activity_splash_mvp.SplashPresenter;
import com.athome.mvp.activity_splash_mvp.SplashView;
import com.athome.preferences.Preferences;
import com.athome.tags.Tags;
import com.athome.ui.activity_home.HomeActivity;
import com.athome.ui.activity_login.LoginActivity;

import io.paperdb.Paper;

public class SplashActivity extends AppCompatActivity implements SplashView {
    private ActivitySplashBinding binding;
    private SplashPresenter presenter;
    private Preferences preferences;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase,Paper.book().read("lang","ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Transition transition = new TransitionSet();
            transition.setInterpolator(new LinearInterpolator());
            transition.setDuration(500);
            getWindow().setEnterTransition(transition);
            getWindow().setExitTransition(transition);

        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        initView();
    }

    private void initView() {
        presenter = new SplashPresenter(this,this);
        preferences = Preferences.getInstance();
        presenter.delaySplash();
    }


    @Override
    public void onNavigateToLanguageActivity() {

    }

    @Override
    public void onNavigateToLocationActivity() {
        if(preferences.getSession(this).equals(Tags.session_login)){

        }else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }}






}