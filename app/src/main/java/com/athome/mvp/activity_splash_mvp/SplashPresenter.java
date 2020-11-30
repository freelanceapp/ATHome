package com.athome.mvp.activity_splash_mvp;

import android.content.Context;
import android.os.Handler;

import com.athome.models.UserModel;
import com.athome.models.UserSettingsModel;
import com.athome.preferences.Preferences;

public class SplashPresenter {
    private Context context;
    private SplashView view;
    private Preferences preferences;
    private UserModel userModel;
    private UserSettingsModel userSettingsModel;

    public SplashPresenter(Context context, SplashView view) {
        this.context = context;
        this.view = view;
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(context);
        userSettingsModel = preferences.getUserSettings(context);
      //  delaySplash();
    }

    public void delaySplash(){
        new Handler().postDelayed(()->{

//            if (userSettingsModel!=null&&userSettingsModel.isLanguageSelected()){
                view.onNavigateToLocationActivity();
//            }else {
//                view.onNavigateToLanguageActivity();
//
//            }



        },2000);
    }
}
