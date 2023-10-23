package com.example.aplicatie_nutritie_sport;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.CountDownTimer;
import android.os.SystemClock;
import android.provider.AlarmClock;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;

import static com.example.aplicatie_nutritie_sport.CaloriiFragment.titlu_1;

public class CronometruFragment extends Fragment {

    ImageView back;
    Button btn_start;
    ImageView sageata;
    GifImageView imagine;
    Animation invarte;
    TextView timp, descriere;
    private static final long START_Time = 40000;
    long timp_ramas = START_Time;
    CountDownTimer countDownTimer;

    private Ringtone ringtone;

    SharedPreferences sharedPreferences;

    int calorii;
    static int i = 0;
    String nume_exercitiu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_cronometru, container, false);

        back = root.findViewById(R.id.back);
        btn_start = root.findViewById(R.id.btn_start);
        sageata = root.findViewById(R.id.sageata);
        imagine = root.findViewById(R.id.img);
        timp = root.findViewById(R.id.timer);
        descriere = root.findViewById(R.id.descriere);

        ringtone = RingtoneManager.getRingtone(getContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
        invarte = AnimationUtils.loadAnimation(root.getContext(), R.anim.animatie_rotatie);

        sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);
        String nume = sharedPreferences.getString("Username", "");
        String exercitiu = sharedPreferences.getString("Nume_exercitiu", "");


        FirebaseDatabase database = FirebaseDatabase.getInstance("https://aplicatie-nutritie-sport-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = database.getReference("Exercitii");
        DatabaseReference exRef = myRef.child("Nume-" + exercitiu);

        exRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                descriere.setText(dataSnapshot.child("descriere").getValue().toString());

                String src = dataSnapshot.child("sursaImagine").getValue().toString();
                String[] cuvinte = src.split("/");

                int drawableId = getResources().getIdentifier(cuvinte[1], "drawable", getContext().getPackageName());
                imagine.setImageResource(drawableId);

                calorii = Integer.parseInt(dataSnapshot.child("calorii").getValue().toString());

                nume_exercitiu = dataSnapshot.child("denumire").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sageata.startAnimation(invarte);
                countDownTimer = new CountDownTimer(timp_ramas, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timp_ramas = millisUntilFinished;
                        updateCountDownText();
                    }

                    @Override
                    public void onFinish() {
                        sageata.clearAnimation();
                        ringtone.play();

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                        builder.setMessage("Exercitiul s-a terminat").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                i++;
                                
                                ringtone.stop();
                                dialog.dismiss();

                                Date data = new Date();
                                String s = DateFormat.format("MMMM d, yyyy ", data.getTime()).toString();

                                //inserare in baza de date
                                FirebaseDatabase database = FirebaseDatabase.getInstance("https://aplicatie-nutritie-sport-default-rtdb.firebaseio.com/");
                                DatabaseReference myRef = database.getReference("IstoricExercitii");


                                DatabaseReference utilizRef = myRef.child("Utilizator-" + nume);
                                DatabaseReference dataRef = utilizRef.child("Data-" + s);
                                DatabaseReference exRef = dataRef.child("Exercitiu-" + i);
                                DatabaseReference numeRef = exRef.child("nume");
                                DatabaseReference caloriiRef = exRef.child("calorii");
                                numeRef.setValue(nume_exercitiu);
                                caloriiRef.setValue(calorii);


                            }
                        });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.getWindow().setLayout(700, 800); //Controlling width and height.
                        alertDialog.show();
                    }
                }.start();
            }
        });

        updateCountDownText();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, new ExercitiiFragment(), "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        return root;
    }

    private void updateCountDownText() {
        int minute = (int) (timp_ramas / 1000) / 60;
        int secunde = (int) (timp_ramas / 1000) % 60;

        String format = String.format(Locale.getDefault(), "%02d:%02d", minute, secunde);

        timp.setText(format);
    }
}