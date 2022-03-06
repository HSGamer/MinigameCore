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

    /**
     * Check if the feature supports the arena.
     * Used to check if the arena meets the requirements of the feature.
     *
     * @param arena the arena
     * @return true if the feature supports the arena
     */
    default boolean isArenaSupported(Arena arena) {
        return true;
    }
}
