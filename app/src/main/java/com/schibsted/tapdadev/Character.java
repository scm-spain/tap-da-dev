package com.schibsted.tapdadev;

public class Character {

    private final int id;
    private final int imageResource;
    private final int imageResourcePunched;

    public Character(int id, int imageResource, int imageResourcePunched) {
        this.id = id;
        this.imageResource = imageResource;
        this.imageResourcePunched = imageResourcePunched;
    }

    public int getId() {
        return id;
    }

    public int getImageResource() {
        return imageResource;
    }

    public int getImageResourcePunched() {
        return imageResourcePunched;
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
