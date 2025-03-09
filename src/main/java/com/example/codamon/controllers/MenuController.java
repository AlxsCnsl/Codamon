package com.example.codamon.controllers;

import com.example.codamon.PokeApp;
import com.example.codamon.core.Trainer;
import com.example.codamon.core.batlle.GraphicBattle;
import com.example.codamon.core.batlle.control.BotControl;
import com.example.codamon.core.batlle.turn_manager.GraphicTurnManager;
import com.example.codamon.core.pokemon.Pokemon;
import com.example.codamon.models.SceneName;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;

public class MenuController {

    public MenuController() {

    }

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

//    @FXML private Text creditText;
//    @FXML private Button battleButton;
//    @FXML private Button teamBuilderButton;

    @FXML
    public void onBattleButtonClick() {
        stage.setScene(PokeApp.getScenes().get(SceneName.BATTLE));
    }

    @FXML
    public void onTeamBuilderButtonClick() {
        stage.setScene(PokeApp.getScenes().get(SceneName.TEAMBUILDER));
    }
}