package com.schibsted.tapdadev;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import java.util.Random;

public class MainPresenter {

    public static final int GAME_ITERATION_DELAY = 50;
    public static final int DEVS_COUNT = 3;

    private static final int MIN_RANDOM_DELAY = 500;
    private static final int MAX_RANDOM_DELAY = 1500;

    private final MainActivity activity;
    private final Handler handler;
    private final Character[] characters;
    private final Target[] targets;

    private boolean gameStarted = false;
    private int lastShownDeveloper = 2;

    public MainPresenter(MainActivity activity, Handler handler, Target[] targets, Character[] developers) {
        this.activity = activity;
        this.handler = handler;
        this.targets = targets;
        this.characters = developers;
        showAll();
    }

    private void showAll() {
        activity.show(targets[0], characters[0]);
        activity.show(targets[1], characters[1]);
        activity.show(targets[2], characters[2]);
    }

    public void onTargetTapped(Target target) {
        if (!gameStarted) {
            gameStarted = true;
            hideThreeDevelopers();
            setNextGameIteration();
        } else {
            activity.hide(target);
        }
    }

    private void hideThreeDevelopers() {
        activity.hideDelayed(targets[0], 180);
        activity.hide(targets[1]);
        activity.hideDelayed(targets[2], 400);
    }

    private void rollDeveloper(int developer) {
        if (developer == 0) {
            activity.show(targets[0], characters[0]);
            activity.hideDelayed(targets[0], getRandomDelay());
        } else if (developer == 1) {
            activity.show(targets[1], characters[1]);
            activity.hideDelayed(targets[1], getRandomDelay());
        } else if (developer == 2) {
            activity.show(targets[2], characters[2]);
            activity.hideDelayed(targets[2], getRandomDelay());
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
        handler.postDelayed(new Runnable() {
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
        return isInvisible(targets[0]) && isInvisible(targets[1]) && isInvisible(targets[2]);
    }

    private boolean isInvisible(Target target) {
        return target.getImageView().getVisibility() == View.INVISIBLE;
    }

    public void onPause() {
        gameStarted = false;
    }
}
