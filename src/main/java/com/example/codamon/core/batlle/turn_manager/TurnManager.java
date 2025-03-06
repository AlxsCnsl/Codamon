package com.example.codamon.core.batlle.turn_manager;

import com.example.codamon.core.Trainer;
import com.example.codamon.core.action.move.Move;
import com.example.codamon.core.batlle.Battle;
import com.example.codamon.core.batlle.Terrain;
import com.example.codamon.core.pokemon.Pokemon;

import java.util.Scanner;

import static com.example.codamon.core.GlobalTools.waitPressEnter;

public class TurnManager implements Turn {
    private static Scanner scanner = new Scanner(System.in);
    private MoveQueue moveQueue= new MoveQueue();

    public TurnManager(){

    }
    //TURN RULE________________________________________________________________
    public void executePhase(BattlePhase phase, Battle battle) {
        switch (phase){
            case START_PHASE -> startPhaseRule(battle);
            case SELECT_MOVE_PHASE -> selectMovePhaseRule(battle);
            case APPLY_MOVE_PHASE -> applyMovePhaseRule(battle);
            case END_PHASE -> endPhaseRule(battle);
            default -> System.out.println("#PHASE# is not correct");
        }

    }
    public void startPhaseRule(Battle battle) {

        waitPressEnter();
    }

    public void selectMovePhaseRule(Battle battle) {
        this.trainersMoveChoice(battle);
        waitPressEnter();
    }

    public void applyMovePhaseRule(Battle battle) {
        while(!moveQueue.getMoveQueue().isEmpty()){
            moveQueue.nextMoveExecute();
        }
        waitPressEnter();
    }

    public void endPhaseRule(Battle battle) {
        terrainsLog(battle);
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
                    moveQueue.addMoveInQueue(
                            trainer.getControl().getMoveChoice(pokemon));
                }
            }
        }
    }

    //ConsoleLOG_______________________________________________________________

    private void terrainsLog(Battle battle){
        for(Terrain terrain : battle.getTerrains()){
            System.out.println(terrain);
        }
    }

}
