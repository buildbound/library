package com.buildbound.library.item.impl;

import com.buildbound.library.configuration.ConfigSection;
import com.buildbound.library.item.Item;
import com.buildbound.library.placeholder.Placeholder;
import com.buildbound.library.utils.ComponentUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BukkitItem implements Item {

    private final String material;
    private final String amount;

    private final String name;
    private final ImmutableList<String> lore;

    public BukkitItem(final @NotNull ConfigSection configSection) {
        this.material = configSection.getString("material", "diamond");
        this.amount = configSection.getString("amount", "1");

        this.name = configSection.getString("name");
        this.lore = Lists.immutable.ofAll(configSection.getStringList("lore"));
    }

    @Override
    public @NotNull ItemStack toItemStack(final @NotNull Placeholder placeholder) {
        final TagResolver[] tagResolvers = placeholder.asTagResolver();

        final Material material = Objects.requireNonNullElse(
                Material.matchMaterial(ComponentUtils.toPlainText(this.material, tagResolvers)),
                Material.DIAMOND
        );
        final ItemStack itemStack = new ItemStack(material);
        itemStack.setAmount(Integer.parseInt(ComponentUtils.toPlainText(this.amount, tagResolvers)));

        this.setItemMeta(itemStack, tagResolvers);
        return itemStack;
    }

    private void setItemMeta(final @NotNull ItemStack itemStack, final @NotNull TagResolver[] tagResolvers) {
        final ItemMeta itemMeta = itemStack.getItemMeta();

        if (this.name != null) {
            itemMeta.itemName(ComponentUtils.toComponent(this.name));
        }

        if (!this.lore.isEmpty()) {
            final Component loreComponent = Component.join(
                    JoinConfiguration.newlines(),
                    ComponentUtils.toComponentList(this.lore, tagResolvers)
            ).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);
            itemMeta.lore(ComponentUtils.split(loreComponent, "\n"));
        }

        itemStack.setItemMeta(itemMeta);
    }

}
