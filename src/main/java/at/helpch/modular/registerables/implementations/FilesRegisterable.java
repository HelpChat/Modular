package at.helpch.modular.registerables.implementations;

import at.helpch.modular.Modular;
import at.helpch.modular.file.FileManager;
import at.helpch.modular.guice.annotations.Config;
import at.helpch.modular.registerables.Registerable;
import com.google.inject.Inject;
import org.bukkit.configuration.file.FileConfiguration;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class FilesRegisterable extends Registerable {
    @Inject private Modular modular;
    @Inject private FileManager fileManager;

    @Override
    protected void execute() {
        try {
            final String dataFolder = modular.getDataFolder().getPath() + "/";

            addAnnotatedBinding(FileConfiguration.class, Config.class, fileManager.load("config", "/config.yml", dataFolder + "config.yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
