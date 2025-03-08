package com.example.codamon.core.batlle.turn_manager;
import com.example.codamon.core.Trainer;
import com.example.codamon.core.batlle.Battle;
import com.example.codamon.core.batlle.Terrain;
import com.example.codamon.core.pokemon.Pokemon;
import javafx.stage.Stage;

import java.util.Random;

import static com.example.codamon.core.GlobalTools.waitPressEnter;

public class GraphicTurnManager implements Turn {
    private Stage stage;
    private MoveQueue moveQueue= new MoveQueue();

    public void setStage(Stage stage) {
        if(stage == null){
            throw new IllegalArgumentException("Stage cannot be null");
        }
        this.stage = stage;
    }

    public GraphicTurnManager() {}

    //TURN RULE________________________________________________________________
    public void executePhase(BattlePhase phase, Battle battle) throws InterruptedException {
        switch (phase){
            case START_PHASE -> startPhaseRule(battle);
            case SELECT_MOVE_PHASE -> selectMovePhaseRule(battle);
            case APPLY_MOVE_PHASE -> applyMovePhaseRule(battle);
            case END_PHASE -> endPhaseRule(battle);
            default -> System.out.println("#PHASE# is not correct");
        }
    }

    public void startPhaseRule(Battle battle) throws InterruptedException {
        System.out.println("START PHASE executed");
        Thread.sleep(1000);
    }

    public void selectMovePhaseRule(Battle battle) throws InterruptedException {
        System.out.println("SELECT MOVE PHASE executed");
        this.trainersMoveChoice(battle);
        Thread.sleep(1000);
    }

    public void applyMovePhaseRule(Battle battle) throws InterruptedException {
        System.out.println("APPLY MOVE PHASE executed");
        while(!moveQueue.getMoveQueue().isEmpty()){
            moveQueue.nextMoveExecute();
        }
        Thread.sleep(1000);
    }

    public void endPhaseRule(Battle battle) throws InterruptedException {
        System.out.println("END PHASE executed");
        terrainsLog(battle);
        Thread.sleep(1000);
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
                    System.out.println("trainer control : " + trainer.getControl());
//                    moveQueue.addMoveInQueue(
//                            trainer.getControl().getMoveChoiceAsync(pokemon));
                    // Assuming getMoveChoiceAsync returns a CompletableFuture<Move>
                    trainer.getControl().getMoveChoiceAsync(pokemon)
                            .thenAccept(move -> {
                                moveQueue.addMoveInQueue(move);
                                System.out.println("moveQueue : " + moveQueue.getMoveQueue());
                            });
                }
            }
        }
    }

    private void switchIfPokemonKo(Battle battle){
        for(Terrain terrain : battle.getTerrains()){
            for(Trainer trainer : terrain.getTrainersTeam()){
                for(Pokemon pokemon : trainer.getActivePokemons()){

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
