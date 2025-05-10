package com.buildbound.library.menu.listeners;

import com.buildbound.library.menu.holder.BukkitMenuHolder;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ContainerSetSlotProtocolListener implements PacketListener {

    @Override
    public void onPacketSend(final PacketSendEvent event) {
        if (event.getPacketType() != PacketType.Play.Server.SET_SLOT) {
            return;
        }

        final Player player = Bukkit.getPlayer(event.getUser().getUUID());

        if (player == null) {
            return;
        }

        final Inventory openInventory = player.getOpenInventory().getTopInventory();

        if (!(openInventory.getHolder(false) instanceof BukkitMenuHolder)) {
            return;
        }

        event.setCancelled(true);
    }
}
