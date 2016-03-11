package com.schibsted.tapdadev;

import java.util.ArrayList;
import java.util.List;

public class CharacterFactory {

    public static List<Character> withImages(int... resources) {
        List<Character> characters = new ArrayList<>();
        for (int i = 0; i < resources.length; i += 2) {
            characters.add(new Character(i, resources[i], resources[i+1]));
        }
        return characters;
    }
}
