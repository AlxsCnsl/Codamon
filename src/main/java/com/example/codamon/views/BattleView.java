package com.example.codamon.views;

import com.example.codamon.PokeApp;
import com.example.codamon.controllers.BattleController;
import com.example.codamon.controllers.MenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class BattleView implements ViewMaker {
    private Stage stage;

    public BattleView(Stage stage) {
        this.stage = stage;
    }

    @Override
    public Scene getScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                PokeApp.class.getResource(
                        "/com/example/codamon/views/Battle-View.fxml"));
        Parent root = fxmlLoader.load();

        BattleController controller = fxmlLoader.getController();
        controller.setStage(stage);

        return new Scene(root, 1500, 800);
    }
}
