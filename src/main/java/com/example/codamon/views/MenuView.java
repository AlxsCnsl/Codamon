package com.example.codamon.views;

import com.example.codamon.PokeApp;
import com.example.codamon.controllers.MenuController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.net.URL;

public class MenuView implements ViewMaker {

    private Stage stage;
    @FXML private Button battleButton;
    @FXML private Button teamBuilderButton;

    public MenuView(Stage stage) {
        this.stage = stage;
    }

    @Override
    public Scene getScene() throws IOException {
        if (getClass().getResource("/com/example/codamon/views/Menu-View.fxml") == null) {
            throw new IOException("Invalid URL");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource(
                        "/com/example/codamon/views/Menu-View.fxml"));
        Parent root = fxmlLoader.load();

        MenuController controller = fxmlLoader.getController();
        controller.setStage(stage);

        return new Scene(root, 1500, 800);
    }
}
