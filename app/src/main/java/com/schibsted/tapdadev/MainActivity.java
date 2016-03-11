package com.schibsted.tapdadev;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainPresenter.PresenterView {

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            presenter = new MainPresenter(this, getTargets(), getCharacters());
        } catch (Exception e) {
            presenter = null;
            Log.e("GAME", e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private List<Target> getTargets() {
        Target.OnTargetTappedListener targetListener = new Target.OnTargetTappedListener() {
            @Override
            public void onTargetTapped(Target target) {
                if (presenter != null) {
                    presenter.onTargetTapped(target);
                }
            }
        };

        int i = 0;
        List<Target> list = new ArrayList<>();
        list.add(new Target(i++, findImageViewById(R.id.l_developer0), targetListener));
        list.add(new Target(i++, findImageViewById(R.id.l_developer1), targetListener));
        list.add(new Target(i++, findImageViewById(R.id.l_developer2), targetListener));
        list.add(new Target(i++, findImageViewById(R.id.l_developer3), targetListener));

        return list;
    }

    private ImageView findImageViewById(int id) {
        return (ImageView) findViewById(id).findViewById(R.id.iv_developer);
    }

    private List<Character> getCharacters() {
        return CharacterFactory.withImages(
                R.drawable.dev_toni,
                R.drawable.dev_toni_punched,
                R.drawable.dev_roc,
                R.drawable.dev_roc_punched,
                R.drawable.dev_oscar,
                R.drawable.dev_oscar_punched
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (presenter != null) {
            presenter.pause();
        }
    }

    @Override
    public void show(Target target) {
        ImageView view = target.getImageView();
        if (view.getVisibility() == View.INVISIBLE) {
            view.setImageResource(target.getCharacter().getImageResource());
            setRandomUpAnimation(view);
            view.setVisibility(View.VISIBLE);
        }
    }

    private void setRandomUpAnimation(ImageView view) {
        if (getRandomValue(0, 1) == 0) {
            view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.overshoot_up));
        } else {
            view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up));
        }
    }

    @Override
    public void punch(Target target) {
        ImageView view = target.getImageView();
        view.setImageResource(target.getCharacter().getImageResourcePunched());
    }

    @Override
    public void hide(Target target) {
        ImageView view = target.getImageView();
        if (view.getVisibility() == View.VISIBLE) {
            setRandomDownAnimation(view);
            view.setVisibility(View.INVISIBLE);
        }
    }

    private void setRandomDownAnimation(ImageView view) {
        if (getRandomValue(0, 1) == 0) {
            view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.overshoot_down));
        } else {
            view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_down));
        }
    }

    @Override
    public void updateTime(String time) {
        TextView textView = (TextView)findViewById(R.id.tv_time_value);
        textView.setText(time);
    }

    @Override
    public void updateScore(String score) {
        TextView textView = (TextView)findViewById(R.id.tv_score_value);
        textView.setText(score);
    }

    @Override
    public void showFinalScore(String score) {
        Toast.makeText(this, getString(R.string.final_score, score), Toast.LENGTH_LONG).show();
    }

    private int getRandomValue(int minValue, int maxValue) {
        return (int) Math.floor(Math.random() * (maxValue - minValue + 1) + minValue);
    }
}
