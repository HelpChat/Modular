import at.helpch.modular.guice.modules.RuntimeBinderModule;
import at.helpch.modular.registerables.Registerable;
import com.google.guiceberry.GuiceBerryModule;
import com.google.guiceberry.junit4.GuiceBerryRule;
import com.google.inject.*;
import lombok.Data;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public final class RegisterableTest {
    private static final TestObject TEST_OBJECT = new TestObject("test");

    @Rule
    public final GuiceBerryRule guiceBerry = new GuiceBerryRule(Env.class);

    @Inject private Injector parent;
    private Injector injector;

    @Before
    public void setUp() {
        AtomicReference<Injector> injectorRef = new AtomicReference<>(parent);
        final Registerable registerable = injectorRef.get().getInstance(TestRegisterable.class);
        registerable.run();

        injectorRef.set(injectorRef.get().createChildInjector(new RuntimeBinderModule(
                registerable.getBindings(),
                registerable.getAnnotatedBindings(),
                registerable.getStaticInjections().toArray(new Class[]{})
        )));

        injector = injectorRef.get();
    }

    @Test
    public void doRegisterableBindingsKeepSameInstance() {
        assertEquals(injector.getInstance(TestObject.class), TEST_OBJECT);
        assertEquals(injector.getInstance(Key.get(TestInterface.class, TestAnnotation.class)), TEST_OBJECT);
        assertEquals(injector.getInstance(TestInterface.class), TEST_OBJECT);
    }

    public static final class Env extends AbstractModule {
        @Override
        protected void configure() {
            install(new GuiceBerryModule());
        }
    }

    private static class TestRegisterable extends Registerable {
        @Override
        protected void execute() {
            addBinding(TEST_OBJECT);
            addAnnotatedBinding(TestInterface.class, TestAnnotation.class, TEST_OBJECT);
            addBinding(TestInterface.class, TEST_OBJECT);
        }
    }

    private interface TestInterface {
        String getTest();
    }

    @Data
    private static class TestObject implements TestInterface {
        private final String test;
    }

    @BindingAnnotation
    @Target({ElementType.FIELD}) @Retention(RetentionPolicy.RUNTIME)
    public @interface TestAnnotation {
    }
}
