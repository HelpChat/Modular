package at.helpch.modular.guice.modules;

import at.helpch.modular.Modular;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class InitialModule extends AbstractModule {
    private final Modular modular;

    public InitialModule(final Modular modular) {
        this.modular = modular;
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    @Override
    public void configure() {
        bind(Modular.class).toInstance(modular);
    }
}
