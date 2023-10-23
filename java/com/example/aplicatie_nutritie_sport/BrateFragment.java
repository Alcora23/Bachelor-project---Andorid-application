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

public class BrateFragment extends Fragment {

    RelativeLayout ex1_b, ex2_b, ex3_b, ex4_b, ex5_b, ex6_b;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_brate, container, false);

        ex1_b = root.findViewById(R.id.ex1_b);
        ex2_b = root.findViewById(R.id.ex2_b);
        ex3_b = root.findViewById(R.id.ex3_b);
        ex4_b = root.findViewById(R.id.ex4_b);
        ex5_b = root.findViewById(R.id.ex5_b);
        ex6_b = root.findViewById(R.id.ex6_b);

        ex1_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Nume_exercitiu","Triceps Kickback");
                editor.commit();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new CronometruFragment(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();

            }
        });

        ex2_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Nume_exercitiu","Flexii biceps");
                editor.commit();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new CronometruFragment(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        ex3_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Nume_exercitiu","Impins deasupra capului");
                editor.commit();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new CronometruFragment(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        ex4_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Nume_exercitiu","Flexii concetrate pentru biceps");
                editor.commit();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new CronometruFragment(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        ex5_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Nume_exercitiu","Ridicări ale brațelor în lateral");
                editor.commit();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new CronometruFragment(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        ex6_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Nume_exercitiu","Ramat vertical");
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