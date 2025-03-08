package com.example.codamon.core.battle.turn_manager;
import com.example.codamon.core.Trainer;
import com.example.codamon.core.battle.Battle;
import com.example.codamon.core.battle.Terrain;
import com.example.codamon.core.battle.move.Move;
import com.example.codamon.core.pokemon.Pokemon;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

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
        System.out.println("#TURNMANAGER# queue befor sort : "+
                moveQueue.toString());
        moveQueue.sortQueue();
        System.out.println("#TURNMANAGER# queue after sort : "+
                moveQueue.toString());
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
        Thread.sleep(1000);
    }

    public void endPhaseRule(Battle battle) throws InterruptedException {
        System.out.println("END PHASE executed");
        switchIfPokemonKo(battle);
        terrainsLog(battle);
        if(checkEndBattleCondition(battle)){
            battle.stop();
        }
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
        ArrayList<Trainer> trainers = new ArrayList<>();

        for(Terrain terrain : battle.getTerrains()){
            //                for(Pokemon pokemon : trainer.getActivePokemons()){
            //                    System.out.println("trainer control : " + trainer.getControl());
            ////                    moveQueue.addMoveInQueue(
            ////                            trainer.getControl().getMoveChoiceAsync(pokemon));
            //                    // Assuming getMoveChoiceAsync returns a CompletableFuture<Move>
            //                    trainer.getControl().getMoveChoiceAsync(pokemon)
            //                            .thenAccept(move -> {
            //                                moveQueue.addMoveInQueue(move);
            //                                System.out.println("moveQueue : " + moveQueue.getMoveQueue());
            //                            });
            //                }
            trainers.addAll(terrain.getTrainersTeam());
        }

        CompletableFuture<Move> humanMoveFuture =
                trainers.getFirst().getControl().getMoveChoiceAsync(
                        trainers.getFirst().getActivePokemons().getFirst());
        CompletableFuture<Move> botMoveFuture =
                trainers.getLast().getControl().getMoveChoiceAsync(
                        trainers.getLast().getActivePokemons().getFirst());

        // Wait until both moves are chosen
        CompletableFuture<Void> bothMovesChosen =
                CompletableFuture.allOf(humanMoveFuture, botMoveFuture);

        bothMovesChosen.thenRun(() -> {
            Move humanMove = humanMoveFuture.join();
            Move botMove = botMoveFuture.join();

            moveQueue.addMoveInQueue(humanMove);
            moveQueue.addMoveInQueue(botMove);
            System.out.println("moveQueue : " + moveQueue.getMoveQueue());
        });

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
