package com.example.myapplication;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.PermissionChecker;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Tab1Phonebook extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback {
    private TextView listContacts;
    private ArrayList<JSONObject> jsonArr = new ArrayList<JSONObject>();
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS};



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Permissioncheck();
        View rootView = inflater.inflate(R.layout.tab1phonebook, container, false);

        listContacts = (TextView) rootView.findViewById(R.id.listContacts2);

//        onRequestPermissionsResult(100, REQUIRED_PERMISSIONS, );
        Permissioncheck();
//        if (checkselfpermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED){ loadContacts(listContacts);}
        return rootView;
    }


    public int checkselfpermission(String permission) {
        return PermissionChecker.checkSelfPermission(getContext(), permission);

//        return super.checkSelfPermission(permission);

    }

    public boolean Permissioncheck() {
        if (checkselfpermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            loadContacts(listContacts);
            return true;
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, 100);
            if (checkselfpermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                loadContacts(listContacts);
                return true;
            } else {
                return false;
            }
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grandResults) {
//
//        if ( requestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {
//
//
//
//            boolean check_result = true;
//
//
//
//
//            for (int result : grandResults) {
//                if (result != PackageManager.PERMISSION_GRANTED) {
//                    check_result = false;
//                    break;
//                }
//            }
//
//
//
//            if ( check_result ) {
//
//
//
//                loadContacts(listContacts);
//            }
//            else {
//
//
//                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[0])
//                        || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[1])) {
//
//
//
//                    Toast.makeText(getContext(), "The permission was denied. Please run the app again to allow permission.", Toast.LENGTH_LONG).show();
//
//
//                }else {
//
//
//
//                    Toast.makeText(getContext(), "The permission was denied. You must allow permutations in Settings (app information).", Toast.LENGTH_LONG).show();
//                }
//            }
//
//        }
//
//
//    }

    private void loadContacts(TextView LV) {
        Toast.makeText(getContext(), "hhhhhhhhh", Toast.LENGTH_LONG).show();
        StringBuilder builder = new StringBuilder();
        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        ArrayList<ContactsListviewItem> contactsData = new ArrayList<ContactsListviewItem>();

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));

                if (hasPhoneNumber > 0) {
                    Cursor cursor2 = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (cursor2.moveToNext()) {
                        String phoneNumber = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        builder.append("Contact: ").append(name).append(", Phone Number : ").append(phoneNumber).append("\n\n");
                        //add contact information in form of JSONObject to jsonArr
                        JSONObject obj = new JSONObject();
                        try{
                            obj.put("name", name);
                            obj.put("number", phoneNumber);
                            jsonArr.add(obj);
                        } catch(JSONException e){
                            e.printStackTrace();
                        }

                    }


                    cursor2.close();
                }
            }
        }
        cursor.close();

        LV.setText(builder.toString());
    }

}