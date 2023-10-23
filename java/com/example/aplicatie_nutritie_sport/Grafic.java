package com.example.aplicatie_nutritie_sport;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class Grafic extends Fragment {
    ImageView back;
    LineChart grafic;
    SharedPreferences sharedPreferences;
    List<Float> valori = new ArrayList<>();
    List<Float> valori_icm = new ArrayList<>();
    String kilograme_initiale;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_grafic, container, false);

        back = root.findViewById(R.id.back);
        grafic = root.findViewById(R.id.grafic);

        grafic.setDragEnabled(true);
        grafic.setScaleEnabled(true);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://aplicatie-nutritie-sport-default-rtdb.firebaseio.com/");

        sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("Username", "");
        String tip_grafic = sharedPreferences.getString("Grafic", "");


        if ("kg".equals(tip_grafic)) {
            DatabaseReference myRef = database.getReference("Utilizatori");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Iterable<DataSnapshot> utilizatori = snapshot.getChildren();
                    for (DataSnapshot ds : utilizatori) {
                        Utilizator u = ds.getValue(Utilizator.class);
                        if (u.getUsername().equals(username)) {
                            kilograme_initiale = u.getKm_initiale();

                            break;
                        }
                    }

                    DatabaseReference greutateRef = database.getReference("IstoricMasuratori");
                    DatabaseReference utilizatorRef = greutateRef.child("Utilizator-" + username);

                    utilizatorRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Iterable<DataSnapshot> istoric = dataSnapshot.getChildren();
                            for (DataSnapshot d : istoric) {
                                valori.add(Float.parseFloat(d.child("greutate").getValue().toString()));
                            }

                            ArrayList<Entry> yValues = new ArrayList<>();
                            yValues.add(new Entry(0, Float.parseFloat(kilograme_initiale)));
                            for (Float v : valori) {
                                yValues.add(new Entry(valori.indexOf(v) + 1, v));
                            }

                            LineDataSet series = new LineDataSet(yValues, "Evolutia greutatii");

                            series.setFillAlpha(110);
                            series.setColor(Color.RED);
                            series.setLineWidth(3f);
                            series.setValueTextSize(15f);

                            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                            dataSets.add(series);

                            LineData data = new LineData(dataSets);

                            grafic.setData(data);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    //este apelat cand se modifica datele dar renuntam la modificare
                }
            });

        } else {

            DatabaseReference greutateRef = database.getReference("IstoricMasuratori");
            DatabaseReference utilizatorRef = greutateRef.child("Utilizator-" + username);

            utilizatorRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> istoric = dataSnapshot.getChildren();
                    for (DataSnapshot d : istoric) {
                        if (d.child("icm").getValue() != null) {
                            valori_icm.add(Float.parseFloat(d.child("icm").getValue().toString()));
                        }
                    }

                    ArrayList<Entry> yValues = new ArrayList<>();
                    for (Float v : valori_icm) {
                        yValues.add(new Entry(valori_icm.indexOf(v), v));
                    }

                    LineDataSet series = new LineDataSet(yValues, "Evolutia Indicelui de Masa Corporala");

                    series.setFillAlpha(110);
                    series.setColor(Color.RED);
                    series.setLineWidth(3f);
                    series.setValueTextSize(15f);

                    ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                    dataSets.add(series);

                    LineData data = new LineData(dataSets);

                    grafic.setData(data);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new EvolutieFragment(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        return root;
    }
}