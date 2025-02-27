package com.example.codamon;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PokeController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("It's PIKACHU");
    }
}