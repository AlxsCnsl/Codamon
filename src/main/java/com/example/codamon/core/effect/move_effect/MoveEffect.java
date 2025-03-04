package com.example.codamon.core.effect.move_effect;

import com.example.codamon.core.batlle.Battle;
import com.example.codamon.core.pokemon.Pokemon;

public interface MoveEffect {
    public void applyEffect(Pokemon user, Pokemon target, Battle battle);

}
