package com.schibsted.tapdadev;

import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import java.util.Collections;
import java.util.List;

public class MainPresenter {

    public static final int GAME_ITERATION_DELAY = 100;
    public static final int GAME_START_TIME = 300;
    public static final int GAME_FINISH_TIME = 0;

    public static final int PUNCH_DELAY = 150;

    private static final int MIN_RANDOM_DELAY = 500;
    private static final int MAX_RANDOM_DELAY = 1500;

    private final PresenterView presenterView;

    private final Handler gameHandler;
    private final Handler targetHandler;

    private final List<Target> targets;
    private final List<Character> characters;

    private Target lastTarget;
    private Character lastCharacter;

    private int time;
    private int score;

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
        this.time = GAME_START_TIME;
        this.score = 0;

        showAll();
    }

    // region resume/pause game
    public void resume() {
        if (gameStarted) {
            Log.d("GAME", "The game is resumed!");
            gamePaused = false;
            setNextGameIteration();
        }
    }

    public void pause() {
        Log.d("GAME", "The game is paused!");
        gamePaused = true;
    }
    // endregion

    // region manager game
    public void onTargetTapped(Target target) {
        if (!gameStarted) {
            Log.d("GAME", "The game is on!");
            gameStarted = true;
            hideAllDelayed();
            setNextGameIteration();
        } else if (!gamePaused && !target.isPunched()) {
            targetHandler.removeCallbacksAndMessages(null);
            presenterView.punch(target);
            target.setPunched(!target.isPunched());
            updateScore();
            hideDelayed(target, PUNCH_DELAY);
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
        updateTime();
        if (time == GAME_FINISH_TIME) {
            finishGame();
        }
        if (allTargetsAreHidden()) {
            rollOneRandomTargetCharacter();
        }
        setNextGameIteration();
    }

    private void finishGame() {
        targetHandler.removeCallbacksAndMessages(null);
        gameHandler.removeCallbacksAndMessages(null);
        gameStarted = false;
        gamePaused = false;
        hideAll();

        presenterView.showFinalScore(Integer.toString(score));
        time = GAME_START_TIME + 1;
        score = -1;
        updateTime();
        updateScore();
        showAll();
    }

    private void updateTime() {
        time--;
        presenterView.updateTime(Integer.toString(time / 10) + "." + Integer.toString(time % 10));
    }

    private void updateScore() {
        score++;
        presenterView.updateScore(Integer.toString(score));
    }

    private void rollOneRandomTargetCharacter() {
        Target nextTarget = getNextRandomTarget();
        Character nextCharacter = getNextRandomCharacter();
        nextTarget.setCharacter(nextCharacter);

        presenterView.show(nextTarget);
        hideDelayed(nextTarget, getRandomDelay());
    }

    private Target getNextRandomTarget() {
        Target nextTarget = targets.get(getRandomValue(0, targets.size() - 1));
        if (nextTarget.equals(lastTarget)) {
            nextTarget = targets.get(1);
        }
        lastTarget = nextTarget;
        nextTarget.setPunched(false);

        return nextTarget;
    }

    private Character getNextRandomCharacter() {
        Collections.shuffle(characters);

        Character nextCharacter = characters.get(0);
        if (nextCharacter.equals(lastCharacter)) {
            nextCharacter = characters.get(1);
        }
        lastCharacter = nextCharacter;

        return nextCharacter;
    }
    // endregion

    // region manager visibility
    private void showAll() {
        for (int i=0; i < targets.size() && i < characters.size(); i++) {
            Target target = targets.get(i);
            target.setCharacter(characters.get(i));
            target.setPunched(false);
            presenterView.show(target);
        }
    }

    private void hideAll() {
        for (Target target: targets) {
            presenterView.hide(target);
        }
    }

    private void hideAllDelayed() {
        for (Target target: targets) {
            hideDelayed(target, getRandomValue(0, 400));
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
        public void show(Target target);

        public void hide(Target target);

        public void punch(Target target);

        public void updateTime(String time);

        public void updateScore(String score);

        public void showFinalScore(String score);
    }
    // endregion
}
