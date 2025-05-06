package com.buildbound.library.message.impl;

import com.buildbound.library.message.Message;
import com.buildbound.library.placeholder.Placeholder;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.jetbrains.annotations.NotNull;

public class SimpleMessage implements Message {

    private final ImmutableList<String> content;

    public SimpleMessage(final @NotNull ConfigurationSection configurationSection) {
        this.content = Lists.immutable.ofAll(configurationSection.getStringList("content"));
    }

    @Override
    public void send(final @NotNull Audience audience, final @NotNull Placeholder placeholder) {
        final TagResolver[] tagResolvers = placeholder.asTagResolver();

        final Component component = Component.join(JoinConfiguration.newlines(), this.content
                .collect(text -> MiniMessage.miniMessage().deserialize(text, tagResolvers)
        ));
        audience.sendMessage(component);
    }

    @Override
    public void broadcast(final @NotNull Placeholder placeholder) {
        this.send(Audience.audience(Bukkit.getOnlinePlayers()), placeholder);
    }
}
