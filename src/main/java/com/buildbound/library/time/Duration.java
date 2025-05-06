package com.buildbound.library.time;

import com.buildbound.library.time.unit.TimeUnit;
import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

public record Duration(long duration, TimeUnit timeUnit) implements Comparable<Duration> {

    @NotNull
    public static Duration millis(final long duration) {
        return new Duration(duration, TimeUnit.MILLIS);
    }

    @NotNull
    public static Duration ticks(final long duration) {
        return new Duration(duration, TimeUnit.TICKS);
    }

    @NotNull
    public static Duration seconds(final long duration) {
        return new Duration(duration, TimeUnit.SECONDS);
    }

    @NotNull
    public static Duration minutes(final long duration) {
        return new Duration(duration, TimeUnit.MINUTES);
    }

    @NotNull
    public static Duration hours(final long duration) {
        return new Duration(duration, TimeUnit.HOURS);
    }

    @NotNull
    public static Duration days(final long duration) {
        return new Duration(duration, TimeUnit.DAYS);
    }

    @NotNull
    public static Duration parse(final @NotNull String text) {
        final String unit = text.replaceAll("[0-9]", "");
        final String numbers = text.replaceAll("[^0-9]", "");

        Preconditions.checkNotNull(unit, "Invalid duration format: %s".formatted(text));
        Preconditions.checkNotNull(numbers, "Invalid duration format: %s".formatted(text));

        final long duration = Long.parseLong(numbers);
        final TimeUnit timeUnit = TimeUnit.fromUnit(unit);

        if (timeUnit == null) {
            throw new IllegalArgumentException("Invalid duration unit: %s".formatted(unit));
        }

        return new Duration(duration, timeUnit);
    }

    @NotNull
    public Duration as(final @NotNull TimeUnit timeUnit) {
        return new Duration(
                this.timeUnit.convert(this.duration, timeUnit),
                timeUnit
        );
    }

    public long to(final @NotNull TimeUnit timeUnit) {
        return this.timeUnit.convert(this.duration, timeUnit);
    }

    @Override
    public int compareTo(@NotNull final Duration o) {
        return Long.compare(this.to(TimeUnit.MILLIS), o.to(TimeUnit.MILLIS));
    }

}
