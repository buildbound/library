package com.buildbound.library.time.unit;

import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.MutableMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public enum TimeUnit {

    DAYS("d", 86400000,"d", "day", "days"),
    HOURS("h", 3600000, "h", "hour", "hours"),
    MINUTES("m", 60000, "m", "minute", "minutes"),
    SECONDS("s", 1000, "s", "second", "seconds"),
    TICKS("t", 1000 / 20, "t", "tick", "ticks"),
    MILLIS("ms", 1, "ms", "millis", "millisecond", "milliseconds");

    private final String unit;

    private final long modifier;
    private final String[] identifiers;

    private static final MutableList<String> UNITS = Lists.mutable.empty();
    private static final MutableMap<String, TimeUnit> TIME_UNITS = Maps.mutable.empty();

    TimeUnit(final @NotNull String unit,
             final long modifier,
             final @NotNull String... identifiers) {
        this.unit = unit;
        this.modifier = modifier;
        this.identifiers = identifiers;
    }

    @NotNull
    public String getUnit() {
        return this.unit;
    }

    @NotNull
    public ImmutableList<String> getIdentifiers() {
        return Lists.immutable.of(this.identifiers);
    }

    public long convert(final long duration,
                        final @NotNull TimeUnit timeUnit) {
        return duration * this.modifier / timeUnit.modifier;
    }

    @Nullable
    public static TimeUnit fromUnit(final @NotNull String unit) {
        return TIME_UNITS.computeIfAbsent(unit.strip().toLowerCase(Locale.ROOT), (u) -> {
            for (final TimeUnit timeUnit : TimeUnit.values()) {
                if (timeUnit.getIdentifiers().contains(u)) {
                    return timeUnit;
                }
            }

            return null;
        });
    }

    @NotNull
    public static String[] units() {
        if (!UNITS.isEmpty()) {
            return UNITS.toArray(new String[0]);
        }

        for (final TimeUnit timeUnit : TimeUnit.values()) {
            UNITS.add(timeUnit.getUnit());
        }

        return UNITS.toArray(new String[0]);
    }

}
