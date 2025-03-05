package com.example.codamon;

import com.example.codamon.core.Trainer;
import com.example.codamon.core.batlle.Battle;
import com.example.codamon.core.pokemon.Pokemon;
import com.example.codamon.core.pokemon.Team;
import com.example.codamon.models.SceneName;
import com.example.codamon.views.BattleView;
import com.example.codamon.views.MenuView;
import com.example.codamon.views.TeamBuilderView;

import com.example.codamon.core.batlle.turn_manager.ConsoleTurnManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PokeApp extends Application {

    private static Map<SceneName, Scene> scenes = new HashMap<>();

    @Override
    public void start(Stage stage) throws IOException {
        Trainer pokemonTrainer = new Trainer("Alexis");
        HashMap<String, Object> userData = new HashMap<>();
        userData.put("pokemonTrainer", pokemonTrainer);
        stage.setUserData(userData);

        scenes.put(
                SceneName.MENU, new MenuView(stage).getScene());
        scenes.put(
                SceneName.BATTLE, new BattleView(stage).getScene());
        scenes.put(
                SceneName.TEAMBUILDER, new TeamBuilderView(stage).getScene());

        stage.setScene(scenes.get(SceneName.TEAMBUILDER));
        stage.setTitle("MENU");
        stage.show();

    }

    public static Map<SceneName, Scene> getScenes() {
        return scenes;
    }

    public static void main(String[] args) {
        launch();
    }
}
