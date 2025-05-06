package com.buildbound.library.testPlugin;

import com.buildbound.library.Plugin;
import com.buildbound.library.testPlugin.commands.TestCommand;

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

        this.registerCommand(new TestCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
