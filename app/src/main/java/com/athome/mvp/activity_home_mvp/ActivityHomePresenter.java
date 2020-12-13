package com.athome.mvp.activity_home_mvp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MenuItem;

import androidx.fragment.app.FragmentManager;

import com.athome.R;
import com.athome.models.UserModel;
import com.athome.preferences.Preferences;
import com.athome.ui.activity_home.fragments.Fragment_Categories;
import com.athome.ui.activity_home.fragments.Fragment_Search;
import com.athome.ui.activity_home.fragments.Fragment_Home;
import com.athome.ui.activity_home.fragments.Fragment_Orders;
import com.athome.ui.activity_home.fragments.Fragment_Profile;


public class ActivityHomePresenter {
    private Context context;
    private FragmentManager fragmentManager;
    private HomeActivityView view;
    private Fragment_Home fragment_home;
    private Fragment_Categories fragment_categories;
    private Fragment_Search fragment_search;
    private Fragment_Orders fragment_orders;
    private Fragment_Profile fragment_profile;
    private Preferences preference;
    private UserModel userModel;
    private double lat=0.0,lng=0.0;
    private int selectedPos =0;

    public ActivityHomePresenter(Context context, HomeActivityView view, FragmentManager fragmentManager, double lat, double lng) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.view = view;
        preference = Preferences.getInstance();
        userModel = preference.getUserData(context);
        this.lat = lat;
        this.lng = lng;
        displayFragmentHome();
    }

    @SuppressLint("NonConstantResourceId")
    public void manageFragments(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case R.id.categories :
                displayFragmentCategories(selectedPos);
                break;
            case R.id.search :
                displayFragmentSearch();
                break;

            case R.id.orders :
                displayFragmentOrders();
                break;
            case R.id.profile :
                displayFragmentProfile();
                break;
            default:
                displayFragmentHome();
                break;
        }
    }
    private void displayFragmentHome(){
        if (fragment_home==null){
            fragment_home = Fragment_Home.newInstance(lat,lng);
        }

        if (fragment_categories !=null&& fragment_categories.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_categories).commit();
        }

        if (fragment_orders !=null&& fragment_orders.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_orders).commit();
        }

        if (fragment_search !=null&& fragment_search.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_search).commit();
        }
        if (fragment_profile!=null&&fragment_profile.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_profile).commit();
        }

        if (fragment_home.isAdded()){
            fragmentManager.beginTransaction().show(fragment_home).commit();
        }else {
            fragmentManager.beginTransaction().add(R.id.fragment_container,fragment_home,"fragment_home").commit();
        }
    }

    public void displayFragmentCategories(int selectedPos)
    {
        this.selectedPos = selectedPos;
        if (fragment_categories ==null){
            fragment_categories = Fragment_Categories.newInstance(lat,lng,selectedPos);

        }

        if (fragment_home!=null&&fragment_home.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_home).commit();
        }

        if (fragment_orders !=null&& fragment_orders.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_orders).commit();
        }

        if (fragment_search !=null&& fragment_search.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_search).commit();
        }
        if (fragment_profile!=null&&fragment_profile.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_profile).commit();
        }

        if (fragment_categories.isAdded()){
            fragmentManager.beginTransaction().show(fragment_categories).commit();
            fragment_categories.setSubCategory(selectedPos);
        }else {
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment_categories,"fragment_appointment").commit();
        }
        view.onCategoryFragmentSelected();
    }

    private void displayFragmentSearch(){
        if (fragment_search ==null){
            fragment_search = Fragment_Search.newInstance();
        }


        if (fragment_home!=null&&fragment_home.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_home).commit();
        }

        if (fragment_orders !=null&& fragment_orders.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_orders).commit();
        }

        if (fragment_categories !=null&& fragment_categories.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_categories).commit();
        }
        if (fragment_profile!=null&&fragment_profile.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_profile).commit();
        }


        if (fragment_search.isAdded()){
            fragmentManager.beginTransaction().show(fragment_search).commit();
        }else {
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment_search,"fragment_consulting").commit();
        }
    }

    private void displayFragmentOrders(){
        if (fragment_orders ==null){
            fragment_orders = Fragment_Orders.newInstance();
        }

        if (fragment_home!=null&&fragment_home.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_home).commit();
        }

        if (fragment_search !=null&& fragment_search.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_search).commit();
        }

        if (fragment_categories !=null&& fragment_categories.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_categories).commit();
        }
        if (fragment_profile!=null&&fragment_profile.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_profile).commit();
        }

        if (fragment_orders.isAdded()){
            fragmentManager.beginTransaction().show(fragment_orders).commit();
        }else {
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment_orders,"fragment_medicine").commit();
        }
    }

    private void displayFragmentProfile(){
        if (fragment_profile==null){
            fragment_profile = Fragment_Profile.newInstance();
        }

        if (fragment_home!=null&&fragment_home.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_home).commit();
        }

        if (fragment_search !=null&& fragment_search.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_search).commit();
        }

        if (fragment_categories !=null&& fragment_categories.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_categories).commit();
        }
        if (fragment_orders !=null&& fragment_orders.isAdded()){
            fragmentManager.beginTransaction().hide(fragment_orders).commit();
        }
        if (fragment_profile.isAdded()){
            fragmentManager.beginTransaction().show(fragment_profile).commit();
        }else {
            fragmentManager.beginTransaction().add(R.id.fragment_container,fragment_profile,"fragment_profile").commit();
        }
    }

    public void refreshFragmentHomeData(){
        if (fragment_home!=null&&fragment_home.isAdded()){
            fragment_home.refreshData();
        }
    }

    public void backPress(){
        if (fragment_home!=null&&fragment_home.isAdded()&&fragment_home.isVisible()){
            if (userModel==null){
                view.onNavigateToLoginActivity();
            }else {
                view.onFinished();
            }
        }else {
            displayFragmentHome();
            view.onHomeFragmentSelected();
        }
    }

    public void cart(){
        view.onNavigateToCartActivity();
    }


}
