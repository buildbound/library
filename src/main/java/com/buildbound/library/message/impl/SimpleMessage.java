package com.buildbound.library.message.impl;

import com.buildbound.library.message.Message;
import com.buildbound.library.placeholder.Placeholder;
import com.buildbound.library.scheduler.Scheduler;
import com.buildbound.library.utils.ComponentUtils;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Locale;

public class SimpleMessage implements Message {

    private final String actionBar;

    private final String bossBar;
    private final String progress;
    private final String color;
    private final String overlay;
    private final String duration;

    private final String title;
    private final String subtitle;
    private final Title.Times times;

    private final ImmutableList<String> content;

    public SimpleMessage(final @NotNull ConfigurationSection configurationSection) {
        this.actionBar = configurationSection.getString("action-bar");

        this.bossBar = configurationSection.getString("boss-bar.text");
        this.progress = configurationSection.getString("boss-bar.progress", "1.0");
        this.color = configurationSection.getString("boss-bar.color", "pink");
        this.overlay = configurationSection.getString("boss-bar.overlay", "progress");
        this.duration = configurationSection.getString("boss-bar.duration", "1s");

        this.title = configurationSection.getString("title.title");
        this.subtitle = configurationSection.getString("title.subtitle");
        this.times = Title.Times.times(
                Duration.ofMillis(configurationSection.getLong("title.fade-in", 1000L)),
                Duration.ofMillis(configurationSection.getLong("title.stay", 3000L)),
                Duration.ofMillis(configurationSection.getLong("title.fade-out", 1000L))
        );

        this.content = Lists.immutable.ofAll(configurationSection.getStringList("content"));
    }

    @Override
    public void send(final @NotNull Audience audience, final @NotNull Placeholder placeholder) {
        final TagResolver[] tagResolvers = placeholder.asTagResolver();
        this.sendActionBar(audience, tagResolvers);
        this.sendContent(audience, tagResolvers);
        this.sendTitle(audience, tagResolvers);
        this.sendBossBar(audience, tagResolvers);
    }

    @Override
    public void broadcast(final @NotNull Placeholder placeholder) {
        this.send(Audience.audience(Bukkit.getOnlinePlayers()), placeholder);
    }

    private void sendActionBar(final @NotNull Audience audience, final @NotNull TagResolver[] tagResolvers) {
        if (this.actionBar == null) {
            return;
        }

        audience.sendActionBar(ComponentUtils.toComponent(this.actionBar, tagResolvers));
    }

    private void sendContent(final @NotNull Audience audience, final @NotNull TagResolver[] tagResolvers) {
        final Component component = Component.join(JoinConfiguration.newlines(), this.content
                .collect(text -> ComponentUtils.toComponent(text, tagResolvers))
        );
        audience.sendMessage(component);
    }

    private void sendTitle(final @NotNull Audience audience,
                           final @NotNull TagResolver[] tagResolvers) {
        if (this.title != null) {
            audience.sendTitlePart(TitlePart.TITLE, ComponentUtils.toComponent(this.title, tagResolvers));
        }

        if (this.subtitle != null) {
            audience.sendTitlePart(TitlePart.SUBTITLE, ComponentUtils.toComponent(this.subtitle, tagResolvers));
        }

        audience.sendTitlePart(TitlePart.TIMES, this.times);
    }

    private void sendBossBar(final @NotNull Audience audience,
                             final @NotNull TagResolver[] tagResolvers) {
        if (this.bossBar == null) {
            return;
        }

        final BossBar bossBar = BossBar.bossBar(
                ComponentUtils.toComponent(this.bossBar, tagResolvers),
                Float.parseFloat(ComponentUtils.toPlainText(this.progress, tagResolvers)),
                BossBar.Color.NAMES.valueOr(
                        ComponentUtils.toPlainText(this.color, tagResolvers).toUpperCase(Locale.ROOT),
                        BossBar.Color.BLUE
                ),
                BossBar.Overlay.NAMES.valueOr(
                        ComponentUtils.toPlainText(this.overlay, tagResolvers).toUpperCase(Locale.ROOT),
                        BossBar.Overlay.PROGRESS
                )
        );
        audience.showBossBar(bossBar);

        Scheduler.ASYNC.runGlobalTaskLater(
                () -> audience.hideBossBar(bossBar),
                com.buildbound.library.time.Duration.parse(ComponentUtils.toPlainText(this.duration, tagResolvers))
        );
    }

}
