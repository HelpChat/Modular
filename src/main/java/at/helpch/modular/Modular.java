package at.helpch.modular;

import me.piggypiglet.framework.Framework;
import me.piggypiglet.framework.utils.annotations.files.Config;
import org.bukkit.plugin.java.JavaPlugin;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class Modular extends JavaPlugin {
    @Override
    public void onEnable() {
        Framework.builder()
                .main(JavaPlugin.class, this)
                .pckg("at.helpch.modular")
                .commandPrefix("modular")
                .file(true, "config", "/config.yml", getDataFolder() + "/config.yml", Config.class)
                .build()
                .init();
    }
}
