package com.buildbound.library.time;

import com.buildbound.library.time.unit.TimeUnit;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public record DurationRange(@NotNull Duration min, @NotNull Duration max) {

    public DurationRange {
        if (min.to(TimeUnit.MILLIS) > max.to(TimeUnit.MILLIS)) {
            throw new IllegalArgumentException("Min duration cannot be greater than max duration");
        }
    }

    @NotNull
    public Duration toDuration(final @NotNull Random random) {
        final long minMillis = this.min.to(TimeUnit.MILLIS);
        final long maxMillis = this.max.to(TimeUnit.MILLIS);

        return Duration.millis(random.nextLong(minMillis, maxMillis));
    }

}
