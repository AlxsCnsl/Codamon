package com.example.codamon;

import com.example.codamon.core.*;

import com.example.codamon.core.TurnManager.ConsoleTurnManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PokeApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PokeApp.class.getResource("views/Poke-View.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Codamon");
        stage.setScene(scene);
        stage.show();


        Pokemon pikachu = new Pokemon("Pikachu");
        Pokemon psykokwak = new Pokemon("Psykokwak");
        Pokemon togekiss = new Pokemon("Togekiss");

        PokemonTeam team1 = new PokemonTeam("Alexis");
        team1.addPokemon(pikachu);
        team1.addPokemon(psykokwak);

        PokemonTeam team2 = new PokemonTeam("Ethan");
        team2.addPokemon(togekiss);

        ClassicBattle battle = new ClassicBattle(team1, team2, new ConsoleTurnManager());
        //System.out.println(team1);System.out.println(team2);
        System.out.println(battle.activesPokeomToString());

    }

    public static void main(String[] args) {
        launch();
    }
}