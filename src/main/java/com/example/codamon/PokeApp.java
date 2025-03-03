package com.example.codamon;

import com.example.codamon.core.action.Category;
import com.example.codamon.core.action.Effect.MoveEffect;
import com.example.codamon.core.action.Effect.Status.StatusEnum;
import com.example.codamon.core.action.Effect.StatusGiverEffect;
import com.example.codamon.core.action.Move;
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
import java.util.ArrayList;
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
        pikachu.addMove(new Move("Charge", Type.NORMAL,Category.PHYSICAL,40, 100, 35, 0));
        pikachu.addMove(new Move("Eclair", Type.ELECTRIC,Category.SPECIAL,40, 100, 30, 0));
        pikachu.addMove(new Move("Fatal-Foudre", Type.ELECTRIC,Category.SPECIAL,110, 70, 10, 0));
        ArrayList<MoveEffect> cageEclaireEffects = new ArrayList<>();
        cageEclaireEffects.add(new StatusGiverEffect(StatusEnum.PARALYSIS));
        pikachu.addMove(new Move("Cage-Eclaire", Type.ELECTRIC,Category.STATUS,0, 90, 10, 0, cageEclaireEffects));

        Pokemon raikou = new Pokemon("Raikou");
        raikou.addMove(new Move("Fatal-Foudre", Type.ELECTRIC,Category.SPECIAL,110, 70, 10, 0));

        Pokemon psykokwak = new Pokemon("Psykokwak");
        psykokwak.addMove(new Move("Charge", Type.NORMAL,Category.PHYSICAL,40, 100, 35, 0));

        Pokemon togekiss = new Pokemon("Togekiss");
        togekiss.addMove(new Move("Charge", Type.NORMAL,Category.PHYSICAL,40, 100, 35, 0));

        PokemonTeam team1 = new PokemonTeam("Alexis");
        team1.addPokemon(pikachu);
        team1.addPokemon(psykokwak);

        PokemonTeam team2 = new PokemonTeam("Ethan");
        team2.addPokemon(togekiss);

        Battle battle = new Battle(team1, team2, new ConsoleTurnManager());

        pikachu.getMoveByIndex(3).execute(pikachu, togekiss, battle);

        if(togekiss.getStatus().testIfNexMoveAccept()) {
            togekiss.getMoveByIndex(0).execute(togekiss, pikachu, battle);
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