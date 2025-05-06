package com.buildbound.library.action;

import com.buildbound.library.action.holder.ActionHolder;
import com.buildbound.library.placeholder.Placeholder;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface Action {

    @NotNull
    static Action holder(final @NotNull String path) {
        return new ActionHolder(path);
    }

    void execute(final @NotNull Player player, final @NotNull Placeholder placeholder);

}
