package com.buildbound.library.collections;

import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.TreeMap;

public class RandomCollection<E> extends TreeMap<Double, E>{

    private double total = 0;
    private final Random random;

    public RandomCollection(final @NotNull Random random) {
        this.random = random;
    }

    public void add(final double weight, final @NotNull E result) {
        if (weight <= 0) {
            return;
        }

        this.total += weight;
        this.put(this.total, result);
    }

    @NotNull
    public E getRandomEntry() {
        final double value = this.random.nextDouble() * this.total;
        return this.ceilingEntry(value).getValue();
    }

}
