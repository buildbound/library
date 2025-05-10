package com.buildbound.library.testPlugin.buttons;

import com.buildbound.library.context.Context;
import com.buildbound.library.menu.Menu;
import com.buildbound.library.menu.button.MenuButton;
import com.buildbound.library.menu.render.RenderResult;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TestButton implements MenuButton {

    @Override
    public @NotNull RenderResult render(final int slot, final @NotNull Context context) {
        return new RenderResult.ItemResult(new ItemStack(Material.DIAMOND));
    }

    @Override
    public void onClick(final int slot, final @NotNull Context context) {
        context.get(Menu.INVENTORY).setItem(slot, new ItemStack(Material.NETHERITE_INGOT));
        context.get(Menu.LAST_CLICK).getWhoClicked().sendMessage("You clicked the button!");
    }

}
