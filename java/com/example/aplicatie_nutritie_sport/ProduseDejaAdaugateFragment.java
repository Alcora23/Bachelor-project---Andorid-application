package com.example.aplicatie_nutritie_sport;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.aplicatie_nutritie_sport.CaloriiFragment.titlu_1;

public class ProduseDejaAdaugateFragment extends Fragment {
    TextView titlu;
    List<Produs> produse_adaugate = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_produse_deja_adaugate, container, false);

        Button adaugare = root.findViewById(R.id.btn_adaugare);
        ImageView back = root.findViewById(R.id.back);
        ListView lv = root.findViewById(R.id.lv_produse);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("Username", "");

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://aplicatie-nutritie-sport-default-rtdb.firebaseio.com/");

        titlu = root.findViewById(R.id.titlu);

        titlu.setText(titlu_1);

        adaugare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new CautareProduseFragment(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new CaloriiFragment(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        Date data = new Date();
        String s = DateFormat.format("MMMM d, yyyy ", data.getTime()).toString();

        DatabaseReference ref = database.getReference("IstoricProduse");
        DatabaseReference utilizatorRef = ref.child("Utilizator-" + username);
        DatabaseReference dataRef = utilizatorRef.child("Data-" + s);

        DatabaseReference categorieRef = dataRef.child("Categorie-" + titlu_1);

        categorieRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> produse = dataSnapshot.getChildren();
                for (DataSnapshot d : produse) {
                    Produs produs = d.getValue(Produs.class);
                    produse_adaugate.add(produs);
                }

                if (produse_adaugate.size() != 0) {
                    ListView lv = root.findViewById(R.id.lv_produse);
                    Class_Adapter adapter = new Class_Adapter(produse_adaugate, getContext(), R.layout.layout_cautare);
                    lv.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return root;
    }
}