package com.schibsted.tapdadev;

public class Character {

    private final int id;
    private final int imageResource;

    public Character(int id, int imageResource) {
        this.id = id;
        this.imageResource = imageResource;
    }

    public int getId() {
        return id;
    }

    public int getImageResource() {
        return imageResource;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()){
            return false;
        }

        Character character = (Character) object;
        return getId() == character.getId();
    }
}
