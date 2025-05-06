package com.buildbound.library.wire.consumer;

import org.jetbrains.annotations.NotNull;

public interface WireConsumer<T> {

    void wire(final @NotNull T instance);

    @SuppressWarnings("unchecked")
    default void wireObject(final @NotNull Object object) {
        this.wire((T) object);
    }

}
