package com.athome.ui.activity_address;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.athome.R;
import com.athome.databinding.ActivityAddressBinding;
import com.athome.databinding.ActivityWebViewBinding;
import com.athome.language.Language;

import io.paperdb.Paper;

public class AddressActivity extends AppCompatActivity {

    private ActivityAddressBinding binding;
    private String url ,url_lang="";
    private boolean islangSelected = false;
    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase,Paper.book().read("lang","ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_address);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        url_lang = intent.getStringExtra("url_lang");


    }

    private void initView() {
        String format = "<div style=\"text-align: right;\">\n" +
                "<b><font size=\"4\" color=\"#ff0000\">فرع القاهرة</font></b><br>\n" +
                "<b>العنوان: مصر الجديدة – النزهة – 22 سليمان عزمي الموازي لعمار بن ياسر – امام مجمع الاسكواش لنادي الشمس</b>\n" +
                "<br>\n" +
                "<b>&nbsp;02/26233631</b>\n" +
                "<br>\n" +
                "<iframe src=\"https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d6902.417612497985!2d31.359295!3d30.116838!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0%3A0x52123ceb65d97f9c!2zQXQgSG9tZSDYp9iqINmH2YjZhSDYp9mE2YLYp9mH2LHZhw!5e0!3m2!1sen!2seg!4v1593436308479!5m2!1sen!2seg\" width=\"50%\" height=\"50%\" frameborder=\"0\" style=\"border:0;\" allowfullscreen=\"\" aria-hidden=\"false\" tabindex=\"0\"></iframe>\n" +
                "<br><br>\n" +
                "<b><font size=\"4\" color=\"#ff0000\">فرع إسكندرية</font></b><br>\n" +
                "<b>العنوان: 242 عبدالسلام عارف لوران امام محطة ترام لوران</b>\n" +
                "<br>\n" +
                "<b>&nbsp; 03/5869053</b>\n" +
                "<br>\n" +
                "<iframe src=\"https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d6821.812102653744!2d29.972923999999995!3d31.251024!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x14f5c534bc09be03%3A0x722a9f4208738bc!2s242%20Abd%20El-Salam%20Aref%2C%20San%20Stifano%2C%20Qism%20El-Raml%2C%20Alexandria%20Governorate!5e0!3m2!1sen!2seg!4v1593437153090!5m2!1sen!2seg\" width=\"50%\" height=\"50%\" frameborder=\"0\" style=\"border:0;\" allowfullscreen=\"\" aria-hidden=\"false\" tabindex=\"0\"></iframe>\n" +
                "<br><br>\n" +
                "<font size=\"4\" color=\"#ff0000\"><b>فرع الزقازيق</b></font><br>\n" +
                "<b>العنوان: امتداد شارع طلبة عويضة امام بالينو صلاح سالم</b>\n" +
                "<br>\n" +
                "<b>&nbsp;055/2378783</b>\n" +
                "<br>\n" +
                "<iframe src=\"https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d13737.72133928845!2d31.484296000000004!3d30.593623!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0%3A0xa57b6838fd26e058!2zQXQgSG9tZSDYp9iqINmH2YjZhSDYp9mE2LLZgtin2LLZitmC!5e0!3m2!1sen!2seg!4v1593437180549!5m2!1sen!2seg\" width=\"50%\" height=\"50%\" frameborder=\"0\" style=\"border:0;\" allowfullscreen=\"\" aria-hidden=\"false\" tabindex=\"0\"></iframe>\n" +
                "<br><br>\n" +
                "<font size=\"4\" color=\"#ff0000\"><b>فرع بنها</b></font><br>\n" +
                "<b>العنوان: الاهرام 2 شارع سيف النصر متفرع من فريد ندا بجوار مطعم لانجليتو</b><br>\n" +
                "<b>&nbsp;0133246695</b>\n" +
                "<br>\n" +
                "<iframe src=\"https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d6877.53760219868!2d31.184638!3d30.470986!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0%3A0x9548769c0b16a799!2zQXQgSG9tZSDYp9iqINmH2YjZhSDYqNmG2YfYpw!5e0!3m2!1sen!2seg!4v1593437206135!5m2!1sen!2seg\" width=\"50%\" height=\"50%\" frameborder=\"0\" style=\"border:0;\" allowfullscreen=\"\" aria-hidden=\"false\" tabindex=\"0\"></iframe>\n" +
                "<br><br>\n" +
                "</div>";


        //binding.tvData
        binding.webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        binding.webView.getSettings().setAllowFileAccess(true);
        binding.webView.getSettings().setAllowFileAccessFromFileURLs(true);
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                binding.progBar.setVisibility(View.GONE);
            }

            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
                binding.progBar.setVisibility(View.GONE);

                if (!islangSelected){
                    binding.webView.loadUrl(url_lang);

                }
                islangSelected = true;

            }
        });
        binding.webView.loadUrl(url);

    }
}