package com.buildbound.library.configuration.impl;

import com.buildbound.library.Plugin;
import com.buildbound.library.action.Action;
import com.buildbound.library.action.holder.ActionHolder;
import com.buildbound.library.configuration.ConfigSection;
import com.buildbound.library.configuration.blank.BlankSection;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Set;

public record DelegateConfigSection(ConfigurationSection delegate, Plugin plugin) implements ConfigSection {

    @Override
    public @NotNull Action getAction(final @NotNull String path) {
        final ActionHolder action = new ActionHolder(path);
        action.setup(this, this.plugin.getActionRegistry());

        return action;
    }

    @Override
    public @NotNull ImmutableList<ConfigSection> getSectionList(final @NotNull String path) {
        if (!this.delegate.contains(path)) {
            return Lists.immutable.empty();
        }

        final List<Map<?, ?>> mapList = this.delegate.getMapList(path);
        final List<ConfigSection> output = Lists.mutable.empty();

        for (final Map<?, ?> mapEntry : mapList) {
            final ConfigSection section = new DelegateConfigSection(
                    new BlankSection(),
                    this.plugin
            );

            for (Map.Entry<?, ?> entry : mapEntry.entrySet()) {
                if (entry.getValue() instanceof Map) {
                    section.createSection(entry.getKey().toString(), (Map<?, ?>) entry.getValue());
                    continue;
                }

                section.set(entry.getKey().toString(), entry.getValue());
            }

            output.add(section);
        }

        return Lists.immutable.ofAll(output);
    }

    @Override
    public @NotNull Set<String> getKeys(final boolean deep) {
        return this.delegate.getKeys(deep);
    }

    @Override
    public @NotNull Map<String, Object> getValues(final boolean deep) {
        return this.delegate.getValues(deep);
    }

    @Override
    public boolean contains(@NotNull final String path) {
        return this.delegate.contains(path);
    }

    @Override
    public boolean contains(@NotNull final String path, final boolean ignoreDefault) {
        return this.delegate.contains(path, ignoreDefault);
    }

    @Override
    public boolean isSet(@NotNull final String path) {
        return this.delegate.isSet(path);
    }

    @Override
    public @Nullable String getCurrentPath() {
        return this.delegate.getCurrentPath();
    }

    @Override
    public @NotNull String getName() {
        return this.delegate.getName();
    }

    @Override
    public @Nullable Configuration getRoot() {
        return this.delegate.getRoot();
    }

    @Override
    public @Nullable ConfigurationSection getParent() {
        return this.delegate.getParent();
    }

    @Override
    public @Nullable Object get(@NotNull final String path) {
        return this.delegate.get(path);
    }

    @Override
    public @Nullable Object get(@NotNull final String path, @Nullable final Object def) {
        return this.delegate.get(path, def);
    }

    @Override
    public void set(@NotNull final String path, @Nullable final Object value) {
        this.delegate.set(path, value);
    }

    @Override
    public @NotNull ConfigurationSection createSection(@NotNull final String path) {
        return this.delegate.createSection(path);
    }

    @Override
    public @NotNull ConfigurationSection createSection(@NotNull final String path, @NotNull final Map<?, ?> map) {
        return this.delegate.createSection(path, map);
    }

    @Override
    public @Nullable String getString(@NotNull final String path) {
        return this.delegate.getString(path);
    }

    @Override
    public @Nullable String getString(@NotNull final String path, @Nullable final String def) {
        return this.delegate.getString(path, def);
    }

    @Override
    public boolean isString(@NotNull final String path) {
        return this.delegate.isString(path);
    }

    @Override
    public int getInt(@NotNull final String path) {
        return this.delegate.getInt(path);
    }

    @Override
    public int getInt(@NotNull final String path, final int def) {
        return this.delegate.getInt(path, def);
    }

    @Override
    public boolean isInt(@NotNull final String path) {
        return this.delegate.isInt(path);
    }

    @Override
    public boolean getBoolean(@NotNull final String path) {
        return this.delegate.getBoolean(path);
    }

    @Override
    public boolean getBoolean(@NotNull final String path, final boolean def) {
        return this.delegate.getBoolean(path, def);
    }

    @Override
    public boolean isBoolean(@NotNull final String path) {
        return this.delegate.isBoolean(path);
    }

    @Override
    public double getDouble(@NotNull final String path) {
        return this.delegate.getDouble(path);
    }

    @Override
    public double getDouble(@NotNull final String path, final double def) {
        return this.delegate.getDouble(path, def);
    }

    @Override
    public boolean isDouble(@NotNull final String path) {
        return this.delegate.isDouble(path);
    }

    @Override
    public long getLong(@NotNull final String path) {
        return this.delegate.getLong(path);
    }

    @Override
    public long getLong(@NotNull final String path, final long def) {
        return this.delegate.getLong(path, def);
    }

    @Override
    public boolean isLong(@NotNull final String path) {
        return this.delegate.isLong(path);
    }

    @Override
    public @Nullable List<?> getList(@NotNull final String path) {
        return this.delegate.getList(path);
    }

    @Override
    public @Nullable List<?> getList(@NotNull final String path, @Nullable final List<?> def) {
        return this.delegate.getList(path, def);
    }

    @Override
    public boolean isList(@NotNull final String path) {
        return this.delegate.isList(path);
    }

    @Override
    public @NotNull List<String> getStringList(@NotNull final String path) {
        return this.delegate.getStringList(path);
    }

    @Override
    public @NotNull List<Integer> getIntegerList(@NotNull final String path) {
        return this.delegate.getIntegerList(path);
    }

    @Override
    public @NotNull List<Boolean> getBooleanList(@NotNull final String path) {
        return this.delegate.getBooleanList(path);
    }

    @Override
    public @NotNull List<Double> getDoubleList(@NotNull final String path) {
        return this.delegate.getDoubleList(path);
    }

    @Override
    public @NotNull List<Float> getFloatList(@NotNull final String path) {
        return this.delegate.getFloatList(path);
    }

    @Override
    public @NotNull List<Long> getLongList(@NotNull final String path) {
        return this.delegate.getLongList(path);
    }

    @Override
    public @NotNull List<Byte> getByteList(@NotNull final String path) {
        return this.delegate.getByteList(path);
    }

    @Override
    public @NotNull List<Character> getCharacterList(@NotNull final String path) {
        return this.delegate.getCharacterList(path);
    }

    @Override
    public @NotNull List<Short> getShortList(@NotNull final String path) {
        return this.delegate.getShortList(path);
    }

    @Override
    public @NotNull List<Map<?, ?>> getMapList(@NotNull final String path) {
        return this.delegate.getMapList(path);
    }

    @Override
    public <T> @Nullable T getObject(@NotNull final String path, @NotNull final Class<T> clazz) {
        return this.delegate.getObject(path, clazz);
    }

    @Override
    public <T> @Nullable T getObject(@NotNull final String path, @NotNull final Class<T> clazz, @Nullable final T def) {
        return this.delegate.getObject(path, clazz, def);
    }

    @Override
    public <T extends ConfigurationSerializable> @Nullable T getSerializable(@NotNull final String path, @NotNull final Class<T> clazz) {
        return this.delegate.getSerializable(path, clazz);
    }

    @Override
    public <T extends ConfigurationSerializable> @Nullable T getSerializable(@NotNull final String path, @NotNull final Class<T> clazz, @Nullable final T def) {
        return this.delegate.getSerializable(path, clazz, def);
    }

    @Override
    public @Nullable Vector getVector(@NotNull final String path) {
        return this.delegate.getVector(path);
    }

    @Override
    public @Nullable Vector getVector(@NotNull final String path, @Nullable final Vector def) {
        return this.delegate.getVector(path, def);
    }

    @Override
    public boolean isVector(@NotNull final String path) {
        return this.delegate.isVector(path);
    }

    @Override
    public @Nullable OfflinePlayer getOfflinePlayer(@NotNull final String path) {
        return this.delegate.getOfflinePlayer(path);
    }

    @Override
    public @Nullable OfflinePlayer getOfflinePlayer(@NotNull final String path, @Nullable final OfflinePlayer def) {
        return this.delegate.getOfflinePlayer(path, def);
    }

    @Override
    public boolean isOfflinePlayer(@NotNull final String path) {
        return this.delegate.isOfflinePlayer(path);
    }

    @Override
    public @Nullable ItemStack getItemStack(@NotNull final String path) {
        return this.delegate.getItemStack(path);
    }

    @Override
    public @Nullable ItemStack getItemStack(@NotNull final String path, @Nullable final ItemStack def) {
        return this.delegate.getItemStack(path, def);
    }

    @Override
    public boolean isItemStack(@NotNull final String path) {
        return this.delegate.isItemStack(path);
    }

    @Override
    public @Nullable Color getColor(@NotNull final String path) {
        return this.delegate.getColor(path);
    }

    @Override
    public @Nullable Color getColor(@NotNull final String path, @Nullable final Color def) {
        return this.delegate.getColor(path, def);
    }

    @Override
    public boolean isColor(@NotNull final String path) {
        return this.delegate.isColor(path);
    }

    @Override
    public @Nullable Location getLocation(@NotNull final String path) {
        return this.delegate.getLocation(path);
    }

    @Override
    public @Nullable Location getLocation(@NotNull final String path, @Nullable final Location def) {
        return this.delegate.getLocation(path, def);
    }

    @Override
    public boolean isLocation(@NotNull final String path) {
        return this.delegate.isLocation(path);
    }

    @Override
    public @Nullable ConfigSection getConfigurationSection(@NotNull final String path) {
        final ConfigurationSection section = this.delegate.getConfigurationSection(path);

        if (section == null) {
            return null;
        }

        return new DelegateConfigSection(section, this.plugin);
    }

    @Override
    public boolean isConfigurationSection(@NotNull final String path) {
        return this.delegate.isConfigurationSection(path);
    }

    @Override
    public @Nullable ConfigSection getDefaultSection() {
        final ConfigurationSection section = this.delegate.getDefaultSection();

        if (section == null) {
            return null;
        }

        return new DelegateConfigSection(section, this.plugin);
    }

    @Override
    public void addDefault(@NotNull final String path, @Nullable final Object value) {
        this.delegate.addDefault(path, value);
    }

    @Override
    public @NotNull List<String> getComments(@NotNull final String path) {
        return this.delegate.getComments(path);
    }

    @Override
    public @NotNull List<String> getInlineComments(@NotNull final String path) {
        return this.delegate.getInlineComments(path);
    }

    @Override
    public void setComments(@NotNull final String path, @Nullable final List<String> comments) {
        this.delegate.setComments(path, comments);
    }

    @Override
    public void setInlineComments(@NotNull final String path, @Nullable final List<String> comments) {
        this.delegate.setInlineComments(path, comments);
    }

}
