package com.example.codamon.core.batlle.turn_manager;
import com.example.codamon.core.Trainer;
import com.example.codamon.core.batlle.Battle;
import com.example.codamon.core.batlle.Terrain;
import com.example.codamon.core.batlle.effect.batlle_effect.status.Status;
import com.example.codamon.core.batlle.move.Move;
import com.example.codamon.core.pokemon.Pokemon;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

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

        // Perform any phase–end effects first
        endPhasesEffect(battle);

        // Now, if a switch is needed, wait for the switch to be made
        switchIfPokemonKoAsync(battle)
                .thenRun(() -> {
                    // Switch is now complete—you can proceed with the remainder of your end phase.
                    terrainsLog(battle);
                    if (checkEndBattleCondition(battle)) {
                        battle.stop();
                    }
                    terrainsLog(battle);
                })
                .exceptionally(ex -> {
                    ex.printStackTrace();
                    return null;
                });

        // Remove the sleep if you're fully asynchronous; otherwise, consider scheduling further actions.
        Thread.sleep(1000);
    }

    private void endPhasesEffect(Battle battle){
        for(Terrain terrain: battle.getTerrains()){
            for (Pokemon pokemon : terrain.getActivePokemons()){
                StatusEffect(pokemon);
            }
        }
    }
    private void StatusEffect(Pokemon pokemon){
        if(pokemon.asMajorStatus()){
            pokemon.getMajorStatus().endPhaseDamage();
        }
    }

    public void startBattleRule(Battle battle){
        for(Terrain terrain : battle.getTerrains()){
            for(Trainer trainer : terrain.getTrainersTeam()){
                trainer.sendPokemon(trainer.getPokemonByIndex(0));
            }
        }
    }

    //Tools____________________________________________________________________
    private void trainersMoveChoice(Battle battle) {
        ArrayList<Trainer> trainers = new ArrayList<>();
        for (Terrain terrain : battle.getTerrains()) {
            trainers.addAll(terrain.getTrainersTeam());
        }

        CompletableFuture<Move> humanMoveFuture =
                trainers.getFirst().getControl().getMoveChoiceAsync(
                        trainers.getFirst().getActivePokemons().getFirst());
        CompletableFuture<Move> botMoveFuture =
                trainers.getLast().getControl().getMoveChoiceAsync(
                        trainers.getLast().getActivePokemons().getFirst());

        // Wait for both moves to be chosen
        CompletableFuture<Void> bothMovesChosen =
                CompletableFuture.allOf(humanMoveFuture, botMoveFuture);

        bothMovesChosen.thenCompose(v -> {
            // Once both moves are gathered, add them to the move queue.
            Move humanMove = humanMoveFuture.join();
            Move botMove = botMoveFuture.join();
            moveQueue.addMoveInQueue(humanMove);
            moveQueue.addMoveInQueue(botMove);
            System.out.println("moveQueue : " + moveQueue.getMoveQueue());
            try {
                battle.executeCurrentPhase(); // APPLY MOVE PHASE
                battle.executeCurrentPhase(); // END PHASE
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // Now, instead of immediately advancing the phases,
            // check if a switch is needed. For example, if the human trainer’s active Pokémon is KO:
            Trainer humanTrainer = trainers.getFirst();
            if (humanTrainer.getTerrain().getActivePokemons().isEmpty()) {
                // The human needs to switch; wait for the switch to complete.
                System.out.println("A switch is needed. Waiting for the player to select...");
                return humanTrainer.getControl().switchBeforeKoAsync(humanTrainer)
                        .thenApply(x -> null); // Chain to a Void future.
            } else {
                // Otherwise, return a completed future so we continue immediately.
                return CompletableFuture.completedFuture(null);
            }
        }).thenRun(() -> {
            // Now all prerequisites are fulfilled—both moves are chosen and any switch is done.
            // Advance the battle phases.
            Platform.runLater(() -> {
                try {
                    battle.executeCurrentPhase(); // START PHASE
                    updatePokemons(battle);
                    battle.executeCurrentPhase();  // SELECT MOVE PHASE
                    updatePokemons(battle);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        });
    }


    private void updatePokemons(Battle battle) {
        battle.getPlayersTeams().getFirst().getFirst().getControl().updatePokemons();
    }

    private CompletableFuture<Void> switchIfPokemonKoAsync(Battle battle) {
        for (Terrain terrain : battle.getTerrains()) {
            for (Trainer trainer : terrain.getTrainersTeam()) {
                if (trainer.getTerrain().getActivePokemons().isEmpty()) {
                    // If at least one Pokémon is KO and there is at least one living Pokémon,
                    // ask the trainer to select a Pokémon via the UI.
                    for (Pokemon pokemon : trainer.getPokemonsTeam().getPokemons()) {
                        if (pokemon.getIsAlive()) {
                            System.out.println("switchIfPokemonKoAsync: Requesting switch for trainer " + trainer);
                            return trainer.getControl().switchBeforeKoAsync(trainer);
                        }
                    }
                }
            }
        }
        // No switch needed: return an already completed future.
        return CompletableFuture.completedFuture(null);
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
