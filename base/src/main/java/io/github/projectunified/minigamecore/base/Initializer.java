package io.github.projectunified.minigamecore.base;

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
     * Called on special event when all the objects are initialized
     */
    default void postInit() {
        // EMPTY
    }

    /**
     * Clear the object
     */
    default void clear() {
        // EMPTY
    }
}
