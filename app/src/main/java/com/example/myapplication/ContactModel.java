package com.example.myapplication;

import android.graphics.drawable.Drawable;

public class ContactModel{
    private String name, number;
    private Drawable iconDrawable ;

    public Drawable getIcon() {
        return this.iconDrawable ;
    }

    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}