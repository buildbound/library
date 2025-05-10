package com.buildbound.library.menu.inventory;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSetPlayerInventory;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerWindowItems;
import io.github.retrooper.packetevents.util.SpigotConversionUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.eclipse.collections.api.factory.Lists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class FakePlayerInventory implements Inventory {

    private ItemStack[] contents = new ItemStack[45];

    private final User user;

    public FakePlayerInventory(final @NotNull Player player) {
        this.user = PacketEvents.getAPI().getPlayerManager().getUser(player);
    }

    @Override
    public int getSize() {
        throw new UnsupportedOperationException("Fake Player Inventory does not support this.");
    }

    @Override
    public int getMaxStackSize() {
        throw new UnsupportedOperationException("Fake Player Inventory does not support this.");
    }

    @Override
    public void setMaxStackSize(final int size) {
        throw new UnsupportedOperationException("Fake Player Inventory does not support this.");
    }

    @Override
    public @Nullable ItemStack getItem(final int index) {
        throw new UnsupportedOperationException("Fake Player Inventory does not support this.");
    }

    @Override
    public void setItem(final int index, @Nullable final ItemStack item) {
        final WrapperPlayServerSetPlayerInventory packet = new WrapperPlayServerSetPlayerInventory(
                index,
                SpigotConversionUtil.fromBukkitItemStack(item)
        );
        this.user.sendPacketSilently(packet);

        this.contents[FakePlayerInventory.toRealSlot(index)] = item;
    }

    @Override
    public @NotNull HashMap<Integer, ItemStack> addItem(final @NotNull ItemStack... items) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Fake Player Inventory does not support this.");
    }

    @Override
    public @NotNull HashMap<Integer, ItemStack> removeItem(final @NotNull ItemStack... items) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Fake Player Inventory does not support this.");
    }

    @Override
    public @NotNull HashMap<Integer, ItemStack> removeItemAnySlot(final @NotNull ItemStack... items) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Fake Player Inventory does not support this.");
    }

    @Override
    public @Nullable ItemStack @NotNull [] getContents() {
        throw new UnsupportedOperationException("Fake Player Inventory does not support this.");
    }

    @Override
    public void setContents(final @Nullable ItemStack @NotNull [] items) throws IllegalArgumentException {
        this.contents = items;
        this.refresh();
    }

    @Override
    public @Nullable ItemStack @NotNull [] getStorageContents() {
        throw new UnsupportedOperationException("Fake Player Inventory does not support this.");
    }

    @Override
    public void setStorageContents(final @Nullable ItemStack @NotNull [] items) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Fake Player Inventory does not support this.");
    }

    @Override
    public boolean contains(@NotNull final Material material) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Fake Player Inventory does not support this.");
    }

    @Override
    public boolean contains(@Nullable final ItemStack item) {
        throw new UnsupportedOperationException("Fake Player Inventory does not support this.");
    }

    @Override
    public boolean contains(@NotNull final Material material, final int amount) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Fake Player Inventory does not support this.");
    }

    @Override
    public boolean contains(@Nullable final ItemStack item, final int amount) {
        throw new UnsupportedOperationException("Fake Player Inventory does not support this.");
    }

    @Override
    public boolean containsAtLeast(@Nullable final ItemStack item, final int amount) {
        throw new UnsupportedOperationException("Fake Player Inventory does not support this.");
    }

    @Override
    public @NotNull HashMap<Integer, ? extends ItemStack> all(@NotNull final Material material) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Fake Player Inventory does not support this.");
    }

    @Override
    public @NotNull HashMap<Integer, ? extends ItemStack> all(@Nullable final ItemStack item) {
        throw new UnsupportedOperationException("Fake Player Inventory does not support this.");
    }

    @Override
    public int first(@NotNull final Material material) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Fake Player Inventory does not support this.");
    }

    @Override
    public int first(@NotNull final ItemStack item) {
        throw new UnsupportedOperationException("Fake Player Inventory does not support this.");
    }

    @Override
    public int firstEmpty() {
        throw new UnsupportedOperationException("Fake Player Inventory does not support this.");
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Fake Player Inventory does not support this.");
    }

    @Override
    public void remove(@NotNull final Material material) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Fake Player Inventory does not support this.");
    }

    @Override
    public void remove(@NotNull final ItemStack item) {
        throw new UnsupportedOperationException("Fake Player Inventory does not support this.");
    }

    @Override
    public void clear(final int index) {
        throw new UnsupportedOperationException("Fake Player Inventory does not support this.");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Fake Player Inventory does not support this.");
    }

    @Override
    public int close() {
        throw new UnsupportedOperationException("Fake Player Inventory does not support this.");
    }

    @Override
    public @NotNull List<HumanEntity> getViewers() {
        throw new UnsupportedOperationException("Fake Player Inventory does not support this.");
    }

    @Override
    public @NotNull InventoryType getType() {
        throw new UnsupportedOperationException("Fake Player Inventory does not support this.");
    }

    @Override
    public @Nullable InventoryHolder getHolder() {
        throw new UnsupportedOperationException("Fake Player Inventory does not support this.");
    }

    @Override
    public @Nullable InventoryHolder getHolder(final boolean useSnapshot) {
        throw new UnsupportedOperationException("Fake Player Inventory does not support this.");
    }

    @Override
    public @NotNull ListIterator<ItemStack> iterator() {
        throw new UnsupportedOperationException("Fake Player Inventory does not support this.");
    }

    @Override
    public @NotNull ListIterator<ItemStack> iterator(final int index) {
        throw new UnsupportedOperationException("Fake Player Inventory does not support this.");
    }

    @Override
    public @Nullable Location getLocation() {
        throw new UnsupportedOperationException("Fake Player Inventory does not support this.");
    }

    public void refresh() {
        final WrapperPlayServerWindowItems packet = new WrapperPlayServerWindowItems(
                0,
                -1,
                Lists.mutable.ofAll(Arrays.asList(this.contents)).collect(SpigotConversionUtil::fromBukkitItemStack),
                null
        );
        this.user.sendPacketSilently(packet);
    }

    public void refreshSlot(final int slot) {
        final WrapperPlayServerSetPlayerInventory packet = new WrapperPlayServerSetPlayerInventory(
                slot,
                SpigotConversionUtil.fromBukkitItemStack(this.contents[FakePlayerInventory.toRealSlot(slot)])
        );
        this.user.sendPacketSilently(packet);
    }

    public void consumeInventory(final @NotNull Player player) {
        final ItemStack[] contents = player.getInventory().getContents();
        final ItemStack[] offset = new ItemStack[45];
        Arrays.fill(offset, 0, 8, ItemStack.empty());

        for (int index = 0; index < 36; index++) {
            offset[FakePlayerInventory.toRealSlot(index)] = contents[index];
        }

        final WrapperPlayServerWindowItems packet = new WrapperPlayServerWindowItems(
                0,
                -1,
                Lists.mutable.ofAll(Arrays.asList(offset)).collect(SpigotConversionUtil::fromBukkitItemStack),
                null
        );
        this.user.sendPacketSilently(packet);
    }

    public static int toRealSlot(final int slot) {
        if (slot < 9) {
            return slot + 36;
        }

        return slot;
    }

}
