package me.hsgamer.minigamecore.implementation.manager;

import me.hsgamer.minigamecore.base.Arena;
import me.hsgamer.minigamecore.base.ArenaManager;

import java.util.List;

/**
 * The {@link ArenaManager} with loaded arenas on initialization
 */
public abstract class LoadedArenaManager extends ArenaManager {
    @Override
    public void init() {
        super.init();
        reloadArena();
    }

    /**
     * Load the arenas
     *
     * @return the arenas
     */
    protected abstract List<Arena> loadArenas();

    /**
     * Reload the arenas
     */
    public void reloadArena() {
        clearAllArenas();
        loadArenas().forEach(arena -> {
            if (addArena(arena)) {
                onArenaSucceedToLoad(arena);
            } else {
                onArenaFailToLoad(arena);
            }
        });
    }

    /**
     * Called when the arena fails to load
     *
     * @param arena the arena
     */
    public void onArenaFailToLoad(Arena arena) {
        // EMPTY
    }

    /**
     * Called when the arena succeeds to load
     *
     * @param arena the arena
     */
    public void onArenaSucceedToLoad(Arena arena) {
        // EMPTY
    }
}
