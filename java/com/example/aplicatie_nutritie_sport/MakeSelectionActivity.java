package com.example.aplicatie_nutritie_sport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MakeSelectionActivity extends AppCompatActivity {

    RelativeLayout btn_telefon;
    ImageView back_arrow;
    TextView telefon;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_selection);

        Bundle bundle = getIntent().getExtras();
        String user = bundle.getString("username");

        btn_telefon = findViewById(R.id.relativ_phone);
        back_arrow = findViewById(R.id.m_back_arrow);
        telefon = findViewById(R.id.phone_number);
        progressBar= findViewById(R.id.progressBar);

        firebaseAuth=FirebaseAuth.getInstance();

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it_forget = new Intent(getApplicationContext(),ForgetPasswordActivity.class);
                startActivity(it_forget);
            }
        });


        progressBar.setVisibility(View.VISIBLE);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://aplicatie-nutritie-sport-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = database.getReference("Utilizatori");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> utilizatori = snapshot.getChildren();
                for (DataSnapshot ds : utilizatori) {
                    Utilizator u = ds.getValue(Utilizator.class);
                    if (u.getUsername().equals(user)) {
                        telefon.setText(u.getTelefon());
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //este apelat cand se modifica datele dar renuntam la modificare
            }
        });


        btn_telefon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it_cod = new Intent(getApplicationContext(),ValidareCodActivity.class);
                it_cod.putExtra("nr_tel",telefon.getText().toString().trim());
                it_cod.putExtra("whatToDo","update");
                startActivity(it_cod);
            }
        });

    }
}