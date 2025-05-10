package com.buildbound.library.menu.impl;

import com.buildbound.library.configuration.ConfigSection;
import com.buildbound.library.context.Context;
import com.buildbound.library.item.Item;
import com.buildbound.library.menu.Menu;
import com.buildbound.library.menu.button.MenuButton;
import com.buildbound.library.menu.holder.BukkitMenuHolder;
import com.buildbound.library.menu.inventory.FakePlayerInventory;
import com.buildbound.library.menu.pattern.Pattern;
import com.buildbound.library.menu.render.RenderResult;
import com.buildbound.library.placeholder.Placeholder;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.eclipse.collections.api.factory.Lists;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BukkitMenu implements Menu {

    private final String title;

    private final Pattern pattern;
    private final Pattern playerPattern;

    public BukkitMenu(final @NotNull ConfigSection configSection) {
        this.title = configSection.getString("title", "Inventory");
        this.pattern = new Pattern(
                Lists.immutable.ofAll(configSection.getStringList("pattern"))
        );
        this.playerPattern = new Pattern(
                Lists.immutable.ofAll(configSection.getStringList("player-pattern"))
        );

        for (final ConfigSection section : configSection.getSectionList("menu-items")) {
            final Item displayItem = section.getItem("display-item");
            final char character = Objects.requireNonNull(section.getString("character")).charAt(0);

            this.pattern.setPalette(
                    character,
                    (slot, context) -> new RenderResult.ItemResult(displayItem.toItemStack(Placeholder.placeholder()))
            );
            this.playerPattern.setPalette(
                    character,
                    (slot, context) -> new RenderResult.ItemResult(displayItem.toItemStack(Placeholder.placeholder()))
            );
        }
    }

    @Override
    public @NotNull Menu withButton(final @NotNull MenuButton menuButton, final char character) {
        this.pattern.setPalette(character, menuButton::render);
        this.pattern.setClick(character, (slot, context) -> {
            menuButton.onClick(slot, context);
            return null;
        });

        this.playerPattern.setPalette(character, menuButton::render);
        this.playerPattern.setClick(character, (slot, context) -> {
            menuButton.onClick(slot, context);
            return null;
        });
        return this;
    }

    @Override
    public void openInventory(final @NotNull Player player, final @NotNull Context context) {
        final BukkitMenuHolder menuHolder = new BukkitMenuHolder(
                this,
                context,
                this.title,
                this.pattern.getInventorySize()
        );

        final Inventory inventory = menuHolder.getInventory();

        // set default context
        context.set(Menu.INVENTORY, inventory);

        if (this.playerPattern.getInventorySize() > 0) {
            context.set(Menu.FAKE_INVENTORY, new FakePlayerInventory(player));
        }

        this.pattern.renderPattern(context, inventory);
//        if (player.getGameMode() != GameMode.CREATIVE) {
            this.playerPattern.renderPlayerPattern(context);
//        }

        player.openInventory(inventory);
    }

    @Override
    public void handleClickEvent(final @NotNull InventoryClickEvent event, final @NotNull Context context) {
        if (event.getView().getTopInventory().equals(event.getClickedInventory())) {
            context.set(Menu.INVENTORY, event.getInventory());
            context.set(Menu.LAST_CLICK, event);

            final int slot = event.getSlot();
            this.pattern.handleClickEvent(slot, context, true);
            return;
        }

        context.set(Menu.INVENTORY, context.get(Menu.FAKE_INVENTORY));
        context.set(Menu.LAST_CLICK, event);

        final int slot = event.getSlot();
        this.playerPattern.handleClickEvent(slot, context, false);

        if (context.has(Menu.FAKE_INVENTORY)) {
            context.get(Menu.FAKE_INVENTORY).refreshSlot(slot);
        }
    }

    @Override
    public void handleCloseEvent(final @NotNull InventoryCloseEvent event, final @NotNull Context context) {
        final Player player = (Player) event.getPlayer();
        player.updateInventory();

        if (!context.has(Menu.FAKE_INVENTORY)){
            return;
        }

        final FakePlayerInventory fakePlayerInventory = context.get(Menu.FAKE_INVENTORY);
        fakePlayerInventory.consumeInventory(player);
    }

    @Override
    public void refreshPlayerInventory(final @NotNull Player player, final @NotNull Context context) {
        if (!context.has(Menu.FAKE_INVENTORY)){
            return;
        }

        final FakePlayerInventory fakePlayerInventory = context.get(Menu.FAKE_INVENTORY);
        fakePlayerInventory.refresh();
    }

}
