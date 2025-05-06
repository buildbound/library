package com.buildbound.library.placeholder.impl;

import com.buildbound.library.placeholder.Placeholder;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.tuple.Pair;
import org.jetbrains.annotations.NotNull;

public class SimplePlaceholder implements Placeholder {

    private final MutableMap<String, Object> placeholders = Maps.mutable.empty();

    @Override
    public @NotNull Placeholder with(final @NotNull String name, final @NotNull Object value) {
        this.placeholders.put(name, value);
        return this;
    }

    @Override
    public @NotNull TagResolver[] asTagResolver() {
        return this.placeholders.keyValuesView()
                .collect(this::toTagResolver)
                .toArray(new TagResolver[0]);
    }

    private TagResolver toTagResolver(final @NotNull Pair<String, Object> pair) {
        final String key = pair.getOne();
        final Object value = pair.getTwo();

        if (value instanceof ComponentLike componentLike) {
            return net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.component(key, componentLike);
        }

        return net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.parsed(key, value.toString());
    }

}
