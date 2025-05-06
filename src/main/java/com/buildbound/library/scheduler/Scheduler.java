package com.buildbound.library.scheduler;

import com.buildbound.library.scheduler.impl.AsyncScheduler;
import com.buildbound.library.scheduler.impl.SyncScheduler;
import com.buildbound.library.scheduler.task.Task;
import com.buildbound.library.time.Duration;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public interface Scheduler {

    /**
     * This will always run {@link Task}'s synchronously.
     */
    Scheduler SYNC = new SyncScheduler();

    /**
     * This will always run {@link Task}'s asynchronously.
     */
    Scheduler ASYNC = new AsyncScheduler();

    /*
    Global Schedulers
     */

    @NotNull
    Task runTask(final @NotNull Runnable runnable);

    @NotNull
    Task runTaskLater(final @NotNull Runnable runnable,
                      final @NotNull Duration duration);

    @NotNull
    Task runTaskTimer(final @NotNull Runnable runnable,
                      final @NotNull Duration duration);

    /*
    Entity Schedulers
     */

    @NotNull
    Task runEntityTask(final @NotNull Entity entity,
                       final @NotNull Runnable runnable);

    @NotNull
    Task runEntityTaskLater(final @NotNull Entity entity,
                            final @NotNull Runnable runnable,
                            final @NotNull Duration duration);

    @NotNull
    Task runEntityTaskTimer(final @NotNull Entity entity,
                            final @NotNull Runnable runnable,
                            final @NotNull Duration duration);

    /*
    Region Tasks
     */

    @NotNull
    Task runLocalTask(final @NotNull Location location,
                      final @NotNull Runnable runnable);

    @NotNull
    Task runLocalTaskLater(final @NotNull Location location,
                           final @NotNull Runnable runnable,
                           final @NotNull Duration duration);

    @NotNull
    Task runLocalTaskTimer(final @NotNull Location location,
                           final @NotNull Runnable runnable,
                           final @NotNull Duration duration);

    /*
    Global Region Tasks
     */

    @NotNull
    Task runGlobalTask(final @NotNull Runnable runnable);

    @NotNull
    Task runGlobalTaskLater(final @NotNull Runnable runnable,
                            final @NotNull Duration duration);

    @NotNull
    Task runGlobalTaskTimer(final @NotNull Runnable runnable,
                            final @NotNull Duration duration);

    void start(final @NotNull JavaPlugin javaPlugin);

}
