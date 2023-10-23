package com.example.aplicatie_nutritie_sport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class LoginActivity extends AppCompatActivity {

    FloatingActionButton fb, google;
    TextInputLayout tv_username, tv_parola;
    TextView tv_forgetPass;
    CircularProgressButton btn_login;
    ProgressBar progressBar;
    float v = 0;
    private int flag = 0;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);

        fb = findViewById(R.id.log_facebook);
        google = findViewById(R.id.log_google);
        tv_username = findViewById(R.id.tv_log_username);
        tv_parola = findViewById(R.id.tv_log_parola);
        tv_forgetPass = findViewById(R.id.tv_forgetPass);
        btn_login = findViewById(R.id.login_button);
        progressBar = findViewById(R.id.progressBar);

        fb.setTranslationY(300);
        google.setTranslationY(300);
        tv_username.setTranslationX(800);
        tv_parola.setTranslationX(800);
        tv_forgetPass.setTranslationX(800);
        btn_login.setTranslationX(800);

        fb.setAlpha(v);
        google.setAlpha(v);
        tv_username.setAlpha(v);
        tv_parola.setAlpha(v);
        tv_forgetPass.setAlpha(v);
        btn_login.setAlpha(v);

        fb.animate().translationY(0).alpha(1).setDuration(2000).setStartDelay(400).start();
        google.animate().translationY(0).alpha(1).setDuration(2000).setStartDelay(600).start();
        tv_username.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(300).start();
        tv_parola.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(500).start();
        tv_forgetPass.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(500).start();
        btn_login.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(700).start();


        //validari introducere date
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validareUsername() | !validareParola()) {
                    return;
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    final String username = tv_username.getEditText().getText().toString().trim();
                    final String parola = tv_parola.getEditText().getText().toString().trim();


                    FirebaseDatabase database = FirebaseDatabase.getInstance("https://aplicatie-nutritie-sport-default-rtdb.firebaseio.com/");
                    DatabaseReference myRef = database.getReference("Utilizatori");

                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Iterable<DataSnapshot> utilizatori = dataSnapshot.getChildren();
                            flag = 0;
                            for (DataSnapshot ds : utilizatori) {
                                Utilizator utilizator_baza = ds.getValue(Utilizator.class);
                                if ((utilizator_baza.getUsername().equals(username) && (!utilizator_baza.getParola().equals(parola)))) {
                                    flag = 1;
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), "Parola este incorecta!", Toast.LENGTH_LONG).show();
                                    break;
                                } else if ((utilizator_baza.getUsername().equals(username) && (utilizator_baza.getParola().equals(parola)))) {
                                    flag = 1;
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), "Te-ai logat cu succes!", Toast.LENGTH_LONG).show();

                                    sharedPreferences = getSharedPreferences("Informatii", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("Username", username);
                                    editor.putString("Parola", parola);
                                    editor.commit();

                                    Intent it = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(it);

                                    EditText ed_username = findViewById(R.id.ed_log_username);
                                    EditText ed_parola = findViewById(R.id.ed_log_parola);

                                    ed_username.setText("");
                                    ed_parola.setText("");

                                    break;
                                }

                            }
                            if (flag == 0) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Logarea a esuat! Inregistreaza-te mai intai in aplicatie!", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            //este apelat cand se modifica datele dar renuntam la modificare
                        }
                    });
                }

            }
        });
    }


    private boolean validareUsername() {
        String username = tv_username.getEditText().getText().toString().trim();

        if (username.isEmpty()) {
            tv_username.setError("Campul trebuie completat!");
            return false;
        } else {
            tv_username.setError(null);
            tv_username.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validareParola() {
        String parola = tv_parola.getEditText().getText().toString().trim();

        if (parola.isEmpty()) {
            tv_parola.setError("Campul trebuie completat!");
            return false;
        } else {
            tv_parola.setError(null);
            tv_parola.setErrorEnabled(false);
            return true;
        }
    }

    public void GoToRegister(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }

    public void GoToForgetPassword(View view) {
        startActivity(new Intent(this, ForgetPasswordActivity.class));
    }
}