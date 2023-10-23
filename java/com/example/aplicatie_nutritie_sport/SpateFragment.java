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


public class SpateFragment extends Fragment {

    RelativeLayout ex1_s, ex2_s, ex3_s, ex4_s, ex5_s, ex6_s, ex7_s;
    SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_spate, container, false);

        ex1_s = root.findViewById(R.id.ex1_s);
        ex2_s = root.findViewById(R.id.ex2_s);
        ex3_s = root.findViewById(R.id.ex3_s);
        ex4_s = root.findViewById(R.id.ex4_s);
        ex5_s = root.findViewById(R.id.ex5_s);
        ex6_s = root.findViewById(R.id.ex6_s);
        ex7_s = root.findViewById(R.id.ex7_s);

        ex1_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Nume_exercitiu","Flotările");
                editor.commit();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new CronometruFragment(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();

            }
        });

        ex2_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Nume_exercitiu", "Bent-Over Reverse Fly");
                editor.commit();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new CronometruFragment(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        ex3_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Nume_exercitiu", "Extensii spate umeri");
                editor.commit();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new CronometruFragment(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        ex4_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Nume_exercitiu", "Tracțiuni din aplecat");
                editor.commit();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new CronometruFragment(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        ex5_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Nume_exercitiu", "Fluturări laterale umeri");
                editor.commit();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new CronometruFragment(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        ex6_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Nume_exercitiu", "Întinderea brațelor lateral stând în picioare");
                editor.commit();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new CronometruFragment(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        ex7_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Nume_exercitiu", "Exercițiul Superman alternativ");
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