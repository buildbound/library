package com.buildbound.library.action.types;

import com.buildbound.library.action.Action;
import com.buildbound.library.placeholder.Placeholder;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public record ConsoleAction(String text) implements Action {

    @Override
    public void execute(final @NotNull Player player, final @NotNull Placeholder placeholder) {
        Bukkit.dispatchCommand(
                Bukkit.getConsoleSender(),
                PlainTextComponentSerializer.plainText().serialize(
                        MiniMessage.miniMessage().deserialize(this.text, placeholder.asTagResolver())
                )
        );
    }

}
