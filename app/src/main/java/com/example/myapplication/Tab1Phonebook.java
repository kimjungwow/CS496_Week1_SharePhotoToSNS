package com.example.myapplication;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Tab1Phonebook extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback {
    private ListView contactsListView;
    private Tab1ContactViewAdapter adapter;
    private ArrayList<ContactModel> contactModelArrayList;
    private ArrayList<JSONObject> jsonArr = new ArrayList<JSONObject>();
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS};



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("전화번호부 fragment", "onCreateView()");
        View rootView = inflater.inflate(R.layout.tab1phonebook, container, false);
        contactsListView = rootView.findViewById(R.id.contactLV);
        adapter = new Tab1ContactViewAdapter(this.getContext(), contactModelArrayList);
        contactModelArrayList = new ArrayList<>();

        FloatingActionButton msgButton = (FloatingActionButton) rootView.findViewById(R.id.messageButton);
        msgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String defaultApplication = Settings.Secure.getString(getContext().getContentResolver(), "sms_default_application");
                PackageManager pm = getContext().getPackageManager();
                Intent intent = pm.getLaunchIntentForPackage(defaultApplication );
                if (intent != null) {
                    startActivity(intent);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.i("전화번호부 fragment", "onResume()");
        if(Permissioncheck()) {
            loadContacts(contactsListView);
        }
    }


    public int checkselfpermission(String permission) {
        return PermissionChecker.checkSelfPermission(getContext(), permission);


    }

    public boolean Permissioncheck() {
        if (checkselfpermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            loadContacts(contactsListView);
            return true;
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, 100);
            if (checkselfpermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                loadContacts(contactsListView);
                return true;
            } else {
                return false;
            }
        }
    }

    private void loadContacts(ListView LV) {

        StringBuilder builder = new StringBuilder();
        ContentResolver contentResolver = getActivity().getContentResolver();

        Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (phones.getCount() != contactModelArrayList.size()) {
            contactModelArrayList.removeAll(contactModelArrayList);
            while (phones.moveToNext()) {
                //default photo is in res/drawable folder
                Bitmap bp = BitmapFactory.decodeResource(getContext().getResources(),
                        R.drawable.default_contact_photo);

                //get name, number, and image uri from contact info
                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String image_uri = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

                //if image is not default
                if (image_uri != null){
                    try {
                        bp = MediaStore.Images.Media
                                .getBitmap(getContext().getContentResolver(),
                                        Uri.parse(image_uri));
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                ContactModel contactModel = new ContactModel();
                contactModel.setName(name);
                contactModel.setNumber(phoneNumber);
                contactModel.setIcon(bp);
                contactModelArrayList.add(contactModel);
                //Log.d("name>>", name + "  " + phoneNumber);

                //add contact information in form of JSONObject to jsonArr

                JSONObject obj = new JSONObject();
                try {
                    obj.put("name", name);
                    obj.put("number", phoneNumber);
                    obj.put("photo", bp);
                    jsonArr.add(obj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            phones.close();
        }
        adapter = new Tab1ContactViewAdapter(getActivity().getApplicationContext(), contactModelArrayList);
        LV.setAdapter(adapter);
        return;
    }

    @Override
    public void onPause() {
        super.onPause(); Log.i("전화번호부 fragment", "onPause()"); }
    @Override
    public void onStop() {
        super.onStop(); Log.i("전화번호부 fragment", "onStop()"); }
    @Override
    public void onDestroy() {
        super.onDestroy(); Log.i("전화번호부 fragment", "onDestroy()"); }
}

