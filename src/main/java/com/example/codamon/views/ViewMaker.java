package com.example.codamon.views;

import javafx.scene.Scene;

import java.io.IOException;

public interface ViewMaker {
    Scene getScene() throws IOException, InterruptedException;
}
