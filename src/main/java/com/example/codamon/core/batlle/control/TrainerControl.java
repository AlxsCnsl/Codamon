package com.example.codamon.core.batlle.control;

import com.example.codamon.core.batlle.move.Move;
import com.example.codamon.core.pokemon.Pokemon;
import javafx.stage.Stage;

public interface TrainerControl {

    public Move getMoveChoice( Pokemon pokemon);

    public Move getSwitchBeforeKo(Pokemon pokemon);

    void setStage(Stage stage);
}
