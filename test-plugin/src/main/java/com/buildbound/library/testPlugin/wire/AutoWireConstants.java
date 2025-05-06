package com.buildbound.library.testPlugin.wire;

import com.buildbound.library.action.Action;
import com.buildbound.library.message.Message;
import com.buildbound.library.wire.AutoWire;

@AutoWire
public class AutoWireConstants {

    public static final Message TEST_MESSAGE = Message.holder("test-message");
    public static final Action ACTION = Action.holder("test-command-actions");

}
