package com.incompetent_modders.modloader_recipe_converter.foundation.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class ResourceLocation {
    String namespace;
    String path;

    public ResourceLocation(String namespace, String path) {
        this.namespace = namespace;
        this.path = path;
    }

    public ResourceLocation(String path) {
        this("minecraft", path);
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isC() {
        return namespace.equals("c");
    }

    public boolean isForge() {
        return namespace.equals("forge");
    }

    public String toString() {
        return namespace + ":" + path;
    }

    public String toRawTagString() {
        return "#" + namespace + ":" + path;
    }

    public static ResourceLocation of(String value) {
        String[] parts = value.split(":");
        return new ResourceLocation(parts[0], parts[1]);
    }

    public JsonElement toJson() {
        return new JsonPrimitive(toString());
    }
}
