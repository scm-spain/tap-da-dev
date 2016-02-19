package com.schibsted.tapdadev;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

        presenter = new MainPresenter(this, developer0, developer1, developer2);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    public class OnDeveloperClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            presenter.onDeveloperTapped(view);
        }
    }
}
