package com.buildbound.library.utils;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public interface RandomUtils {

    @NotNull
    Logger LOGGER = LoggerFactory.getLogger("buildbound/Random");

    @NotNull
    static Random createRandom(final @NotNull String name) {
        final int hashCode = name.hashCode();
        final long seed = System.currentTimeMillis() + hashCode;

        LOGGER.info("Created seeded random for {} with seed {}", name, seed);
        return new Random(seed);
    }

    static boolean passes(final @NotNull Random random, final double chance) {
        return random.nextDouble() <= chance / 100;
    }

}
