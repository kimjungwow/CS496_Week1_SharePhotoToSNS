package com.example.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


public class Tab3Etc extends Fragment {
    private View cameraButton;
    private View galleryButton;
    private View shareButton;
    private View saveButton;
    private ImageView newPicture;
    private Bitmap mainImage;

    int REQUEST_IMAGE_CAPTURE = 1;
    int REQUEST_GALLERY = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3etc, container, false);
        cameraButton = rootView.findViewById(R.id.cameraButton);
        galleryButton = rootView.findViewById(R.id.galleryButton);
        shareButton = rootView.findViewById(R.id.shareButton);
        saveButton = rootView.findViewById(R.id.saveButton);
        newPicture = rootView.findViewById(R.id.newImage);

        //open camera app
        cameraButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check if phone has camera
                if (getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
                    //Check for camera permission
                    //open camera app
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }
            }
        });
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            if (mainImage != null){
                mainImage.recycle();
            }
            Bundle extras = data.getExtras();
            mainImage = (Bitmap) extras.get("data");
            newPicture.setImageBitmap(mainImage);
        }
        else if (requestCode == REQUEST_GALLERY && resultCode == getActivity().RESULT_OK) {
            //갤러리 이미지 가져오기
        }
    }
}