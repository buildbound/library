package com.buildbound.library.item.impl;

import com.buildbound.library.configuration.ConfigSection;
import com.buildbound.library.item.Item;
import com.buildbound.library.placeholder.Placeholder;
import com.buildbound.library.utils.ComponentUtils;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public class BukkitItem implements Item {

    private final String material;
    private final String amount;

    private final String name;
    private final ImmutableList<String> lore;

    private final String skull;

    public BukkitItem(final @NotNull ConfigSection configSection) {
        this.material = configSection.getString("material", "diamond");
        this.amount = configSection.getString("amount", "1");

        this.name = configSection.getString("name");
        this.lore = Lists.immutable.ofAll(configSection.getStringList("lore"));

        this.skull = configSection.getString("skull");
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

    @Override
    public void updateItemStack(final @NotNull ItemStack itemStack, final @NotNull Placeholder placeholder) {
        final TagResolver[] tagResolvers = placeholder.asTagResolver();
        this.setItemMeta(itemStack, tagResolvers);
    }

    private void setItemMeta(final @NotNull ItemStack itemStack, final @NotNull TagResolver[] tagResolvers) {
        final ItemMeta itemMeta = itemStack.getItemMeta();

        if (this.name != null) {
            itemMeta.itemName(ComponentUtils.toComponent(this.name, tagResolvers));
        }

        if (!this.lore.isEmpty()) {
            final Component loreComponent = Component.join(
                    JoinConfiguration.newlines(),
                    ComponentUtils.toComponentList(this.lore, tagResolvers)
            ).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);
            itemMeta.lore(ComponentUtils.split(loreComponent, "\n"));
        }

        itemStack.editMeta(SkullMeta.class, meta -> {
            if (this.skull == null) {
                return;
            }

            final PlayerProfile profile = Bukkit.createProfile(UUID.fromString("e309b40a-8260-4acc-b7a3-70f4bc80af12"));
            profile.setProperty(new ProfileProperty("textures", ComponentUtils.toPlainText(this.skull, tagResolvers)));
            meta.setPlayerProfile(profile);
        });

        itemStack.setItemMeta(itemMeta);
    }

}
