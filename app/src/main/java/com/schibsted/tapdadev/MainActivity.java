package com.schibsted.tapdadev;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainPresenter.PresenterView {

    private MainPresenter presenter;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            presenter = new MainPresenter(this, getTargets(), getCharacters());
        } catch (Exception e) {
            Log.e("GAME", e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
        }
    }

    private List<Target> getTargets() {
        Target.OnTargetTappedListener targetListener = new Target.OnTargetTappedListener() {
            @Override
            public void onTargetTapped(Target target) {
                presenter.onTargetTapped(target);
            }
        };

        int i = 0;
        List<Target> list = new ArrayList<>();
        list.add(new Target(i++, findImageViewById(R.id.l_developer0), targetListener));
        list.add(new Target(i++, findImageViewById(R.id.l_developer1), targetListener));
        list.add(new Target(i++, findImageViewById(R.id.l_developer2), targetListener));
        /*
        list.add(new Target(i++, findImageViewById(R.id.l_developer3), targetListener));
        list.add(new Target(i++, findImageViewById(R.id.l_developer4), targetListener));
        list.add(new Target(i++, findImageViewById(R.id.l_developer5), targetListener));
        list.add(new Target(i++, findImageViewById(R.id.l_developer6), targetListener));
        list.add(new Target(i++, findImageViewById(R.id.l_developer7), targetListener));
        list.add(new Target(i++, findImageViewById(R.id.l_developer8), targetListener));
        */
        return list;
    }

    private ImageView findImageViewById(int id) {
        return (ImageView) findViewById(id).findViewById(R.id.iv_developer);
    }

    private List<Character> getCharacters() {
        return CharacterFactory.withImages(R.drawable.dev_toni, R.drawable.dev_roc, R.drawable.dev_oscar);
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

    public void show(Target target, Character character) {
        ImageView view = target.getImageView();
        if (view.getVisibility() == View.INVISIBLE) {
            view.setImageResource(character.getImageResource());
            view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up));
            view.setVisibility(View.VISIBLE);
        }
    }

    public void hide(Target target) {
        ImageView view = target.getImageView();
        if (view.getVisibility() == View.VISIBLE) {
            view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_down));
            view.setVisibility(View.INVISIBLE);
        }
    }
}
