package com.buildbound.library.context;

import com.buildbound.library.context.key.ContextKey;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.map.MutableMap;
import org.jetbrains.annotations.NotNull;

public class Context {

    private final MutableMap<String, Object> context = Maps.mutable.empty();

    public <T> void set(final @NotNull ContextKey<T> key, final @NotNull T value) {
        this.context.put(key.key().asString(), value);
    }

    @SuppressWarnings("unchecked") // :( - Find fix for in future
    public <T> T get(final @NotNull ContextKey<T> key) {
        return (T) this.context.get(key.key().asString());
    }

}
