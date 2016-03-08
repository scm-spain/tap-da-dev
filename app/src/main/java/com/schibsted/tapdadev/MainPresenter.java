package com.schibsted.tapdadev;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

public class MainPresenter {

    public static final int GAME_ITERATION_DELAY = 50;
    public static final int DEVS_COUNT = 3;

    private static final int MIN_RANDOM_DELAY = 500;
    private static final int MAX_RANDOM_DELAY = 1500;

    private final MainActivity activity;
    private final ImageView developer0;
    private final ImageView developer1;
    private final ImageView developer2;

    private boolean gameStarted = false;
    private int lastShownDeveloper = 2;

    public MainPresenter(MainActivity activity, ImageView[] holes, int[] developerImages) {
        this.activity = activity;
        this.developer0 = holes[0];
        this.developer1 = holes[1];
        this.developer2 = holes[2];

        developer0.setImageResource(developerImages[0]);
        developer1.setImageResource(developerImages[1]);
        developer2.setImageResource(developerImages[2]);
    }

    public void onDeveloperTapped(View view) {
        if (!gameStarted) {
            gameStarted = true;
            hideThreeDevelopers();
            setNextGameIteration();
        } else {
            activity.hide(view);
        }
    }

    private void hideThreeDevelopers() {
        activity.hideDelayed(developer0, 180);
        activity.hide(developer1);
        activity.hideDelayed(developer2, 400);
    }

    private void rollDeveloper(int developer) {
        if (developer == 0) {
            activity.show(developer0);
            activity.hideDelayed(developer0, getRandomDelay());
        } else if (developer == 1) {
            activity.show(developer1);
            activity.hideDelayed(developer1, getRandomDelay());
        } else if (developer == 2) {
            activity.show(developer2);
            activity.hideDelayed(developer2, getRandomDelay());
        }
        lastShownDeveloper = developer;
    }

    private int getRandomDelay() {
        return getRandomValue(MIN_RANDOM_DELAY, MAX_RANDOM_DELAY);
    }

    private int getRandomValue(int maxValue, int minValue) {
        return Integer.valueOf((int) Math.floor(Math.random() * (maxValue - minValue + 1) + minValue));
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
            rollOneRandomDeveloper();
        }
        setNextGameIteration();
    }

    private void rollOneRandomDeveloper() {
        rollDeveloper(getNextDeveloperToShow());
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
