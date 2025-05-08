package com.buildbound.library.testPlugin;

import com.buildbound.library.Plugin;
import com.buildbound.library.menu.Menu;
import com.buildbound.library.menu.impl.BukkitMenu;
import com.buildbound.library.testPlugin.buttons.TestButton;
import com.buildbound.library.testPlugin.commands.TestCommand;

import java.util.List;

public final class TestPlugin extends Plugin {

    @Override
    public void onLoad() {
        super.autoWireRepository.wireHandler(
                String.class,
                instance -> System.out.println("Wire: " + instance)
        );
    }

    @Override
    public void onEnable() {
        super.onEnable();

        // Load Menu
        final Menu menu = new BukkitMenu(this.getConfig("example-menu.yml"))
                .withButton(new TestButton(), List.of(1));

        // Register Command
        this.registerCommand(new TestCommand(menu));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
