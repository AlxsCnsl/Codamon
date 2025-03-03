package com.example.codamon.controllers;

import com.example.codamon.PokeApp;
import com.example.codamon.core.TypeTools;
import com.example.codamon.models.SceneName;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

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

    private ImageView createPokemonImageView(String pokemonName) {
        URL pokemonSpriteURL = getClass().getResource(
                "/com/example/codamon/sprites/Pokemon/" +
                        pokemonName + "/" + pokemonName + "_face.png");
        Image pokemonSprite = new Image(pokemonSpriteURL.toExternalForm());

        ImageView pokemonSpriteImageView = new ImageView(pokemonSprite);
        pokemonSpriteImageView.setFitWidth(100);
        pokemonSpriteImageView.setPickOnBounds(true);
        pokemonSpriteImageView.setPreserveRatio(true);

        ArrayList<ImageView> pokemonSprites = new ArrayList<>();
        pokemonSprites.add(pokemonSpriteImageView);

        ArrayList<Object> userData = new ArrayList<>();
        userData.add(pokemonSprites);

        stage.setUserData(userData);

        return pokemonSpriteImageView;
    }

    private ImageView createTypeImageView(String typeName) {
        URL typeSpriteURL = getClass().getResource(
                "/com/example/codamon/types/" +
                        typeName + ".png");
        Image typeSprite = new Image(typeSpriteURL.toExternalForm());

        ImageView typeImageView = new ImageView(typeSprite);
        typeImageView.setFitWidth(50);
        typeImageView.setPickOnBounds(true);
        typeImageView.setPreserveRatio(true);

        ArrayList<Object> userData = (ArrayList<Object>) stage.getUserData();
        ArrayList<ImageView> pokemonSprites = (ArrayList<ImageView>) userData.get(0);
        pokemonSprites.add(typeImageView);
        stage.setUserData(userData);

        return typeImageView;
    }

    private HBox createPokemonSpritesHBox() {
        ImageView pokemonSpriteImageView = createPokemonImageView(
                "null");

        ImageView firstTypeImageView = createTypeImageView("null");
        ImageView secondTypeImageView = createTypeImageView("null");

        HBox pokemonSprites = new HBox(10.0);
        pokemonSprites.setAlignment(Pos.CENTER);
        pokemonSprites.getChildren().add(pokemonSpriteImageView);
        pokemonSprites.getChildren().add(firstTypeImageView);
        pokemonSprites.getChildren().add(secondTypeImageView);

        return pokemonSprites;
    }

    private void pokemonListJSONReader(ArrayList<String> pokemonList) {
        ObjectMapper objectMapper = new ObjectMapper();
        String pathName = "/com/example/codamon/data/pokemonList.json";
        InputStream inputStream = getClass().getResourceAsStream(pathName);
        if (inputStream == null) {
            System.err.println("File not found : " + pathName);
        }
        try {
            JsonNode rootNode = objectMapper.readTree(inputStream);
            for (int i = 0; i < rootNode.get("pokemonList").size() ; i++) {
                pokemonList.add(rootNode.get("pokemonList").get(i).asText());
            }
        } catch (IOException e) {
            System.out.println(pathName + " could not be opened");
        }
    }

    private void pokemonTypesJSONReader(ArrayList<String> pokemonTypes,
                                        String pokemonName) {
        ObjectMapper objectMapper = new ObjectMapper();
        String pathName = "/com/example/codamon/data/pokemon/" +
                        pokemonName.toLowerCase()+".json";

        InputStream inputStream = getClass().getResourceAsStream(pathName);

        if (inputStream == null) {
            System.err.println("File not found : " + pathName);
        }
        try {
            JsonNode rootNode = objectMapper.readTree(inputStream);
            for (int i = 0 ; i < rootNode.get("types").size() ; i++) {
                pokemonTypes.add(rootNode.get("types").get(i).asText());
            }
        } catch (IOException e){
            System.out.println(pathName + " could not be opened");
        }
    }

    private void pokemonStatsJSONReader(ArrayList<String> pokemonStats,
                                        String pokemonName) {
        ObjectMapper objectMapper = new ObjectMapper();
        String pathName = "/com/example/codamon/data/pokemon/" +
                pokemonName.toLowerCase()+".json";

        InputStream inputStream = getClass().getResourceAsStream(pathName);

        if (inputStream == null) {
            System.err.println("File not found : " + pathName);
        }
        try {
            JsonNode rootNode = objectMapper.readTree(inputStream);
            pokemonStats.add(rootNode.get("stats").get("HP").asText());
            pokemonStats.add(rootNode.get("stats").get("ATK").asText());
            pokemonStats.add(rootNode.get("stats").get("DEF").asText());
            pokemonStats.add(rootNode.get("stats").get("SPA").asText());
            pokemonStats.add(rootNode.get("stats").get("SPE").asText());
            pokemonStats.add(rootNode.get("stats").get("SPE").asText());

        } catch (IOException e){
            System.out.println(pathName + " could not be opened");
        }
    }

    private ArrayList<String> createPokemonNamesList() {
        ArrayList<String> pokemonNames = new ArrayList<>();
        pokemonListJSONReader(pokemonNames);
        return pokemonNames;
    }

    private ComboBox<String> setPokemonChoices(ArrayList<String> pokemonNames) {
        ComboBox<String> pokemonChoice = new ComboBox<>();
        for (String pokemonName : pokemonNames) {
            pokemonChoice.getItems().add(pokemonName);
        }

        return pokemonChoice;
    }

    private ArrayList<String> readPokemonTypes(String pokemonName) {
        ArrayList<String> pokemonTypes = new ArrayList<>();
        pokemonTypesJSONReader(pokemonTypes, pokemonName);
        return pokemonTypes;
    }

    private Image getPokemonSprite(String pokemonName) {
        URL pokemonSpriteURL = getClass().getResource(
                "/com/example/codamon/sprites/Pokemon/" +
                        pokemonName + "/" +
                        pokemonName + "_face.png");

        return new Image(
                pokemonSpriteURL.toExternalForm());
    }

    private Image getTypeSprite(String pokemonFirstType) {
        URL firstTypeSpriteURL = getClass().getResource(
                "/com/example/codamon/types/" +
                        pokemonFirstType + ".png");

        return new Image(
                firstTypeSpriteURL.toExternalForm());
    }

    private Image getNullTypeSprite() {
        URL nullTypeSpriteURL = getClass().getResource(
                "/com/example/codamon/types/null.png");

        return new Image(
                nullTypeSpriteURL.toExternalForm());
    }

    private void setPokemonSprites(Image pokemonSprite, Image firstTypeSprite,
                                   Image secondTypeSprite) {
        ArrayList<Object> userData = (ArrayList<Object>) stage.getUserData();
        ArrayList<ImageView> pokemonSpritesImageView =
                (ArrayList<ImageView>) userData.get(0);
        pokemonSpritesImageView.get(0).setImage(pokemonSprite);
        pokemonSpritesImageView.get(1).setImage(firstTypeSprite);
        pokemonSpritesImageView.get(2).setImage(secondTypeSprite);
    }

    private VBox createPokemonVBox(Label pokemonLabel,
                                   ComboBox<String> pokemonChoice) {
        VBox PokemonVBox = new VBox(5.0);
        PokemonVBox.setAlignment(Pos.BOTTOM_CENTER);
        PokemonVBox.getChildren().add(pokemonLabel);
        PokemonVBox.getChildren().add(pokemonChoice);
        return PokemonVBox;
    }

    private VBox createPokemonChoiceBox() {
        Label pokemonLabel = new Label("Pokémon");
        ArrayList<String> pokemonNames = createPokemonNamesList();
        ComboBox<String> pokemonChoice = setPokemonChoices(pokemonNames);

        pokemonChoice.setOnAction((actionEvent -> {
            ArrayList<String> pokemonTypes = readPokemonTypes(
                    pokemonChoice.getValue());
            Image pokemonSprite = getPokemonSprite(pokemonChoice.getValue());
            Image firstTypeSprite = getTypeSprite(pokemonTypes.get(0));
            Image secondTypeSprite = pokemonTypes.size() == 2 ?
                    getTypeSprite(pokemonTypes.get(1)) : getNullTypeSprite();

            setPokemonSprites(pokemonSprite, firstTypeSprite, secondTypeSprite);

            ArrayList<Object> userData = (ArrayList<Object>) stage.getUserData();
            ArrayList<ProgressBar> StatBars =
                    (ArrayList<ProgressBar>) userData.get(1);
            StatBars.get(0).setProgress(0.90);

            ArrayList<String> pokemonStats = new ArrayList<>();
            pokemonStatsJSONReader(pokemonStats, pokemonChoice.getValue());

            for (int i = 0 ; i < StatBars.size() ; i++) {
                StatBars.get(i).setProgress(Double.parseDouble(pokemonStats.get(i)) / 200);
            }
        }));

        return createPokemonVBox(pokemonLabel, pokemonChoice);
    }

    private VBox createItemChoiceBox() {
        Label itemLabel = new Label("Item");

        ChoiceBox<String> itemChoice = new ChoiceBox<>();
        itemChoice.getItems().add("Restes");
        itemChoice.getItems().add("Ballon");
        itemChoice.getItems().add("Orbe Vie");

        VBox ItemVBox = new VBox(5.0);
        ItemVBox.setAlignment(Pos.BOTTOM_CENTER);

        ItemVBox.getChildren().add(itemLabel);
        ItemVBox.getChildren().add(itemChoice);

        return ItemVBox;
    }

    private VBox createTalentChoiceBox() {
        Label talentLabel = new Label("Talent");

        ChoiceBox<String> talentChoice = new ChoiceBox<>();
        talentChoice.getItems().add("Agitation");
        talentChoice.getItems().add("Sérénité");
        talentChoice.getItems().add("Chanceux");

        VBox TalentVBox = new VBox(5.0);
        TalentVBox.setAlignment(Pos.BOTTOM_CENTER);

        TalentVBox.getChildren().add(talentLabel);
        TalentVBox.getChildren().add(talentChoice);

        return TalentVBox;
    }

    private HBox createPokemonDetailsHBox() {
        VBox PokemonChoiceBox = createPokemonChoiceBox();
        VBox ItemChoiceBox = createItemChoiceBox();
        VBox TalentChoiceBox = createTalentChoiceBox();

        HBox pokemonDetails = new HBox(5.0);
        pokemonDetails.setAlignment(Pos.CENTER);
        pokemonDetails.getChildren().add(PokemonChoiceBox);
        pokemonDetails.getChildren().add(ItemChoiceBox);
        pokemonDetails.getChildren().add(TalentChoiceBox);

        return pokemonDetails;
    }

    private VBox spritesAndDetailsVBox() {
        //===== Pokemon Sprites HBox =====
        HBox pokemonSprites = createPokemonSpritesHBox();
        //====================

        //===== Pokémon Details HBox =====;
        HBox pokemonDetails = createPokemonDetailsHBox();
        //====================

        //===== Sprites and Details VBox =====
        VBox spritesAndDetailsVBox = new VBox();
        spritesAndDetailsVBox.setAlignment(Pos.CENTER);
        spritesAndDetailsVBox.getChildren().add(pokemonSprites);
        spritesAndDetailsVBox.getChildren().add(pokemonDetails);
        //=====================================

        return spritesAndDetailsVBox;
    }

    private ChoiceBox<String> createMoveChoiceBox() {
        ChoiceBox<String> moveChoice = new ChoiceBox<>();
        moveChoice.getItems().add("Lame d'Air");
        moveChoice.getItems().add("Aurasphère");
        moveChoice.getItems().add("Lance-Flammes");
        moveChoice.getItems().add("Pouvoir Lunaire");

        return moveChoice;
    }

    private VBox createMovesVBox() {
        Label movesLabel = new Label("Attaques");

        ChoiceBox<String> moveChoice1 = createMoveChoiceBox();
        ChoiceBox<String> moveChoice2 = createMoveChoiceBox();
        ChoiceBox<String> moveChoice3 = createMoveChoiceBox();
        ChoiceBox<String> moveChoice4 = createMoveChoiceBox();

        VBox MovesVBox = new VBox();
        MovesVBox.setAlignment(Pos.CENTER);

        MovesVBox.getChildren().add(movesLabel);
        MovesVBox.getChildren().add(moveChoice1);
        MovesVBox.getChildren().add(moveChoice2);
        MovesVBox.getChildren().add(moveChoice3);
        MovesVBox.getChildren().add(moveChoice4);

        return MovesVBox;
    }

    private HBox createHPDisplay() {
        Label HPLabel = new Label("HP");
        ProgressBar HPBar = new ProgressBar(0.45);
        HBox HPDisplay = new HBox(5.0);
        HPDisplay.setAlignment(Pos.CENTER_RIGHT);
        HPDisplay.getChildren().add(HPLabel);
        HPDisplay.getChildren().add(HPBar);

        ArrayList<Object> userData = (ArrayList<Object>) stage.getUserData();
        ArrayList<ProgressBar> StatBars = new ArrayList<>();
        StatBars.add(HPBar);
        userData.add(StatBars);
        stage.setUserData(userData);

        return HPDisplay;
    }

    private HBox createATKDisplay() {
        Label ATKLabel = new Label("ATK");
        ProgressBar ATKBar = new ProgressBar(0.28);
        HBox ATKDisplay = new HBox(5.0);
        ATKDisplay.setAlignment(Pos.CENTER_RIGHT);
        ATKDisplay.getChildren().add(ATKLabel);
        ATKDisplay.getChildren().add(ATKBar);

        ArrayList<Object> userData = (ArrayList<Object>) stage.getUserData();
        ArrayList<ProgressBar> StatBars =
                (ArrayList<ProgressBar>) userData.get(1);
        StatBars.add(ATKBar);
        stage.setUserData(userData);

        return ATKDisplay;
    }

    private HBox createDEFDisplay() {
        Label DEFLabel = new Label("DEF");
        ProgressBar DEFBar = new ProgressBar(0.50);
        HBox DEFDisplay = new HBox(5.0);
        DEFDisplay.setAlignment(Pos.CENTER_RIGHT);
        DEFDisplay.getChildren().add(DEFLabel);
        DEFDisplay.getChildren().add(DEFBar);

        ArrayList<Object> userData = (ArrayList<Object>) stage.getUserData();
        ArrayList<ProgressBar> StatBars =
                (ArrayList<ProgressBar>) userData.get(1);
        StatBars.add(DEFBar);
        stage.setUserData(userData);

        return DEFDisplay;
    }

    private HBox createSPADisplay() {
        Label SPALabel = new Label("SPA");
        ProgressBar SPABar = new ProgressBar(0.62);
        HBox SPADisplay = new HBox(5.0);
        SPADisplay.setAlignment(Pos.CENTER_RIGHT);
        SPADisplay.getChildren().add(SPALabel);
        SPADisplay.getChildren().add(SPABar);

        ArrayList<Object> userData = (ArrayList<Object>) stage.getUserData();
        ArrayList<ProgressBar> StatBars =
                (ArrayList<ProgressBar>) userData.get(1);
        StatBars.add(SPABar);
        stage.setUserData(userData);

        return SPADisplay;
    }

    private HBox createSPDDisplay() {
        Label SPDLabel = new Label("SPD");
        ProgressBar SPDBar = new ProgressBar(0.59);
        HBox SPDDisplay = new HBox(5.0);
        SPDDisplay.setAlignment(Pos.CENTER_RIGHT);
        SPDDisplay.getChildren().add(SPDLabel);
        SPDDisplay.getChildren().add(SPDBar);

        ArrayList<Object> userData = (ArrayList<Object>) stage.getUserData();
        ArrayList<ProgressBar> StatBars =
                (ArrayList<ProgressBar>) userData.get(1);
        StatBars.add(SPDBar);
        stage.setUserData(userData);

        return SPDDisplay;
    }

    private HBox createSPEDisplay() {
        Label SPELabel = new Label("SPE");
        ProgressBar SPEBar = new ProgressBar(0.43);
        HBox SPEDisplay = new HBox(5.0);
        SPEDisplay.setAlignment(Pos.CENTER_RIGHT);
        SPEDisplay.getChildren().add(SPELabel);
        SPEDisplay.getChildren().add(SPEBar);

        ArrayList<Object> userData = (ArrayList<Object>) stage.getUserData();
        ArrayList<ProgressBar> StatBars =
                (ArrayList<ProgressBar>) userData.get(1);
        StatBars.add(SPEBar);
        stage.setUserData(userData);

        return SPEDisplay;
    }

    private VBox createStatsVBox() {
        Label statsLabel = new Label("Statistiques");

        VBox StatsVBox = new VBox();
        StatsVBox.setAlignment(Pos.CENTER);
        StatsVBox.getChildren().add(statsLabel);
        StatsVBox.getChildren().add(createHPDisplay());
        StatsVBox.getChildren().add(createATKDisplay());
        StatsVBox.getChildren().add(createDEFDisplay());
        StatsVBox.getChildren().add(createSPADisplay());
        StatsVBox.getChildren().add(createSPDDisplay());
        StatsVBox.getChildren().add(createSPEDisplay());

        return StatsVBox;
    }

    private HBox createPokemon() {
        VBox spritesAndDetailsVBox = spritesAndDetailsVBox();

        VBox MovesVBox = createMovesVBox();

        VBox StatsVBox = createStatsVBox();

        HBox pokemon = new HBox(10.0);
        pokemon.setId("Pokemon");
        pokemon.setAlignment(Pos.BOTTOM_CENTER);
        pokemon.setStyle("-fx-background-color:lightgray");

        Insets insets = new Insets(5.0);
        pokemon.setPadding(insets);

        pokemon.getChildren().add(spritesAndDetailsVBox);
        pokemon.getChildren().add(MovesVBox);
        pokemon.getChildren().add(StatsVBox);

        return pokemon;
    }

    @FXML
    public void onAddPokemonButtonClick() {
        HBox pokemon = createPokemon();
        Pokemons.getChildren().add(pokemon);
    }
}