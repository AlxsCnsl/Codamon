package com.example.codamon.controllers;

import com.example.codamon.PokeApp;
import com.example.codamon.models.SceneName;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

public class TeamBuilderController {

    @FXML private VBox Pokemons;

    public TeamBuilderController() {

    }

    private Stage stage;

    public void setStage(Stage stage) {
        if (stage == null) {
            throw new IllegalArgumentException("Stage cannot be null");
        }

        this.stage = stage;
    }

    @FXML
    public void onExitButtonClick() {
        stage.setScene(PokeApp.getScenes().get(SceneName.MENU));
    }

    @FXML
    public void onAddPokemonButtonClick() {
        //===== Pokemon Sprites HBox =====
        URL pokemonSpriteURL = getClass().getResource("/com/example/codamon/sprites/Pokemon/togekiss/togekiss_face.png");
        Image pokemonSprite = new Image(pokemonSpriteURL.toExternalForm());

        ImageView pokemonSpriteImageView = new ImageView(pokemonSprite);
        pokemonSpriteImageView.setFitWidth(100);
        pokemonSpriteImageView.setPickOnBounds(true);
        pokemonSpriteImageView.setPreserveRatio(true);

        URL firstTypeSpriteURL = getClass().getResource("/com/example/codamon/types/fairy.png");
        Image firstTypeSprite = new Image(firstTypeSpriteURL.toExternalForm());

        ImageView firstTypeImageView = new ImageView(firstTypeSprite);
        firstTypeImageView.setFitWidth(50);
        firstTypeImageView.setPickOnBounds(true);
        firstTypeImageView.setPreserveRatio(true);

        URL secondTypeSpriteURL = getClass().getResource("/com/example/codamon/types/flying.png");
        Image secondTypeSprite = new Image(secondTypeSpriteURL.toExternalForm());

        ImageView secondTypeImageView = new ImageView(secondTypeSprite);
        secondTypeImageView.setFitWidth(50);
        secondTypeImageView.setPickOnBounds(true);
        secondTypeImageView.setPreserveRatio(true);

        HBox pokemonSprites = new HBox(10.0);
        pokemonSprites.setAlignment(Pos.CENTER);
        pokemonSprites.getChildren().add(pokemonSpriteImageView);
        pokemonSprites.getChildren().add(firstTypeImageView);
        pokemonSprites.getChildren().add(secondTypeImageView);
        //====================

        //===== Pokémon Details HBox =====;
        ChoiceBox<String> pokemonChoice = new ChoiceBox<>();
        pokemonChoice.getItems().add("Togekiss");
        pokemonChoice.getItems().add("Lugulabre");
        pokemonChoice.getItems().add("Lançargot");

        Label pokemonLabel = new Label("Pokémon");

        VBox PokemonVBox = new VBox(5.0);
        PokemonVBox.setAlignment(Pos.BOTTOM_CENTER);

        PokemonVBox.getChildren().add(pokemonLabel);
        PokemonVBox.getChildren().add(pokemonChoice);

        ChoiceBox<String> itemChoice = new ChoiceBox<>();
        itemChoice.getItems().add("Restes");
        itemChoice.getItems().add("Ballon");
        itemChoice.getItems().add("Orbe Vie");

        Label itemLabel = new Label("Item");

        VBox ItemVBox = new VBox(5.0);
        ItemVBox.setAlignment(Pos.BOTTOM_CENTER);

        ItemVBox.getChildren().add(itemLabel);
        ItemVBox.getChildren().add(itemChoice);

        ChoiceBox<String> talentChoice = new ChoiceBox<>();
        talentChoice.getItems().add("Agitation");
        talentChoice.getItems().add("Sérénité");
        talentChoice.getItems().add("Chanceux");

        Label talentLabel = new Label("Talent");

        VBox TalentVBox = new VBox(5.0);
        TalentVBox.setAlignment(Pos.BOTTOM_CENTER);

        TalentVBox.getChildren().add(talentLabel);
        TalentVBox.getChildren().add(talentChoice);

        HBox pokemonDetails = new HBox(5.0);
        pokemonDetails.setAlignment(Pos.CENTER);
        pokemonDetails.getChildren().add(PokemonVBox);
        pokemonDetails.getChildren().add(ItemVBox);
        pokemonDetails.getChildren().add(TalentVBox);
        //====================

        //===== Sprites and Details VBox =====
        VBox spritesAndDetailsVBox = new VBox();
        spritesAndDetailsVBox.setAlignment(Pos.CENTER);
        spritesAndDetailsVBox.getChildren().add(pokemonSprites);
        spritesAndDetailsVBox.getChildren().add(pokemonDetails);
        //=====================================

        //===== Moves VBox =====
        Label movesLabel = new Label("Attaques");

        ChoiceBox<String> moveChoice = new ChoiceBox<>();
        moveChoice.getItems().add("Lame d'Air");
        moveChoice.getItems().add("Aurasphère");
        moveChoice.getItems().add("Lance-Flammes");
        moveChoice.getItems().add("Pouvoir Lunaire");

        ChoiceBox<String> moveChoice2 = new ChoiceBox<>();
        moveChoice2.getItems().add("Lame d'Air");
        moveChoice2.getItems().add("Aurasphère");
        moveChoice2.getItems().add("Lance-Flammes");
        moveChoice2.getItems().add("Pouvoir Lunaire");

        ChoiceBox<String> moveChoice3 = new ChoiceBox<>();
        moveChoice3.getItems().add("Lame d'Air");
        moveChoice3.getItems().add("Aurasphère");
        moveChoice3.getItems().add("Lance-Flammes");
        moveChoice3.getItems().add("Pouvoir Lunaire");

        ChoiceBox<String> moveChoice4 = new ChoiceBox<>();
        moveChoice4.getItems().add("Lame d'Air");
        moveChoice4.getItems().add("Aurasphère");
        moveChoice4.getItems().add("Lance-Flammes");
        moveChoice4.getItems().add("Pouvoir Lunaire");

        VBox MovesVBox = new VBox();
        MovesVBox.setAlignment(Pos.CENTER);

        MovesVBox.getChildren().add(movesLabel);
        MovesVBox.getChildren().add(moveChoice);
        MovesVBox.getChildren().add(moveChoice2);
        MovesVBox.getChildren().add(moveChoice3);
        MovesVBox.getChildren().add(moveChoice4);
        //=====================================

        //===== Stats VBox =====
        Label statsLabel = new Label("Statistiques");

        Label HPLabel = new Label("HP");
        ProgressBar HPBar = new ProgressBar(0.45);
        HBox HPDisplay = new HBox(5.0);
        HPDisplay.setAlignment(Pos.CENTER_RIGHT);
        HPDisplay.getChildren().add(HPLabel);
        HPDisplay.getChildren().add(HPBar);

        Label ATKLabel = new Label("HP");
        ProgressBar ATKBar = new ProgressBar(0.28);
        HBox ATKDisplay = new HBox(5.0);
        ATKDisplay.setAlignment(Pos.CENTER_RIGHT);
        ATKDisplay.getChildren().add(ATKLabel);
        ATKDisplay.getChildren().add(ATKBar);

        Label DEFLabel = new Label("HP");
        ProgressBar DEFBar = new ProgressBar(0.50);
        HBox DEFDisplay = new HBox(5.0);
        DEFDisplay.setAlignment(Pos.CENTER_RIGHT);
        DEFDisplay.getChildren().add(DEFLabel);
        DEFDisplay.getChildren().add(DEFBar);

        Label SPALabel = new Label("HP");
        ProgressBar SPABar = new ProgressBar(0.62);
        HBox SPADisplay = new HBox(5.0);
        SPADisplay.setAlignment(Pos.CENTER_RIGHT);
        SPADisplay.getChildren().add(SPALabel);
        SPADisplay.getChildren().add(SPABar);

        Label SPDLabel = new Label("HP");
        ProgressBar SPDBar = new ProgressBar(0.59);
        HBox SPDDisplay = new HBox(5.0);
        SPDDisplay.setAlignment(Pos.CENTER_RIGHT);
        SPDDisplay.getChildren().add(SPDLabel);
        SPDDisplay.getChildren().add(SPDBar);

        Label SPELabel = new Label("HP");
        ProgressBar SPEBar = new ProgressBar(0.43);
        HBox SPEDisplay = new HBox(5.0);
        SPEDisplay.setAlignment(Pos.CENTER_RIGHT);
        SPEDisplay.getChildren().add(SPELabel);
        SPEDisplay.getChildren().add(SPEBar);

        VBox StatsVBox = new VBox();
        StatsVBox.setAlignment(Pos.CENTER);
        StatsVBox.getChildren().add(statsLabel);
        StatsVBox.getChildren().add(HPDisplay);
        StatsVBox.getChildren().add(ATKDisplay);
        StatsVBox.getChildren().add(DEFDisplay);
        StatsVBox.getChildren().add(SPADisplay);
        StatsVBox.getChildren().add(SPDDisplay);
        StatsVBox.getChildren().add(SPEDisplay);
        //======================================

        //===== Pokemon HBox =====
        HBox pokemon = new HBox(10.0);
        pokemon.setId("Pokemon");
        pokemon.setAlignment(Pos.BOTTOM_CENTER);
        pokemon.setStyle("-fx-background-color:lightgray");

        Insets insets = new Insets(5.0);
        pokemon.setPadding(insets);

        pokemon.getChildren().add(spritesAndDetailsVBox);
        pokemon.getChildren().add(MovesVBox);
        pokemon.getChildren().add(StatsVBox);
        //=========================

        Pokemons.getChildren().add(pokemon);
    }
}