package at.helpch.modular.registerables.implementations;

import at.helpch.modular.Modular;
import at.helpch.modular.guice.annotations.Config;
import at.helpch.modular.registerables.Registerable;
import com.google.inject.Inject;
import org.bukkit.configuration.file.FileConfiguration;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class FilesTest extends Registerable {
    @Inject private Modular modular;
    @Inject @Config private FileConfiguration config;

    @Override
    protected void execute() {
        modular.getLogger().info(config.getString("test", "oof"));
    }
}
