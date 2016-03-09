package com.schibsted.tapdadev;

import android.view.View;
import android.widget.ImageView;

public class Target {

    private final ImageView imageView;

    public Target(ImageView imageView, final OnTargetTappedListener listener) {
        this.imageView = imageView;
        this.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTargetTapped(Target.this);
            }
        });
    }

    public ImageView getImageView() {
        return imageView;
    }

    public interface OnTargetTappedListener {
        void onTargetTapped(Target target);
    }
}
