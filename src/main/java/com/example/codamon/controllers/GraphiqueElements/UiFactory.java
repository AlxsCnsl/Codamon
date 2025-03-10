package com.example.codamon.controllers.GraphiqueElements;


import com.example.codamon.core.batlle.effect.batlle_effect.status.StatusTools;
import com.example.codamon.core.batlle.move.Move;
import com.example.codamon.core.pokemon.Pokemon;
import com.example.codamon.core.type.Type;
import com.example.codamon.core.type.TypeTools;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;

public class UiFactory {


    public static Image getPokemonSprite(String pokemonName, String direction) {
        URL pokemonSpriteURL = UiFactory.class.getResource(
                "/com/example/codamon/sprites/Pokemon/" +
                        pokemonName.toLowerCase() + "/" +
                        pokemonName.toLowerCase() + "_" + direction + ".png");
        if (pokemonSpriteURL == null) {
            throw new IllegalArgumentException("Sprite introuvable pour : " + pokemonName + " (" + direction + ")");
        }
        return new Image(pokemonSpriteURL.toExternalForm());
    }


    public static VBox newPokemonBattleCard(Pokemon pokemonSwitch){
        VBox cardBorder = new VBox();
        VBox card = new VBox();
        HBox pokemonSelfId = newSelfIdBox(pokemonSwitch) ;
        HBox pokemonHealth = newHealthInfo(pokemonSwitch);
        card.getChildren().add(pokemonSelfId);
        card.getChildren().add(pokemonHealth);
        cardBorder.getChildren().add(card);
        return cardBorder;
    }

    public static HBox newAttackButton(Move move){
        HBox attack = new HBox();
        Text name = new Text(move.getName());
        Text pp = new Text(STR."\{move.getCurrentPp()}/\{move.getPp()}");
        attack.getChildren().add(name);
        attack.getChildren().add(pp);
        attack.setStyle(STR."-fx-background-color: \{
                TypeTools.stringTypeToColor(
                        TypeTools.typeToString(move.getType()))};");
        return attack;
    }


    // Pokemon Card____________________________________________________________
    private static HBox newHealthInfo(Pokemon pokemon){
        HBox healthBox = new HBox();
        VBox hpBox= new VBox();
        VBox statusBox = newStatue(pokemon);
        ProgressBar hpBar = new ProgressBar((double) pokemon.
                getCurrentHP()/pokemon.getCurrentState("HP"));
        Text hpText = new Text(STR."\{pokemon.
                getCurrentHP()}/\{pokemon.getCurrentState("HP")}");
        hpBox.getChildren().add(hpBar);
        hpBox.getChildren().add(hpText);
        healthBox.getChildren().add(hpBox);
        healthBox.getChildren().add(statusBox);
        return healthBox;

    }

    private static HBox newSelfIdBox(Pokemon pokemon){
        HBox pokemonSelfId = new HBox();//Img + name + lvl + Types
        ImageView pokemonMiniature = new ImageView(
                getPokemonSprite(pokemon.getName(),"miniature"));
        VBox pokemonString = newSelfIdStringBox(pokemon);//name + lvl
        VBox pokemonTypes = newTypesBox(pokemon);
        pokemonSelfId.getChildren().add(pokemonMiniature);
        pokemonSelfId.getChildren().add(pokemonString);
        pokemonSelfId.getChildren().add(pokemonTypes);
        return pokemonSelfId;
    }

    private static VBox newSelfIdStringBox(Pokemon pokemon){
        VBox pokemonSelfId = new VBox();
        Text name = new Text(pokemon.getName());
        Text lvl = new Text(STR."LVL:\{pokemon.getLevel()}");
        pokemonSelfId.getChildren().add(name);
        pokemonSelfId.getChildren().add(lvl);
        return pokemonSelfId;
    }

    private static VBox newTypeBox (String type){
        VBox typeBox = new VBox();
        Text typeName = new Text(type);
        typeBox.getChildren().add(typeName);
        typeBox.setStyle(STR."-fx-background-color: \{
                TypeTools.stringTypeToColor(type)};");
        return typeBox;
    }

    private static VBox newTypesBox (Pokemon pokemon){
        VBox typesBox = new VBox();
        for(Type type: pokemon.getTypes()){
            typesBox.getChildren().
                    add(newTypeBox(TypeTools.typeToString(type)));
        }
        return typesBox;
    }

    private static VBox newStatue (Pokemon pokemon){
        VBox statusBox = new VBox();
        if(pokemon.asMajorStatus()){
            String statusName =  pokemon.getMajorStatus().getName();
            Text status = new Text(statusName);
            statusBox.getChildren().add(status);
            statusBox.setStyle(STR."-fx-background-color: \{
                    StatusTools.statusToColor(statusName)};");
        }
        return statusBox;
    }
}
