package com.example.codamon;

import com.example.codamon.core.action.Category.Category;
import com.example.codamon.core.action.Move.Move;
import com.example.codamon.core.action.Move.MoveTools;
import com.example.codamon.models.SceneName;
import com.example.codamon.views.BattleView;
import com.example.codamon.views.MenuView;
import com.example.codamon.views.TeamBuilderView;
import com.example.codamon.core.*;

import com.example.codamon.core.turn_manager.ConsoleTurnManager;
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
        pikachu.addMove(MoveTools.newMove("Charge"));
        pikachu.addMove(MoveTools.newMove("Eclair"));
        pikachu.addMove(MoveTools.newMove("Fatal-Foudre"));
        pikachu.addMove(MoveTools.newMove("Cage-Eclair"));

        Pokemon raikou = new Pokemon("Raikou");
        pikachu.addMove(MoveTools.newMove("Fatal-Foudre"));

        Pokemon psykokwak = new Pokemon("Psykokwak");
        psykokwak.addMove(MoveTools.newMove("Charge"));
        psykokwak.addMove(MoveTools.newMove("Mimi-Queue"));

        Pokemon togekiss = new Pokemon("Togekiss");
        togekiss.addMove(MoveTools.newMove("Charge"));

        PokemonTeam team1 = new PokemonTeam("Alexis");
        team1.addPokemon(pikachu);
        team1.addPokemon(psykokwak);

        PokemonTeam team2 = new PokemonTeam("Ethan");
        team2.addPokemon(togekiss);

        Battle battle = new Battle(team1, team2, new ConsoleTurnManager());

        pikachu.getMoveByName("Fatal-Foudre").execute(pikachu, togekiss, battle);
        psykokwak.getMoveByName("Charge").execute(psykokwak, togekiss, battle);


        if(togekiss.getStatus() != null &&
                togekiss.getStatus().testIfNexMoveAccept()) {
            togekiss.getMoveByName("Charge").execute(togekiss, pikachu, battle);
        }


        System.out.println(team1);System.out.println(team2);
        //System.out.println(battle.activePokemonsToString());
        battle.run();

        //______________________________________________________________________


    }

    public static Map<SceneName, Scene> getScenes() {
        return scenes;
    }

    public static void main(String[] args) {
        launch();
    }
}