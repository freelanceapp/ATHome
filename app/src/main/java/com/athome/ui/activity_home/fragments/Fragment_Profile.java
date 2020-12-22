package com.athome.ui.activity_home.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.athome.R;
import com.athome.databinding.FragmentProfileBinding;
import com.athome.interfaces.Listeners;
import com.athome.models.UserModel;
import com.athome.preferences.Preferences;
import com.athome.share.Common;
import com.athome.ui.activity_contact_us.ContactUsActivity;
import com.athome.ui.activity_favorite.FavoriteActivity;
import com.athome.ui.activity_home.HomeActivity;
import com.athome.ui.activity_language.LanguageActivity;
import com.athome.ui.activity_menu.MenuActivity;
import com.athome.ui.activity_select_address.SelectAddressActivity;
import com.athome.ui.activity_web_view.WebViewActivity;

import io.paperdb.Paper;

public class Fragment_Profile extends Fragment implements Listeners.ProfileActions {
    private FragmentProfileBinding binding;
    private String lang;
    private HomeActivity activity;
    private Preferences preferences;
    private UserModel userModel;

    public static Fragment_Profile newInstance(){
        return new Fragment_Profile();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeActivity) getActivity();
        Paper.init(activity);
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        if (userModel!=null){

        }
        binding.setActions(this);

    }

    @Override
    public void onMyWallet() {

    }

    @Override
    public void onFavorite() {
        userModel = preferences.getUserData(activity);
        if (userModel!=null){
            Intent intent = new Intent(activity, FavoriteActivity.class);
            startActivityForResult(intent,100);
        }else {
            Common.CreateDialogAlert(activity,getString(R.string.pls_signin_signup));
        }

    }

    @Override
    public void onAddress() {
        userModel = preferences.getUserData(activity);
        if (userModel!=null){
            Intent intent = new Intent(activity, SelectAddressActivity.class);
            startActivity(intent);
        }else {
            Common.CreateDialogAlert(activity,getString(R.string.pls_signin_signup));
        }
    }

    @Override
    public void onChangeLanguage() {
        Intent intent = new Intent(activity, LanguageActivity.class);
        startActivityForResult(intent,200);
    }

    @Override
    public void onTerms() {
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra("url","https://athomegy.com/terms");
        startActivity(intent);
    }

    @Override
    public void onContactUs() {
        Intent intent = new Intent(activity, ContactUsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onMenu() {
        userModel = preferences.getUserData(activity);
        if (userModel!=null){
            Intent intent = new Intent(activity, MenuActivity.class);
            startActivity(intent);
        }else {
            Common.CreateDialogAlert(activity,getString(R.string.pls_signin_signup));
        }
    }

    @Override
    public void onFacebook() {

    }

    @Override
    public void onTwitter() {

    }

    @Override
    public void onInstagram() {

    }

    @Override
    public void onLogout() {
        activity.logout();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode== Activity.RESULT_OK){
            activity.refreshFragmentHomeData();
        }else if (requestCode==200&&resultCode== Activity.RESULT_OK&&data!=null){
            String lang = data.getStringExtra("lang");
            activity.refreshActivity(lang);
        }

    }
}
