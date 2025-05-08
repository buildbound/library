package com.buildbound.library.testPlugin.commands;

import com.buildbound.library.placeholder.Placeholder;
import com.buildbound.library.testPlugin.wire.AutoWireConstants;
import org.incendo.cloud.annotation.specifier.Greedy;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;
import org.incendo.cloud.paper.util.sender.PlayerSource;
import org.jetbrains.annotations.NotNull;

public class TestCommand {

    @Command("shout <text>")
    @CommandDescription("Broadcast a message to all players.")
    @Permission("buildbound.testplugin.shout")
    public void testCommand(final @NotNull PlayerSource commandSender,
                            final @NotNull @Argument("text") @Greedy String text) {
        AutoWireConstants.TEST_MESSAGE.send(commandSender.source(), Placeholder.placeholder()
                .with("player_name", commandSender.source().name())
        );
        AutoWireConstants.TEST_MESSAGE.broadcast(Placeholder.placeholder()
                .with("player_name", commandSender.source().name())
        );
        AutoWireConstants.ACTION.execute(commandSender.source(), Placeholder.placeholder()
                .with("player", commandSender.source().name())
        );
        commandSender.source().getInventory().addItem(AutoWireConstants.ITEM.toItemStack(Placeholder.placeholder()));
    }

}
