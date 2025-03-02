package com.example.codamon.controllers;

import com.example.codamon.PokeApp;
import com.example.codamon.models.SceneName;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TeamBuilderController {

    public TeamBuilderController() {

    }

    private Stage stage;

    public void setStage(Stage stage) {
        if (stage == null) {
            throw new IllegalArgumentException("Stage cannot be null");
        }

        this.stage = stage;
    }

    @FXML
    public void onExitButtonClick() {
        stage.setScene(PokeApp.getScenes().get(SceneName.MENU));
    }

    @FXML
    public void onAddPokemonButtonClick() {

    }
}