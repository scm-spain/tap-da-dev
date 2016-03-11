package com.schibsted.tapdadev;

import android.view.View;
import android.widget.ImageView;

public class Target {

    private final int id;
    private final ImageView imageView;

    private Character character;
    private boolean punched;

    public Target(int id, ImageView imageView, final OnTargetTappedListener listener) {
        this.id = id;
        this.imageView = imageView;
        this.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTargetTapped(Target.this);
            }
        });
    }

    public int getId() {
        return id;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public boolean isPunched() {
        return punched;
    }

    public void setPunched(boolean punched) {
        this.punched = punched;
    }

    public interface OnTargetTappedListener {
        void onTargetTapped(Target target);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()){
            return false;
        }

        Target target = (Target) object;
        return getId() == target.getId();
    }
}
