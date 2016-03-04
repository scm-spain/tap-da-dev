package com.schibsted.tapdadev;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Random;

public class MainPresenter {

    public static final int GAME_ITERATION_DELAY = 50;
    public static final int DEVS_COUNT = 3;

    private static final int MIN_RANDOM_DELAY = 500;
    private static final int MAX_RANDOM_DELAY = 1500;

    private final MainActivity activity;
    private final View developer0;
    private final View developer1;
    private final View developer2;

    private boolean gameStarted = false;
    private int lastShownDeveloper = 2;

    public MainPresenter(MainActivity activity, ImageView developer0, ImageView developer1, ImageView developer2) {
        this.activity = activity;
        this.developer0 = developer0;
        this.developer1 = developer1;
        this.developer2 = developer2;

        developer0.setImageResource(R.drawable.dev_toni);
        developer1.setImageResource(R.drawable.dev_roc);
        developer2.setImageResource(R.drawable.dev_oscar);
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
        hide(developer0, 180);
        hide(developer1);
        hide(developer2, 400);
    }

    private void hide(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.slide_down));
            view.setVisibility(View.INVISIBLE);
        }
    }

    private void show(int developer) {
        if (developer == 0) {
            show(developer0);
        } else if (developer == 1) {
            show(developer1);
        } else if (developer == 2) {
            show(developer2);
        }
        lastShownDeveloper = developer;
    }

    private void show(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.slide_up));
        view.setVisibility(View.VISIBLE);
        hide(view, getRandomDelay());
    }

    private int getRandomDelay() {
        return Integer.valueOf((int) Math.floor(Math.random() * (MAX_RANDOM_DELAY - MIN_RANDOM_DELAY + 1) + MIN_RANDOM_DELAY));
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
                    runGameIteration();
                }
            }
        }, GAME_ITERATION_DELAY);
    }

    private void runGameIteration() {
        Log.d("GAME", "The game is on!");
        if (allDevelopersAreHidden()) {
            showOneRandomDeveloper();
        }
        setNextGameIteration();
    }

    private void showOneRandomDeveloper() {
        show(getNextDeveloperToShow());
    }

    private int getNextDeveloperToShow() {
        Random random = new Random();
        int nextDeveloper = random.nextInt(DEVS_COUNT);
        while (nextDeveloper == lastShownDeveloper) {
            nextDeveloper = random.nextInt(DEVS_COUNT);
        }
        return nextDeveloper;
    }

    private boolean allDevelopersAreHidden() {
        return isInvisible(developer0) && isInvisible(developer1) && isInvisible(developer2);
    }

    private boolean isInvisible(View view) {
        return view.getVisibility() == View.INVISIBLE;
    }

    public void onPause() {
        gameStarted = false;
    }
}
