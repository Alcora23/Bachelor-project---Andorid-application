package com.example.aplicatie_nutritie_sport;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.aplicatie_nutritie_sport.CautareProduseFragment.*;

public class CaloriiFragment extends Fragment implements View.OnClickListener {

    public static String titlu_1;
    int prog = 0;
    ProgressBar counter;
    TextView calorii_consumate, calorii_total, user_logat, apa, tv_grasimi, tv_proteine, tv_carbo;
    CardView dejun, pranz, cina, gustare;
    ImageView p1, p2, p3, p4, p5, p6;
    Switch notificari;

    FirebaseDatabase database;
    String areNotificare;

    float SumCalorii = 0;
    float sumaCaloriiDejun = 0;
    float sumaCaloriiPranz = 0;
    float sumaCaloriiCina = 0;
    float sumaCaloriiGustari = 0;

    float sumaProteine = 0;
    float sumaGrasimi = 0;
    float sumaCarbo = 0;

    int suma = 0;
    List<Integer> calorii_exercitii = new ArrayList<>();
    float consum_apa;
    float calorii_necesare;

    static int nr_apasari = 0;
    int varsta;
    float rata_metabolica;
    Float inaltime;
    Float greutate;
    String dataUtilizator;
    String activitate;
    String gen;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_calorii, container, false);

        counter = root.findViewById(R.id.counter);
        calorii_consumate = root.findViewById(R.id.calorii_consumate);
        calorii_total = root.findViewById(R.id.calorii_total);
        user_logat = root.findViewById(R.id.user_logat);
        dejun = root.findViewById(R.id.mic_dejun);
        pranz = root.findViewById(R.id.pranz);
        cina = root.findViewById(R.id.cina);
        gustare = root.findViewById(R.id.gustari);
        p1 = root.findViewById(R.id.pahar1);
        p2 = root.findViewById(R.id.pahar2);
        p3 = root.findViewById(R.id.pahar3);
        p4 = root.findViewById(R.id.pahar4);
        p5 = root.findViewById(R.id.pahar5);
        p6 = root.findViewById(R.id.pahar6);
        apa = root.findViewById(R.id.litii_apa);
        tv_grasimi = root.findViewById(R.id.ed_grame_grasimi);
        tv_proteine = root.findViewById(R.id.ed_grame_proteine);
        tv_carbo = root.findViewById(R.id.ed_grame_carbo);
        TextView tv_proteine = root.findViewById(R.id.ed_grame_proteine);
        TextView tv_grasimi = root.findViewById(R.id.ed_grame_grasimi);
        TextView tv_carbo = root.findViewById(R.id.ed_grame_carbo);
        TextView d_calorii = root.findViewById(R.id.dejun_calorii);
        TextView p_calorii = root.findViewById(R.id.pranz_calorii);
        TextView c_calorii = root.findViewById(R.id.cina_calorii);
        TextView g_calorii = root.findViewById(R.id.gustari_calorii);
        notificari = root.findViewById(R.id.switch_notificari);

        database = FirebaseDatabase.getInstance("https://aplicatie-nutritie-sport-default-rtdb.firebaseio.com/");

        p1.setOnClickListener(this);
        p2.setOnClickListener(this);
        p3.setOnClickListener(this);
        p4.setOnClickListener(this);
        p5.setOnClickListener(this);
        p6.setOnClickListener(this);

        //extrag din fisier
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Informatii", Context.MODE_PRIVATE);
        String nume = sharedPreferences.getString("Username", "");


        if (nume != null) {
            user_logat.setText(nume);
        }

        DatabaseReference databaseReference = database.getReference("Notificari");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> utilizatori = dataSnapshot.getChildren();
                for (DataSnapshot ds : utilizatori) {
                    Iterable<DataSnapshot> argumente = ds.getChildren();
                    for(DataSnapshot arg: argumente) {
                        if (arg.getKey().equals("notificare")) {
                            areNotificare = arg.getValue().toString();
                        }
                    }
                }
                if (areNotificare.equals("activata")) {
                    notificari.setChecked(true);
                } else {
                    notificari.setChecked(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        notificari.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getContext(), "Ati activat notificarile", Toast.LENGTH_LONG).show();
                    creareNotificareMicDejun();
                    creareNotificareGustare1();
                    creareNotificarePranz();
                    creareNotificareGustare2();
                    creareNotificareCina();

                    // inserez in baza
                    DatabaseReference myRef = database.getReference("Notificari");

                    DatabaseReference utilizRef = myRef.child("Utilizator-" + nume);
                    DatabaseReference referinta = utilizRef.child("notificare");
                    referinta.setValue("activata");
                }
                else{
                    Toast.makeText(getContext(), "Ati dezactivat notificarile", Toast.LENGTH_LONG).show();
                    creareNotificareMicDejun();
                    creareNotificareGustare1();
                    creareNotificarePranz();
                    creareNotificareGustare2();
                    creareNotificareCina();

                    // inserez in baza
                    DatabaseReference myRef = database.getReference("Notificari");

                    DatabaseReference utilizRef = myRef.child("Utilizator-" + nume);
                    DatabaseReference referinta = utilizRef.child("notificare");
                    referinta.setValue("dezactivata");
                }
            }
        });

        //calcul calorii necesare
        DatabaseReference reference = database.getReference("Utilizatori");
        DatabaseReference reference2 = reference.child("Utilizator-" + nume);

        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> argumente = dataSnapshot.getChildren();
                for (DataSnapshot arg : argumente) {
                    if (arg.getKey().equals("inaltime")) {
                        inaltime = Float.parseFloat(arg.getValue().toString());
                    } else if (arg.getKey().equals("km_initiale")) {
                        greutate = Float.parseFloat(arg.getValue().toString());
                    } else if (arg.getKey().equals("data_nasterii")) {
                        dataUtilizator = arg.getValue().toString();
                    } else if (arg.getKey().equals("gen")) {
                        gen = arg.getValue().toString();
                    } else if (arg.getKey().equals("tip_activitate")) {
                        activitate = arg.getValue().toString();
                    }
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String dataCurenta = dateFormat.format(Calendar.getInstance().getTime());

                try {
                    Date d1 = dateFormat.parse(dataUtilizator);
                    Date d2 = dateFormat.parse(dataCurenta);

                    long startDate = d1.getTime();
                    long endDate = d2.getTime();

                    Period period = new Period(startDate, endDate, PeriodType.yearMonthDay());
                    varsta = period.getYears();

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //calculez rata metabolica in functie de gen
                if (gen.equals("Feminin")) {
                    rata_metabolica = 655 + (9.5f * greutate) + (1.8f * inaltime) - (4.7f * varsta);
                } else {
                    rata_metabolica = 66 + (13.7f * greutate) + (5.0f * inaltime) - (6.8f * varsta);
                }


                // calorii necesare
                if (activitate.equals("Sedentar")) {
                    calorii_necesare = rata_metabolica * 1.2f;
                } else if (activitate.equals("Semisedentar")) {
                    calorii_necesare = rata_metabolica * 1.375f;
                } else if (activitate.equals("Moderat")) {
                    calorii_necesare = rata_metabolica * 1.55f;
                } else if (activitate.equals("Semiactiv")) {
                    calorii_necesare = rata_metabolica * 1.725f;
                } else if (activitate.equals("Activ")) {
                    calorii_necesare = rata_metabolica * 1.9f;
                }

                calorii_total.setText("" + (float) Math.round(calorii_necesare * 100) / 100);
                updateProgressBar(calorii_total);

                //calcul calorii pe categorii si per total
                Date data = new Date();
                String s = DateFormat.format("MMMM d, yyyy ", data.getTime()).toString();

                DatabaseReference ref = database.getReference("IstoricProduse");
                DatabaseReference utilizatorRef = ref.child("Utilizator-" + nume);
                DatabaseReference dataRef = utilizatorRef.child("Data-" + s);

                DatabaseReference categorieRef = dataRef.child("Categorie-" + "Mic Dejun");
                categorieRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> produse = dataSnapshot.getChildren();
                        for (DataSnapshot d : produse) {
                            Produs produs = d.getValue(Produs.class);
                            sumaCaloriiDejun += Float.parseFloat(produs.getCalorii());
                            sumaProteine += Float.parseFloat(produs.getProteine());
                            sumaCarbo += Float.parseFloat(produs.getCarbohidrati());
                            sumaGrasimi += Float.parseFloat(produs.getGrasimi());
                        }

                        d_calorii.setText("" + (float) Math.round(sumaCaloriiDejun * 100) / 100);
                        tv_proteine.setText("" + (float) Math.round(sumaProteine * 100) / 100);
                        tv_grasimi.setText("" + (float) Math.round(sumaGrasimi * 100) / 100);
                        tv_carbo.setText("" + (float) Math.round(sumaCarbo * 100) / 100);

                        //actualizare progress calorii
                        SumCalorii += sumaCaloriiDejun;
                        if (prog <= 100) {
                            prog += (100 * sumaCaloriiDejun) / calorii_necesare;
                            updateProgressBar(calorii_total);
                        } else {
                            updateProgressBar(calorii_total);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                DatabaseReference categorieRef2 = dataRef.child("Categorie-" + "Pranz");
                categorieRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> produse = dataSnapshot.getChildren();
                        for (DataSnapshot d : produse) {
                            Produs produs = d.getValue(Produs.class);
                            sumaCaloriiPranz += Float.parseFloat(produs.getCalorii());
                            sumaProteine += Float.parseFloat(produs.getProteine());
                            sumaCarbo += Float.parseFloat(produs.getCarbohidrati());
                            sumaGrasimi += Float.parseFloat(produs.getGrasimi());
                        }
                        p_calorii.setText("" + (float) Math.round(sumaCaloriiPranz * 100) / 100);
                        tv_proteine.setText("" + (float) Math.round(sumaProteine * 100) / 100);
                        tv_grasimi.setText("" + (float) Math.round(sumaGrasimi * 100) / 100);
                        tv_carbo.setText("" + (float) Math.round(sumaCarbo * 100) / 100);

                        //actualizare progress calorii
                        SumCalorii += sumaCaloriiPranz;
                        if (prog <= 100) {
                            prog += (100 * sumaCaloriiPranz) / calorii_necesare;
                            updateProgressBar(calorii_total);
                        } else {
                            updateProgressBar(calorii_total);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                DatabaseReference categorieRef3 = dataRef.child("Categorie-" + "Cina");
                categorieRef3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> produse = dataSnapshot.getChildren();
                        for (DataSnapshot d : produse) {
                            Produs produs = d.getValue(Produs.class);
                            sumaCaloriiCina += Float.parseFloat(produs.getCalorii());
                            sumaProteine += Float.parseFloat(produs.getProteine());
                            sumaCarbo += Float.parseFloat(produs.getCarbohidrati());
                            sumaGrasimi += Float.parseFloat(produs.getGrasimi());
                        }
                        c_calorii.setText("" + (float) Math.round(sumaCaloriiCina * 100) / 100);
                        tv_proteine.setText("" + (float) Math.round(sumaProteine * 100) / 100);
                        tv_grasimi.setText("" + (float) Math.round(sumaGrasimi * 100) / 100);
                        tv_carbo.setText("" + (float) Math.round(sumaCarbo * 100) / 100);

                        //actualizare progress calorii
                        SumCalorii += sumaCaloriiCina;
                        if (prog <= 100) {
                            prog += (100 * sumaCaloriiCina) / calorii_necesare;
                            updateProgressBar(calorii_total);
                        } else {
                            updateProgressBar(calorii_total);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                DatabaseReference categorieRef4 = dataRef.child("Categorie-" + "Gustare");
                categorieRef4.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> produse = dataSnapshot.getChildren();
                        for (DataSnapshot d : produse) {
                            Produs produs = d.getValue(Produs.class);
                            sumaCaloriiGustari += Float.parseFloat(produs.getCalorii());
                            sumaProteine += Float.parseFloat(produs.getProteine());
                            sumaCarbo += Float.parseFloat(produs.getCarbohidrati());
                            sumaGrasimi += Float.parseFloat(produs.getGrasimi());
                        }
                        g_calorii.setText("" + (float) Math.round(sumaCaloriiGustari * 100) / 100);
                        tv_proteine.setText("" + (float) Math.round(sumaProteine * 100) / 100);
                        tv_grasimi.setText("" + (float) Math.round(sumaGrasimi * 100) / 100);
                        tv_carbo.setText("" + (float) Math.round(sumaCarbo * 100) / 100);

                        //actualizare progress calorii
                        SumCalorii += sumaCaloriiGustari;
                        if (prog <= 100) {
                            prog += (100 * sumaCaloriiGustari) / calorii_necesare;
                            updateProgressBar(calorii_total);
                        } else {
                            updateProgressBar(calorii_total);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                //calcul calorii dupa terminare ex
                DatabaseReference reference1 = database.getReference("IstoricExercitii");
                DatabaseReference utilizatorReference3 = reference1.child("Utilizator-" + nume);
                DatabaseReference dataReference = utilizatorReference3.child("Data-" + s);

                dataReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> exercitii = dataSnapshot.getChildren();
                        for (DataSnapshot e : exercitii) {
                            Iterable<DataSnapshot> atr = e.getChildren();
                            for (DataSnapshot a : atr) {
                                if (a.getKey().equals("calorii")) {
                                    calorii_exercitii.add(Integer.parseInt(a.getValue().toString()));
                                }

                            }
                        }

                        for (Integer v : calorii_exercitii) {
                            suma += v;
                        }
                        float total = suma + calorii_necesare;
                        calorii_total.setText("" + (float) Math.round(total * 100) / 100);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dejun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titlu_1 = "Mic Dejun";
                if (Float.parseFloat(d_calorii.getText().toString()) == 0.0) {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_fragment, new CautareProduseFragment(), "fragment")
                            .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                } else {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_fragment, new ProduseDejaAdaugateFragment(), "fragment")
                            .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                }
            }
        });

        pranz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titlu_1 = "Pranz";
                if (Float.parseFloat(p_calorii.getText().toString()) == 0.0) {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_fragment, new CautareProduseFragment(), "fragment")
                            .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                } else {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_fragment, new ProduseDejaAdaugateFragment(), "fragment")
                            .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                }
            }
        });

        cina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titlu_1 = "Cina";
                if (Float.parseFloat(c_calorii.getText().toString()) == 0.0) {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_fragment, new CautareProduseFragment(), "fragment")
                            .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                } else {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_fragment, new ProduseDejaAdaugateFragment(), "fragment")
                            .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                }
            }
        });

        gustare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titlu_1 = "Gustare";
                if (Float.parseFloat(g_calorii.getText().toString()) == 0.0) {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_fragment, new CautareProduseFragment(), "fragment")
                            .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                } else {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_fragment, new ProduseDejaAdaugateFragment(), "fragment")
                            .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                }
            }
        });

        return root;
    }

    private void updateProgressBar(TextView calorii_total) {
        if (Float.parseFloat(calorii_consumate.getText().toString()) == 0.0f) {
            counter.setProgress(0);
            calorii_consumate.setText("" + 0);
        }
        if (calorii_consumate.getText().toString().trim().isEmpty() == false && Float.parseFloat(calorii_consumate.getText().toString().trim()) < Float.parseFloat(calorii_total.getText().toString().trim())) {
            counter.setProgress(prog);
            calorii_consumate.setText("" + (float) Math.round(SumCalorii * 100) / 100);
        } else {
            if (Float.parseFloat(calorii_consumate.getText().toString().trim()) >= Float.parseFloat(calorii_total.getText().toString().trim())) {
                counter.setProgress(100);
                calorii_consumate.setText("" + (float) Math.round(SumCalorii * 100) / 100);
            }
        }
    }

    private void creareNotificareMicDejun() {
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());//setting the time from device
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Intent intent1 = new Intent(getContext(), NotificareDejun.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager am = (AlarmManager) getContext().getSystemService(getContext().ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        if(!notificari.isChecked()){
            Toast.makeText(getContext(), "anulat", Toast.LENGTH_SHORT).show();
            am.cancel(pendingIntent);
        }
    }

    private void creareNotificareGustare1() {
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());//setting the time from device
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Intent intent1 = new Intent(getContext(), NotificareGustare.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 100, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager am = (AlarmManager) getContext().getSystemService(getContext().ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        if(!notificari.isChecked()){
            am.cancel(pendingIntent);
        }

    }

    private void creareNotificarePranz() {
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());//setting the time from device
        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Intent intent1 = new Intent(getContext(), NotificarePranz.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 200, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager am = (AlarmManager) getContext().getSystemService(getContext().ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        if(!notificari.isChecked()){
            am.cancel(pendingIntent);
        }

    }

    private void creareNotificareGustare2() {
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());//setting the time from device
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Intent intent1 = new Intent(getContext(), NotificareGustare.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 100, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager am = (AlarmManager) getContext().getSystemService(getContext().ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        if(!notificari.isChecked()){
            am.cancel(pendingIntent);
        }

    }

    private void creareNotificareCina() {
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());//setting the time from device
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Intent intent1 = new Intent(getContext(), NotificareCina.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 300, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager am = (AlarmManager) getContext().getSystemService(getContext().ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        if(!notificari.isChecked()){
            am.cancel(pendingIntent);
        }
    }


    @Override
    public void onClick(View v) {

        if (nr_apasari < 6) {
            ImageView imagine = (ImageView) v;
            imagine.setImageResource(R.drawable.ic_glass_full_of_liquid);

            consum_apa = consum_apa + 0.5f;

            apa.setText("" + consum_apa);

            nr_apasari++;

        }

    }
}
