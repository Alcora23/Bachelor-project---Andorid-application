package com.example.aplicatie_nutritie_sport;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FesieriFragment extends Fragment {

    RelativeLayout ex1_f, ex2_f, ex3_f, ex4_f, ex5_f;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.fragment_fesieri, container, false);

        ex1_f = root.findViewById(R.id.ex1_f);
        ex2_f = root.findViewById(R.id.ex2_f);
        ex3_f = root.findViewById(R.id.ex3_f);
        ex4_f = root.findViewById(R.id.ex4_f);
        ex5_f = root.findViewById(R.id.ex5_f);

        ex1_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Nume_exercitiu","Genuflexiunile");
                editor.commit();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new CronometruFragment(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();

            }
        });

        ex2_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Nume_exercitiu","Balans Spinal");
                editor.commit();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new CronometruFragment(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        ex3_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Nume_exercitiu","Genuflexiuni Plie");
                editor.commit();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new CronometruFragment(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        ex4_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Nume_exercitiu","Podul pentru întărirea mușchilor fesieri");
                editor.commit();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new CronometruFragment(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        ex5_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Nume_exercitiu","Scoica (Supinated clamshell)");
                editor.commit();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new CronometruFragment(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        return  root;

    }
}