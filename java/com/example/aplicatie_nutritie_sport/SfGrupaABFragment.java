package com.example.aplicatie_nutritie_sport;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SfGrupaABFragment extends Fragment {

    String mesaj;
    TextView titlu;
    ImageView back;

    public SfGrupaABFragment(String mesaj) {
        this.mesaj = mesaj;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_sf_grupa_a_b, container, false);

        titlu = root.findViewById(R.id.titlu);
        back =root.findViewById(R.id.back);
        titlu.setText(mesaj);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new SfaturiNutritionaleFragment(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        return root;

    }
}