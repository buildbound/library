package com.buildbound.library.utils;

import com.buildbound.library.placeholder.Placeholder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.jetbrains.annotations.NotNull;

public interface ComponentUtils {

    @NotNull
    static String toPlainText(final @NotNull Component component) {
        return PlainTextComponentSerializer.plainText().serialize(component);
    }

    @NotNull
    static String toPlainText(final @NotNull String text, final @NotNull Placeholder placeholder) {
        return PlainTextComponentSerializer.plainText().serialize(
                MiniMessage.miniMessage().deserialize(text, placeholder.asTagResolver())
        );
    }

    @NotNull
    static String toPlainText(final @NotNull String text, final @NotNull TagResolver[] tagResolvers) {
        return PlainTextComponentSerializer.plainText().serialize(
                MiniMessage.miniMessage().deserialize(text, tagResolvers)
        );
    }

    @NotNull
    static Component toComponent(final @NotNull String text) {
        return MiniMessage.miniMessage().deserialize(text);
    }

    @NotNull
    static Component toComponent(final @NotNull String text, final @NotNull Placeholder placeholder) {
        return MiniMessage.miniMessage().deserialize(text, placeholder.asTagResolver());
    }

    @NotNull
    static Component toComponent(final @NotNull String text, final @NotNull TagResolver[] tagResolvers) {
        return MiniMessage.miniMessage().deserialize(text, tagResolvers);
    }

}
