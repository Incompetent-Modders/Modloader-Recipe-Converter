package com.incompetent_modders.modloader_recipe_converter.foundation.convertion;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.incompetent_modders.modloader_recipe_converter.foundation.Modloaders;
import com.incompetent_modders.modloader_recipe_converter.foundation.TagConversions;

import java.util.Map;

public class TagConverting {
    
    public static void convertItemTag(ObjectNode jsonNode, Modloaders convertingFrom, Modloaders convertingTo) {
        ArrayNode ingredients = (ArrayNode) jsonNode.get("ingredients");
        ObjectNode keys = (ObjectNode) jsonNode.get("key");
        if (jsonNode.has("ingredients") && shouldModify(jsonNode, convertingFrom)) {
            for (JsonNode ingredient : ingredients) {
                if (isItemTag(ingredient)) {
                    String tagString = ingredient.get("tag").asText();
                    if (convertingFrom == Modloaders.FORGE && convertingTo == Modloaders.FABRIC) {
                        tagString = TagConversions.toFabric(tagString);
                    } else if (convertingFrom == Modloaders.FABRIC && convertingTo == Modloaders.FORGE) {
                        tagString = TagConversions.toForge(tagString);
                    }
                    ((ObjectNode) ingredient).put("tag", tagString);
                }
            }
        }
        if (jsonNode.has("key") && shouldModify(jsonNode, convertingFrom)) {
            for (JsonNode key : keys) {
                ObjectNode keyField = (ObjectNode) key;
                if (isItemTag(keyField)) {
                    String tagString = keyField.get("tag").asText();
                    if (convertingFrom == Modloaders.FORGE && convertingTo == Modloaders.FABRIC) {
                        tagString = TagConversions.toFabric(tagString);
                    } else if (convertingFrom == Modloaders.FABRIC && convertingTo == Modloaders.FORGE) {
                        tagString = TagConversions.toForge(tagString);
                    }
                    keyField.put("tag", tagString);
                }
            }
            //keys.fields().forEachRemaining(key -> {
            //    ObjectNode keyField = (ObjectNode) key.getValue();
            //    if (isItemTag(keyField)) {
            //        String tagString = keyField.get("tag").asText();
            //        if (convertingFrom == Modloaders.FORGE && convertingTo == Modloaders.FABRIC) {
            //            tagString = TagConversions.toFabric(tagString);
            //        } else if (convertingFrom == Modloaders.FABRIC && convertingTo == Modloaders.FORGE) {
            //            tagString = TagConversions.toForge(tagString);
            //        }
            //        keyField.put("tag", tagString);
            //    }
            //});
        }
    }
    public static void convertFluidTag(ObjectNode jsonNode, Modloaders convertingFrom, Modloaders convertingTo) {
        ArrayNode ingredients = (ArrayNode) jsonNode.get("ingredients");
        if (jsonNode.has("ingredients") && shouldModify(jsonNode, convertingFrom)) {
            for (JsonNode ingredient : ingredients) {
                if (isFluidTag(ingredient)) {
                    String tagString = ingredient.get("fluidTag").asText();
                    if (convertingFrom == Modloaders.FORGE && convertingTo == Modloaders.FABRIC) {
                        tagString = TagConversions.simpleToC(tagString);
                    } else if (convertingFrom == Modloaders.FABRIC && convertingTo == Modloaders.FORGE) {
                        tagString = TagConversions.simpleToForge(tagString);
                    }
                    ((ObjectNode) ingredient).put("fluidTag", tagString);
                }
            }
            //ingredients.forEach(ingredient -> {
            //    if (isFluidTag(ingredient)) {
            //        String tagString = ingredient.get("fluidTag").asText();
            //        if (convertingFrom == Modloaders.FORGE && convertingTo == Modloaders.FABRIC) {
            //            tagString = TagConversions.simpleToC(tagString);
            //        } else if (convertingFrom == Modloaders.FABRIC && convertingTo == Modloaders.FORGE) {
            //            tagString = TagConversions.simpleToForge(tagString);
            //        }
            //        ((ObjectNode) ingredient).put("fluidTag", tagString);
            //    }
            //});
            
        }
    }
    
    private static boolean isItemTag(JsonNode jsonNode) {
        return jsonNode.has("tag");
    }
    private static boolean isFluidTag(JsonNode jsonNode) {
        return jsonNode.has("fluidTag");
    }
    
    private static boolean shouldModify(JsonNode jsonNode, Modloaders loader) {
        if (jsonNode.has("convertedTo")) {
            return !jsonNode.get("convertedTo").asText().equals(loader.getName());
        }
        return true;
    }
}
