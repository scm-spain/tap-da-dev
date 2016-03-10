package com.schibsted.tapdadev;

import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import java.util.Collections;
import java.util.List;

public class MainPresenter {

    public static final int GAME_ITERATION_DELAY = 50;

    private static final int MIN_RANDOM_DELAY = 500;
    private static final int MAX_RANDOM_DELAY = 1500;

    private final PresenterView presenterView;

    private final Handler gameHandler;
    private final Handler targetHandler;

    private final List<Target> targets;
    private final List<Character> characters;

    private Target lastTarget;
    private Character lastCharacter;

    private boolean gameStarted = false;
    private boolean gamePaused = false;

    public MainPresenter(PresenterView presenterView, List<Target> targets, List<Character> characters) throws Exception {
        if (targets == null || targets.size() < 3) {
            throw new Exception("The layouts array must have at least 3 targets");
        }

        if (characters == null || characters.size() < 2) {
            throw new Exception("The resources array must have at least 3 characters");
        }

        this.presenterView = presenterView;
        this.gameHandler = new Handler();
        this.targetHandler = new Handler();
        this.targets = targets;
        this.characters = characters;

        showAll();
    }

    // region manager game
    public void resume() {
        if (gameStarted == true) {
            Log.d("GAME", "The game is resumed!");
            gamePaused = false;
            setNextGameIteration();
        }
    }

    public void pause() {
        Log.d("GAME", "The game is paused!");
        gamePaused = true;
    }

    public void onTargetTapped(Target target) {
        if (!gameStarted) {
            Log.d("GAME", "The game is on!");

            gameStarted = true;
            hideAllDelayed();
            setNextGameIteration();
        } else if (!gamePaused) {
            presenterView.hide(target);
        }
    }

    private void setNextGameIteration() {
        gameHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (gameStarted && !gamePaused) {
                    runGameIteration();
                }
            }
        }, GAME_ITERATION_DELAY);
    }

    private void runGameIteration() {
        if (allTargetsAreHidden()) {
            rollOneRandomTarget();
        }
        setNextGameIteration();
    }

    private void rollOneRandomTarget() {
        Collections.shuffle(targets);
        Collections.shuffle(characters);

        Target nextTarget = targets.get(0);
        if (nextTarget.equals(lastTarget)) {
            nextTarget = targets.get(1);
        }
        lastTarget = nextTarget;

        Character nextCharacter = characters.get(0);
        if (nextCharacter.equals(lastCharacter)) {
            nextCharacter = characters.get(1);
        }
        lastCharacter = nextCharacter;

        presenterView.show(nextTarget, nextCharacter);
        hideDelayed(nextTarget, getRandomDelay());
    }
    // endregion

    // region manager visibility
    private void showAll() {
        for (int i=0; i < targets.size() && i < characters.size(); i++) {
            presenterView.show(targets.get(i), characters.get(i));
        }
    }

    private void hideAllDelayed() {
        for (Target target: targets) {
            hideDelayed(target, getRandomValue(100, 500));
        }
    }

    private void hideDelayed(final Target target, int delay) {
        targetHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                presenterView.hide(target);
            }
        }, delay);
    }

    private boolean allTargetsAreHidden() {
        for (Target target: targets) {
            if (ImageView.VISIBLE == target.getImageView().getVisibility()) {
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
        return (int) Math.floor(Math.random() * (maxValue - minValue + 1) + minValue);
    }
    // endregion

    // region interface
    public interface PresenterView {
        public void show(Target target, Character character);

        public void hide(Target target);
    }
    // endregion
}
