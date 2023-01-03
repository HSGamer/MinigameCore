package me.hsgamer.minigamecore.base;

/**
 * A unit to register
 *
 * @param <T> the type of the unit
 */
public final class Unit<T> {
    /**
     * The class of the unit
     */
    public final Class<? extends T> clazz;
    /**
     * The instance of the unit
     */
    public final T instance;

    /**
     * Create a new unit
     *
     * @param clazz    the class of the unit
     * @param instance the instance of the unit
     * @param <R>      the type of the unit
     */
    public <R extends T> Unit(Class<T> clazz, R instance) {
        this.clazz = clazz;
        this.instance = instance;
    }

    /**
     * Create a new unit
     *
     * @param instance the instance of the unit
     */
    public Unit(T instance) {
        // noinspection unchecked
        this((Class<T>) instance.getClass(), instance);
    }
}
