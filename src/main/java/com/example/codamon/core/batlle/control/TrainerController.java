package com.example.codamon.core.batlle.control;

import com.example.codamon.core.Trainer;
import com.example.codamon.core.batlle.move.Move;
import com.example.codamon.core.pokemon.Pokemon;
import javafx.stage.Stage;

import java.util.concurrent.CompletableFuture;

public interface TrainerController {

    public CompletableFuture<Move> getMoveChoiceAsync(Pokemon pokemon);

    public CompletableFuture<Void> switchBeforeKo(Trainer trainer);

    void setStage(Stage stage) throws InterruptedException;

    public void updatePokemons();
    public void updateHistory(String text, int size);
}
