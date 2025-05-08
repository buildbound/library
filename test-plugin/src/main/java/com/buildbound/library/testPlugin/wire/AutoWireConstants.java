package com.buildbound.library.testPlugin.wire;

import com.buildbound.library.action.Action;
import com.buildbound.library.item.Item;
import com.buildbound.library.message.Message;
import com.buildbound.library.wire.AutoWire;

@AutoWire
public class AutoWireConstants {

    public static final Message TEST_MESSAGE = Message.holder("test-message");
    public static final Action ACTION = Action.holder("test-command-actions");
    public static final Item ITEM = Item.holder("config.yml", section -> section.getItem("test-item"));

}
