package com.example.codamon.core.batlle.control;

import com.example.codamon.core.batlle.move.Move;
import com.example.codamon.core.pokemon.Pokemon;
import javafx.stage.Stage;

import java.util.concurrent.CompletableFuture;

public interface TrainerControl {

    public CompletableFuture<Move> getMoveChoiceAsync(Pokemon pokemon);

    public Move getSwitchBeforeKo(Pokemon pokemon);

    void setStage(Stage stage);
}
