package com.example.codamon.core.battle.control;

import com.example.codamon.core.Trainer;
import com.example.codamon.core.battle.move.Move;
import com.example.codamon.core.pokemon.Pokemon;
import javafx.stage.Stage;

import java.util.concurrent.CompletableFuture;

public interface TrainerControl {

    public CompletableFuture<Move> getMoveChoiceAsync(Pokemon pokemon);

    public void switchBeforeKo(Trainer trainer);

    void setStage(Stage stage);
}
