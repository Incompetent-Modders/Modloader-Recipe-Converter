package com.incompetent_modders.modloader_recipe_converter.foundation.convertion;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.incompetent_modders.modloader_recipe_converter.foundation.Modloaders;

public class AmountConverting {
    public static void convertFluidAmount(ObjectNode jsonNode, Modloaders convertingFrom, Modloaders convertingTo) {
        ArrayNode ingredients = (ArrayNode) jsonNode.get("ingredients");
        if (jsonNode.has("ingredients") && shouldModify(jsonNode)) {
            ingredients.forEach(ingredient -> {
                if (isIngredientFluid(ingredient) && ingredient.has("amount")) {
                    int amount = ingredient.get("amount").asInt();
                    if (convertingFrom == Modloaders.FORGE && convertingTo == Modloaders.FABRIC) {
                        amount *= 81;
                    } else if (convertingFrom == Modloaders.FABRIC && convertingTo == Modloaders.FORGE) {
                        amount /= 81;
                    }
                    ((ObjectNode) ingredient).put("amount", amount);
                    jsonNode.put("info", "This recipe has been modified by the Incompetent Modders Recipe Converter.");
                }
            });
        }
        if (jsonNode.has("results") && shouldModify(jsonNode)) {
            ArrayNode results = (ArrayNode) jsonNode.get("results");
            results.forEach(result -> {
                if (isIngredientFluid(result) && result.has("amount")) {
                    int amount = result.get("amount").asInt();
                    if (convertingFrom == Modloaders.FORGE && convertingTo == Modloaders.FABRIC) {
                        amount *= 81;
                    } else if (convertingFrom == Modloaders.FABRIC && convertingTo == Modloaders.FORGE) {
                        amount /= 81;
                    }
                    ((ObjectNode) result).put("amount", amount);
                    jsonNode.put("info", "This recipe has been modified by the Incompetent Modders Recipe Converter.");
                }
            });
        }
    }
    
    private static boolean isIngredientFluid(JsonNode jsonNodes) {
        return jsonNodes.has("fluid") || jsonNodes.has("fluidTag");
    }
    private static boolean shouldModify(JsonNode jsonNode) {
        return !jsonNode.has("info");
    }
}
