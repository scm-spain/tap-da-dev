package com.schibsted.tapdadev;

import android.view.View;
import android.view.animation.AnimationUtils;

public class MainPresenter {

    private final MainActivity activity;

    public MainPresenter(MainActivity activity) {
        this.activity = activity;
    }

    public void hide(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.slide_down));
        view.setVisibility(View.INVISIBLE);
    }
}
