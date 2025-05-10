package com.buildbound.library.menu.render;

import org.bukkit.inventory.ItemStack;

public sealed interface RenderResult permits RenderResult.ItemResult {

    record ItemResult(ItemStack itemStack) implements RenderResult {}

}
