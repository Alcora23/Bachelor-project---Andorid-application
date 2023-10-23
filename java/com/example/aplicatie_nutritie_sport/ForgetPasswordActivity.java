package com.example.aplicatie_nutritie_sport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForgetPasswordActivity extends AppCompatActivity {

    ImageView back_arrow;
    Button btn_next;
    TextInputLayout tv_fp_username;
    int flag =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        back_arrow = findViewById(R.id.back_arrow);
        btn_next = findViewById(R.id.btn_next);
        tv_fp_username= findViewById(R.id.tv_forgetP_user);

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it_log = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(it_log);
            }
        });


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validareUsername()) return;
                else{

                    String username = tv_fp_username.getEditText().getText().toString().trim();

                    FirebaseDatabase database = FirebaseDatabase.getInstance("https://aplicatie-nutritie-sport-default-rtdb.firebaseio.com/");
                    DatabaseReference myRef = database.getReference("Utilizatori");

                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Iterable<DataSnapshot> utilizatori = snapshot.getChildren();
                            flag = 0;
                            for (DataSnapshot ds : utilizatori) {
                                Utilizator u = ds.getValue(Utilizator.class);
                                if (u.getUsername().equals(username)) {
                                    flag = 1;
                                    Intent it_selection = new Intent(getApplicationContext(),MakeSelectionActivity.class);
                                    it_selection.putExtra("username",tv_fp_username.getEditText().getText().toString().trim());
                                    startActivity(it_selection);
                                }
                            }
                            if (flag == 0) {
                                Toast.makeText(getApplicationContext(), "Nu exista nici un cont cu acest nume!", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            //este apelat cand se modifica datele dar renuntam la modificare
                        }
                    });
                }

            }
        });

    }

    private boolean validareUsername() {
        String username = tv_fp_username.getEditText().getText().toString().trim();

        if (username.isEmpty()) {
            tv_fp_username.setError("Campul trebuie completat!");
            return false;
        } else if (username.length() < 4) {
            tv_fp_username.setError("Username-ul este prea scurt!");
            return false;
        } else if (username.length() > 20) {
            tv_fp_username.setError("Username-ul este prea lung!");
            return false;
        } else {
            tv_fp_username.setError(null);
            tv_fp_username.setErrorEnabled(false);
            return true;
        }
    }
}