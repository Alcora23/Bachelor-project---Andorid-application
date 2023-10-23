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

public class CardioFragment extends Fragment {

    RelativeLayout ex1_c, ex2_c, ex3_c, ex4_c, ex5_c;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_cardio, container, false);

        ex1_c = root.findViewById(R.id.ex1_c);
        ex2_c = root.findViewById(R.id.ex2_c);
        ex3_c = root.findViewById(R.id.ex3_c);
        ex4_c = root.findViewById(R.id.ex4_c);
        ex5_c = root.findViewById(R.id.ex5_c);

        ex1_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Nume_exercitiu","Genuflexiuni cu săritură ghemuite");
                editor.commit();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new CronometruFragment(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();

            }
        });

        ex2_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Nume_exercitiu","Burpees");
                editor.commit();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new CronometruFragment(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        ex3_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Nume_exercitiu","Fandări laterale cardio");
                editor.commit();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new CronometruFragment(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        ex4_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Nume_exercitiu","Jumping Jacks");
                editor.commit();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new CronometruFragment(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        ex5_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Nume_exercitiu","Genuflexiuni cu săritură");
                editor.commit();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new CronometruFragment(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });


        return root;
    }
}