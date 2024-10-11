package com.incompetent_modders.modloader_recipe_converter.foundation.tag;

import com.incompetent_modders.modloader_recipe_converter.foundation.TagConversion;
import com.incompetent_modders.modloader_recipe_converter.foundation.util.ResourceLocation;

import java.util.Objects;

public class ToForge {
    final TagConversion value;

    public ToForge(TagConversion pValue) {
        this.value = pValue;
    }

    protected String getForgeFormat() {
        return value.getForgeFormat();
    }

    protected String getFabricFormat() {
        return value.getFabricFormat();
    }

    public ResourceLocation simple(ResourceLocation tag) {
        if (!tag.isC()) {
            return tag;
        }
        tag.setNamespace("forge");
        return tag;
    }

    public ResourceLocation convert(ResourceLocation tag) {
        if (!tag.isC()) {
            return tag;
        }
        String namespace = tag.getNamespace();
        String path = tag.getPath();
        String fabricFormat = getFabricFormat();
        ResourceLocation forgedTag = new ResourceLocation(namespace, path);
        if (Objects.equals(namespace, "c")) {
            forgedTag.setNamespace("forge");
        } else {
            forgedTag.setNamespace(namespace);
        }
        String toRemove = fabricFormat.replace("{}", "");
        String tagValue = path.replace(toRemove, "");
        forgedTag.setPath(getForgeFormat().replace("{}", tagValue));
        return forgedTag;
    }
}
