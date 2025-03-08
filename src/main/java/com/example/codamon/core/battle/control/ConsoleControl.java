package com.example.codamon.core.battle.control;

import com.example.codamon.core.Trainer;
import com.example.codamon.core.battle.move.Move;
import com.example.codamon.core.pokemon.Pokemon;
import javafx.stage.Stage;

import java.util.concurrent.CompletableFuture;

public class ConsoleControl implements TrainerControl{
    public String moveChoice(Trainer trainer, Pokemon pokemon){
        return null;
    }

    public CompletableFuture<Move> getMoveChoiceAsync(Pokemon pokemon){
        return null;
    }

    public void switchBeforeKo(Trainer trainer){
    }

    public void setStage(Stage stage){}

}
