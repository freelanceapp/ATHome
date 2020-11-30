package com.athome.models;

import android.content.Context;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.athome.BR;
import com.athome.R;

import java.io.Serializable;

public class SignUpModel extends BaseObservable implements Serializable {
    private String name;

    private String phone_code;
    private String phone;
private String email;
    private String password;
    public ObservableField<String> error_phone = new ObservableField<>();
    public ObservableField<String> error_password = new ObservableField<>();
    public ObservableField<String> error_name = new ObservableField<>();


    public SignUpModel() {
        this.phone_code = "";
        this.phone = "";
       this. password = "";
        this.name = "";
this.email="";
    }

    public boolean isDataValid(Context context){
        if (!name.isEmpty()&&!phone.isEmpty()&&!password.isEmpty()&&password.length()>=6
        ){
         return true;
        }
            else {
                if (name.isEmpty()){
                error_name.set(context.getString(
                        R.string.field_req));
            }else {
                error_name.set(null);
            }

            if (phone.isEmpty()){
                error_phone.set(context.getString(R.string.field_req));

            }else {
                error_phone.set(null);

            }

            if (password.isEmpty()){
                error_password.set(context.getString(R.string.field_req));
            }else if (password.length()<6){
                error_password.set(context.getString(R.string.password_short));
            }else {
                error_password.set(null);
            }

            return false;
        }}

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }




    public String getPhone_code() {
        return phone_code;
    }

    public void setPhone_code(String phone_code) {
        this.phone_code = phone_code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
