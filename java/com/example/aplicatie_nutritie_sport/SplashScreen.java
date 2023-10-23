package com.example.aplicatie_nutritie_sport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class SplashScreen extends AppCompatActivity {

    ImageView logo,background;
    TextView text, text2;
    LottieAnimationView lottieAnimationView;

    private static final int NR_FRAGMENTE =3;
    private ViewPager viewPager;
    private ScreenSlidePagerAdapter pagerAdapter;

    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logo = findViewById(R.id.logo);
        text = findViewById(R.id.text);
        text2 = findViewById(R.id.text2);
        background = findViewById(R.id.image_splash);
        lottieAnimationView = findViewById(R.id.lottie);

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        animation = AnimationUtils.loadAnimation(this,R.anim.animatie_preview);
        viewPager.startAnimation(animation);

        background.animate().translationY(-3000).setDuration(5000).setStartDelay(4000);
        logo.animate().translationY(1800).setDuration(5000).setStartDelay(4000);
        text.animate().translationY(1400).setDuration(5000).setStartDelay(4000);
        text2.animate().translationY(1400).setDuration(5000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(1400).setDuration(5000).setStartDelay(4000);
    }


    private class  ScreenSlidePagerAdapter extends FragmentStatePagerAdapter{

        public ScreenSlidePagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    Preview1 fila1 =new Preview1();
                    return fila1;
                case 1:
                    Preview2 fila2 =new Preview2();
                    return fila2;
                case 2:
                    Preview3 fila3 =new Preview3();
                    return fila3;

            }
            return  null;
        }

        @Override
        public int getCount() {
            return NR_FRAGMENTE;
        }
    }



}