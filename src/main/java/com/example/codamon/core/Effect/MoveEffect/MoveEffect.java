package com.example.codamon.core.Effect.MoveEffect;

import com.example.codamon.core.Battle;
import com.example.codamon.core.Pokemon;

public interface MoveEffect {
    public void applyEffect(Pokemon user, Pokemon target, Battle battle);

}
