package com.buildbound.library.menu.listeners;

import com.buildbound.library.menu.Menu;
import com.buildbound.library.menu.holder.BukkitMenuHolder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

public class MenuCloseListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryClose(final @NotNull InventoryCloseEvent event) {
        final InventoryView inventoryView = event.getView();
        final Inventory topInventory = inventoryView.getTopInventory();

        if (!(topInventory.getHolder(false) instanceof BukkitMenuHolder bukkitMenuHolder)) {
            return;
        }

        final Menu menu = bukkitMenuHolder.getMenu();
        bukkitMenuHolder.getContext().set(Menu.CLOSED, true);

        menu.handleCloseEvent(event, bukkitMenuHolder.getContext());
    }

}
