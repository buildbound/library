package com.buildbound.library.menu.holder;

import com.buildbound.library.context.Context;
import com.buildbound.library.menu.Menu;
import com.buildbound.library.utils.ComponentUtils;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class BukkitMenuHolder implements InventoryHolder {

    private final Menu menu;
    private final Context context;
    private final Inventory inventory;

    public BukkitMenuHolder(final @NotNull Menu menu,
                            final @NotNull Context context,
                            final @NotNull String title,
                            final int size) {
        this.menu = menu;
        this.context = context;
        this.inventory = Bukkit.createInventory(this, size, ComponentUtils.toComponent(title));
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }

    @NotNull
    public Menu getMenu() {
        return this.menu;
    }

    @NotNull
    public Context getContext() {
        return this.context;
    }

}
