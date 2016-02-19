package com.schibsted.tapdadev;

import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;

import java.util.Random;

public class MainPresenter {

    private final MainActivity activity;
    private final View developer1;
    private final View developer2;
    private final View developer3;

    private boolean gameStarted = false;

    public MainPresenter(MainActivity activity, View developer1, View developer2, View developer3) {
        this.activity = activity;
        this.developer1 = developer1;
        this.developer2 = developer2;
        this.developer3 = developer3;
    }

    public void onDeveloperTapped(View view) {
        if (!gameStarted) {
            gameStarted = true;

            Random random = new Random();
            hide(developer1, random.nextInt(1000));
            hide(developer2);
            hide(developer3, random.nextInt(1000));
        } else {
            hide(view);
        }
    }

    private void hide(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.slide_down));
        view.setVisibility(View.INVISIBLE);
    }

    private void hide(final View view, int delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hide(view);
            }
        }, delay);
    }
}
