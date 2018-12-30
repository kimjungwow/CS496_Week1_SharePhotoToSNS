package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Tab3Etc extends Fragment {
    private View cameraButton;
    private View galleryButton;
    private View shareButton;
    private View saveButton;
    GridView gridview;
    private ImageView newPicture;
    private Bitmap mainImage;
    boolean writePermission;
    boolean readPermission;
    ImageAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3etc, container, false);
        cameraButton = rootView.findViewById(R.id.cameraButton);
        galleryButton = rootView.findViewById(R.id.galleryButton);
        shareButton = rootView.findViewById(R.id.shareButton);
        saveButton = rootView.findViewById(R.id.saveButton);
        newPicture = rootView.findViewById(R.id.newImage);


        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ReadPermissioncheck()) {

                    readPermission=ReadPermissioncheck();
                    Intent intent = new Intent(getActivity().getApplicationContext(), Tab3Gallery.class);
                    intent.putExtra("readPermission", readPermission);


                    startActivity(intent);
                }

            }
        });
        return rootView;
    }


    public int checkselfpermission(String permission) {
        return PermissionChecker.checkSelfPermission(getContext(), permission);
    }

    public boolean ReadPermissioncheck() {
        if (checkselfpermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            if (checkselfpermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        }
    }

}