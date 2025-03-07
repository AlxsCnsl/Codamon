package com.example.codamon.core.batlle.control;

import com.example.codamon.core.batlle.move.Move;
import com.example.codamon.core.pokemon.Pokemon;
import javafx.stage.Stage;

public class GraphicControl implements TrainerControl {
    private Stage stage;

    public GraphicControl(){}

    public Move getMoveChoice(Pokemon pokemon){
        return null;
    }

    public Move getSwitchBeforeKo(Pokemon pokemon){
        return null;
    }

    public void setStage(Stage stage) {
        if(stage == null){
            throw new IllegalArgumentException("Stage cannot be null");
        }
        this.stage = stage;
    }

}

