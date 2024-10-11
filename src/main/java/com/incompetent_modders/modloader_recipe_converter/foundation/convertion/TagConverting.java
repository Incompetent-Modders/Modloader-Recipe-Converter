package com.incompetent_modders.modloader_recipe_converter.foundation.convertion;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.prism.
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.incompetent_modders.modloader_recipe_converter.foundation.Modloaders;
import com.incompetent_modders.modloader_recipe_converter.foundation.TagConversion;
import com.incompetent_modders.modloader_recipe_converter.foundation.tag.ToFabric;
import com.incompetent_modders.modloader_recipe_converter.foundation.tag.ToForge;
import com.incompetent_modders.modloader_recipe_converter.foundation.util.ResourceLocation;

import java.util.Map;

public class TagConverting {
    
    public static void convertItemTag(JsonObject jsonNode, Modloaders convertingFrom, Modloaders convertingTo) {
        JsonArray ingredients = (JsonArray) jsonNode.get("ingredients");
        JsonObject keys = (JsonObject) jsonNode.get("key");
        if (jsonNode.has("ingredients") && shouldModify(jsonNode, convertingFrom)) {
            for (JsonElement ingredient : ingredients) {
                JsonObject ingred = ingredient.getAsJsonObject();
                if (isItemTag(ingred)) {
                    ResourceLocation tag = ResourceLocation.of(ingred.get("tag").getAsString());
                    ToForge toForge = new ToForge(TagConversion.getTagType(tag));
                    ToFabric toFabric = new ToFabric(TagConversion.getTagType(tag));
                    if (convertingFrom == Modloaders.FORGE && convertingTo == Modloaders.FABRIC) {
                        tag = toFabric.convert(tag);
                    } else if (convertingFrom == Modloaders.FABRIC && convertingTo == Modloaders.FORGE) {
                        tag = toForge.convert(tag);
                    }
                    assert tag != null;
                    ((JsonObject) ingredient).add("tag", tag.toJson());
                }
            }
        }
        if (jsonNode.has("key") && shouldModify(jsonNode, convertingFrom)) {
            for (Map.Entry<String, JsonElement> key : keys.entrySet()) {
                if (isItemTag(key.getValue().getAsJsonObject())) {
                    ResourceLocation tag = ResourceLocation.of(key.getValue().getAsJsonObject().get("tag").getAsString());
                    ToForge toForge = new ToForge(TagConversion.getTagType(tag));
                    ToFabric toFabric = new ToFabric(TagConversion.getTagType(tag));
                    if (convertingFrom == Modloaders.FORGE && convertingTo == Modloaders.FABRIC) {
                        tag = toFabric.convert(tag);
                    } else if (convertingFrom == Modloaders.FABRIC && convertingTo == Modloaders.FORGE) {
                        tag = toForge.convert(tag);
                    }
                    key.getValue().getAsJsonObject().addProperty("tag", tag.toString());
                }
            }
            //keys.fields().forEachRemaining(key -> {
            //    ObjectNode keyField = (ObjectNode) key.getValue();
            //    if (isItemTag(keyField)) {
            //        String tagString = keyField.get("tag").asText();
            //        if (convertingFrom == Modloaders.FORGE && convertingTo == Modloaders.FABRIC) {
            //            tagString = TagConversion.toFabric(tagString);
            //        } else if (convertingFrom == Modloaders.FABRIC && convertingTo == Modloaders.FORGE) {
            //            tagString = TagConversion.toForge(tagString);
            //        }
            //        keyField.put("tag", tagString);
            //    }
            //});
        }
    }
    public static void convertFluidTag(JsonObject jsonNode, Modloaders convertingFrom, Modloaders convertingTo) {
        JsonArray ingredients = (JsonArray) jsonNode.get("ingredients");
        if (jsonNode.has("ingredients") && shouldModify(jsonNode, convertingFrom)) {
            for (JsonElement ingredient : ingredients) {
                if (isFluidTag(ingredient.getAsJsonObject())) {
                    ResourceLocation tagString = ResourceLocation.of(ingredient.getAsJsonObject().get("fluidTag").getAsString());
                    ToForge toForge = new ToForge(TagConversion.SIMPLE);
                    ToFabric toFabric = new ToFabric(TagConversion.SIMPLE);
                    if (convertingFrom == Modloaders.FORGE && convertingTo == Modloaders.FABRIC) {
                        tagString = toFabric.simple(tagString);
                    } else if (convertingFrom == Modloaders.FABRIC && convertingTo == Modloaders.FORGE) {
                        tagString = toForge.simple(tagString);
                    }
                    ingredient.getAsJsonObject().add("fluidTag", tagString.toJson());
                }
            }
            //ingredients.forEach(ingredient -> {
            //    if (isFluidTag(ingredient)) {
            //        String tagString = ingredient.get("fluidTag").asText();
            //        if (convertingFrom == Modloaders.FORGE && convertingTo == Modloaders.FABRIC) {
            //            tagString = TagConversion.simpleToC(tagString);
            //        } else if (convertingFrom == Modloaders.FABRIC && convertingTo == Modloaders.FORGE) {
            //            tagString = TagConversion.simpleToForge(tagString);
            //        }
            //        ((ObjectNode) ingredient).put("fluidTag", tagString);
            //    }
            //});
            
        }
    }
    
    private static boolean isItemTag(JsonObject jsonNode) {
        return jsonNode.has("tag");
    }
    private static boolean isFluidTag(JsonObject jsonNode) {
        return jsonNode.has("fluidTag");
    }
    
    private static boolean shouldModify(JsonObject jsonNode, Modloaders loader) {
        if (jsonNode.has("convertedTo")) {
            return !jsonNode.get("convertedTo").getAsString().equals(loader.getName());
        }
        return true;
    }
}
