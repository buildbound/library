package com.buildbound.library.menu.button;

import com.buildbound.library.context.Context;
import com.buildbound.library.menu.render.RenderResult;
import org.jetbrains.annotations.NotNull;

public interface MenuButton {

    @NotNull
    RenderResult render(final int slot, final @NotNull Context context);

    void onClick(final int slot, final @NotNull Context context);

}
