package com.buildbound.library.utils;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public interface IOUtils {

    @NotNull
    Logger LOGGER = LoggerFactory.getLogger("buildbound/IO");

    static boolean ensureCreated(final @NotNull File file) {
        try {
            file.getParentFile().mkdirs();
            return file.createNewFile();
        } catch (final IOException exception) {
            LOGGER.error("Failed to create file: {}", file.getAbsolutePath(), exception);
            return false;
        }
    }

}
