package com.buildbound.library;

import com.buildbound.library.action.holder.ActionHolder;
import com.buildbound.library.action.registry.ActionRegistry;
import com.buildbound.library.configuration.ConfigSection;
import com.buildbound.library.configuration.impl.DelegateConfigSection;
import com.buildbound.library.menu.listeners.ContainerRefreshProtocolListener;
import com.buildbound.library.menu.listeners.ContainerSetSlotProtocolListener;
import com.buildbound.library.menu.listeners.MenuClickListener;
import com.buildbound.library.menu.listeners.MenuCloseListener;
import com.buildbound.library.message.holder.MessageHolder;
import com.buildbound.library.scheduler.Scheduler;
import com.buildbound.library.utils.IOUtils;
import com.buildbound.library.wire.AutoWire;
import com.buildbound.library.wire.holder.impl.ConfigHolder;
import com.buildbound.library.wire.repository.AutoWireRepository;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import io.github.classgraph.*;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.PaperCommandManager;
import org.incendo.cloud.paper.util.sender.PaperSimpleSenderMapper;
import org.incendo.cloud.paper.util.sender.Source;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class Plugin extends JavaPlugin {

    private AnnotationParser<Source> annotationParser;

    protected final AutoWireRepository autoWireRepository = new AutoWireRepository();
    protected final ActionRegistry actionRegistry = new ActionRegistry();

    public Plugin() {
        this.autoWireRepository.wireHandler(
                ConfigHolder.class,
                holder -> holder.load(this)
        );

        this.autoWireRepository.wireHandler(
                MessageHolder.class,
                holder -> holder.setup(this.getConfig("messages.yml"))
        );

        this.autoWireRepository.wireHandler(
                ActionHolder.class,
                holder -> holder.setup(this.getConfig("actions.yml"), this.actionRegistry)
        );

        Scheduler.SYNC.start(this);
        Scheduler.ASYNC.start(this);
    }

    @Override
    public void onEnable() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().load();
        PacketEvents.getAPI().getEventManager().registerListener(new ContainerRefreshProtocolListener(), PacketListenerPriority.NORMAL);
        PacketEvents.getAPI().getEventManager().registerListener(new ContainerSetSlotProtocolListener(), PacketListenerPriority.NORMAL);

        final PaperCommandManager<Source> commandManager = PaperCommandManager.builder(PaperSimpleSenderMapper.simpleSenderMapper())
                .executionCoordinator(ExecutionCoordinator.simpleCoordinator())
                .buildOnEnable(this);

        this.annotationParser = new AnnotationParser<>(commandManager, Source.class);

        // Auto Wire
        this.autoWire();

        // Register Menu Listeners
        this.registerListener(new MenuClickListener());
        this.registerListener(new MenuCloseListener());

        super.onEnable();
    }

    public void registerCommand(final @NotNull Object command) {
        this.annotationParser.parse(command);
    }

    public void registerListener(final @NotNull Listener listener) {
        Bukkit.getPluginManager().registerEvents(
                listener,
                this
        );
    }

    @NotNull
    public ConfigSection getConfig(final @NotNull String path) {
        final File file = new File(
                this.getDataFolder(),
                path
        );

        if (IOUtils.ensureCreated(file)) {
            this.saveResource(path, true);
        }

        return new DelegateConfigSection(YamlConfiguration.loadConfiguration(file), this);
    }

    private void autoWire() {
        try (final ScanResult scanResult = new ClassGraph()
                .enableClassInfo()
                .enableAnnotationInfo()
                .scan()) {
            for (final ClassInfo routeClassInfo : scanResult.getClassesWithAnnotation(AutoWire.class)) {
                final Class<?> clazz = routeClassInfo.loadClass();
                this.autoWireRepository.wire(clazz);
            }
        }
    }

    @NotNull
    public ActionRegistry getActionRegistry() {
        return this.actionRegistry;
    }

}
