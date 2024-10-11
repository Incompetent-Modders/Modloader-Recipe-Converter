package com.incompetent_modders.modloader_recipe_converter.foundation.tag;

import com.incompetent_modders.modloader_recipe_converter.foundation.TagConversion;
import com.incompetent_modders.modloader_recipe_converter.foundation.util.ResourceLocation;

import java.util.Objects;

public class ToFabric {
    final TagConversion value;

    public ToFabric(TagConversion pValue) {
        this.value = pValue;
    }

    protected String getForgeFormat() {
        return value.getForgeFormat();
    }

    protected String getFabricFormat() {
        return value.getFabricFormat();
    }

    public ResourceLocation simple(ResourceLocation tag) {
        if (!tag.isForge()) {
            return tag;
        }
        tag.setNamespace("c");
        return tag;
    }

    public ResourceLocation convert(ResourceLocation tag) {
        if (!tag.isForge()) {
            return tag;
        }
        String namespace = tag.getNamespace();
        String path = tag.getPath();
        String forgeFormat = getForgeFormat();
        ResourceLocation fabricatedTag = new ResourceLocation(namespace, path);
        if (Objects.equals(namespace, "forge")) {
            fabricatedTag.setNamespace("c");
        } else {
            fabricatedTag.setNamespace(namespace);
        }
        String toRemove = forgeFormat.replace("{}", "");
        String tagValue = path.replace(toRemove, "");
        fabricatedTag.setPath(getFabricFormat().replace("{}", tagValue));
        return fabricatedTag;
    }
}
