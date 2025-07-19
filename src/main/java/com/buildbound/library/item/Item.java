package com.buildbound.library.item;

import com.buildbound.library.configuration.ConfigSection;
import com.buildbound.library.item.holder.ItemHolder;
import com.buildbound.library.placeholder.Placeholder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public interface Item {

    @NotNull
    static Item holder(final @NotNull String configPath, final @NotNull Function<ConfigSection, Item> function) {
        return new ItemHolder(configPath, function);
    }

    @NotNull ItemStack toItemStack(final @NotNull Placeholder placeholder);

    void updateItemStack(final @NotNull ItemStack itemStack, final @NotNull Placeholder placeholder);

}
