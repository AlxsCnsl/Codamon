package com.example.codamon.core.batlle.control;

import com.example.codamon.core.batlle.move.Move;
import com.example.codamon.core.batlle.Battle;
import com.example.codamon.core.batlle.Terrain;
import com.example.codamon.core.pokemon.Pokemon;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class BotControl implements TrainerControl{
    public BotControl(){}


    public Move getMoveChoice( Pokemon pokemon){
        Random random = new Random();
        int firstChoice = random.nextInt(10);
        if(firstChoice == 0){
            return getRandSwitch(pokemon);
        }
        return getRandAttack(pokemon);
    }
    public Move getSwitchPokemonAbsent(){
      return null;
    };

    public Move getSwitchBeforeKo(Pokemon pokemon){
        return null;
    }

    private Move getRandSwitch( Pokemon pokemon){
        Random random = new Random();
        int index;
        ArrayList<Pokemon> team =
                pokemon.getOwner().getPokemonsTeam().getPokemons();
        Pokemon nextPokemon;
        do {
            index = random.nextInt(team.size());
            nextPokemon = team.get(index);
        }while(!nextPokemon.getIsAlive());
        pokemon.loadMove("Switch", nextPokemon);
        return pokemon.getMoveByName("Switch");
    }

    private Move getRandAttack( Pokemon pokemon){
        Random random = new Random();
        int index;
        int moveSize = pokemon.getMoves().size();
        index = random.nextInt(moveSize);
        Move move = pokemon.getMoveByIndex(index);
        Pokemon target = getRandTargetPokemon(pokemon);
        pokemon.loadMove(move.getName(), target);
        return move;
    }

    private Pokemon getRandTargetPokemon(Pokemon pokemon){
        Pokemon target = null;
         Battle battle = pokemon.getTerrain().getBattle();
         for(Terrain terrain : battle.getTerrains()){//Si : 2 terrain ↓↓↓↓↓↓
             if(!terrain.equals(pokemon.getTerrain())){
                 Random random = new Random();
                 int size = terrain.getActivePokemons().size();
                 int index =  random.nextInt(size);
                 target =terrain.getActivePokemons().get(index);
             }
         }
        return target;
    }

    public void setStage(Stage stage){};

}
