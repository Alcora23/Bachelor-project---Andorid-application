package com.example.aplicatie_nutritie_sport;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SfaturiNutritionaleFragment extends Fragment {
    CardView dupa_sala, inainte_sala, grupa_ab, grupa_b, grupa_0, grupa_a;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_sfaturi_nutritionale, container, false);

        dupa_sala = root.findViewById(R.id.dupa_sala);
        inainte_sala = root.findViewById(R.id.inainte_sala);
        grupa_ab = root.findViewById(R.id.grupa_AB);
        grupa_a = root.findViewById(R.id.grupa_A);
        grupa_b = root.findViewById(R.id.grupa_B);
        grupa_0 = root.findViewById(R.id.grupa_0);

        dupa_sala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new SfDupaSalaFragment("Stiati ca dupa antrenament..."), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        inainte_sala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new SfInainteSalaFragment("Stiati ca inainte de antrenament..."), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        grupa_ab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new SfGrupaABFragment("Stiati ca persoanele cu grupa AB..."), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        grupa_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new SfGrupaAFragment("Stiati ca persoanele cu grupa A..."), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        grupa_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new SfGrupa0Fragment("Stiati ca persoanele cu grupa 0..."), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        grupa_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new SfGrupaBFragment("Stiati ca persoanele cu grupa B..."), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        return root;
    }
}