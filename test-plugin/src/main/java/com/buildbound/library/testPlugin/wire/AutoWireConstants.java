package com.buildbound.library.testPlugin.wire;

import com.buildbound.library.action.Action;
import com.buildbound.library.item.Item;
import com.buildbound.library.item.temporary.TemporaryItem;
import com.buildbound.library.message.Message;
import com.buildbound.library.wire.AutoWire;
import com.buildbound.library.wire.holder.Holder;
import org.jetbrains.annotations.NotNull;

@AutoWire
public interface AutoWireConstants {

    @NotNull Message TEST_MESSAGE = Message.holder("test-message");
    @NotNull Action ACTION = Action.holder("test-command-actions");
    @NotNull Item ITEM = Item.holder("config.yml", section -> section.getItem("test-item"));
    @NotNull Holder<TemporaryItem> TEMPORARY_ITEM = Holder.config("config.yml", section -> section.getTemporaryItem("test-temporary-item"));

}
