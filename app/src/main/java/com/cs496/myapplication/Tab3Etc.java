package com.cs496.myapplication;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.FaceDetector;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.content.PermissionChecker;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.kakao.kakaotalk.callback.TalkResponseCallback;
import com.kakao.network.ErrorResult;
import com.kakao.util.helper.log.Logger;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


import net.alhazmy13.imagefilter.ImageFilter;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class Tab3Etc extends Fragment {
    private View cameraButton;
    private View galleryButton;
    private View shareButton;
    private View saveButton;
    private View shareok;
    private RadioGroup snsradio;

    private View back;
    private int groupa, groupb;



    GridView gridview;
    private ImageView newPicture;
    private Bitmap mainImage;
    private Bitmap shareImage;
    private RecyclerView recyclerView;

    int REQUEST_IMAGE_CAPTURE = 1;
    int REQUEST_GALLERY = 2;
    int REQUEST_SNS = 3;
    String type = "image/*";
    String filename = "/myPhoto.jpg";
    String mediaPath = Environment.getExternalStorageDirectory() + filename;


    boolean writePermission;
    boolean readPermission;
    ImageAdapter adapter;

    CallbackManager callbackManager;
    ShareDialog shareDialog;
    ShareContent shareContent;

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

        saveButton = rootView.findViewById(R.id.newPicSaveButton);
        newPicture = rootView.findViewById(R.id.newImage);
        recyclerView = rootView.findViewById(R.id.newPicFilterThumbnails);
        shareok = rootView.findViewById(R.id.shareok);
        snsradio = rootView.findViewById(R.id.snsradiogroup);
        back = rootView.findViewById(R.id.back);


        mainImage = BitmapFactory.decodeResource(getResources(), R.drawable.default_empty_image);


        shareButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                change_visibility(1);
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
                shareImage = mainImage;



            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change_visibility(0);
            }
        });

        shareok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity().getApplicationContext(),"da",1).show();

                    int radioButtonID = snsradio.getCheckedRadioButtonId();
                    View radioButton = snsradio.findViewById(radioButtonID);
                    int idx = snsradio.indexOfChild(radioButton);

                    switch (idx) {
                        case 0:

                            /* FACEBOOK */
                            /* FACEBOOK */

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

                            Bitmap tempimage = ((BitmapDrawable) newPicture.getDrawable()).getBitmap();
                            Uri bitmapuri = getImageUri(getActivity().getApplicationContext(), tempimage);
//
                            //We will fetch photo from link and conver to bitmap
                            Picasso.with(getActivity().getBaseContext())
//                        .load("https://en.wikipedia.org/wiki/Batman#/media/File:Batman_DC_Comics.png")
                                    .load(bitmapuri)
                                    .into(target);

                            /* FACEBOOK */
                            /* FACEBOOK */
                            break;
                        case 1:
                            /* INSTAGRAM */
                            /* INSTAGRAM */

                            Intent instagramshare = new Intent(Intent.ACTION_SEND);
                            instagramshare.setType("image/*");
//                            bitmapuri = getImageUri(getActivity().getApplicationContext(), shareImage);
                            Bitmap tempimage2 = ((BitmapDrawable) newPicture.getDrawable()).getBitmap();
                            bitmapuri = getImageUri(getActivity().getApplicationContext(), tempimage2);

                            try {
                                instagramshare.putExtra(Intent.EXTRA_STREAM, bitmapuri);

                                instagramshare.putExtra(Intent.EXTRA_TEXT, "텍스트는 지원하지 않음!");
                                instagramshare.setPackage("com.instagram.android");
                                startActivity(instagramshare);
                            } catch (ActivityNotFoundException e) {
                                Toast.makeText(getActivity().getApplicationContext(), "인스타그램이 설치되어 있지 않습니다.", Toast.LENGTH_SHORT).show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            /* INSTAGRAM */
                            /* INSTAGRAM */
                            break;
                        case 2:

                            /* KAKAOTALK*/
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("image/png");
//                            bitmapuri = getImageUri(getActivity().getApplicationContext(), shareImage);
                            Bitmap tempimage3 = ((BitmapDrawable) newPicture.getDrawable()).getBitmap();
                            bitmapuri = getImageUri(getActivity().getApplicationContext(), tempimage3);

                            intent.putExtra(Intent.EXTRA_STREAM, bitmapuri);
                            intent.setPackage("com.kakao.talk");
                            startActivity(intent);
                            break;
                        default:
                            break;


                    }


                    change_visibility(0);
//                    cameraButton.setVisibility(View.VISIBLE);
//                    galleryButton.setVisibility(View.VISIBLE);
//                    shareButton.setVisibility(View.VISIBLE);
//
//                    snsradio.setVisibility(View.GONE);
//
//                    saveButton.setVisibility(View.VISIBLE);
//                    shareok.setVisibility(View.GONE);
//                    back.setVisibility(View.GONE);




            }
        });


        return rootView;
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    public void onResume() {
        super.onResume();
        LoadFilterThumbnails();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {


            if (mainImage != null) {
                mainImage.recycle();
            }
            Bundle extras = data.getExtras();
            mainImage = (Bitmap) extras.get("data");
            shareImage = mainImage;
            newPicture.setImageBitmap(mainImage);
            LoadFilterThumbnails();
        } else if (requestCode == REQUEST_GALLERY && resultCode == getActivity().RESULT_OK) {
            try {
                InputStream is = getActivity().getContentResolver().openInputStream(data.getData());
                mainImage = BitmapFactory.decodeStream(is);

                is.close();
                if (mainImage != null) {
                    shareImage = mainImage;
                    newPicture.setImageBitmap(shareImage);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }



    }

    private void LoadFilterThumbnails() {
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
        for (int index = 1; index < filterTypes.length; index++) {
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

    private void LoadPicture(int index) {
//        if (index == 0) {newPicture.setImageBitmap(mainImage);
//
//        shareImage=mainImage;
//        }
//        else

        newPicture.setImageBitmap(ApplyFilterByIndex(mainImage, index));
        shareImage = ApplyFilterByIndex(mainImage, index);
        return;
    }

    private Bitmap ApplyFilterByIndex(Bitmap bitmap, int value) {
        switch (value) {
            case 0:

                shareImage = mainImage;
                return shareImage;
            case 1:
                shareImage = ImageFilter.applyFilter(bitmap, ImageFilter.Filter.GRAY);
                return shareImage;
            case 2:
                shareImage = ImageFilter.applyFilter(bitmap, ImageFilter.Filter.AVERAGE_BLUR, 9);
                return shareImage;
            case 3:
                shareImage = ImageFilter.applyFilter(bitmap, ImageFilter.Filter.OIL, 10);
                return shareImage;
            case 4:
                shareImage = ImageFilter.applyFilter(bitmap, ImageFilter.Filter.NEON, 200, 50, 100);
                return shareImage;
            case 5:
                shareImage = ImageFilter.applyFilter(bitmap, ImageFilter.Filter.BLOCK);
                return shareImage;
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

    public void change_visibility(int input) {
        if (input==0) {
            groupa = View.VISIBLE;
            groupb = View.GONE;
            cameraButton.setVisibility(groupa);
            galleryButton.setVisibility(groupa);
            shareButton.setVisibility(groupa);
            saveButton.setVisibility(groupa);
            snsradio.setVisibility(groupb);
            shareok.setVisibility(groupb);
            back.setVisibility(groupb);

        } else {
            groupa = View.GONE;
            groupb = View.VISIBLE;
            cameraButton.setVisibility(groupa);
            galleryButton.setVisibility(groupa);
            shareButton.setVisibility(groupa);
            saveButton.setVisibility(groupa);
            snsradio.setVisibility(groupb);
            shareok.setVisibility(groupb);
            back.setVisibility(groupb);
        }
    }

}