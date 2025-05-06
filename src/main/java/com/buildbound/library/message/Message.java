package com.buildbound.library.message;

import com.buildbound.library.message.holder.MessageHolder;
import com.buildbound.library.message.impl.SimpleMessage;
import com.buildbound.library.placeholder.Placeholder;
import net.kyori.adventure.audience.Audience;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public interface Message {

    @NotNull
    static Message holder(final @NotNull String path) {
        return new MessageHolder(path);
    }

    @NotNull
    static Message message(final @NotNull ConfigurationSection section,
                           final @NotNull String path) {
        return new SimpleMessage(Objects.requireNonNull(section.getConfigurationSection(path)));
    }

    @NotNull
    static Message message(final @NotNull ConfigurationSection section) {
        return new SimpleMessage(section);
    }

    void send(final @NotNull Audience audience, final @NotNull Placeholder placeholder);

    void broadcast(final @NotNull Placeholder placeholder);

}
