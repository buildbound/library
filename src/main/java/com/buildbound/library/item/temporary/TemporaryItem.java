package com.buildbound.library.item.temporary;

import com.buildbound.library.configuration.ConfigSection;
import com.buildbound.library.context.Context;
import com.buildbound.library.item.Item;
import com.buildbound.library.menu.Menu;
import com.buildbound.library.message.Message;
import com.buildbound.library.placeholder.Placeholder;
import com.buildbound.library.scheduler.Scheduler;
import com.buildbound.library.time.Duration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class TemporaryItem {

    private final Item item;
    private final Message message;

    public TemporaryItem(final @NotNull ConfigSection configSection) {
        this.item = configSection.getItem("display-item");
        this.message = configSection.getMessage("interact-message");
    }

    public void send(final @NotNull Context context, final @NotNull Placeholder placeholder) {
        final Menu menu = context.get(Menu.MENU);
        final Inventory inventory = context.get(Menu.INVENTORY);
        final InventoryClickEvent inventoryClickEvent = context.get(Menu.LAST_CLICK);

        final HumanEntity humanEntity = inventoryClickEvent.getWhoClicked();
        final int slot = inventoryClickEvent.getSlot();

        inventory.setItem(slot, this.item.toItemStack(Placeholder.placeholder()));
        this.message.send(humanEntity, placeholder);

        Scheduler.ASYNC.runEntityTaskLater(humanEntity, () -> menu.renderSlot(inventory, context, slot), Duration.seconds(3));
    }

}
