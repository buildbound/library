package com.buildbound.library.scheduler.impl;

import com.buildbound.library.scheduler.Scheduler;
import com.buildbound.library.scheduler.task.Task;
import com.buildbound.library.time.Duration;
import com.buildbound.library.time.unit.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

public class SyncScheduler implements Scheduler {

    private JavaPlugin javaPlugin;
    private final @NotNull BukkitScheduler bukkitScheduler;

    public SyncScheduler() {
        this.bukkitScheduler = Bukkit.getScheduler();
    }

    @Override
    public @NotNull Task runTask(final @NotNull Runnable runnable) {
        return Task.bukkit(this.bukkitScheduler.runTask(this.javaPlugin, runnable));
    }

    @Override
    public @NotNull Task runTaskLater(final @NotNull Runnable runnable, final @NotNull Duration duration) {
        return Task.bukkit(this.bukkitScheduler.runTaskLater(
                this.javaPlugin,
                runnable,
                duration.to(TimeUnit.TICKS)
        ));
    }

    @Override
    public @NotNull Task runTaskTimer(final @NotNull Runnable runnable, final @NotNull Duration duration) {
        return Task.bukkit(this.bukkitScheduler.runTaskTimer(
                this.javaPlugin,
                runnable,
                duration.to(TimeUnit.TICKS),
                duration.to(TimeUnit.TICKS)
        ));
    }

    @Override
    public @NotNull Task runEntityTask(final @NotNull Entity entity, final @NotNull Runnable runnable) {
        return this.runTask(runnable);
    }

    @Override
    public @NotNull Task runEntityTaskLater(final @NotNull Entity entity, final @NotNull Runnable runnable, final @NotNull Duration duration) {
        return this.runTaskLater(runnable, duration);
    }

    @Override
    public @NotNull Task runEntityTaskTimer(final @NotNull Entity entity, final @NotNull Runnable runnable, final @NotNull Duration duration) {
        return this.runTaskTimer(runnable, duration);
    }

    @Override
    public @NotNull Task runLocalTask(final @NotNull Location location, final @NotNull Runnable runnable) {
        return this.runTask(runnable);
    }

    @Override
    public @NotNull Task runLocalTaskLater(final @NotNull Location location, final @NotNull Runnable runnable, final @NotNull Duration duration) {
        return this.runTaskLater(runnable, duration);
    }

    @Override
    public @NotNull Task runLocalTaskTimer(final @NotNull Location location, final @NotNull Runnable runnable, final @NotNull Duration duration) {
        return this.runTaskTimer(runnable, duration);
    }

    @Override
    public @NotNull Task runGlobalTask(final @NotNull Runnable runnable) {
        return this.runTask(runnable);
    }

    @Override
    public @NotNull Task runGlobalTaskLater(final @NotNull Runnable runnable, final @NotNull Duration duration) {
        return this.runTaskLater(runnable, duration);
    }

    @Override
    public @NotNull Task runGlobalTaskTimer(final @NotNull Runnable runnable, final @NotNull Duration duration) {
        return this.runTaskTimer(runnable, duration);
    }

    @Override
    public void start(final @NotNull JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

}
