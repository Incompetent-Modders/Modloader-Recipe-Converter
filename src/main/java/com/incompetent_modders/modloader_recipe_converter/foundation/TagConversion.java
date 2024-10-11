package com.incompetent_modders.modloader_recipe_converter.foundation;

import com.incompetent_modders.modloader_recipe_converter.foundation.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public enum TagConversion {
    INGOT("{}_ingots", "ingots/{}", List.of("ingots")),
    NUGGET("{}_nuggets", "nuggets/{}", List.of("nuggets")),
    GEM("{}_gems", "gems/{}", List.of("gems")),
    DUST("{}_dusts", "dusts/{}", List.of("dusts")),
    PLATE("{}_plates", "plates/{}", List.of("plates")),
    GEAR("{}_gears", "gears/{}", List.of("gears")),
    ROD("{}_rods", "rods/{}", List.of("rods")),
    BLOCK("{}_blocks", "storage_blocks/{}", List.of("blocks", "storage", "storage_block")),
    ORE("{}_ores", "ores/{}", List.of("ores")),
    COIN("{}_coins", "coins/{}", List.of("coins")),
    CRYSTAL("{}_crystals", "crystals/{}", List.of("crystals")),
    DYE("{}_dyes", "dyes/{}", List.of("dyes")),
    RAW_MATERIAL("raw_{}_ores", "raw_materials/{}", List.of("raw_materials", "materials", "raw")),
    TINY_DUSTS("{}_tiny_dusts", "tiny_dusts/{}", List.of("tiny_dusts", "tiny")),
    SMALL_DUSTS("{}_small_dusts", "small_dusts/{}", List.of("small_dusts", "small")),
    SEEDS("{}_seeds", "seeds/{}", List.of("seeds")),
    SAPLINGS("{}_saplings", "saplings/{}", List.of("saplings")),
    SIMPLE("{}", "{}", List.of()),
    ;
    private static final String fabricTag = "c:";
    private static final String forgeTag = "forge:";
    private final String fabricFormat;
    private final String forgeFormat;
    private final List<String> keyWords;
    
    TagConversion(String fabricFormat, String forgeFormat, List<String> keyWords) {
        this.fabricFormat = fabricFormat;
        this.forgeFormat = forgeFormat;
        this.keyWords = keyWords;
    }
    
    public String getFabricFormat() {
        return fabricFormat;
    }
    
    public String getForgeFormat() {
        return forgeFormat;
    }

    public List<String> getKeyWords() {
        return keyWords;
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
            for (TagConversion conversion : TagConversion.values()) {
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
        for (TagConversion conversion : TagConversion.values()) {
            String fabricFormat = conversion.getFabricFormat().replace("{}", "");
            if (material.endsWith(fabricFormat)) {
                System.out.println("Found " + fabricFormat + " in " + material);
                material.replace(fabricFormat, "");
                forgeTagResult.set(forgeTag + conversion.getForgeFormat().replace("{}", material));
            } else {
                forgeTagResult.set(forgeTag + material);
            }
        }
        System.out.printf("Converted " + fabricTag + " to " + forgeTagResult.get());
        return forgeTagResult.get();
    }

    public static TagConversion getTagType(ResourceLocation input) {
        String path = input.getPath();
        if (path.endsWith("_ingots") || path.startsWith("ingots/")) {
            return TagConversion.INGOT;
        }
        if (path.endsWith("_nuggets") || path.startsWith("nuggets/")) {
            return TagConversion.NUGGET;
        }
        if (path.endsWith("_gems") || path.startsWith("gems/")) {
            return TagConversion.GEM;
        }
        if ((path.endsWith("_dusts") && !path.endsWith("_tiny_dusts") && !path.endsWith("_small_dusts")) || path.startsWith("dusts/")) {
            return TagConversion.DUST;
        }
        if (path.endsWith("_plates") || path.startsWith("plates/")) {
            return TagConversion.PLATE;
        }
        if (path.endsWith("_gears") || path.startsWith("gears/")) {
            return TagConversion.GEAR;
        }
        if (path.endsWith("_rods") || path.startsWith("rods/")) {
            return TagConversion.ROD;
        }
        if (path.endsWith("_blocks") || path.startsWith("storage_blocks/")) {
            return TagConversion.BLOCK;
        }
        if (path.endsWith("_ores") || path.startsWith("ores/")) {
            return TagConversion.ORE;
        }
        if (path.endsWith("_coins") || path.startsWith("coins/")) {
            return TagConversion.COIN;
        }
        if (path.endsWith("_crystals") || path.startsWith("crystals/")) {
            return TagConversion.CRYSTAL;
        }
        if (path.endsWith("_dyes") || path.startsWith("dyes/")) {
            return TagConversion.DYE;
        }
        if (path.endsWith("_seeds") || path.startsWith("seeds/")) {
            return TagConversion.SEEDS;
        }
        if (path.endsWith("_saplings") || path.startsWith("saplings/")) {
            return TagConversion.SAPLINGS;
        }
        if ((path.startsWith("raw_") && path.endsWith("_ores")) || path.startsWith("raw_materials/")) {
            return TagConversion.RAW_MATERIAL;
        }
        if (path.endsWith("tiny_dusts") || path.startsWith("tiny_dusts/")) {
            return TagConversion.TINY_DUSTS;
        }
        if (path.endsWith("small_dusts") || path.startsWith("small_dusts/")) {
            return TagConversion.SMALL_DUSTS;
        }
        else {
            return TagConversion.SIMPLE;
        }
    }
}
