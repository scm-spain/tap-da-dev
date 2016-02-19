package com.schibsted.tapdadev;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;

public class MainPresenter {

    public static final int GAME_ITERATION_DELAY = 50;
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
            hideThreeDevelopers();
            setNextGameIteration();
        } else {
            hide(view);
        }
    }

    private void hideThreeDevelopers() {
        hide(developer1, 180);
        hide(developer2);
        hide(developer3, 400);
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

    private void setNextGameIteration() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (gameStarted) {
                    Log.d("GAME", "The game is on!");
                    setNextGameIteration();
                }
            }
        }, GAME_ITERATION_DELAY);
    }

    public void onPause() {
        gameStarted = false;
    }
}
