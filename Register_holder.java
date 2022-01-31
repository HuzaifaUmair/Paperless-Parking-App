package com.example.smartparking;

import android.widget.EditText;

import androidx.annotation.NonNull;

public class Register_holder {

    String name,Email,Phone,Password;

    public Register_holder(String name, String email, String phone, String password) {
        this.name = name;
        Email = email;
        Phone = phone;
        Password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
