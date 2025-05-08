package com.buildbound.library.item;

import com.buildbound.library.configuration.ConfigSection;
import com.buildbound.library.item.holder.ItemHolder;
import com.buildbound.library.item.impl.BukkitItem;
import com.buildbound.library.placeholder.Placeholder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Function;

public interface Item {

    @NotNull
    static Item holder(final @NotNull String configPath, final @NotNull Function<ConfigSection, Item> function) {
        return new ItemHolder(configPath, function);
    }

    @NotNull
    static Item item(final @NotNull ConfigSection configSection, final @NotNull String path) {
        return new BukkitItem(Objects.requireNonNull(configSection.getConfigurationSection(path)));
    }

    @NotNull ItemStack toItemStack(final @NotNull Placeholder placeholder);

}
