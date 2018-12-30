package com.cs496.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.FaceDetector;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.content.PermissionChecker;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import net.alhazmy13.imagefilter.ImageFilter;

import java.io.InputStream;
import java.util.ArrayList;

public class Tab3Etc extends Fragment {
    private View cameraButton;
    private View galleryButton;
    private View shareButton;
    private View saveButton;
    GridView gridview;
    private ImageView newPicture;
    private Bitmap mainImage;
    private RecyclerView recyclerView;

    int REQUEST_IMAGE_CAPTURE = 1;
    int REQUEST_GALLERY = 2;


    boolean writePermission;
    boolean readPermission;
    ImageAdapter adapter;

    CallbackManager callbackManager;
    ShareDialog shareDialog;


    Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            SharePhoto sharePhoto = new SharePhoto.Builder()
                    .setBitmap(bitmap)
                    .build();


            if (ShareDialog.canShow(SharePhotoContent.class)) {
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(sharePhoto)
                        .build();
                shareDialog.show(content);

            }
        }
        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }
        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.tab3etc, container, false);

        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(getActivity());





        cameraButton = rootView.findViewById(R.id.cameraButton);
        galleryButton = rootView.findViewById(R.id.galleryButton);
        shareButton = rootView.findViewById(R.id.shareButton);
        saveButton = rootView.findViewById(R.id.saveButton);
        newPicture = rootView.findViewById(R.id.newImage);
        recyclerView = rootView.findViewById(R.id.newPicFilterThumbnails);
        mainImage =  BitmapFactory.decodeResource(getResources(), R.drawable.default_empty_image);


        shareButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Create callback
                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(getActivity().getApplicationContext(), "Share Successful!", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getActivity().getApplicationContext(), "Share cancel!", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(getActivity().getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });



                //We will fetch photo from link and conver to bitmap
                Picasso.with(getActivity().getBaseContext())
                        .load("https://en.wikipedia.org/wiki/Batman#/media/File:Batman_DC_Comics.png")
                        .into(target);

            }
        });

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

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GALLERY);



            }
        });
        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        LoadFilterThumbnails();
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
            LoadFilterThumbnails();
        }
        else if (requestCode == REQUEST_GALLERY && resultCode == getActivity().RESULT_OK) {
            try {
                InputStream is = getActivity().getContentResolver().openInputStream(data.getData());
                mainImage = BitmapFactory.decodeStream(is);
                is.close();
                if (mainImage != null) {
                    newPicture.setImageBitmap(mainImage);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //갤러리 이미지 가져오기
        }


    }

    private void LoadFilterThumbnails(){
        // array for filter type names and thumbnails
        String[] filterTypes = {"ORIGINAL", "GRAY", "BLUR", "OIL", "NEON", "BLOCK"};
        ArrayList<FilteredThumbnail> thumbnails = new ArrayList<>();

        //resize mainImage to smaller thumbnails
        int desiredWidth = 100;
        int desiredHeight = mainImage.getHeight() * 100 / mainImage.getWidth();
        Bitmap thumbImage = Bitmap.createScaledBitmap(mainImage, desiredWidth, desiredHeight, false);

        //make thumbnails and add it to thumnails ArrayList
        FilteredThumbnail original = new FilteredThumbnail();
        original.setFilterType("ORIGINAL");
        original.setImgBP(thumbImage);
        original.setFilterTypeIndex(0);
        thumbnails.add(original);
        for (int index = 1; index < filterTypes.length; index++){
            //filter images by type
            Bitmap filteredImg = ApplyFilterByIndex(thumbImage, index);
            //and save images and corresponding filters to thumbnails array
            FilteredThumbnail thumbnail = new FilteredThumbnail();
            thumbnail.setFilterTypeIndex(index);
            thumbnail.setFilterType(filterTypes[index]);
            thumbnail.setImgBP(filteredImg);
            thumbnails.add(thumbnail);
        }

        // Create an adapter and set onClickListener
        FilterThumbnailAdapter adapter = new FilterThumbnailAdapter(thumbnails, new FilterThumbnailAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FilteredThumbnail item) {
                LoadPicture(item.getFilterTypeIndex());
            }
        });

        //use adapter to put images in recyclerview
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void LoadPicture(int index){
        if (index == 0) newPicture.setImageBitmap(mainImage);
        else newPicture.setImageBitmap(ApplyFilterByIndex(mainImage, index));
        return;
    }

    private Bitmap ApplyFilterByIndex(Bitmap bitmap, int value){
        switch (value) {
            case 1:
                return ImageFilter.applyFilter(bitmap, ImageFilter.Filter.GRAY);
            case 2:
                return ImageFilter.applyFilter(bitmap, ImageFilter.Filter.AVERAGE_BLUR, 9);
            case 3:
                return ImageFilter.applyFilter(bitmap, ImageFilter.Filter.OIL,10);
            case 4:
                return ImageFilter.applyFilter(bitmap, ImageFilter.Filter.NEON,200, 50, 100);
            case 5:
                return ImageFilter.applyFilter(bitmap, ImageFilter.Filter.BLOCK);
            default:
                return bitmap;
        }
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