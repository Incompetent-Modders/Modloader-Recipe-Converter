package com.incompetent_modders.modloader_recipe_converter.foundation;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public enum TagConversions {
    INGOT("{}_ingots", "ingots/{}"),
    NUGGET("{}_nuggets", "nuggets/{}"),
    GEM("{}_gems", "gems/{}"),
    DUST("{}_dusts", "dusts/{}"),
    PLATE("{}_plates", "plates/{}"),
    GEAR("{}_gears", "gears/{}"),
    ROD("{}_rods", "rods/{}"),
    BLOCK("{}_blocks", "storage_blocks/{}"),
    ORE("{}_ores", "ores/{}"),
    COIN("{}_coins", "coins/{}"),
    CRYSTAL("{}_crystals", "crystals/{}"),
    DYE("{}_dyes", "dyes/{}"),
    RAW_MATERIAL("raw_{}_ores", "raw_materials/{}"),
    TINY_DUSTS("{}_tiny_dusts", "tiny_dusts/{}"),
    SMALL_DUSTS("{}_small_dusts", "small_dusts/{}"),
    SEEDS("{}_seeds", "seeds/{}"),
    SAPLINGS("{}_saplings", "saplings/{}"),
    ;
    private static final String fabricTag = "c:";
    private static final String forgeTag = "forge:";
    private final String fabricFormat;
    private final String forgeFormat;
    
    TagConversions(String fabricFormat, String forgeFormat) {
        this.fabricFormat = fabricFormat;
        this.forgeFormat = forgeFormat;
    }
    
    public String getFabricFormat() {
        return fabricFormat;
    }
    
    public String getForgeFormat() {
        return forgeFormat;
    }
    
    public static String simpleToC(String name) {
        System.out.println("Converting " + name + " to " + name.replace(forgeTag, fabricTag));
        return name.replace(forgeTag, fabricTag);
    }
    public static String simpleToForge(String name) {
        System.out.println("Converting " + name + " to " + name.replace(fabricTag, forgeTag));
        return name.replace(fabricTag, forgeTag);
    }
    public static String toFabric(String forgeTag) {
        if (forgeTag == null || !forgeTag.startsWith("forge:")) {
            return forgeTag;
        }
        String[] parts = forgeTag.split(":");
        String domain = parts[0];
        String path = parts[1];
        AtomicReference<String> fabricTagResult = new AtomicReference<>();
        if (path.contains("/")) {
            String[] pathParts = path.split("/");
            String category = pathParts[0];
            String material = pathParts[1];
            for (TagConversions conversion : TagConversions.values()) {
                String forgeFormat = conversion.getForgeFormat().replace("{}", "");
                if (category.equals(conversion.getForgeFormat().replace("/{}", ""))) {
                    fabricTagResult.set(fabricTag + conversion.getFabricFormat().replace("{}", material));
                }
            }
        } else {
            fabricTagResult.set(fabricTag + path);
        }
        System.out.printf("Converted " + forgeTag + " to " + fabricTagResult.get());
        return fabricTagResult.get();
    }
    
    public static String toForge(String fabricTag) {
        if (fabricTag == null || !fabricTag.startsWith("c:")) {
            return fabricTag;
        }
        String[] parts = fabricTag.split(":");
        String domain = parts[0];
        String material = parts[1];
        AtomicReference<String> forgeTagResult = new AtomicReference<>();
        for (TagConversions conversion : TagConversions.values()) {
            String fabricFormat = conversion.getFabricFormat().replace("{}", "");
            if (material.endsWith(fabricFormat)) {
                System.out.println("Found " + fabricFormat + " in " + material);
                material.replace(fabricFormat, "");
                forgeTagResult.set(forgeTag + conversion.getForgeFormat().replace("{}", material));
            } else {
                forgeTagResult.set(forgeTag + material);
            }
        }
        //if (material.endsWith("_ingots")) {
        //    return "forge:ingots/" + material.replace("_ingots", "");
        //} else if (material.endsWith("_nuggets")) {
        //    return "forge:nuggets/" + material.replace("_nuggets", "");
        //} else if (material.endsWith("_gems")) {
        //    return "forge:gems/" + material.replace("_gems", "");
        //} else if (material.endsWith("_dusts")) {
        //    return "forge:dusts/" + material.replace("_dusts", "");
        //} else if (material.endsWith("_plates")) {
        //    return "forge:plates/" + material.replace("_plates", "");
        //} else if (material.endsWith("_gears")) {
        //    return "forge:gears/" + material.replace("_gears", "");
        //} else if (material.endsWith("_rods")) {
        //    return "forge:rods/" + material.replace("_rods", "");
        //} else if (material.endsWith("_blocks")) {
        //    return "forge:storage_blocks/" + material.replace("_blocks", "");
        //} else if (material.endsWith("_ores")) {
        //    return "forge:ores/" + material.replace("_ores", "");
        //} else if (material.endsWith("_coins")) {
        //    return "forge:coins/" + material.replace("_coins", "");
        //} else if (material.endsWith("_crystals")) {
        //    return "forge:crystals/" + material.replace("_crystals", "");
        //} else if (material.endsWith("_dyes")) {
        //    return "forge:dyes/" + material.replace("_dyes", "");
        //} else if (material.startsWith("raw_") && material.endsWith("_ores")) {
        //    return "forge:raw_materials/" + material.replace("raw_", "").replace("_ores", "");
        //} else if (material.endsWith("_tiny_dusts")) {
        //    return "forge:tiny_dusts/" + material.replace("_tiny_dusts", "");
        //} else if (material.endsWith("_small_dusts")) {
        //    return "forge:small_dusts/" + material.replace("_small_dusts", "");
        //} else if (material.endsWith("_seeds")) {
        //    return "forge:seeds/" + material.replace("_seeds", "");
        //} else if (material.endsWith("_saplings")) {
        //    return "forge:saplings/" + material.replace("_saplings", "");
        //}
        System.out.printf("Converted " + fabricTag + " to " + forgeTagResult.get());
        return forgeTagResult.get();
    }
    
}
