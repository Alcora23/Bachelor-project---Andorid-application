package com.example.aplicatie_nutritie_sport;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;


public class EvolutieFragment extends Fragment {

    RelativeLayout kg, icm;
    TextView grafic_kg, grafic_icm, icm_rez, interpretare;

    SharedPreferences sharedPreferences;

    float nr_kg;
    float inaltime_bd;
    float rezultat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_evolutie, container, false);

        kg = root.findViewById(R.id.relativ_greutate);
        icm = root.findViewById(R.id.relativ_icm);
        grafic_kg = root.findViewById(R.id.grafic_kg);
        grafic_icm = root.findViewById(R.id.grafic_icm);

        sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("Username", "");

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://aplicatie-nutritie-sport-default-rtdb.firebaseio.com/");
        DatabaseReference databaseReference = database.getReference("Utilizatori");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> utilizatori = dataSnapshot.getChildren();
                for (DataSnapshot ds : utilizatori) {
                    Utilizator utilizator_baza = ds.getValue(Utilizator.class);
                    if ((utilizator_baza.getUsername().equals(username))) {
                        inaltime_bd = Float.parseFloat(utilizator_baza.getInaltime());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                View view = getLayoutInflater().inflate(R.layout.alert_dialog_greutate, null);

                builder.setView(view);
                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setLayout(700, 800); //Controlling width and height.
                alertDialog.show();

                Button btn_ok = view.findViewById(R.id.btn);
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Date data = new Date();
                        String s = DateFormat.format("MMMM d, yyyy ", data.getTime()).toString();

                        EditText ed_greutate = view.findViewById(R.id.ed_kg);
                        TextInputLayout inputLayout = view.findViewById(R.id.tv_kg);
                        String greutate = ed_greutate.getText().toString().trim();

                        if (greutate.isEmpty()) {
                            inputLayout.setError("Campul trebuie completat!");
                        } else {
                            nr_kg = Float.parseFloat(greutate);
                        }

                        if (nr_kg < 0 || nr_kg == 0) {
                            inputLayout.setError("Numar invalid!");
                        } else {
                            inputLayout.setError(null);
                            inputLayout.setErrorEnabled(false);

                            // inserez in baza
                            FirebaseDatabase database = FirebaseDatabase.getInstance("https://aplicatie-nutritie-sport-default-rtdb.firebaseio.com/");
                            DatabaseReference myRef = database.getReference("IstoricMasuratori");

                            DatabaseReference utilizRef = myRef.child("Utilizator-" + username);
                            DatabaseReference masuratoareRef = utilizRef.child("Data-" + s);
                            DatabaseReference referinta = masuratoareRef.child("greutate");
                            referinta.setValue(greutate);

                            alertDialog.cancel();
                        }
                    }
                });
            }
        });

        icm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                View view = getLayoutInflater().inflate(R.layout.alert_dialog_icm, null);

                builder.setView(view);
                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setLayout(300, 800); //Controlling width and height.
                alertDialog.show();

                Button btn_ok = view.findViewById(R.id.btn);
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Date data = new Date();
                        String s = DateFormat.format("MMMM d, yyyy ", data.getTime()).toString();

                        // inserez in baza
                        FirebaseDatabase database = FirebaseDatabase.getInstance("https://aplicatie-nutritie-sport-default-rtdb.firebaseio.com/");
                        DatabaseReference myRef = database.getReference("IstoricMasuratori");

                        DatabaseReference utilizRef = myRef.child("Utilizator-" + username);
                        DatabaseReference masuratoareRef = utilizRef.child("Data-" + s);
                        DatabaseReference referinta = masuratoareRef.child("icm");
                        referinta.setValue(rezultat);

                        alertDialog.cancel();

                    }
                });

                Button btn_calculeaza = view.findViewById(R.id.btn_calculeaza);
                btn_calculeaza.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText ed_greutate = view.findViewById(R.id.ed_kg);
                        TextInputLayout inputLayout = view.findViewById(R.id.tv_kg);
                        String greutate = ed_greutate.getText().toString().trim();

                        if (greutate.isEmpty()) {
                            inputLayout.setError("Campul trebuie completat!");
                        } else {
                            nr_kg = Float.parseFloat(greutate);
                        }
                        if (nr_kg < 0 || nr_kg == 0) {
                            inputLayout.setError("Numar invalid!");
                        } else {
                            inputLayout.setError(null);
                            inputLayout.setErrorEnabled(false);

                            // calcul IMC

                            float kg = Float.parseFloat(greutate);
                            float inaltime_intreg = inaltime_bd / 100;
                            float inaltime_2 = (inaltime_intreg)*(inaltime_intreg);
                            rezultat = (kg / inaltime_2);

                            icm_rez = view.findViewById(R.id.icm_rez);
                            interpretare = view.findViewById(R.id.interpretare);
                            icm_rez.setText("" + rezultat);

                            if (rezultat < 18.49) {
                                interpretare.setText("Subponderal");
                            } else if (rezultat > 18.50 && rezultat < 24.99) {
                                interpretare.setText("Greutate normala");
                            } else if (rezultat > 25.00 && rezultat < 29.99) {
                                interpretare.setText("Superponderal");
                            } else if (rezultat > 30.00 && rezultat < 34.99) {
                                interpretare.setText("Obezitate gadul I");
                            } else if (rezultat > 35.00 && rezultat < 39.99) {
                                interpretare.setText("Obezitate gadul II");
                            } else if (rezultat > 40.00) {
                                interpretare.setText("Obezitate morbida");
                            }
                        }
                    }
                });

            }
        });

        grafic_icm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Grafic","icm");
                editor.apply();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new Grafic(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();

            }
        });

        grafic_kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Grafic","kg");
                editor.apply();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new Grafic(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();

            }
        });


        return root;
    }
}