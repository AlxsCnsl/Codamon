package com.example.codamon.views;

import com.example.codamon.PokeApp;
import com.example.codamon.controllers.BattleController;
import com.example.codamon.core.Trainer;
import com.example.codamon.core.battle.GraphicBattle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class BattleView implements ViewMaker {
    private Stage stage;
    public GraphicBattle graphicBattle;

    public BattleView(Stage stage, GraphicBattle graphicBattle) {
        this.stage = stage;
        this.graphicBattle = graphicBattle;
    }

    @Override
    public Scene getScene() throws IOException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                PokeApp.class.getResource(
                        "/com/example/codamon/views/Battle-View.fxml"));
        Parent root = fxmlLoader.load();

        HashMap<String, Object> userData = (HashMap<String, Object>) stage.getUserData();
        Trainer pokemonTrainer = (Trainer) userData.get("pokemonTrainer");

        BattleController controller = fxmlLoader.getController();
        pokemonTrainer.setController(controller);
        controller.setStage(stage);
        controller.setGraphicBattle(graphicBattle);

        return new Scene(root, 1500, 800);
    }
}
