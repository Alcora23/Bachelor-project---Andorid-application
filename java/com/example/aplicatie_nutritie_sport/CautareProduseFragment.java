package com.example.aplicatie_nutritie_sport;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.aplicatie_nutritie_sport.CaloriiFragment.titlu_1;

public class CautareProduseFragment extends Fragment {

    TextView titlu;
    ImageView back, cautare;
    List<Produs> produse = new ArrayList<>();
    List<Produs> lista_istoric = new ArrayList<>();

    float nr_c;
    float calorii_produs;
    float grame_proteine;
    float grame_garsimi;
    float grame_carbo;

    public static String masa_principala;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_cautare_produse, container, false);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://aplicatie-nutritie-sport-default-rtdb.firebaseio.com/");

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("Username", "");

        titlu = root.findViewById(R.id.titlu);
        back = root.findViewById(R.id.back);
        cautare = root.findViewById(R.id.imageView2);

        titlu.setText(titlu_1);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.cautare_fragm, new CaloriiFragment(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        //istoric alimente
        DatabaseReference refIstoric = database.getReference("IstoricProduse");
        DatabaseReference refUtilizator = refIstoric.child("Utilizator-" + username);

        refUtilizator.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lista_istoric.clear();
                Iterable<DataSnapshot> date = dataSnapshot.getChildren();
                long nr_elemente = dataSnapshot.getChildrenCount();
                long elemente_parcurse = 0;
                for (DataSnapshot data : date) {
                    if (elemente_parcurse < nr_elemente - 1) {
                        elemente_parcurse++;
                        continue;
                    } else {


                        Iterable<DataSnapshot> categorii = data.getChildren();
                        for (DataSnapshot categorie : categorii) {

                            Iterable<DataSnapshot> produse = categorie.getChildren();
                            for (DataSnapshot produs : produse) {
                                Produs p = produs.getValue(Produs.class);
                                lista_istoric.add(p);
                            }

                        }

                        if (lista_istoric.size() != 0) {
                            ListView lv = root.findViewById(R.id.lv_produse);
                            Class_Adapter adapter = new Class_Adapter(lista_istoric, getContext(), R.layout.layout_cautare);
                            lv.setAdapter(adapter);
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        cautare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                produse.clear();

                // cautarea in baza de date dupa ce primesc in edit text

                DatabaseReference myRef = database.getReference("Produse");

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> produse_bd = dataSnapshot.getChildren();
                        EditText ed_nume_produs = root.findViewById(R.id.editText);
                        String nume_produs = ed_nume_produs.getText().toString().trim();
                        for (DataSnapshot ds : produse_bd) {
                            Produs produs_baza = ds.getValue(Produs.class);
                            if (nume_produs.length() > 0) {
                                if (produs_baza.getDenumire().contains(nume_produs)) {
                                    produse.add(produs_baza);
                                }
                            }
                        }

                        if (nume_produs.length() > 0) {
                            if (produse.size() != 0) {
                                ListView lv = root.findViewById(R.id.lv_produse);
                                Class_Adapter adapter = new Class_Adapter(produse, getContext(), R.layout.layout_cautare);
                                lv.setAdapter(adapter);
                            } else {
                                Toast.makeText(getContext(), "Nu exista produse!", Toast.LENGTH_LONG).show();
                                produse.clear();
                                ListView lv = root.findViewById(R.id.lv_produse);
                                Class_Adapter adapter = new Class_Adapter(produse, getContext(), R.layout.layout_cautare);
                                lv.setAdapter(adapter);
                            }
                        } else {
                            Toast.makeText(getContext(), "Trebuie sa introduceti o denumire!", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        //este apelat cand se modifica datele dar renuntam la modificare
                    }
                });
            }
        });

        ListView lv = root.findViewById(R.id.lv_produse);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Produs p = (Produs) parent.getItemAtPosition(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                View view2 = getLayoutInflater().inflate(R.layout.alert_dialog_produse, null);

                builder.setView(view2);
                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setLayout(700, 800); //Controlling width and height.
                alertDialog.show();

                Button btn_ok = view2.findViewById(R.id.btn);
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Date data = new Date();
                        String s = DateFormat.format("MMMM d, yyyy ", data.getTime()).toString();

                        EditText ed_cantitate = view2.findViewById(R.id.ed_cantitate);
                        TextInputLayout inputLayout = view2.findViewById(R.id.tv_cantitate);
                        String cant_introdusa = ed_cantitate.getText().toString().trim();

                        if (cant_introdusa.isEmpty()) {
                            inputLayout.setError("Campul trebuie completat!");
                        } else {
                            nr_c = Float.parseFloat(cant_introdusa);
                        }

                        if (nr_c < 0 || nr_c == 0) {
                            inputLayout.setError("Numar invalid!");
                        } else {
                            inputLayout.setError(null);
                            inputLayout.setErrorEnabled(false);

                            // calcul calorii
                            calorii_produs = (Float.parseFloat(cant_introdusa) * Float.parseFloat(p.getCalorii())) / Float.parseFloat(p.getCantitate());
                            grame_garsimi = (Float.parseFloat(cant_introdusa) * Float.parseFloat(p.getGrasimi())) / Float.parseFloat(p.getCantitate());
                            grame_proteine = (Float.parseFloat(cant_introdusa) * Float.parseFloat(p.getProteine())) / Float.parseFloat(p.getCantitate());
                            grame_carbo = (Float.parseFloat(cant_introdusa) * Float.parseFloat(p.getCarbohidrati())) / Float.parseFloat(p.getCantitate());

                            Produs produs = new Produs(p.getDenumire(), cant_introdusa.toString(), "" + calorii_produs, "" + grame_proteine, "" + grame_garsimi, "" + grame_carbo);

                            //introducere in baza de date
                            FirebaseDatabase database = FirebaseDatabase.getInstance("https://aplicatie-nutritie-sport-default-rtdb.firebaseio.com/");
                            DatabaseReference myRef = database.getReference("IstoricProduse");

                            DatabaseReference utilizRef = myRef.child("Utilizator-" + username);
                            DatabaseReference dataRef = utilizRef.child("Data-" + s);
                            DatabaseReference categorieRef = dataRef.child("Categorie-" + titlu_1);
                            DatabaseReference produsRef = categorieRef.child("Produs-" + p.getDenumire());
                            produsRef.setValue(produs);

                            alertDialog.cancel();
                        }
                    }
                });
            }
        });

        return root;
    }
}