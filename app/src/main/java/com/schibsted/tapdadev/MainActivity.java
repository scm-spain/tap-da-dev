package com.schibsted.tapdadev;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private MainPresenter presenter;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView developer0 = (ImageView) findViewById(R.id.l_developer0).findViewById(R.id.iv_developer);
        ImageView developer1 = (ImageView) findViewById(R.id.l_developer1).findViewById(R.id.iv_developer);
        ImageView developer2 = (ImageView) findViewById(R.id.l_developer2).findViewById(R.id.iv_developer);

        handler = new Handler();

        Target.OnTargetTappedListener targetListener = new Target.OnTargetTappedListener() {
            @Override
            public void onTargetTapped(Target target) {
                presenter.onTargetTapped(target);
            }
        };
        Target target0 = new Target(developer0, targetListener);
        Target target1 = new Target(developer1, targetListener);
        Target target2 = new Target(developer2, targetListener);

        presenter = new MainPresenter(this, handler,
          new Target[]{target0, target1, target2},
          CharacterFactory.withImages(R.drawable.dev_toni, R.drawable.dev_roc, R.drawable.dev_oscar)
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    void show(Target target, Character character) {
        ImageView view = target.getImageView();
        view.setImageResource(character.getImageResource());
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up));
        view.setVisibility(View.VISIBLE);
    }

    void hideDelayed(final Target target, int delay) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hide(target);
            }
        }, delay);
    }

    void hide(Target target) {
        ImageView view = target.getImageView();
        if (view.getVisibility() == View.VISIBLE) {
            view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_down));
            view.setVisibility(View.INVISIBLE);
        }
    }
}
