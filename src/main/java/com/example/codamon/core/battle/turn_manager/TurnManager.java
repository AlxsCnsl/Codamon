package com.example.codamon.core.battle.turn_manager;

import com.example.codamon.core.Trainer;
import com.example.codamon.core.battle.Battle;
import com.example.codamon.core.battle.Terrain;
import com.example.codamon.core.pokemon.Pokemon;

import java.util.ArrayList;
import java.util.Scanner;

import static com.example.codamon.core.GlobalTools.waitPressEnter;

public class TurnManager implements Turn {
    private static Scanner scanner = new Scanner(System.in);
    private MoveQueue moveQueue= new MoveQueue();
    private MoveQueue switchQueue = new MoveQueue();

    public TurnManager(){

    }
    //TURN RULE________________________________________________________________
    public void executePhase(BattlePhase phase, Battle battle) {
        switch (phase){
            case START_PHASE -> startPhaseRule(battle);
            case SELECT_MOVE_PHASE -> selectMovePhaseRule(battle);
            case APPLY_MOVE_PHASE -> applyMovePhaseRule(battle);
            case END_PHASE -> endPhaseRule(battle);
            default -> System.err.println("#PHASE# is not correct");
        }

    }

    public void startPhaseRule(Battle battle) {
        waitPressEnter();
    }

    public void selectMovePhaseRule(Battle battle) {
        this.trainersMoveChoice(battle);
        System.out.println("#TURNMANAGER# queue befor sort : "+
                moveQueue.toString());
        moveQueue.sortQueue();
        System.out.println("#TURNMANAGER# queue after sort : "+
                moveQueue.toString());
        waitPressEnter();
    }

    public void applyMovePhaseRule(Battle battle) {
        while(!moveQueue.getMoveQueue().isEmpty()){
            Pokemon owner = moveQueue.getNextMove().getOwner();
            if(!owner.getIsAlive()){
                System.out.println("#TURNMANAGER# Move of "+
                        owner.getName()+ " is delete on Move Queue");
                moveQueue.deleteFirstMove();
            }else{
                    moveQueue.nextMoveExecute();
            }
        }
        waitPressEnter();
    }

    public void endPhaseRule(Battle battle) {
        switchIfPokemonKo(battle);
        terrainsLog(battle);
        if(checkEndBattleCondition(battle)){
            battle.stop();
        }
        waitPressEnter();
    }

    public void startBattleRule(Battle battle){
        for(Terrain terrain : battle.getTerrains()){
            for(Trainer trainer : terrain.getTrainersTeam()){
                trainer.sendPokemon(trainer.getPokemonByIndex(0));
            }
        }
    }

    //Tools____________________________________________________________________

    private void trainersMoveChoice(Battle battle){
        for(Terrain terrain : battle.getTerrains()){
            for(Trainer trainer : terrain.getTrainersTeam()){
                for(Pokemon pokemon : trainer.getActivePokemons()){
//                    moveQueue.addMoveInQueue(
//                            trainer.getControl().getMoveChoiceAsync(pokemon));
                    trainer.getControl().getMoveChoiceAsync(pokemon)
                            .thenAccept(move -> {
                                moveQueue.addMoveInQueue(move);
                                System.out.println("moveQueue : " + moveQueue.getMoveQueue());
                            });
                }
            }
        }
    }

    private void switchIfPokemonKo(Battle battle){//=====à Modifier Pour 2V2
        for(Terrain terrain : battle.getTerrains()){
            for(Trainer trainer : terrain.getTrainersTeam()){
                if(trainer.getTerrain().getActivePokemons().isEmpty()){
                    ArrayList<Pokemon> trainerTeam =
                            trainer.getPokemonsTeam().getPokemons();
                    for(Pokemon pokemon : trainerTeam ){
                        if(pokemon.getIsAlive()){
                            //aux moin 1 pkm vivant donc le trainer peut switch
                            trainer.getControl().switchBeforeKo(trainer);
                            return;
                        }
                    }
                }
            }
        }
    }

    private Boolean checkEndBattleCondition(Battle battle){
        for (Terrain terrain : battle.getTerrains()){
            for (Trainer trainer : terrain.getTrainersTeam()){
                if(!trainer.getTeamIsAlive()){
                    System.out.println("#TURNMANAGER# "+trainer.getName()+
                            " has no more Pokémon left to fight.");
                    return true;
                }
            }
        }
        return false;
    }

    //ConsoleLOG_______________________________________________________________

    private void terrainsLog(Battle battle){
        for(Terrain terrain : battle.getTerrains()){
            System.out.println(terrain);
        }
    }

}
