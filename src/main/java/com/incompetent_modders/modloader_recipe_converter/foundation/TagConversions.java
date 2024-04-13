package com.incompetent_modders.modloader_recipe_converter.foundation;

public enum TagConversions {
    INGOT("c:{}_ingots", "forge:ingots/{}"),
    NUGGET("c:{}_nuggets", "forge:nuggets/{}"),
    GEM("c:{}_gems", "forge:gems/{}"),
    DUST("c:{}_dusts", "forge:dusts/{}"),
    PLATE("c:{}_plates", "forge:plates/{}"),
    GEAR("c:{}_gears", "forge:gears/{}"),
    ROD("c:{}_rods", "forge:rods/{}"),
    BLOCK("c:{}_blocks", "forge:storage_blocks/{}"),
    ORE("c:{}_ores", "forge:ores/{}"),
    COIN("c:{}_coins", "forge:coins/{}"),
    CRYSTAL("c:{}_crystals", "forge:crystals/{}"),
    DYE("c:{}_dyes", "forge:dyes/{}"),
    RAW_MATERIAL("c:raw_{}_ores", "forge:raw_materials/{}"),
    ;
    
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
        return name.replace("forge:", "c:");
    }
    public static String simpleToForge(String name) {
        return name.replace("c:", "forge:");
    }
    public static String toFabric(String forgeTag) {
        if (forgeTag == null || !forgeTag.startsWith("forge:")) {
            return forgeTag;
        }
        String[] parts = forgeTag.split(":");
        String domain = parts[0];
        String path = parts[1];
        
        if (path.contains("/")) {
            String[] pathParts = path.split("/");
            String category = pathParts[0];
            String material = pathParts[1];
            
            return switch (category) {
                case "ingots" -> "c:" + material + "_ingots";
                case "nuggets" -> "c:" + material + "_nuggets";
                case "gems" -> "c:" + material + "_gems";
                case "dusts" -> "c:" + material + "_dusts";
                case "plates" -> "c:" + material + "_plates";
                case "gears" -> "c:" + material + "_gears";
                case "rods" -> "c:" + material + "_rods";
                case "storage_blocks" -> "c:" + material + "_blocks";
                case "ores" -> "c:" + material + "_ores";
                case "coins" -> "c:" + material + "_coins";
                case "crystals" -> "c:" + material + "_crystals";
                case "dyes" -> "c:" + material + "_dyes";
                case "raw_materials" -> "c:raw_" + material + "_ores";
                case "tiny_dusts" -> "c:" + material + "_tiny_dusts";
                case "small_dusts" -> "c:" + material + "_small_dusts";
                case "seeds" -> "c:" + material + "_seeds";
                case "saplings" -> "c:" + material + "_saplings";
                default -> "c:" + path.replace("/", "_");
            };
        } else {
            return "c:" + path;
        }
    }
    
    public static String toForge(String fabricTag) {
        if (fabricTag == null || !fabricTag.startsWith("c:")) {
            return fabricTag;
        }
        String[] parts = fabricTag.split(":");
        String domain = parts[0];
        String material = parts[1];
        if (material.endsWith("_ingots")) {
            return "forge:ingots/" + material.replace("_ingots", "");
        } else if (material.endsWith("_nuggets")) {
            return "forge:nuggets/" + material.replace("_nuggets", "");
        } else if (material.endsWith("_gems")) {
            return "forge:gems/" + material.replace("_gems", "");
        } else if (material.endsWith("_dusts")) {
            return "forge:dusts/" + material.replace("_dusts", "");
        } else if (material.endsWith("_plates")) {
            return "forge:plates/" + material.replace("_plates", "");
        } else if (material.endsWith("_gears")) {
            return "forge:gears/" + material.replace("_gears", "");
        } else if (material.endsWith("_rods")) {
            return "forge:rods/" + material.replace("_rods", "");
        } else if (material.endsWith("_blocks")) {
            return "forge:storage_blocks/" + material.replace("_blocks", "");
        } else if (material.endsWith("_ores")) {
            return "forge:ores/" + material.replace("_ores", "");
        } else if (material.endsWith("_coins")) {
            return "forge:coins/" + material.replace("_coins", "");
        } else if (material.endsWith("_crystals")) {
            return "forge:crystals/" + material.replace("_crystals", "");
        } else if (material.endsWith("_dyes")) {
            return "forge:dyes/" + material.replace("_dyes", "");
        } else if (material.startsWith("raw_") && material.endsWith("_ores")) {
            return "forge:raw_materials/" + material.replace("raw_", "").replace("_ores", "");
        } else if (material.endsWith("_tiny_dusts")) {
            return "forge:tiny_dusts/" + material.replace("_tiny_dusts", "");
        } else if (material.endsWith("_small_dusts")) {
            return "forge:small_dusts/" + material.replace("_small_dusts", "");
        } else if (material.endsWith("_seeds")) {
            return "forge:seeds/" + material.replace("_seeds", "");
        } else if (material.endsWith("_saplings")) {
            return "forge:saplings/" + material.replace("_saplings", "");
        }
        else {
            return "forge:" + material;
        }
    }
    
}
