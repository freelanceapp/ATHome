package com.athome.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.athome.models.CartDataModel;
import com.google.gson.Gson;
import com.athome.models.UserModel;
import com.athome.models.UserSettingsModel;
import com.athome.tags.Tags;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class Preferences {

    private static Preferences instance = null;

    private Preferences() {
    }

    public static Preferences getInstance() {
        if (instance == null) {
            instance = new Preferences();
        }
        return instance;
    }

    public void create_update_user_settings(Context context, UserSettingsModel model) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings_pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String data = new Gson().toJson(model);
        editor.putString("settings", data);
        editor.apply();


    }

    public UserSettingsModel getUserSettings(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("settings_pref", Context.MODE_PRIVATE);
        UserSettingsModel model = new Gson().fromJson(preferences.getString("settings", ""), UserSettingsModel.class);
        return model;

    }


    public void create_update_userdata(Context context, UserModel userModel) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String user_data = gson.toJson(userModel);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_data", user_data);
        editor.apply();
        create_update_session(context, Tags.session_login);

    }

    public UserModel getUserData(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String user_data = preferences.getString("user_data", "");
        UserModel userModel = gson.fromJson(user_data, UserModel.class);
        return userModel;
    }

    public void create_update_session(Context context, String session) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("state", session);
        editor.apply();


    }

    public void createUpdateCartData(Context context, CartDataModel cartDataModel) {
        SharedPreferences preferences = context.getSharedPreferences("cart", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String data = gson.toJson(cartDataModel);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("data", data);
        editor.apply();
    }

    public CartDataModel getCartData(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("cart", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        return gson.fromJson(preferences.getString("data", ""),CartDataModel.class);
    }

    public String getSession(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        String session = preferences.getString("state", Tags.session_logout);
        return session;
    }

    public void clear(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.clear();
        edit.apply();
        create_update_session(context, Tags.session_logout);
    }


}
