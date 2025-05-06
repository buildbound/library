package com.buildbound.library.action.holder;

import com.buildbound.library.action.Action;
import com.buildbound.library.action.registry.ActionRegistry;
import com.buildbound.library.placeholder.Placeholder;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ActionHolder implements Action {

    private final String path;

    private final MutableList<Action> actions = Lists.mutable.empty();

    public ActionHolder(final @NotNull String path) {
        this.path = path;
    }

    public void setup(final @NotNull ConfigurationSection configurationSection,
                      final @NotNull ActionRegistry actionRegistry) {
        final List<String> lines = configurationSection.getStringList(this.path);

        for (final String line : lines) {
            final Action action = actionRegistry.deserialize(line);
            this.actions.add(action);
        }
    }

    @Override
    public void execute(final @NotNull Player player, final @NotNull Placeholder placeholder) {
        for (final Action action : this.actions) {
            action.execute(player, placeholder);
        }
    }

}
