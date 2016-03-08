package com.schibsted.tapdadev;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView developer0 = (ImageView) findViewById(R.id.l_developer0).findViewById(R.id.iv_developer);
        ImageView developer1 = (ImageView) findViewById(R.id.l_developer1).findViewById(R.id.iv_developer);
        ImageView developer2 = (ImageView) findViewById(R.id.l_developer2).findViewById(R.id.iv_developer);

        View.OnClickListener listener = new OnDeveloperClickListener();
        developer0.setOnClickListener(listener);
        developer1.setOnClickListener(listener);
        developer2.setOnClickListener(listener);

        presenter = new MainPresenter(this,
          new ImageView[]{developer0, developer1, developer2},
          new int[]{R.drawable.dev_toni, R.drawable.dev_roc, R.drawable.dev_oscar}
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    void show(ImageView view, int developerImage) {
        view.setImageResource(developerImage);
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up));
        view.setVisibility(View.VISIBLE);
    }

    void hideDelayed(final View view, int delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hide(view);
            }
        }, delay);
    }

    void hide(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_down));
            view.setVisibility(View.INVISIBLE);
        }
    }

    public class OnDeveloperClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            presenter.onDeveloperTapped(view);
        }
    }
}
