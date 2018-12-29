package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;


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

                Intent intent = new Intent(getActivity().getApplicationContext(),ZoomActivity.class);
                intent.putExtra("index", position);

                startActivity(intent);



//                Toast.makeText(getActivity().getApplicationContext(), (position + 1) + " 번째 사진", Toast.LENGTH_SHORT).show();
            }
        });



        return rootView;
    }


}

