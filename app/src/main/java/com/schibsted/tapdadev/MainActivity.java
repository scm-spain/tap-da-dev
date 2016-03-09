package com.schibsted.tapdadev;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            presenter = new MainPresenter(this, getDeveloperLayouts(), getDeveloperResources());
        } catch (Exception e) {
            Log.e("GAME", e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
        }
    }

    private List<Integer> getDeveloperResources() {
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.dev_oscar);
        list.add(R.drawable.dev_roc);
        list.add(R.drawable.dev_toni);

        return list;
    }

    private List<ImageView> getDeveloperLayouts() {
        List<ImageView> list = new ArrayList<>();
        list.add(createDeveloper(R.id.l_developer0));
        list.add(createDeveloper(R.id.l_developer1));
        list.add(createDeveloper(R.id.l_developer2));
        list.add(createDeveloper(R.id.l_developer3));
        list.add(createDeveloper(R.id.l_developer4));
        list.add(createDeveloper(R.id.l_developer5));
        list.add(createDeveloper(R.id.l_developer6));
        list.add(createDeveloper(R.id.l_developer7));
        list.add(createDeveloper(R.id.l_developer8));

        return list;
    }

    private ImageView createDeveloper(int developerLayout) {
        ImageView developer = (ImageView) findViewById(developerLayout).findViewById(R.id.iv_developer);
        developer.setOnClickListener(new OnDeveloperClickListener());
        return developer;
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    public class OnDeveloperClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view instanceof ImageView) {
                presenter.onDeveloperTapped((ImageView)view);
            }
        }
    }
}
