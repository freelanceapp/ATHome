package com.athome.ui.activity_product_details;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.athome.R;
import com.athome.adapters.DataAdapter;
import com.athome.adapters.SliderAdapter;
import com.athome.adapters.ViewPagerAdapter;
import com.athome.databinding.ActivityProductDetailsBinding;
import com.athome.databinding.ActivitySignUpBinding;
import com.athome.language.Language;
import com.athome.models.SignUpModel;
import com.athome.mvp.activity_product_details_mvp.ActivityProductDetailsPresenter;
import com.athome.mvp.activity_product_details_mvp.ActivityProductDetailsView;
import com.athome.mvp.activity_sign_up_mvp.ActivitySignUpPresenter;
import com.athome.mvp.activity_sign_up_mvp.ActivitySignUpView;
import com.athome.ui.activity_product_details.fragments.Fragment_Comments;
import com.athome.ui.activity_product_details.fragments.Fragment_Descreptions;
import com.athome.ui.activity_product_details.fragments.Fragment_Reviews;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.paperdb.Paper;

public class ProductDetailsActivity extends AppCompatActivity implements ActivityProductDetailsView {
    private ActivityProductDetailsBinding binding;

    private ActivityProductDetailsPresenter presenter;
    private String lang;
    private ViewPagerAdapter adapter;
    private List<Fragment> fragmentList;
    private List<String> titles;
    private SliderAdapter sliderAdapter;
    private int current_page = 0, NUM_PAGES;
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_details);
        change_slide_image();
        initView();

    }

    private void initView()
    {

        presenter = new ActivityProductDetailsPresenter(this,this);

        SliderAdapter sliderAdapter = new SliderAdapter(new ArrayList<>(), this);
        binding.tab.setupWithViewPager(binding.pager);
        binding.pager.setAdapter(sliderAdapter);


        titles = new ArrayList<>();

        fragmentList = new ArrayList<>();

        fragmentList.add(Fragment_Descreptions.newInstance());
        fragmentList.add(Fragment_Comments.newInstance());
        fragmentList.add(Fragment_Reviews.newInstance());

        titles.add(getString(R.string.descriptions));
        titles.add(getString(R.string.comments));
        titles.add(getString(R.string.reviews));

        binding.tabLayout.setupWithViewPager(binding.pager2);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragments_Titles(fragmentList, titles);
        binding.pager2.setAdapter(adapter);

        binding.pager2.setAdapter(adapter);
        binding.pager2.setOffscreenPageLimit(fragmentList.size());
        binding.pager2.setCurrentItem(0,false);
        binding.tvOldprice.setPaintFlags(binding.tvOldprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


    }
    private void change_slide_image() {
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (current_page == NUM_PAGES) {
                    current_page = 0;
                }
                binding.pager.setCurrentItem(current_page++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);
    }


}