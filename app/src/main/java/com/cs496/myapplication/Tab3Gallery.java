package com.cs496.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import android.widget.GridView;

import java.util.ArrayList;

public class Tab3Gallery extends FragmentActivity {

    GridView gridview;
    ImageAdapter adapter;
    boolean readPermission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab2images);

        Intent intent = getIntent();
        readPermission = intent.getBooleanExtra("readPermission", false);

        Log.d("TAGGGG", "b4 gridview");
        gridview = (GridView) findViewById(R.id.gridView);


        if(readPermission) {
            Log.d("TAGGGG", "b4 loadPic");
            loadPictures();
            Log.d("TAGGGG", "after loadPic");
        }

    }

    protected void loadPictures(){
        ArrayList<String> imagePaths = getImagesPath(this);
        adapter = new ImageAdapter(getApplicationContext(), R.layout.row, imagePaths);
        gridview.setAdapter(adapter);
    }

    public int checkselfpermission(String permission) {
        return PermissionChecker.checkSelfPermission(getApplicationContext(), permission);
    }

    public boolean ReadPermissioncheck() {
        if (checkselfpermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            if (checkselfpermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        }
    }
    protected ArrayList<String> getImagesPath(Activity activity) {
        Uri uri;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        String PathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            PathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(PathOfImage);
        }
        return listOfAllImages;
    }
}
