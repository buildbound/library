package com.buildbound.library.testPlugin.context;

import com.buildbound.library.context.key.ContextKey;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

public interface TestContext {

    @NotNull
    ContextKey<String> CONTEXT = new ContextKey<>(Key.key("test", "context"));

}
