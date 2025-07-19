package com.buildbound.library.menu.impl;

import com.buildbound.library.configuration.ConfigSection;
import com.buildbound.library.context.Context;
import com.buildbound.library.context.key.ContextKey;
import com.buildbound.library.item.Item;
import com.buildbound.library.menu.Menu;
import com.buildbound.library.menu.button.MenuButton;
import com.buildbound.library.menu.context.DynamicContextFunction;
import com.buildbound.library.menu.holder.BukkitMenuHolder;
import com.buildbound.library.menu.inventory.FakePlayerInventory;
import com.buildbound.library.menu.pattern.Pattern;
import com.buildbound.library.menu.render.RenderResult;
import com.buildbound.library.placeholder.Placeholder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Function;

public class BukkitMenu implements Menu {

    private final String title;

    private final Pattern pattern;
    private final Pattern playerPattern;

    private final Context context = new Context();
    private final MutableList<DynamicContextFunction<?>> contextFunctions = Lists.mutable.empty();

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
    public @NotNull Menu withButton(final @NotNull MenuButton menuButton, final @NotNull String character) {
        final char characters = Objects.requireNonNull(character).charAt(0);
        return this.withButton(menuButton, characters);
    }

    @Override
    public @NotNull <T> Menu constant(final @NotNull ContextKey<T> contextKey, @NotNull final T value) {
        this.context.set(contextKey, value);
        return this;
    }

    @Override
    public @NotNull <T> Menu dynamic(final @NotNull ContextKey<T> contextKey, final @NotNull Function<Context, T> function) {
        this.contextFunctions.add(new DynamicContextFunction<>(contextKey, function));
        return this;
    }

    @Override
    public void openInventory(final @NotNull Player player, final @NotNull Context context) {
        context.consume(this.context);

        final BukkitMenuHolder menuHolder = new BukkitMenuHolder(
                this,
                context,
                this.title,
                this.pattern.getInventorySize()
        );

        final Inventory inventory = menuHolder.getInventory();

        // set default context
        context.set(Menu.INVENTORY, inventory);
        context.set(Menu.MENU, this);
        context.set(Menu.PLAYER, player);

        if (this.playerPattern.getInventorySize() > 0) {
            context.set(Menu.FAKE_INVENTORY, new FakePlayerInventory(player));
        }

        // apply dynamic context functions
        for (final DynamicContextFunction<?> function : this.contextFunctions) {
            function.apply(context);
        }

        this.pattern.renderPattern(context, inventory);
        this.playerPattern.renderPlayerPattern(context);

        player.openInventory(inventory);
    }

    @Override
    public void handleClickEvent(final @NotNull InventoryClickEvent event, final @NotNull Context context) {
        final Context snapshot = context.createSnapshot();

        if (event.getView().getTopInventory().equals(event.getClickedInventory())) {
            snapshot.set(Menu.INVENTORY, event.getInventory());
            snapshot.set(Menu.LAST_CLICK, event);

            final int slot = event.getSlot();
            this.pattern.handleClickEvent(slot, snapshot, true);
            return;
        }

        snapshot.set(Menu.INVENTORY, context.get(Menu.FAKE_INVENTORY));
        snapshot.set(Menu.LAST_CLICK, event);

        final int slot = event.getSlot();
        this.playerPattern.handleClickEvent(slot, snapshot, false);

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

    @Override
    public void renderSlot(final @NotNull Inventory inventory,
                           final @NotNull Context context,
                           final int slot) {
        if (inventory instanceof FakePlayerInventory) {
            this.playerPattern.renderSlot(inventory, context, slot);
            return;
        }

        this.pattern.renderSlot(inventory, context, slot);
    }

}
