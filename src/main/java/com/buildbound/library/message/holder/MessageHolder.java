package com.buildbound.library.message.holder;

import com.buildbound.library.message.Message;
import com.buildbound.library.placeholder.Placeholder;
import net.kyori.adventure.audience.Audience;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

public class MessageHolder implements Message {

    private Message message;
    private final String path;

    public MessageHolder(final @NotNull String path) {
        this.path = path;
    }

    public void setup(final @NotNull ConfigurationSection configurationSection) {
        this.message = Message.message(configurationSection, this.path);
    }

    @Override
    public void send(final @NotNull Audience audience, final @NotNull Placeholder placeholder) {
        this.message.send(audience, placeholder);
    }

    @Override
    public void broadcast(final @NotNull Placeholder placeholder) {
        this.message.broadcast(placeholder);
    }

}
