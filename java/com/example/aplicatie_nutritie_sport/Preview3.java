package com.example.aplicatie_nutritie_sport;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Preview3 extends Fragment {

    FloatingActionButton btn_next;
    TextView tv_skip;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_preview3, container, false);

        btn_next = root.findViewById(R.id.icon_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
            }
        });


        return root;
    }
}