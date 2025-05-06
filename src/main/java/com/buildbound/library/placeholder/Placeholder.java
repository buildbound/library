package com.buildbound.library.placeholder;

import com.buildbound.library.placeholder.impl.SimplePlaceholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;

public interface Placeholder {

    @NotNull
    static Placeholder placeholder() {
        return new SimplePlaceholder();
    }

    @NotNull
    Placeholder with(final @NotNull String name, final @NotNull Object value);

    @NotNull TagResolver[] asTagResolver();

}
