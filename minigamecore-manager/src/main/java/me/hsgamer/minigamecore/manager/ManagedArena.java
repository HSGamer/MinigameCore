package me.hsgamer.minigamecore.manager;

import me.hsgamer.minigamecore.base.Arena;
import me.hsgamer.minigamecore.base.FeatureUnit;
import me.hsgamer.minigamecore.manager.ArenaManager;

/**
 * An extension of {@link Arena} that can be managed by {@link ArenaManager}
 */
public interface ManagedArena {
    /**
     * Convenient method. Remove the arena from the arena manager
     */
    default void removeFromManager() {
        if (this instanceof Arena) {
            Arena arena = (Arena) this;
            FeatureUnit parentUnit = arena.getParent();
            if (parentUnit instanceof ArenaManager) {
                ((ArenaManager) parentUnit).removeArena(arena);
            }
        }
    }
}
