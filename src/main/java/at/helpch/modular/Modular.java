package at.helpch.modular;

import at.helpch.modular.guice.modules.InitialModule;
import at.helpch.modular.guice.modules.RuntimeBinderModule;
import at.helpch.modular.registerables.Registerable;
import at.helpch.modular.registerables.implementations.FilesRegisterable;
import at.helpch.modular.registerables.implementations.FilesTest;
import com.google.inject.Injector;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class Modular extends JavaPlugin {
    @Override
    public void onEnable() {
        AtomicReference<Injector> injector = new AtomicReference<>(new InitialModule(this).createInjector());

        Stream.of(
                FilesRegisterable.class,
                FilesTest.class
        ).forEach(r -> {
            Registerable registerable = injector.get().getInstance(r);
            registerable.run();

            if (registerable.getBindings().size() > 0 || registerable.getAnnotatedBindings().size() > 0 || registerable.getStaticInjections().size() > 0) {
                injector.set(injector.get().createChildInjector(new RuntimeBinderModule(
                        registerable.getBindings(),
                        registerable.getAnnotatedBindings(),
                        registerable.getStaticInjections().toArray(new Class[]{})
                )));
            }
        });

        getLogger().info("Startup complete.");
    }
}