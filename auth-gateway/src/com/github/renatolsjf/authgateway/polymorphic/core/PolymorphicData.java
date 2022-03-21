package com.github.renatolsjf.authgateway.polymorphic.core;

import java.lang.reflect.Constructor;
import java.util.Map;

/**
 * The pattern main abstraction. The idea is that each implementation has its own data and also a shared set of
 * data. The base uses generics to assert that a certain implementation can only be transformed to an implementation
 * of its own set. In the end, there is no single base for the pattern implementation. This is just an
 * example implementation
 * @param <T> The main class that represents the polymorphic set of data. Generally, must the the own class
 *           extending this abstraction, which, int turn, should also be an abstraction. The actual implementations
 *           should extend T, so no two shared sets of data can be intertwined.
 */
public abstract class PolymorphicData<T extends PolymorphicData> {

    private static final String SHARED = "Shared";
    private final String PROPERTIES_PREFIX = this.getClass().getSimpleName() + "_";

    protected final Map<String, String> properties;

    protected PolymorphicData(Map<String, String> properties) {
        this.properties = properties;
    }

    public final <S extends T> S as(Class<S> clazz) {

        try {
            Constructor<S> c = clazz.getConstructor(Map.class);
            return c.newInstance(this.properties);
        } catch (Exception e) {
            throw new TransformationException("An unexpected error occurred while doing a " +
                    "polymorphic transformation. Perhaps a misconfiguration?", e);
        }

    }

    protected void setProperty(String key, String value) {
        this.properties.put(this.PROPERTIES_PREFIX + key, value);
    }
    protected  String getProperty(String key) {
        return this.properties.get(this.PROPERTIES_PREFIX + key);
    }
    protected String getPropertyOrDefault(String key, String defaultValue) {
        String v = this.getProperty(key);
        return v != null ? v : defaultValue;
    }

    protected void setSharedProperty(String key, String value) { this.properties.put(SHARED + key, value); }
    protected  String getSharedProperty(String key) {
        return this.properties.get(SHARED + key);
    }
    protected String getSharedPropertyOrDefault(String key, String defaultValue) {
        String v = this.getSharedProperty(key);
        return v != null ? v : defaultValue;
    }

}
