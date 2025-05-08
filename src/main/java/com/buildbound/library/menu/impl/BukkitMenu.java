package com.buildbound.library.menu.impl;

import com.buildbound.library.configuration.ConfigSection;
import com.buildbound.library.context.Context;
import com.buildbound.library.item.Item;
import com.buildbound.library.menu.Menu;
import com.buildbound.library.menu.button.MenuButton;
import com.buildbound.library.menu.holder.BukkitMenuHolder;
import com.buildbound.library.placeholder.Placeholder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BukkitMenu implements Menu {

    private final String title;
    private final int size;

    private final MutableMap<Integer, MenuButton> buttons = Maps.mutable.empty();
    private final ImmutableMap<Integer, Item> items;

    public BukkitMenu(final @NotNull ConfigSection configSection) {
        this.title = configSection.getString("title", "Inventory");
        this.size = configSection.getInt("size");

        // Load Items
        final MutableMap<Integer, Item> items = Maps.mutable.empty();

        for (final ConfigSection section : configSection.getSectionList("menu-items")) {
            final List<Integer> slots = section.getIntegerList("slots");
            final Item displayItem = section.getItem("display-item");

            for (final Integer slot : slots) {
                items.put(slot, displayItem);
            }
        }

        this.items = items.toImmutable();
    }

    @Override
    public @NotNull Menu withButton(final @NotNull MenuButton menuButton, final @NotNull Iterable<Integer> slots) {
        for (final int slot : slots) {
            this.buttons.put(slot, menuButton);
        }

        return this;
    }

    @Override
    public void openInventory(final @NotNull Player player, final @NotNull Context context) {
        final BukkitMenuHolder menuHolder = new BukkitMenuHolder(
                this,
                context,
                this.title,
                this.size
        );

        final Placeholder placeholder = Placeholder.placeholder();
        final Inventory inventory = menuHolder.getInventory();

        // set default context
        context.set(Menu.INVENTORY, inventory);

        // set static items
        for (final Pair<Integer, Item> itemPair : this.items.keyValuesView()) {
            final int slot = itemPair.getOne();
            final Item item = itemPair.getTwo();

            inventory.setItem(slot, item.toItemStack(placeholder));
        }

        // render buttons
        for (final Pair<Integer, MenuButton> buttonPair : this.buttons.keyValuesView()) {
            final int slot = buttonPair.getOne();
            final MenuButton button = buttonPair.getTwo();

            button.render(slot, context);
        }

        player.openInventory(inventory);
    }

    @Override
    public void handleClickEvent(final @NotNull InventoryClickEvent event, final @NotNull Context context) {
        final int slot = event.getSlot();
        final MenuButton button = this.buttons.get(slot);

        if (button == null) {
            return;
        }

        context.set(Menu.LAST_CLICK, event);
        button.onClick(slot, context);
    }

}
