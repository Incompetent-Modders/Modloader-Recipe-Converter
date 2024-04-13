package com.incompetent_modders.modloader_recipe_converter.foundation.convertion;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.incompetent_modders.modloader_recipe_converter.foundation.Modloaders;
import com.incompetent_modders.modloader_recipe_converter.foundation.TagConversions;

public class TagConverting {
    
    public static void convertItemTag(ObjectNode jsonNode, Modloaders convertingFrom, Modloaders convertingTo) {
        ArrayNode ingredients = (ArrayNode) jsonNode.get("ingredients");
        ObjectNode keys = (ObjectNode) jsonNode.get("key");
        if (jsonNode.has("ingredients") && shouldModify(jsonNode)) {
            ingredients.fields().forEachRemaining(ingredient -> {
                ObjectNode ingredientField = (ObjectNode) ingredient.getValue();
                if (isItemTag(ingredientField)) {
                    String tagString = ingredientField.get("tag").asText();
                    if (convertingFrom == Modloaders.FORGE && convertingTo == Modloaders.FABRIC) {
                        tagString = TagConversions.toFabric(tagString);
                    } else if (convertingFrom == Modloaders.FABRIC && convertingTo == Modloaders.FORGE) {
                        tagString = TagConversions.toForge(tagString);
                    }
                    ingredientField.put("tag", tagString);
                    jsonNode.put("info", "This recipe has been modified by the Incompetent Modders Recipe Converter.");
                }
            });
        }
        if (jsonNode.has("key") && shouldModify(jsonNode)) {
            keys.fields().forEachRemaining(key -> {
                ObjectNode keyField = (ObjectNode) key.getValue();
                if (isItemTag(keyField)) {
                    String tagString = keyField.get("tag").asText();
                    if (convertingFrom == Modloaders.FORGE && convertingTo == Modloaders.FABRIC) {
                        tagString = TagConversions.toFabric(tagString);
                    } else if (convertingFrom == Modloaders.FABRIC && convertingTo == Modloaders.FORGE) {
                        tagString = TagConversions.toForge(tagString);
                    }
                    keyField.put("tag", tagString);
                    jsonNode.put("info", "This recipe has been modified by the Incompetent Modders Recipe Converter.");
                }
            });
        }
    }
    public static void convertFluidTag(ObjectNode jsonNode, Modloaders convertingFrom, Modloaders convertingTo) {
        ArrayNode ingredients = (ArrayNode) jsonNode.get("ingredients");
        if (jsonNode.has("ingredients") && shouldModify(jsonNode)) {
            ingredients.forEach(ingredient -> {
                if (isFluidTag(ingredient)) {
                    String tagString = ingredient.get("fluidTag").asText();
                    if (convertingFrom == Modloaders.FORGE && convertingTo == Modloaders.FABRIC) {
                        tagString = TagConversions.simpleToC(tagString);
                    } else if (convertingFrom == Modloaders.FABRIC && convertingTo == Modloaders.FORGE) {
                        tagString = TagConversions.simpleToForge(tagString);
                    }
                    ((ObjectNode) ingredient).put("fluidTag", tagString);
                    jsonNode.put("info", "This recipe has been modified by the Incompetent Modders Recipe Converter.");
                }
            });
            
        }
    }
    
    private static boolean isItemTag(JsonNode jsonNode) {
        return jsonNode.has("tag");
    }
    private static boolean isFluidTag(JsonNode jsonNode) {
        return jsonNode.has("fluidTag");
    }
    
    private static boolean shouldModify(JsonNode jsonNode) {
        return !jsonNode.has("info");
    }
}
