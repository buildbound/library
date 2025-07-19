package com.buildbound.library.menu.container;

import com.buildbound.library.context.Context;
import com.buildbound.library.context.key.ContextKey;
import com.buildbound.library.menu.Menu;
import com.buildbound.library.menu.button.MenuButton;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class MenuContainer implements Menu {

    private final Menu menu;

    public MenuContainer(final @NotNull Menu menu) {
        this.menu = menu;
    }

    @Override
    public @NotNull Menu withButton(final @NotNull MenuButton menuButton, final char character) {
        return this.menu.withButton(menuButton, character);
    }

    @Override
    public @NotNull Menu withButton(final @NotNull MenuButton menuButton, final @NotNull String character) {
        return this.menu.withButton(menuButton, character);
    }

    @Override
    public @NotNull <T> Menu constant(final @NotNull ContextKey<T> contextKey, @NotNull final T value) {
        return this.menu.constant(contextKey, value);
    }

    @Override
    public @NotNull <T> Menu dynamic(final @NotNull ContextKey<T> contextKey, final @NotNull Function<Context, T> function) {
        return this.menu.dynamic(contextKey, function);
    }

    @Override
    public void openInventory(final @NotNull Player player, final @NotNull Context context) {
        this.menu.openInventory(player, context);
    }

    @Override
    public void handleClickEvent(final @NotNull InventoryClickEvent event, final @NotNull Context context) {
        this.menu.handleClickEvent(event, context);
    }

    @Override
    public void handleCloseEvent(final @NotNull InventoryCloseEvent event, final @NotNull Context context) {
        this.menu.handleCloseEvent(event, context);
    }

    @Override
    public void refreshPlayerInventory(final @NotNull Player player, final @NotNull Context context) {
        this.menu.refreshPlayerInventory(player, context);
    }

    @Override
    public void renderSlot(final @NotNull Inventory inventory, final @NotNull Context context, final int slot) {
        this.menu.renderSlot(inventory, context, slot);
    }

}