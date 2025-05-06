package com.buildbound.library.wire.repository;

import com.buildbound.library.wire.AutoWire;
import com.buildbound.library.wire.consumer.WireConsumer;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class AutoWireRepository {

    private final MutableMap<Class<?>, WireConsumer<?>> consumers = Maps.mutable.empty();
    private static final Logger LOGGER = LoggerFactory.getLogger("buildbound/AutoWire");

    public <T> void wireHandler(final @NotNull Class<T> clazz, final @NotNull WireConsumer<T> consumer) {
        if (this.consumers.containsKey(clazz)) {
            throw new IllegalStateException("Handler already registered for class: " + clazz.getName());
        }

        this.consumers.put(clazz, consumer);
    }

    public void wire(final @NotNull Class<?> clazz) {
        if (!clazz.isAnnotationPresent(AutoWire.class)) {
            return;
        }

        for (final Field field : clazz.getDeclaredFields()) {
            try {
                this.wireField(field);
                LOGGER.debug("Automatically wired field: {}", field.getName());
            } catch (final IllegalAccessException exception) {
                LOGGER.error("Failed to wire field: {}", field.getName(), exception);
            }
        }
    }

    private void wireField(final @NotNull Field field) throws IllegalAccessException {
        final Object instance = field.get(null);

        for (final Pair<Class<?>, WireConsumer<?>> wiring : this.consumers.keyValuesView()) {
            final Class<?> clazz = wiring.getOne();

            if (!clazz.isInstance(instance)) {
                continue;
            }

            final WireConsumer<?> consumer = wiring.getTwo();
            consumer.wireObject(instance);
        }
    }

}
