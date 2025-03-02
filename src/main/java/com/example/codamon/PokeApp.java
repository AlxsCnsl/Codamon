package com.example.codamon;

import com.example.codamon.models.SceneName;
import com.example.codamon.views.BattleView;
import com.example.codamon.views.MenuView;
import com.example.codamon.views.TeamBuilderView;
import com.example.codamon.core.*;

import com.example.codamon.core.TurnManager.ConsoleTurnManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PokeApp extends Application {

    private static Map<SceneName, Scene> scenes = new HashMap<>();

    @Override
    public void start(Stage stage) throws IOException {
        scenes.put(
                SceneName.MENU, new MenuView(stage).getScene());
        scenes.put(
                SceneName.BATTLE, new BattleView(stage).getScene());
        scenes.put(
                SceneName.TEAMBUILDER, new TeamBuilderView(stage).getScene());

        stage.setScene(scenes.get(SceneName.TEAMBUILDER));
        stage.setTitle("MENU");
        stage.show();


        //ALEXIS________________________________________________________________
        Scanner scanner = new Scanner(System.in); // Création du scanner

        Pokemon pikachu = new Pokemon("Pikachu");
        Pokemon psykokwak = new Pokemon("Psykokwak");
        Pokemon togekiss = new Pokemon("Togekiss");

        PokemonTeam team1 = new PokemonTeam("Alexis");
        team1.addPokemon(pikachu);
        team1.addPokemon(psykokwak);

        PokemonTeam team2 = new PokemonTeam("Ethan");
        team2.addPokemon(togekiss);

        Battle battle = new Battle(team1, team2, new ConsoleTurnManager());
        //System.out.println(team1);System.out.println(team2);
        battle.run();
        System.out.println(battle.activesPokeomToString());
        //______________________________________________________________________


    }

    public static Map<SceneName, Scene> getScenes() {
        return scenes;
    }

    public static void main(String[] args) {
        launch();
    }
}