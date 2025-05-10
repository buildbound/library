package com.buildbound.library.menu.pattern;

import com.buildbound.library.context.Context;
import com.buildbound.library.menu.Menu;
import com.buildbound.library.menu.inventory.FakePlayerInventory;
import com.buildbound.library.menu.render.RenderResult;
import com.buildbound.library.menu.slot.Slot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.BiFunction;

public class Pattern {

    private final int inventorySize;

    private final MutableMap<Slot, Character> characters = Maps.mutable.empty();

    private final MutableMap<Character, BiFunction<Integer, Context, RenderResult>> renders = Maps.mutable.empty();
    private final MutableMap<Character, BiFunction<Integer, Context, Void>> clicks = Maps.mutable.empty();

    public Pattern(final @NotNull ImmutableList<String> lines) {
        for (int index = 0; index < lines.size(); index++) {
            final String line = lines.get(index);
            final char[] chars = line.replace(" ", "").toCharArray();

            for (int slotIndex = 0; slotIndex < Math.min(9, chars.length); slotIndex++) {
                final char character = chars[slotIndex];
                this.characters.put(new Slot(true, (index * 9) + slotIndex), character);
            }
        }

        this.inventorySize = Math.min(54, lines.size() * 9);
    }

    public int getInventorySize() {
        return this.inventorySize;
    }

    public void setPalette(final char character, final @NotNull BiFunction<Integer, Context, RenderResult> renderResult) {
        this.renders.put(character, renderResult);
    }

    public void setClick(final char character, final @NotNull BiFunction<Integer, Context, Void> click) {
        this.clicks.put(character, click);
    }

    public void renderPattern(final @NotNull Context context,
                              final @NotNull Inventory inventory) {
        if (this.getInventorySize() == 0) {
            return;
        }

        for (final Pair<Slot, Character> entry : this.characters.keyValuesView()) {
            final int slot = entry.getOne().slot();
            final char character = entry.getTwo();

            final BiFunction<Integer, Context, RenderResult> renderer = this.renders.get(character);

            if (renderer == null) {
                continue;
            }

            final RenderResult renderResult = renderer.apply(slot, context);
            switch (renderResult) {
                case RenderResult.ItemResult itemResult -> inventory.setItem(slot, itemResult.itemStack());
            }
        }
    }

    public void renderPlayerPattern(final @NotNull Context context) {
        if (this.getInventorySize() == 0) {
            return;
        }

        final ItemStack[] inventory = new ItemStack[45];
        Arrays.fill(inventory, 0, 8, ItemStack.empty());

        final FakePlayerInventory fakePlayerInventory = context.get(Menu.FAKE_INVENTORY);

        for (int index = 0; index < 36; index++) {
            final int slot = (index % 36) + 9;
            final Character character = this.characters.get(new Slot(true, index));

            if (character == null) {
                inventory[slot] = ItemStack.empty();
                continue;
            }

            final BiFunction<Integer, Context, RenderResult> renderer = this.renders.get(character);

            if (renderer == null) {
                inventory[slot] = ItemStack.empty();
                continue;
            }

            inventory[slot] = switch (renderer.apply(index, context)) {
                case RenderResult.ItemResult itemResult -> itemResult.itemStack();
            };
        }

        fakePlayerInventory.setContents(inventory);
    }

    public void handleClickEvent(final int slot, final @NotNull Context context, final boolean topInventory) {
        final int realSlot = topInventory ? slot : (slot + 27) % 36;
        final Character character = this.characters.get(new Slot(true, realSlot));

        if (character == null) {
            return;
        }

        final BiFunction<Integer, Context, Void> click = this.clicks.get(character);

        if (click == null) {
            return;
        }

        click.apply(slot, context);
    }

}
