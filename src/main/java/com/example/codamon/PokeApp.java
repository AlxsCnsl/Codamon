package com.example.codamon;

import com.example.codamon.core.*;

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


        /*
        Pokemon pikachu = new Pokemon("Pikachu",
                TypeTools.typeList(Type.ELECTRIC),
                35, 55, 40, 50, 50,90);

        Pokemon togekiss = new Pokemon("Togekiss",
                TypeTools.typeList(Type.FLYING, Type.FAIRY),
                85, 50, 95, 120, 115, 80);
         */
        Pokemon pikachu = new Pokemon("Pikachu");
        Pokemon psykokwak = new Pokemon("Psykokwak");
        Pokemon togekiss = new Pokemon("Togekiss");

        PokemonTeam team1 = new PokemonTeam("Alexis");
        team1.addPokemon(pikachu);
        team1.addPokemon(psykokwak);

        PokemonTeam team2 = new PokemonTeam("Ethan");
        team2.addPokemon(togekiss);

        ClassicBattle battle = new ClassicBattle(team1, team2);
        //System.out.println(team1);System.out.println(team2);

    }

    public static void main(String[] args) {
        launch();
    }
}