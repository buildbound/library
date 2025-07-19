package com.buildbound.library.menu.context;

import com.buildbound.library.context.Context;
import com.buildbound.library.context.key.ContextKey;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public record DynamicContextFunction<T>(ContextKey<T> contextKey, Function<Context, T> function) {

    public void apply(final @NotNull Context context) {
        context.set(this.contextKey, this.function.apply(context));
    }

}
