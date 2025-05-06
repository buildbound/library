package com.buildbound.library.wire.holder;

import com.buildbound.library.configuration.ConfigSection;
import com.buildbound.library.wire.holder.impl.ConfigHolder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public interface Holder<T> {

    @NotNull
    static <T> Holder<T> config(final @NotNull String configPath,
                                final @NotNull Function<ConfigSection, T> function) {
        return new ConfigHolder<>(configPath, function);
    }

    @NotNull
    T getValue();

}
