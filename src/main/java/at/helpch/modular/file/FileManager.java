package at.helpch.modular.file;

import at.helpch.modular.Modular;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Data;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
@Singleton
public final class FileManager {
    @Inject private Modular modular;

    private final Map<String, Config> configs = new HashMap<>();

    public FileConfiguration load(String name, String internalPath, String externalPath) throws Exception {
        modular.getLogger().info("Loading " + name + ".");

        File file = new File(externalPath);

        if (!file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.getParentFile().mkdirs();

            if (file.createNewFile() && exportResource(Modular.class.getResourceAsStream(internalPath), externalPath)) {
                return load(name, file);
            }
        }

        return load(name, file);
    }

    public FileConfiguration getConfig(String name) {
        return configs.get(name).getConfig();
    }

    public void save(String name) {
        Config config = configs.get(name);

        try {
            config.getConfig().save(config.getFile());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private FileConfiguration load(String name, File file) throws Exception {
        FileConfiguration config = new YamlConfiguration();
        config.load(file);
        configs.put(name, new Config(config, file));
        return config;
    }

    private boolean exportResource(InputStream in, String destination) {
        boolean success = true;

        try {
            Files.copy(in, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        }

        return success;
    }

    @Data
    private final class Config {
        private final FileConfiguration config;
        private final File file;
    }
}