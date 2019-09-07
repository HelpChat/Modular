package at.helpch.modular.loader;

import com.google.inject.Inject;
import me.piggypiglet.framework.jars.loading.framework.Jar;
import me.piggypiglet.framework.jars.loading.framework.ScannableLoader;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class ModuleLoader extends ScannableLoader<Module> {
    @Inject
    public ModuleLoader(JavaPlugin main) {
        super(main.getDataFolder().getPath() + "/modules", c -> Arrays.asList(c.getInterfaces()).contains(Module.class));
    }

    @Override
    public Jar[] process(File[] files) {
        List<Jar> jars = new ArrayList<>();

        for (File file : files) {
            String[] split = file.getName().split("-");

            jars.add(new Jar() {
                @Override
                public String getName() {
                    return split[0];
                }

                @Override
                public String getPath() {
                    return file.getPath();
                }

                @Override
                public String getVersion() {
                    return split[1];
                }
            });
        }

        return jars.toArray(new Jar[]{});
    }

    @Override
    protected void init(Module module) {
        module.init();
    }
}
