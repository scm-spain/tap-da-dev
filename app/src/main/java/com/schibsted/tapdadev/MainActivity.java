package com.schibsted.tapdadev;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    View developer0;
    View developer1;
    View developer2;

    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        developer0 = findViewById(R.id.l_developer0).findViewById(R.id.iv_developer);
        developer1 = findViewById(R.id.l_developer1).findViewById(R.id.iv_developer);
        developer2 = findViewById(R.id.l_developer2).findViewById(R.id.iv_developer);

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
