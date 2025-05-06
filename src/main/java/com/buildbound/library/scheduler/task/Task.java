package com.buildbound.library.scheduler.task;

import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public record Task(BukkitTask bukkitTask, boolean async) {

    @NotNull
    public static Task bukkit(final @NotNull BukkitTask bukkitTask) {
        return new Task(
                bukkitTask,
                !bukkitTask.isSync()
        );
    }

    public boolean isActive() {
        return !this.bukkitTask.isCancelled();
    }

    public void cancel() {
        this.bukkitTask.cancel();
    }

}
