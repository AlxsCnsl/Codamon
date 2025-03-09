package com.example.codamon.controllers;

import com.example.codamon.PokeApp;
import com.example.codamon.models.SceneName;
import javafx.fxml.FXML;
import javafx.stage.Stage;

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