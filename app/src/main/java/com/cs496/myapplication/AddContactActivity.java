package com.cs496.myapplication;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;

public class AddContactActivity extends FragmentActivity {
    private EditText tvName;
    private EditText tvNumber;
    private View closeBtn;
    private View cancelBtn;
    private View addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcontact);

        tvName = findViewById(R.id.nameInput);
        tvNumber = findViewById(R.id.numberInput);
        closeBtn = findViewById(R.id.popupCloseBtn);
        cancelBtn = findViewById(R.id.popupCancelBtn);
        addBtn = findViewById(R.id.popupAddBtn);


        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closePopUp();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closePopUp();
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = tvName.getText().toString();
                String number = tvNumber.getText().toString();
                if (name != "" && number != ""){
                    //Intent intent;
                }
            }
        });
    }

    public void closePopUp(){
        onBackPressed();
    }
}
