package me.hsgamer.minigamecore.base;

/**
 * The interface represents that the object can be initialized and cleared
 */
public interface Initializer {
    /**
     * Init the object
     */
    default void init() {
        // EMPTY
    }

    /**
     * Clear the object
     */
    default void clear() {
        // EMPTY
    }
}
