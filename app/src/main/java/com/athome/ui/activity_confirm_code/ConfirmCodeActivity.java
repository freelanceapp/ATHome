package com.athome.ui.activity_confirm_code;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.athome.R;
import com.athome.databinding.ActivityConfirmCodeBinding;
import com.athome.language.Language;
import com.athome.models.UserModel;
import com.athome.mvp.activity_confirm_code_mvp.ActivityConfirmCodePresenter;
import com.athome.mvp.activity_confirm_code_mvp.ActivityConfirmCodeView;
import com.athome.share.Common;
import com.athome.ui.activity_login.LoginActivity;
import com.athome.ui.activity_sign_up.SignUpActivity;

import java.util.Locale;

import io.paperdb.Paper;

public class ConfirmCodeActivity extends AppCompatActivity implements ActivityConfirmCodeView {
    private ActivityConfirmCodeBinding binding;
    private String phone_code = "";
    private String phone = "";
    private boolean canSend = false;
    private ActivityConfirmCodePresenter presenter;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirm_code);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            phone_code = intent.getStringExtra("phone_code");
            phone = intent.getStringExtra("phone");

        }
    }

    private void initView() {

        String mPhone = phone_code + phone;
        binding.setPhone(mPhone);
        presenter = new ActivityConfirmCodePresenter(this, this, phone, phone_code);



        binding.btnConfirm.setOnClickListener(v -> {
            String sms = binding.edtCode.getText().toString().trim();
            if (!sms.isEmpty()) {
                presenter.checkValidCode(sms);
            } else {
                binding.edtCode.setError(getString(R.string.inv_code));
            }
        });
        binding.btnResendCode.setOnClickListener(view -> {
            if (canSend) {
                canSend = false;
                presenter.resendCode();
            }
        });
    }


    @Override
    public void onSuccessCode() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onCounterStarted(String time) {
        binding.btnResendCode.setText(String.format(Locale.ENGLISH, "%s %s", getString(R.string.resend2), time));
        binding.btnResendCode.setTextColor(ContextCompat.getColor(ConfirmCodeActivity.this, R.color.colorPrimary));
        binding.btnResendCode.setBackgroundResource(R.color.transparent);
    }

    @Override
    public void onCounterFinished()
    {
        canSend = true;
        binding.btnResendCode.setText(R.string.resend);
        binding.btnResendCode.setTextColor(ContextCompat.getColor(ConfirmCodeActivity.this, R.color.gray4));
        binding.btnResendCode.setBackgroundResource(R.color.white);
    }

    @Override
    public void onCodeFailed(String msg)
    {
        Common.CreateDialogAlert(this, msg);

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.stopTimer();
    }
}