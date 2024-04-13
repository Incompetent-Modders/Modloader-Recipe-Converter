package com.incompetent_modders.modloader_recipe_converter.foundation;

public enum Modloaders {
    FORGE("forge"),
    FABRIC("fabric"),
    CUSTOM("custom")
    ;
    
    private final String name;
    
    Modloaders(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
}
