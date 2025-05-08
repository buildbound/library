package com.buildbound.library.menu.button;

import com.buildbound.library.context.Context;
import org.jetbrains.annotations.NotNull;

public interface MenuButton {

    void render(final int slot, final @NotNull Context context);

    void onClick(final int slot, final @NotNull Context context);

}
