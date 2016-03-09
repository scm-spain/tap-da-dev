package com.schibsted.tapdadev;

public class CharacterFactory {

    public static Character[] withImages(int... resources) {
        Character[] characters = new Character[resources.length];
        for (int i = 0; i < resources.length; i++) {
            characters[i] = new Character(resources[i]);
        }
        return characters;
    }
}
