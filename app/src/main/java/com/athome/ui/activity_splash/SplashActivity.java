package com.athome.ui.activity_splash;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
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
import com.athome.ui.activity_get_location.GetLocationActivity;
import com.athome.ui.activity_home.HomeActivity;
import com.athome.ui.activity_language.LanguageActivity;
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        initView();
    }

    private void initView() {
        presenter = new SplashPresenter(this,this);
        preferences = Preferences.getInstance();
        String path = "android.resource://"+getPackageName()+"/"+R.raw.splash;
        binding.videoView.setVideoPath(path);
        binding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                binding.videoView.start();
            }
        });

        binding.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

                presenter.delaySplash();

                /*   String session = preferences.getSession(SplashActivity.this);

                if (session.equals(Tags.session_login))
                {
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }else
                {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }*/
            }
        });
    }


    @Override
    public void onNavigateToLanguageActivity() {
        Intent intent = new Intent(this, LanguageActivity.class);
        startActivityForResult(intent,100);

    }

    @Override
    public void onNavigateToLocationActivity() {
        Intent intent = new Intent(this, GetLocationActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode==RESULT_OK&&data!=null){
            String lang = data.getStringExtra("lang");
            refreshActivity(lang);
        }
    }

    private void refreshActivity(String lang) {
        Paper.init(this);
        Paper.book().write("lang",lang);
        UserSettingsModel userSettings = preferences.getUserSettings(this);
        if (userSettings==null){
            userSettings = new UserSettingsModel();
        }
        userSettings.setLanguageSelected(true);
        preferences.create_update_user_settings(this,userSettings);
        Language.updateResources(this,lang);
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}