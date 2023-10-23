package com.example.aplicatie_nutritie_sport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ValidareCodActivity extends AppCompatActivity {

    ImageView cancel_btn;
    PinView pinView;
    String codSistem, whatToDo, nr_tel;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validare_cod);

        cancel_btn = findViewById(R.id.cancel_button);
        pinView = findViewById(R.id.pin_view);


        nr_tel = getIntent().getStringExtra("nr_tel");
        whatToDo = getIntent().getStringExtra("whatToDo");

        auth = FirebaseAuth.getInstance();

        TrimitereVerificareCodCatreUser(nr_tel);

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it_cancel = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(it_cancel);
            }
        });

    }

    private void TrimitereVerificareCodCatreUser(String nr_tel) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(nr_tel, 60, TimeUnit.SECONDS,
                ValidareCodActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        codSistem = s;
                        Toast.makeText(getApplicationContext(), "Codul a fost trimis", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                }
        );

    }

    public void verificare(View view) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codSistem, pinView.getText().toString().trim());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (whatToDo.equals("update")) {
                                Intent it = new Intent(getApplicationContext(), SetNewPasswordActivity.class);
                                it.putExtra("phone", nr_tel);
                                startActivity(it);
                                finish();
                            }

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(ValidareCodActivity.this, "A aparut o eroare la verificare!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });

    }
}