package at.helpch.modular.registerables;

import at.helpch.modular.guice.objects.AnnotatedBinding;
import lombok.Getter;

import java.lang.annotation.Annotation;
import java.util.*;

// ------------------------------
// Copyright (c) PiggyPiglet 2019
// https://www.piggypiglet.me
// ------------------------------
public abstract class Registerable {
    @Getter private final Map<Class, Object> bindings = new HashMap<>();
    @Getter private final List<AnnotatedBinding> annotatedBindings = new ArrayList<>();
    @Getter private final List<Class> staticInjections = new ArrayList<>();

    protected abstract void execute();

    // TODO: provide injector back into registerable and inject members
    protected void addBinding(Class interfaze, Object instance) {
        bindings.put(interfaze, instance);
    }

    protected void addBinding(Object instance) {
        bindings.put(instance.getClass(), instance);
    }

    protected void addAnnotatedBinding(Class interfaze, Class<? extends Annotation> annotation, Object instance) {
        annotatedBindings.add(AnnotatedBinding.builder()
                .clazz(interfaze)
                .annotation(annotation)
                .instance(instance)
                .build());
    }

    protected void requestStaticInjections(Class... classes) {
        staticInjections.addAll(Arrays.asList(classes));
    }

    public void run() {
        execute();
    }
}