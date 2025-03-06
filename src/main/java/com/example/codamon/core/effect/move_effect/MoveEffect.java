package com.example.codamon.core.effect.move_effect;

import com.example.codamon.core.action.move.Move;
import com.example.codamon.core.batlle.Battle;
import com.example.codamon.core.pokemon.Pokemon;

import java.util.ArrayList;

public interface MoveEffect {

    public void applyEffect(Move move, ArrayList<Pokemon> targets, Battle battle);

}
