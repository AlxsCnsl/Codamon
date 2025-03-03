package com.example.codamon.core.action.Effect;

import com.example.codamon.core.Battle;
import com.example.codamon.core.Pokemon;

public interface MoveEffect {
    public void applyEffect(Pokemon user, Pokemon target, Battle battle);
}
