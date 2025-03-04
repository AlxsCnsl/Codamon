package com.example.codamon;

import com.example.codamon.models.SceneName;
import com.example.codamon.views.BattleView;
import com.example.codamon.views.MenuView;
import com.example.codamon.views.TeamBuilderView;
import com.example.codamon.core.*;

import com.example.codamon.core.TurnManager.ConsoleTurnManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.HashMap;
import java.util.Map;

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
        
        Pokemon pikachu = new Pokemon("Pikachu");
        Pokemon psykokwak = new Pokemon("Psykokwak");
        Pokemon togekiss = new Pokemon("Togekiss");
        Pokemon lugulabre = new Pokemon("Lugulabre");

        PokemonTeam team1 = new PokemonTeam("Alexis");
        team1.addPokemon(pikachu);
        team1.addPokemon(psykokwak);

        PokemonTeam team2 = new PokemonTeam("Ethan");
        team2.addPokemon(togekiss);
        team2.addPokemon(lugulabre);

        System.out.println(togekiss.getTypes());
        System.out.println(togekiss.getName());

        ClassicBattle battle = new ClassicBattle(team1, team2, new ConsoleTurnManager());
        //System.out.println(team1);System.out.println(team2);
        System.out.println(battle.activesPokeomToString());

    }

    public static Map<SceneName, Scene> getScenes() {
        return scenes;
    }

    public static void main(String[] args) {
        launch();
    }
}