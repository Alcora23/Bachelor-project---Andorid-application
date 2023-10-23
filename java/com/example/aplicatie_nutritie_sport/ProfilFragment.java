package com.example.aplicatie_nutritie_sport;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProfilFragment extends Fragment {

    SharedPreferences sharedPreferences;
    TextView nume, mail, telefon, inaltime, kg, slabire;
    Button btn_save;

    Float kilograme_initiale;
    List<Float> valori = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_profil, container, false);

        sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://aplicatie-nutritie-sport-default-rtdb.firebaseio.com/");

        String username = sharedPreferences.getString("Username", "");

        nume = root.findViewById(R.id.nume);
        mail = root.findViewById(R.id.p_mail);
        telefon = root.findViewById(R.id.telefon);
        inaltime = root.findViewById(R.id.inaltime);
        kg = root.findViewById(R.id.kilo);
        slabire = root.findViewById(R.id.numar);
        btn_save = root.findViewById(R.id.btn_salvare);

        nume.setText(username);

        DatabaseReference myRef = database.getReference("Utilizatori");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> utilizatori = snapshot.getChildren();
                for (DataSnapshot ds : utilizatori) {
                    Utilizator u = ds.getValue(Utilizator.class);
                    if (u.getUsername().equals(username)) {
                        mail.setText(u.getEmail());
                        telefon.setText(u.getTelefon());
                        inaltime.setText(u.getInaltime());
                        kg.setText(u.getKm_initiale());
                        kilograme_initiale = Float.parseFloat(u.getKm_initiale());

                        break;
                    }
                }

                // sa extrag din baza utimele kg
                DatabaseReference greutateRef = database.getReference("IstoricMasuratori");
                DatabaseReference utilizatorRef = greutateRef.child("Utilizator-" + username);

                utilizatorRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> istoric = dataSnapshot.getChildren();
                        for (DataSnapshot d : istoric) {
                            valori.add(Float.parseFloat(d.child("greutate").getValue().toString()));
                        }


                        //calcul kg
                        if (valori.get(valori.size() - 1) < kilograme_initiale) {
                            float rezultat = kilograme_initiale-valori.get(valori.size() - 1);
                            slabire.setText("" + rezultat);
                        } else {
                            slabire.setText("" + 0);
                        }


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

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                View view = getLayoutInflater().inflate(R.layout.alert_dialog_profil, null);

                builder.setView(view);
                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setLayout(700, 800); //Controlling width and height.
                alertDialog.show();

                EditText editText = view.findViewById(R.id.ed_profil);
                TextInputLayout inputLayout = view.findViewById(R.id.textInputLayout);
                editText.setHint("Introduceti noua adresa de mail");

                Button btn_ok = view.findViewById(R.id.btn);
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //validare mail
                        String email = editText.getText().toString().trim();
                        String formatCorect = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

                        if (email.isEmpty()) {
                            inputLayout.setError("Campul trebuie completat!");
                        } else if (!email.matches(formatCorect)) {
                            inputLayout.setError("Email invalid!");
                        } else {
                            inputLayout.setError(null);
                            inputLayout.setErrorEnabled(false);
                            alertDialog.cancel();

                            mail.setText(email);
                        }
                    }
                });

            }
        });

        telefon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                View view = getLayoutInflater().inflate(R.layout.alert_dialog_profil, null);

                builder.setView(view);
                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setLayout(700, 800); //Controlling width and height.
                alertDialog.show();

                EditText editText = view.findViewById(R.id.ed_profil);
                TextInputLayout inputLayout = view.findViewById(R.id.textInputLayout);
                editText.setHint("Introduceti noul numar de telefon");

                Button btn_ok = view.findViewById(R.id.btn);
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String telefon_2 = editText.getText().toString().trim();

                        if (telefon_2.isEmpty()) {
                            inputLayout.setError("Campul trebuie completat!");
                        } else if (telefon_2.length() < 10) {
                            inputLayout.setError("Numar invalid!");
                        } else {
                            inputLayout.setError(null);
                            inputLayout.setErrorEnabled(false);
                            alertDialog.cancel();
                            telefon.setText(telefon_2);
                        }
                    }
                });
            }
        });

        inaltime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                View view = getLayoutInflater().inflate(R.layout.alert_dialog_profil, null);

                builder.setView(view);
                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setLayout(700, 800); //Controlling width and height.
                alertDialog.show();

                EditText editText = view.findViewById(R.id.ed_profil);
                TextInputLayout inputLayout = view.findViewById(R.id.textInputLayout);
                editText.setHint("Introduceti inaltimea dumneavoastra");

                Button btn_ok = view.findViewById(R.id.btn);
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String inaltime_2 = editText.getText().toString().trim();
                        int nr_cm = 0;

                        if (inaltime_2.isEmpty()) {
                            inputLayout.setError("Campul trebuie completat!");
                        } else {
                            nr_cm = Integer.parseInt(inaltime_2);
                        }
                        if (nr_cm < 0 || nr_cm == 0) {
                            inputLayout.setError("Numar invalid!");
                        } else {
                            inputLayout.setError(null);
                            inputLayout.setErrorEnabled(false);
                            alertDialog.cancel();

                            inaltime.setText(inaltime_2);
                        }
                    }
                });
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance("https://aplicatie-nutritie-sport-default-rtdb.firebaseio.com/").getReference("Utilizatori");
                reference.child("Utilizator-" + username).child("email").setValue(mail.getText().toString().trim());

                reference.child("Utilizator-" + username).child("telefon").setValue(telefon.getText().toString().trim());

                reference.child("Utilizator-" + username).child("inaltime").setValue(inaltime.getText().toString().trim());
            }
        });


        return root;
    }

}