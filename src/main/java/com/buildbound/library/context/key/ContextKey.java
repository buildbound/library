package com.buildbound.library.context.key;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

public record ContextKey<T>(@NotNull Key key) {
}
