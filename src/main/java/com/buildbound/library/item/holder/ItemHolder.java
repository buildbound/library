package com.buildbound.library.item.holder;

import com.buildbound.library.configuration.ConfigSection;
import com.buildbound.library.item.Item;
import com.buildbound.library.placeholder.Placeholder;
import com.buildbound.library.wire.holder.impl.ConfigHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class ItemHolder extends ConfigHolder<Item> implements Item {

    public ItemHolder(final @NotNull String configPath, final @NotNull Function<ConfigSection, Item> function) {
        super(configPath, function);
    }

    @Override
    public @NotNull ItemStack toItemStack(final @NotNull Placeholder placeholder) {
        return this.getValue().toItemStack(placeholder);
    }

}
