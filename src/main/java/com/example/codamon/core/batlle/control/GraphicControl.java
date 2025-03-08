package com.example.codamon.core.batlle.control;

import com.example.codamon.core.Trainer;
import com.example.codamon.core.batlle.move.Move;
import com.example.codamon.core.pokemon.Pokemon;
import javafx.stage.Stage;

import java.util.concurrent.CompletableFuture;

public class GraphicControl implements TrainerControl {
    private Stage stage;

    public GraphicControl(){}

    public CompletableFuture<Move> getMoveChoiceAsync(Pokemon pokemon){
        return null;
    }

    public CompletableFuture<Void> switchBeforeKoAsync(Trainer trainer){
        return null;
    }

    public void setStage(Stage stage) {
        if(stage == null){
            throw new IllegalArgumentException("Stage cannot be null");
        }
        this.stage = stage;
    }

    @Override
    public void updatePokemons() {

    }

}

