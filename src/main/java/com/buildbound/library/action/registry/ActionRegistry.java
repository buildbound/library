package com.buildbound.library.action.registry;

import com.buildbound.library.action.Action;
import com.buildbound.library.action.types.ConsoleAction;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.map.MutableMap;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActionRegistry {

    private final MutableMap<String, Function<String, Action>> constructors = Maps.mutable.empty();
    private static final Pattern PATTERN = Pattern.compile("\\[([a-z]+)\\]:\\s?(.+)");

    public ActionRegistry() {
        this.constructors.put("console", ConsoleAction::new);
    }

    @NotNull
    public Action deserialize(final @NotNull String line) {
        final Matcher matcher = PATTERN.matcher(line);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid action format: " + line);
        }

        final String type = matcher.group(1);
        final String value = matcher.group(2);

        final Function<String, Action> constructor = this.constructors.get(type);

        if (constructor == null) {
            throw new IllegalArgumentException("Unknown action type: " + type);
        }

        return constructor.apply(value);
    }

}
