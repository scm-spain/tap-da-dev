package com.schibsted.tapdadev;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Collections;
import java.util.List;

public class MainPresenter {

    public static final int GAME_ITERATION_DELAY = 50;

    private static final int MIN_RANDOM_DELAY = 500;
    private static final int MAX_RANDOM_DELAY = 1500;

    private final MainActivity activity;

    private final List<ImageView> developerLayouts;
    private final List<Integer> developerResources;

    private int lastDeveloperResource = 0;
    private boolean gameStarted = false;

    public MainPresenter(MainActivity activity, List<ImageView> developerLayouts, List<Integer> developerResources) throws Exception {
        if (developerLayouts == null || developerLayouts.size() < 3) {
            throw new Exception("The layouts array must have at least 3 developers");
        }

        if (developerResources == null || developerResources.size() < 2) {
            throw new Exception("The resources array must have at least 3 developers");
        }

        this.activity = activity;
        this.developerLayouts = developerLayouts;
        this.developerResources = developerResources;

        for (int i=0; i< developerLayouts.size() && i< developerResources.size(); i++) {
            developerLayouts.get(i).setImageResource(developerResources.get(i));
        }
    }

    // region manager game
    public void onDeveloperTapped(ImageView developerLayout) {
        if (!gameStarted) {
            gameStarted = true;
            hideAllDeveloperLayouts();
            setNextGameIteration();
        } else {
            hideDeveloperLayout(developerLayout);
        }
    }

    public void onPause() {
        Log.d("GAME", "The game is paused!");
        gameStarted = false;
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
        if (allDeveloperLayoutsAreHidden()) {
            rollOneRandomLayout();
        }
        setNextGameIteration();
    }

    private void rollOneRandomLayout() {
        ImageView devevloperLayout = developerLayouts.get(getRandomValue(0, developerLayouts.size() - 1));
        showRandomDeveloperLayout(devevloperLayout);
        hideDeveloperLayoutWithDelay(devevloperLayout, getRandomDelay());
    }
    // endregion

    // region manager visibility
    private void showRandomDeveloperLayout(ImageView developerLayout) {
        if (developerLayout.getVisibility() == View.INVISIBLE) {
            developerLayout.setImageResource(getRandomDeveloperResource());
            developerLayout.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.slide_up));
            developerLayout.setVisibility(View.VISIBLE);
        }
    }

    private Integer getRandomDeveloperResource() {
        Collections.shuffle(developerResources);

        int randomDeveloperResource;
        if (lastDeveloperResource == 0 || lastDeveloperResource != developerResources.get(0)) {
            randomDeveloperResource = developerResources.get(0);
        } else {
            randomDeveloperResource = developerResources.get(1);
        }

        lastDeveloperResource = randomDeveloperResource;
        return randomDeveloperResource;
    }

    private void hideAllDeveloperLayouts() {
        for (ImageView developerLayout: developerLayouts) {
            hideDeveloperLayoutWithDelay(developerLayout, getRandomValue(100, 500));
        }
    }

    private void hideDeveloperLayoutWithDelay(final ImageView developerLayout, int delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideDeveloperLayout(developerLayout);
            }
        }, delay);
    }

    private void hideDeveloperLayout(ImageView developerLayout) {
        if (developerLayout.getVisibility() == View.VISIBLE) {
            developerLayout.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.slide_down));
            developerLayout.setVisibility(View.INVISIBLE);
        }
    }

    private boolean allDeveloperLayoutsAreHidden() {
        for (ImageView imageView: developerLayouts) {
            if (imageView.getVisibility() == View.VISIBLE) {
                return false;
            }
        }
        return true;
    }
    // endregion

    // region random methods
    private int getRandomDelay() {
        return getRandomValue(MIN_RANDOM_DELAY, MAX_RANDOM_DELAY);
    }

    private int getRandomValue(int minValue, int maxValue) {
        return Integer.valueOf((int) Math.floor(Math.random() * (maxValue - minValue + 1) + minValue));
    }
    // endregion
}
