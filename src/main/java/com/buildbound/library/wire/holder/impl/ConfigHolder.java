package com.buildbound.library.wire.holder.impl;

import com.buildbound.library.Plugin;
import com.buildbound.library.configuration.ConfigSection;
import com.buildbound.library.wire.holder.Holder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class ConfigHolder<T> implements Holder<T> {

    private T value;

    private final String configPath;
    private final Function<ConfigSection, T> function;

    public ConfigHolder(final @NotNull String configPath,
                        final @NotNull Function<ConfigSection, T> function) {
        this.configPath = configPath;
        this.function = function;
    }

    @Override
    public @NotNull T getValue() {
        return this.value;
    }

    public void load(final @NotNull Plugin plugin) {
        final ConfigSection configSection = plugin.getConfig(this.configPath);
        this.value = this.function.apply(configSection);
    }

}
