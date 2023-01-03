package me.hsgamer.minigamecore.base;

/**
 * The feature of the arena
 */
public interface Feature extends Initializer {
    /**
     * Get the name of the feature
     *
     * @return the name
     */
    default String getName() {
        return getClass().getSimpleName();
    }
}
