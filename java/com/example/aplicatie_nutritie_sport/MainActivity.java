package com.example.aplicatie_nutritie_sport;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ResideMenu resideMenu;
    private Context context;
    private ResideMenuItem calorii;
    private ResideMenuItem formular;
    private ResideMenuItem sfaturi;
    private ResideMenuItem exercitii;
    private ResideMenuItem evolutie;
    private ResideMenuItem profil;
    private ResideMenuItem logOut;

    String nume;
    String parola;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("Informatii", MODE_PRIVATE);
        nume = sharedPreferences.getString("Username", "");
        parola = sharedPreferences.getString("Parola", "");

        context = this;
        setUpMenu();

        if (savedInstanceState == null) {
            changeFragment(new CaloriiFragment());
        }

    }

    private void setUpMenu() {
        resideMenu = new ResideMenu(this);

        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        resideMenu.setBackground(R.color.meniu_color);
        resideMenu.setScaleValue(0.5f);

        calorii = new ResideMenuItem(this, R.drawable.ic_calories_calculator, "Calcul calorii");
        formular = new ResideMenuItem(this, R.drawable.ic_checklist, "Formular");
        sfaturi = new ResideMenuItem(this, R.drawable.ic_plan, "Sfaturi");
        exercitii = new ResideMenuItem(this, R.drawable.ic_fitness, "Sport");
        evolutie = new ResideMenuItem(this, R.drawable.ic_growth, "Evolutie personala");
        profil = new ResideMenuItem(this, R.drawable.ic_profile, "Profilul meu");
        logOut = new ResideMenuItem(this, R.drawable.ic_logout, "Deconectare");

        calorii.setOnClickListener(this);
        formular.setOnClickListener(this);
        sfaturi.setOnClickListener(this);
        exercitii.setOnClickListener(this);
        evolutie.setOnClickListener(this);
        profil.setOnClickListener(this);
        logOut.setOnClickListener(this);

        resideMenu.addMenuItem(calorii, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(formular, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(sfaturi, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(exercitii, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(evolutie, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(profil, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(logOut, ResideMenu.DIRECTION_LEFT);

        findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {

        if (view == calorii) {
            changeFragment(new CaloriiFragment());
        } else if (view == formular) {
            changeFragment(new FormularFragment());
        } else if (view == sfaturi) {
            changeFragment(new SfaturiNutritionaleFragment());
        } else if (view == exercitii) {
            changeFragment(new ExercitiiFragment());
        } else if (view == evolutie) {
            changeFragment(new EvolutieFragment());
        } else if (view == profil) {
            changeFragment(new ProfilFragment());
        } else if (view == logOut) {
            if (nume != null) {
                //resetez valorile din fisier
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Username", "");
                editor.putString("Parola", "");
                editor.apply();

                Toast.makeText(context, "Te-ai delogat cu succes!", Toast.LENGTH_LONG).show();
                Intent it = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(it);
                finish();



            } else {
                Toast.makeText(context, "Delogarea a esuat!", Toast.LENGTH_LONG).show();
            }
        }

        resideMenu.closeMenu();
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {

        }

        @Override
        public void closeMenu() {

        }
    };

    private void changeFragment(Fragment targetFragment) {
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

}