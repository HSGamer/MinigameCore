package me.hsgamer.minigamecore.base;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * Wrap the list of instances into a list of units
     *
     * @param list the list of instances
     * @param <T>  the type of the unit
     * @return the list of units
     */
    public static <T> List<Unit<T>> wrap(List<T> list) {
        return list.stream().map(Unit::new).collect(Collectors.toList());
    }

    /**
     * Wrap the array of instances into a list of units
     *
     * @param array the array of instances
     * @param <T>   the type of the unit
     * @return the list of units
     */
    public static <T> List<Unit<T>> wrap(T... array) {
        return wrap(Arrays.asList(array));
    }
}
