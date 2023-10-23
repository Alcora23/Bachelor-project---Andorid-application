package com.example.aplicatie_nutritie_sport;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class RegisterActivity extends AppCompatActivity {

    FloatingActionButton fb, google;
    TextInputLayout tv_mail, tv_parola, tv_data, tv_nume, tv_conf_parola, tv_kg, tv_nr, tv_inaltime, tv_activitate, tv_gen;
    CircularProgressButton btn_reg;
    CardView cardView;
    TextView dataNasterii;
    DatePickerDialog datePickerDialog;
    ProgressBar progressBar;
    Spinner spinner;
    RadioGroup radioGroup;

    String tip_activitate = "";
    String gen = "";

    float v = 0;

    private int flag = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        changeStatusBarColor();

        fb = findViewById(R.id.r_facebook);
        google = findViewById(R.id.r_google);
        tv_mail = findViewById(R.id.tv_reg_email);
        tv_parola = findViewById(R.id.tv_reg_parola);
        tv_data = findViewById(R.id.tv_reg_datanasterii);
        tv_nume = findViewById(R.id.tv_reg_username);
        btn_reg = findViewById(R.id.register_button);
        cardView = findViewById(R.id.cardview);
        tv_conf_parola = findViewById(R.id.tv_reg_conf_parola);
        tv_kg = findViewById(R.id.tv_kg);
        tv_nr = findViewById(R.id.tv_nr_tel);
        tv_inaltime = findViewById(R.id.tv_inaltime);
        tv_activitate = findViewById(R.id.tv_activitate);
        tv_gen = findViewById(R.id.tv_gen);
        dataNasterii = findViewById(R.id.reg_datanasterii);
        progressBar = findViewById(R.id.progressBar);
        spinner = findViewById(R.id.spinner);
        radioGroup = findViewById(R.id.radio_group);

        fb.setTranslationY(300);
        google.setTranslationY(300);
        tv_mail.setTranslationX(800);
        tv_parola.setTranslationX(800);
        tv_data.setTranslationX(800);
        tv_nume.setTranslationX(800);
        btn_reg.setTranslationX(800);
        cardView.setTranslationX(800);
        tv_conf_parola.setTranslationX(800);
        tv_kg.setTranslationX(800);
        tv_nr.setTranslationX(800);
        tv_inaltime.setTranslationX(800);
        tv_activitate.setTranslationX(800);
        tv_gen.setTranslationX(800);

        fb.setAlpha(v);
        google.setAlpha(v);
        tv_mail.setAlpha(v);
        tv_parola.setAlpha(v);
        tv_data.setAlpha(v);
        tv_nume.setAlpha(v);
        btn_reg.setAlpha(v);
        cardView.setAlpha(v);
        tv_conf_parola.setAlpha(v);
        tv_kg.setAlpha(v);
        tv_nr.setAlpha(v);
        tv_inaltime.setAlpha(v);
        tv_activitate.setAlpha(v);
        tv_gen.setAlpha(v);

        fb.animate().translationY(0).alpha(1).setDuration(2000).setStartDelay(400).start();
        google.animate().translationY(0).alpha(1).setDuration(2000).setStartDelay(600).start();
        cardView.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(100).start();
        tv_nume.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(300).start();
        tv_mail.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(500).start();
        tv_data.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(700).start();
        tv_nr.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(900).start();
        tv_parola.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(1100).start();
        tv_conf_parola.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(1300).start();
        tv_kg.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(1500).start();
        tv_inaltime.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(1700).start();
        tv_activitate.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(1900).start();
        tv_gen.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(2100).start();
        btn_reg.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(2300).start();


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.activitati, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tip_activitate = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dataNasterii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                datePickerDialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int luna = month + 1;
                        String data_n = dayOfMonth + "/" + luna + "/" + year;
                        dataNasterii.setText(data_n);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );

                datePickerDialog.show();
            }
        });

        //validari introducere date
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.f) gen = "Feminin";
                if (radioGroup.getCheckedRadioButtonId() == R.id.m) gen = "Masculin";
                if (!validareUsername() | !validareEmail() | !validateData() | !validareParola() | !validareConfParola() | !validareKg() | !validareTelefon() | !validareInaltime() | !validareActivitate() | !validareGen()) {
                    return;
                } else {
                    String username = tv_nume.getEditText().getText().toString().trim();
                    String email = tv_mail.getEditText().getText().toString().trim();
                    String data = dataNasterii.getText().toString().trim();
                    String telefon = tv_nr.getEditText().getText().toString().trim();
                    String parola = tv_parola.getEditText().getText().toString().trim();
                    String kg = tv_kg.getEditText().getText().toString().trim();
                    String inaltime = tv_inaltime.getEditText().getText().toString().trim();

                    progressBar.setVisibility(View.VISIBLE);

                    Utilizator u = new Utilizator(username, email, data, telefon, parola, kg, inaltime, tip_activitate, gen);

                    FirebaseDatabase database = FirebaseDatabase.getInstance("https://aplicatie-nutritie-sport-default-rtdb.firebaseio.com/");
                    DatabaseReference myRef = database.getReference("Utilizatori");

                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Iterable<DataSnapshot> utilizatori = dataSnapshot.getChildren();
                            flag = 0;
                            for (DataSnapshot ds : utilizatori) {
                                Utilizator utilizator_baza = ds.getValue(Utilizator.class);
                                if ((utilizator_baza.getUsername().equals(username))) {
                                    flag = 1;
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), "Exista deja un utiliaztor cu username-ul ales!", Toast.LENGTH_LONG).show();
                                    break;
                                }

                                if ((utilizator_baza.getEmail().equals(email))) {
                                    flag = 1;
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), "Mail-ul este deja folosit!", Toast.LENGTH_LONG).show();
                                    break;
                                }

                            }
                            if (flag == 0) {
                                DatabaseReference utilizRef = myRef.child("Utilizator-" + u.getUsername());
                                utilizRef.setValue(u);
                                Toast.makeText(getApplicationContext(), "Te-ai inregistrat cu succes! Intoarce-te la login pentru a accesa aplicatia!", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
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
        String username = tv_nume.getEditText().getText().toString().trim();

        if (username.isEmpty()) {
            tv_nume.setError("Campul trebuie completat!");
            return false;
        } else if (username.length() < 4) {
            tv_nume.setError("Username-ul este prea scurt!");
            return false;
        } else if (username.length() > 20) {
            tv_nume.setError("Username-ul este prea lung!");
            return false;
        } else {
            tv_nume.setError(null);
            tv_nume.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validareEmail() {
        String email = tv_mail.getEditText().getText().toString().trim();
        String formatCorect = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (email.isEmpty()) {
            tv_mail.setError("Campul trebuie completat!");
            return false;
        } else if (!email.matches(formatCorect)) {
            tv_mail.setError("Email invalid!");
            return false;
        } else {
            tv_mail.setError(null);
            tv_mail.setErrorEnabled(false);
            return true;
        }
    }


    private boolean validateData() {
        String data = dataNasterii.getText().toString().trim();
        int varsta;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataCurenta = dateFormat.format(Calendar.getInstance().getTime());


        if (data.isEmpty()) {
            tv_data.setError("Campul trebuie completat!");
            return false;
        } else {
            int anUtilizator = datePickerDialog.getDatePicker().getYear();
            int lunaUtilizator = datePickerDialog.getDatePicker().getMonth() + 1;
            int ziUtilizator = datePickerDialog.getDatePicker().getDayOfMonth();
            String dataUtilizator = ziUtilizator + "/" + lunaUtilizator + "/" + anUtilizator;

            try {
                Date d1 = dateFormat.parse(dataUtilizator);
                Date d2 = dateFormat.parse(dataCurenta);

                long startDate = d1.getTime();
                long endDate = d2.getTime();

                if (startDate <= endDate) {
                    Period period = new Period(startDate, endDate, PeriodType.yearMonthDay());
                    varsta = period.getYears();
                    int luni = period.getMonths();
                    int zile = period.getDays();


                } else {
                    tv_data.setError("Anul trebuie sa fie mai mic decat cel curent!");
                    return false;
                }

                if (varsta < 14) {
                    tv_data.setError("Nu ai varsta necesara pentru a aplica!");
                    return false;
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        tv_data.setError(null);
        tv_data.setErrorEnabled(false);
        return true;
    }


    private boolean validareParola() {
        String parola = tv_parola.getEditText().getText().toString().trim();

        if (parola.isEmpty()) {
            tv_parola.setError("Campul trebuie completat!");
            return false;
        } else if (parola.length() < 6) {
            tv_parola.setError("Parola trebuie sa aiba cel putin 6 caractere!");
            return false;
        } else {
            tv_parola.setError(null);
            tv_parola.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validareConfParola() {
        String conf_parola = tv_conf_parola.getEditText().getText().toString().trim();

        if (conf_parola.isEmpty()) {
            tv_conf_parola.setError("Campul trebuie completat!");
            return false;
        } else if (!(tv_parola.getEditText().getText().toString().trim().equals(conf_parola))) {
            tv_conf_parola.setError("Parola incorect introdusa!");
            return false;
        } else {
            tv_conf_parola.setError(null);
            tv_conf_parola.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validareKg() {
        String kg = tv_kg.getEditText().getText().toString().trim();
        float nr_kg;

        if (kg.isEmpty()) {
            tv_kg.setError("Campul trebuie completat!");
            return false;
        } else {
            nr_kg = Float.parseFloat(kg);
        }
        if (nr_kg < 0 || nr_kg == 0) {
            tv_kg.setError("Numar invalid!");
            return false;
        } else {
            tv_kg.setError(null);
            tv_kg.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validareTelefon() {
        String telefon = tv_nr.getEditText().getText().toString().trim();

        if (telefon.isEmpty()) {
            tv_nr.setError("Campul trebuie completat!");
            return false;
        } else if (telefon.length() < 10) {
            tv_nr.setError("Numar invalid!");
            return false;
        } else {
            tv_nr.setError(null);
            tv_nr.setErrorEnabled(false);
            return true;
        }
    }


    private boolean validareInaltime() {
        String inaltime = tv_inaltime.getEditText().getText().toString().trim();
        int nr_cm = 0;

        if (inaltime.isEmpty()) {
            tv_inaltime.setError("Campul trebuie completat!");
            return false;
        } else {
            nr_cm = Integer.parseInt(inaltime);
        }
        if (nr_cm < 0 || nr_cm == 0) {
            tv_inaltime.setError("Numar invalid!");
            return false;
        } else {
            tv_inaltime.setError(null);
            tv_inaltime.setErrorEnabled(false);
            return true;
        }
    }


    private boolean validareActivitate() {
        if (tip_activitate.isEmpty()) {
            tv_activitate.setError("Campul trebuie completat!");
            return false;
        } else {
            tv_activitate.setError(null);
            tv_activitate.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validareGen() {
        if (gen.isEmpty()) {
            tv_gen.setError("Campul trebuie completat!");
            return false;
        } else {
            tv_gen.setError(null);
            tv_gen.setErrorEnabled(false);
            return true;
        }
    }


    public void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.color_login));
        }
    }

    public void BackToLogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}