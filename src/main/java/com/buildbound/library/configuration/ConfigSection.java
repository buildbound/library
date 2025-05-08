package com.buildbound.library.configuration;

import com.buildbound.library.action.Action;
import com.buildbound.library.item.Item;
import org.bukkit.configuration.ConfigurationSection;
import org.eclipse.collections.api.list.ImmutableList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ConfigSection extends ConfigurationSection {

    @NotNull
    Action getAction(final @NotNull String path);

    @NotNull
    Item getItem(final @NotNull String path);

    @NotNull
    ImmutableList<ConfigSection> getSectionList(final @NotNull String path);

    @Override
    @Nullable ConfigSection getDefaultSection();

    @Override
    @Nullable ConfigSection getConfigurationSection(@NotNull String path);
}
