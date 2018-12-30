package com.example.myapplication;

import android.graphics.Bitmap;

public class FilteredThumbnail {
    private Bitmap imgBP;
    private String filterType;
    public Bitmap getImgBP(){return imgBP;}
    public void setImgBP(Bitmap newBP){this.imgBP = newBP;}
    public String getFilterType(){return filterType;}
    public void setFilterType(String newType){this.filterType = newType;}
}
