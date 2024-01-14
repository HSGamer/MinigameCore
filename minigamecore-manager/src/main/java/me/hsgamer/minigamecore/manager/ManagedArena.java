package me.hsgamer.minigamecore.manager;

import me.hsgamer.minigamecore.base.Arena;
import me.hsgamer.minigamecore.base.FeatureUnit;

/**
 * An extension of {@link Arena} that can be managed by {@link ArenaManager}
 *
 * @param <T> the type of the identifier of the arena
 */
public interface ManagedArena<T> {
    /**
     * Get the identifier of the arena
     *
     * @return the identifier
     */
    T getIdentifier();

    /**
     * Convenient method. Remove the arena from the arena manager
     */
    default void removeFromManager() {
        if (this instanceof Arena) {
            Arena arena = (Arena) this;
            FeatureUnit parentUnit = arena.getParent();
            if (parentUnit instanceof ArenaManager) {
                //noinspection unchecked
                ((ArenaManager<T>) parentUnit).removeArena(getIdentifier());
            }
        }
    }
}
