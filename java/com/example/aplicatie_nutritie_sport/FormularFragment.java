package com.example.aplicatie_nutritie_sport;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FormularFragment extends Fragment {

    EditText den_preparat;
    TextView insereaza, chenar;

    int nr_chenare = 3;
    int flag_cantitate = 0;

    float suma_cantitate = 0;
    static float suma_calorii = 0;
    static float suma_proteine = 0;
    static float suma_grasimi = 0;
    static float suma_carbohidrati = 0;

    List<Produs> Lista_produse = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_formular, container, false);

        den_preparat = root.findViewById(R.id.den_preparat);
        insereaza = root.findViewById(R.id.insereaza);
        chenar = root.findViewById(R.id.adauga_chenar);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://aplicatie-nutritie-sport-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = database.getReference("Produse");

        insereaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validareDenPreparat()) {
                    Toast.makeText(getContext(), "Denumirea preparatului nu a fost introdusa!", Toast.LENGTH_LONG).show();
                    return;
                } else {

                    for (int i = 1; i <= nr_chenare; i++) {
                        String nume = "cantitate" + i;
                        int resID = getResources().getIdentifier(nume, "id", getContext().getPackageName());

                        EditText editText = root.findViewById(resID);

                        if (!validareCantitate(editText)) {
                            Toast.makeText(getContext(), "Cantitate invalida la chenarul " + i + " !", Toast.LENGTH_LONG).show();
                            flag_cantitate = 1;
                        }
                    }

                    for (int i = 1; i <= nr_chenare; i++) {
                        String nume = "produs" + i;
                        int resID = getResources().getIdentifier(nume, "id", getContext().getPackageName());

                        EditText editText = root.findViewById(resID);

                        FirebaseDatabase database = FirebaseDatabase.getInstance("https://aplicatie-nutritie-sport-default-rtdb.firebaseio.com/");
                        DatabaseReference myRef = database.getReference("Produse");

                        String produs = editText.getText().toString().trim();

                        if (produs.isEmpty()) {
                            Toast.makeText(getContext(), "Denumirea din chenarul " + i + " nu e completata!", Toast.LENGTH_LONG).show();
                            return;
                        }

                        myRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Iterable<DataSnapshot> produse = dataSnapshot.getChildren();
                                for (DataSnapshot ds : produse) {
                                    Produs produs_baza = ds.getValue(Produs.class);
                                    if ((produs_baza.getDenumire().equals(produs))) {
                                        return;
                                    }
                                }
                                Toast.makeText(getContext(), "Produsul " + produs + " nu exista in baza de date", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                //este apelat cand se modifica datele dar renuntam la modificare
                            }
                        });
                    }

                    if (flag_cantitate == 1) {
                        return;
                    } else {
                        String preparat = den_preparat.getText().toString().trim();
                        int i;
                        for (i = 1; i <= nr_chenare; i++) {
                            String id = "produs" + i;
                            int resID = getResources().getIdentifier(id, "id", getContext().getPackageName());

                            EditText editText = root.findViewById(resID);

                            String den_produs = editText.getText().toString().trim();


                            String id2 = "cantitate" + i;
                            int resID2 = getResources().getIdentifier(id2, "id", getContext().getPackageName());

                            EditText editText_cant = root.findViewById(resID2);

                            String val_cantitate = editText_cant.getText().toString().trim();

                            suma_cantitate += Float.parseFloat(val_cantitate);

                            //aflare si calculare cantitati

                            DatabaseReference myRef2 = database.getReference("Produse");
                            myRef2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Iterable<DataSnapshot> produse = dataSnapshot.getChildren();
                                    for (DataSnapshot p : produse) {
                                        Produs produs_baza = p.getValue(Produs.class);
                                        if (produs_baza.getDenumire().equals(den_produs)) {
                                            suma_calorii += (Float.parseFloat(val_cantitate) * Float.parseFloat(produs_baza.getCalorii())) / Float.parseFloat(produs_baza.getCantitate());
                                            suma_proteine += (Float.parseFloat(val_cantitate) * Float.parseFloat(produs_baza.getProteine())) / Float.parseFloat(produs_baza.getCantitate());
                                            suma_carbohidrati += (Float.parseFloat(val_cantitate) * Float.parseFloat(produs_baza.getCarbohidrati())) / Float.parseFloat(produs_baza.getCantitate());
                                            suma_grasimi += (Float.parseFloat(val_cantitate) * Float.parseFloat(produs_baza.getGrasimi())) / Float.parseFloat(produs_baza.getCantitate());

                                        }
                                    }

                                    Produs produs_nou = new Produs(preparat, "" + suma_cantitate, "" + suma_calorii, "" + suma_proteine, "" + suma_grasimi, "" + suma_carbohidrati);
                                    Lista_produse.add(produs_nou);

                                    if(Lista_produse.size()==nr_chenare) {
                                        DatabaseReference utilizRef = myRef.child("Produs-" + produs_nou.getDenumire());
                                        utilizRef.setValue(produs_nou);
                                        Toast.makeText(getActivity().getApplicationContext(), "Iti multumim pentru contributia ta!", Toast.LENGTH_LONG).show();
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }


                    }
                }
            }
        });

        chenar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View v) {
                nr_chenare++;
                //adaugare chenar

                LinearLayout radacina = root.findViewById(R.id.layout);

                LinearLayout parent = new LinearLayout(getContext());
                parent.setOrientation(LinearLayout.VERTICAL);
                parent.setBackgroundResource(R.drawable.border_formular);
                parent.setLeftTopRightBottom(5, 0, 5, 10);

                LinearLayout parent2 = new LinearLayout(getContext());
                parent2.setOrientation(LinearLayout.HORIZONTAL);
                parent.setLeftTopRightBottom(0, 10, 0, 0);
                parent.addView(parent2);

                TextView tv_produs = new TextView(getContext());
                tv_produs.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
                tv_produs.setText("Produs folosit:");
                tv_produs.setTextSize(18f);
                tv_produs.setLeftTopRightBottom(15, 20, 10, 0);
                parent2.addView(tv_produs);


                EditText ed_produs = new EditText(getContext());
                //ed_produs.setId(Integer.parseInt("produs"+nr_chenare));
                ed_produs.setLayoutParams(new TableLayout.LayoutParams(200, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
                ed_produs.setLeftTopRightBottom(20, 20, 0, 0);
                ed_produs.setBackgroundResource(R.drawable.edit_text);
                ed_produs.setElevation(4f);
                ed_produs.setPadding(20, 15, 0, 10);
                ed_produs.setTextSize(14f);
                parent2.addView(ed_produs);

                LinearLayout parent3 = new LinearLayout(getContext());
                parent3.setOrientation(LinearLayout.HORIZONTAL);
                parent.setLeftTopRightBottom(0, 10, 0, 0);
                parent.addView(parent3);


                TextView tv_cantitate = new TextView(getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(15, 20, 10, 0);
                tv_cantitate.setText("Cantitate");
                tv_cantitate.setTextSize(18f);
                tv_cantitate.setLeftTopRightBottom(15, 20, 10, 0);
                parent3.addView(tv_cantitate, params);


                EditText ed_cantitate = new EditText(getContext());
                //ed_produs.setId(Integer.parseInt("cantitate"+nr_chenare));
                ed_cantitate.setLayoutParams(new TableLayout.LayoutParams(200, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
                ed_cantitate.setLeftTopRightBottom(20, 20, 0, 0);
                ed_cantitate.setBackgroundResource(R.drawable.edit_text);
                ed_cantitate.setElevation(4f);
                ed_cantitate.setPadding(20, 15, 0, 10);
                ed_cantitate.setTextSize(14f);
                parent3.addView(ed_cantitate);


                View view = new View(getContext());
                view.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 50, 1f));

                radacina.addView(view);
                radacina.addView(parent);


            }
        });


        return root;
    }

    private boolean validareDenPreparat() {
        String preparat = den_preparat.getText().toString().trim();

        if (preparat.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private boolean validareCantitate(EditText editText) {
        String cantitate = editText.getText().toString().trim();
        float nr = 0;

        if (cantitate.isEmpty()) {
            return false;
        } else {
            nr = Float.parseFloat(cantitate);
        }
        if (nr < 0 || nr == 0) {
            return false;
        } else {
            return true;
        }
    }
}