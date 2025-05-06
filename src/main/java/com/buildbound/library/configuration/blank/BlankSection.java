package com.buildbound.library.configuration.blank;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationOptions;
import org.bukkit.configuration.MemorySection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class BlankSection extends MemorySection implements Configuration {

    public BlankSection() {
        super();
    }

    @Override
    public void addDefaults(@NotNull final Map<String, Object> defaults) {

    }

    @Override
    public void addDefaults(@NotNull final Configuration defaults) {

    }

    @Override
    public void setDefaults(@NotNull final Configuration defaults) {

    }

    @Override
    public @Nullable Configuration getDefaults() {
        return null;
    }

    @Override
    public @NotNull ConfigurationOptions options() {
        return new Options(this);
    }

    static class Options extends ConfigurationOptions {

        protected Options(@NotNull final Configuration configuration) {
            super(configuration);
        }

    }

}
