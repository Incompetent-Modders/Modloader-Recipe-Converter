package com.incompetent_modders.modloader_recipe_converter.foundation.convertion;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.incompetent_modders.modloader_recipe_converter.foundation.Modloaders;

public class AmountConverting {
    public static void convertFluidAmount(ObjectNode jsonNode, Modloaders convertingFrom, Modloaders convertingTo) {
        ArrayNode ingredients = (ArrayNode) jsonNode.get("ingredients");
        if (jsonNode.has("ingredients") && shouldModify(jsonNode, convertingTo)) {
            ingredients.forEach(ingredient -> {
                if (isIngredientFluid(ingredient) && ingredient.has("amount")) {
                    int amount = ingredient.get("amount").asInt();
                    if (convertingFrom == Modloaders.FORGE && convertingTo == Modloaders.FABRIC) {
                        amount *= 81;
                    } else if (convertingFrom == Modloaders.FABRIC && convertingTo == Modloaders.FORGE) {
                        amount /= 81;
                    }
                    System.out.printf("Converted " + ingredient.get("amount").asInt() + " to " + amount);
                    ((ObjectNode) ingredient).put("amount", amount);
                }
            });
        }
        ArrayNode results = (ArrayNode) jsonNode.get("results");
        if (jsonNode.has("results") && shouldModify(jsonNode, convertingTo)) {
            results.forEach(result -> {
                if (isIngredientFluid(result) && result.has("amount")) {
                    int amount = result.get("amount").asInt();
                    if (convertingFrom == Modloaders.FORGE && convertingTo == Modloaders.FABRIC) {
                        amount *= 81;
                    } else if (convertingFrom == Modloaders.FABRIC && convertingTo == Modloaders.FORGE) {
                        amount /= 81;
                    }
                    System.out.printf("Converted " + result.get("amount").asInt() + " to " + amount);
                    ((ObjectNode) result).put("amount", amount);
                }
            });
        }
    }
    
    private static boolean isIngredientFluid(JsonNode jsonNodes) {
        return jsonNodes.has("fluid") || jsonNodes.has("fluidTag");
    }
    private static boolean shouldModify(JsonNode jsonNode, Modloaders loader) {
        if (jsonNode.has("convertedTo")) {
            return !jsonNode.get("convertedTo").asText().equals(loader.getName());
        }
        return true;
    }
}
