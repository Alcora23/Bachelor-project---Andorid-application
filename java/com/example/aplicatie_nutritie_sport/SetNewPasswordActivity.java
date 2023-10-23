package com.example.aplicatie_nutritie_sport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SetNewPasswordActivity extends AppCompatActivity {

    TextInputLayout parola_n, Confparola;
    String telefon, username;
    int flag = 0;
    int modificare = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);

        parola_n = findViewById(R.id.tv_parola_noua);
        Confparola = findViewById(R.id.tv_conf_parola);

        telefon = getIntent().getStringExtra("phone");

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://aplicatie-nutritie-sport-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = database.getReference("Utilizatori");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> utilizatori = snapshot.getChildren();
                for (DataSnapshot ds : utilizatori) {
                    Utilizator u = ds.getValue(Utilizator.class);
                    if (u.getTelefon().equals(telefon)) {
                        username = u.getUsername();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //este apelat cand se modifica datele dar renuntam la modificare
            }
        });

    }

    private boolean validareParolaNoua() {
        String parola = parola_n.getEditText().getText().toString().trim();

        if (parola.isEmpty()) {
            parola_n.setError("Campul trebuie completat!");
            return false;
        } else if (parola.length() < 6) {
            parola_n.setError("Parola trebuie sa aiba cel putin 6 caractere!");
            return false;
        } else {
            parola_n.setError(null);
            parola_n.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validareConfParola() {
        String conf_parola = Confparola.getEditText().getText().toString().trim();

        if (conf_parola.isEmpty()) {
            Confparola.setError("Campul trebuie completat!");
            return false;
        } else if (!(parola_n.getEditText().getText().toString().trim().equals(conf_parola))) {
            Confparola.setError("Parola incorect introdusa!");
            return false;
        } else {
            Confparola.setError(null);
            Confparola.setErrorEnabled(false);
            return true;
        }
    }

    public void SetareParolaNoua(View view) {
        if (!validareParolaNoua() | !validareConfParola()) return;

        if (modificare == 0) {
            String parolaNoua = parola_n.getEditText().getText().toString().trim();
            DatabaseReference reference = FirebaseDatabase.getInstance("https://aplicatie-nutritie-sport-default-rtdb.firebaseio.com/").getReference("Utilizatori");
            reference.child("Utilizator-" + username).child("parola").setValue(parolaNoua);
            modificare = 1;
        }

        if (modificare == 1) {
            Intent it = new Intent(getApplicationContext(), SuccessMassageForgetPasswordActivity.class);
            startActivity(it);
            //finish();
        }
    }
}
