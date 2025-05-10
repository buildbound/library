package com.buildbound.library.menu;

import com.buildbound.library.context.Context;
import com.buildbound.library.context.key.ContextKey;
import com.buildbound.library.menu.button.MenuButton;
import com.buildbound.library.menu.inventory.FakePlayerInventory;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public interface Menu {

    @NotNull
    ContextKey<Inventory> INVENTORY = new ContextKey<>(Key.key("library", "inventory"));

    @NotNull
    ContextKey<FakePlayerInventory> FAKE_INVENTORY = new ContextKey<>(Key.key("library", "fake_inventory"));

    @NotNull
    ContextKey<InventoryClickEvent> LAST_CLICK = new ContextKey<>(Key.key("library", "last_click"));

    @NotNull
    ContextKey<Boolean> CLOSED = new ContextKey<>(Key.key("library", "closed"));

    @NotNull
    Menu withButton(final @NotNull MenuButton menuButton, final char character);

    void openInventory(final @NotNull Player player, final @NotNull Context context);

    void handleClickEvent(final @NotNull InventoryClickEvent event, final @NotNull Context context);

    void handleCloseEvent(final @NotNull InventoryCloseEvent event, final @NotNull Context context);

    void refreshPlayerInventory(final @NotNull Player player, final @NotNull Context context);

}
