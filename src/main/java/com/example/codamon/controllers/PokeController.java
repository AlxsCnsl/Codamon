package com.example.codamon.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class PokeController {
    @FXML private Text welcomeText;
    @FXML private Button button;

    public void initialize() {

    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("It's PIKACHU");
    }
}