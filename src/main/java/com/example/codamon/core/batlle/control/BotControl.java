package com.example.codamon.core.batlle.control;

import com.example.codamon.core.Trainer;
import com.example.codamon.core.batlle.move.Move;
import com.example.codamon.core.batlle.Battle;
import com.example.codamon.core.batlle.Terrain;
import com.example.codamon.core.pokemon.Pokemon;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class BotControl implements TrainerControl{
    public BotControl(){}


    public CompletableFuture<Move> getMoveChoiceAsync(Pokemon pokemon){
        Random random = new Random();
        int firstChoice = random.nextInt(10);
        if(firstChoice == 0){
            return CompletableFuture.completedFuture(getRandSwitch(pokemon));
        }
        return CompletableFuture.completedFuture(getRandAttack(pokemon));
    }
    public Move getSwitchPokemonAbsent(){
      return null;
    };


    public CompletableFuture<Void> switchBeforeKoAsync(Trainer trainer){
        trainer.sendPokemon(getRandPokemonAlive(trainer));
        return null;
    }

    public void setStage(Stage stage){}

    @Override
    public void updatePokemons() {

    }

    ;

    //Bot Tools________________________________________________________________

    private Pokemon getRandPokemonAlive(Trainer trainer){
        Random random = new Random();
        int index;
        ArrayList<Pokemon> team = trainer.getPokemonsTeam().getPokemons();
        Pokemon randPokemon = null;
        do {
            index = random.nextInt(team.size());
            randPokemon = team.get(index);
        }while(randPokemon == null || !randPokemon.getIsAlive());
        return randPokemon;
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
        }while(!nextPokemon.getIsAlive() && !nextPokemon.equals(pokemon));
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

}
