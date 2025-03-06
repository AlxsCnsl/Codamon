package com.example.codamon.core.action.move;

import com.example.codamon.core.effect.MoveEffectTools;
import com.example.codamon.core.type.Type;
import com.example.codamon.core.type.TypeTools;
import com.example.codamon.core.action.category.Category;
import com.example.codamon.core.action.category.CathegoryTools;
import com.example.codamon.core.effect.move_effect.MoveEffect;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MoveTools {
    public MoveTools(){};

    public static Move newMove(String name){
        ObjectMapper objectMapper = new ObjectMapper();
        String pathName =
                "/com/example/codamon/data/move/"+
                        name.toLowerCase()+".json";
        InputStream inputStream =
                MoveTools.class.getResourceAsStream(pathName);

        if (inputStream == null) {
            System.err.println("File not found : " + pathName);
        }
        try {
            JsonNode rootNode = objectMapper.readTree(inputStream);
            System.out.println("#MOVE#INTI# new move : " +name);
            return initMoveWithJson(rootNode);

        }catch (IOException e){
            System.out.println(pathName + " could not be opened");
            e.printStackTrace();
        }
        return null;
    }

    private static Move initMoveWithJson(JsonNode rootNode){
        String name = rootNode.get("name").asText();
        Type type = TypeTools.stringToType(
                rootNode.get("type").asText());
        Category category = CathegoryTools.stringToCategory(
                rootNode.get("category").asText());
        int power = rootNode.get("power").asInt();
        int accurate = rootNode.get("power").asInt();
        int pp = rootNode.get("pp").asInt();
        int priority = rootNode.get("priority").asInt();
        ArrayList<MoveEffect> effects = new ArrayList<>();
        JsonNode effectsNode = rootNode.get("effects");
        effectsBuilder(effectsNode, effects);
        Move move = new Move(name,
                type, category, power, accurate,pp, priority, effects);
        return move;
    }

    public static void effectsBuilder(JsonNode effectsNode,
                                ArrayList<MoveEffect> effects){
        if(effectsNode != null && effectsNode.isArray()){
            for (JsonNode effectNode : effectsNode) {
                MoveEffectTools.effectSetter(effectNode, effects);
            }
        }
    }



}
