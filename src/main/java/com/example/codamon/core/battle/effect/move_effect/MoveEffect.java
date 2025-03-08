package com.example.codamon.core.battle.effect.move_effect;

import com.example.codamon.core.battle.move.Move;
import com.example.codamon.core.battle.Battle;
import com.example.codamon.core.pokemon.Pokemon;

import java.util.ArrayList;

public interface MoveEffect {

    public void applyEffect(Move move, ArrayList<Pokemon> targets, Battle battle);

}
