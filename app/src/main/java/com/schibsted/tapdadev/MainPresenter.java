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
    private final int[] developerImages;
    private final ImageView hole0;
    private final ImageView hole1;
    private final ImageView hole2;

    private boolean gameStarted = false;
    private int lastShownDeveloper = 2;

    public MainPresenter(MainActivity activity, ImageView[] holes, int[] developerImages) {
        this.activity = activity;
        this.developerImages = developerImages;
        this.hole0 = holes[0];
        this.hole1 = holes[1];
        this.hole2 = holes[2];

        showAll();
    }

    private void showAll() {
        activity.show(hole0, developerImages[0]);
        activity.show(hole1, developerImages[1]);
        activity.show(hole2, developerImages[2]);
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
        activity.hideDelayed(hole0, 180);
        activity.hide(hole1);
        activity.hideDelayed(hole2, 400);
    }

    private void rollDeveloper(int developer) {
        if (developer == 0) {
            activity.show(hole0, developerImages[0]);
            activity.hideDelayed(hole0, getRandomDelay());
        } else if (developer == 1) {
            activity.show(hole1, developerImages[1]);
            activity.hideDelayed(hole1, getRandomDelay());
        } else if (developer == 2) {
            activity.show(hole2, developerImages[2]);
            activity.hideDelayed(hole2, getRandomDelay());
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
        return isInvisible(hole0) && isInvisible(hole1) && isInvisible(hole2);
    }

    private boolean isInvisible(View view) {
        return view.getVisibility() == View.INVISIBLE;
    }

    public void onPause() {
        gameStarted = false;
    }
}
