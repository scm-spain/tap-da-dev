package com.schibsted.tapdadev;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    View developer1;
    View developer2;
    View developer3;

    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);

        developer1 = findViewById(R.id.l_developer1).findViewById(R.id.iv_developer);
        developer2 = findViewById(R.id.l_developer2).findViewById(R.id.iv_developer);
        developer3 = findViewById(R.id.l_developer3).findViewById(R.id.iv_developer);

        View.OnClickListener listener = new OnDeveloperClickListener();
        developer1.setOnClickListener(listener);
        developer2.setOnClickListener(listener);
        developer3.setOnClickListener(listener);
    }

    public class OnDeveloperClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            presenter.hide(view);
        }
    }
}
