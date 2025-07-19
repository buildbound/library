package com.buildbound.library.context.player;

import com.buildbound.library.Plugin;
import com.buildbound.library.context.Context;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public record PlayerContextAccessor(@NotNull Class<? extends Plugin> pluginClazz, @NotNull Key key) {

    @NotNull
    public Context access(final @NotNull Player player) {
        final String key = this.key.asString();
        final List<MetadataValue> metadataValues = player.getMetadata(key);

        if (metadataValues.isEmpty()) {
            final Context context = new Context();
            player.setMetadata(key, new FixedMetadataValue(JavaPlugin.getPlugin(this.pluginClazz), context));

            return context;
        }

        final MetadataValue metadataValue = metadataValues.getFirst();
        return (Context) Objects.requireNonNull(metadataValue.value());
    }

}
