package com.athome.interfaces;


public interface Listeners {

    interface LoginListener {
        void checkDataLogin(String phone_code, String phone);
    }



    interface BackListener
    {
        void back();
    }


    interface ProfileActions
    {
        void onMyWallet();
        void onFavorite();
        void onAddress();
        void onChangeLanguage();
        void onTerms();
        void onContactUs();
        void onFacebook();
        void onTwitter();
        void onInstagram();
        void onLogout();

    }



}
