package com.example.codamon.core.batlle.control;

import com.example.codamon.core.action.move.Move;
import com.example.codamon.core.pokemon.Pokemon;
import javafx.stage.Stage;

public interface TrainerControl {

    public Move getMoveChoice( Pokemon pokemon);

    void setStage(Stage stage);
}
