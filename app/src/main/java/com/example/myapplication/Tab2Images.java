package com.example.myapplication;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.ImageAdapter;

import java.io.File;
import java.util.ArrayList;


public class Tab2Images extends Fragment {
    EditText editText;
    GridView gridview;
    final int REQ_CODE_SELECT_IMAGE = 100;

    private static int RESULT_LOAD_IMAGE = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.tab2images, container, false);
        gridview = (GridView) rootView.findViewById(R.id.gridView);
        int[] img = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h,
                R.drawable.i, R.drawable.j, R.drawable.k, R.drawable.l, R.drawable.m, R.drawable.n, R.drawable.o,
                R.drawable.p, R.drawable.q, R.drawable.r, R.drawable.s, R.drawable.t, R.drawable.u, R.drawable.v
        };
        ImageAdapter adapter = new ImageAdapter(getActivity().getApplicationContext(), R.layout.row, img);
        gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(), (position + 1) + " 번째 사진", Toast.LENGTH_SHORT).show();
            }
        });

//        @Override
//        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//            Toast.makeText(getActivity().getBaseContext(), "resultCode : "+resultCode,Toast.LENGTH_SHORT).show();
//            if(requestCode==REQ_CODE_SELECT_IMAGE){
//                if(resultCode==Activity.RESULT_OK) {
//                    try {
//                        Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
//                        ImageView image = (ImageView) getActivity().findViewById(R.id.)
//                    }
//                }
//            }
//
//
//            super.onActivityResult(requestCode, resultCode, data);
//            if (requestCode == RESULT_LOAD_IMAGE && resultCode == getActivity().RESULT_OK && null != data) {
//                Uri selectedImage = data.getData();
//                String[] filePathColumn = { MediaStore.Images.Media.DATA };
//                Cursor cursor = getActivity().getContentResolver().query(selectedImage,filePathColumn, null, null, null);
//                cursor.moveToFirst();
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                String picturePath = cursor.getString(columnIndex);
//                cursor.close();
//                ImageView imageView = (ImageView) getActivity().findViewById(R.id.imgView);
//                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//            }
//        }


        return rootView;
    }


}

