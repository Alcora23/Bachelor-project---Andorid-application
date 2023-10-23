package com.example.aplicatie_nutritie_sport;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.aplicatie_nutritie_sport.CaloriiFragment.titlu_1;

public class AbdomenFragment extends Fragment {

    RelativeLayout ex1_ab, ex2_ab, ex3_ab, ex4_ab, ex5_ab, ex6_ab;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_abdomen, container, false);


        ex1_ab = root.findViewById(R.id.ex1_ab);
        ex2_ab = root.findViewById(R.id.ex2_ab);
        ex3_ab = root.findViewById(R.id.ex3_ab);
        ex4_ab = root.findViewById(R.id.ex4_ab);
        ex5_ab = root.findViewById(R.id.ex5_ab);
        ex6_ab = root.findViewById(R.id.ex6_ab);

        ex1_ab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Nume_exercitiu","Plank");
                editor.commit();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new CronometruFragment(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        ex2_ab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Nume_exercitiu","Crunch bicicletă");
                editor.commit();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new CronometruFragment(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        ex3_ab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Nume_exercitiu","Ridicări de picioare din întins");
                editor.commit();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new CronometruFragment(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        ex4_ab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Nume_exercitiu","Mountain climber");
                editor.commit();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new CronometruFragment(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        ex5_ab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Nume_exercitiu","Sarituri");
                editor.commit();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new CronometruFragment(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        ex6_ab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Nume_exercitiu","Ghemuit pe spate");
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