package me.hsgamer.minigamecore.implementation.manager;

import me.hsgamer.minigamecore.base.Arena;
import me.hsgamer.minigamecore.base.ArenaManager;

import java.util.List;
import java.util.Optional;

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
        Optional.ofNullable(loadArenas()).ifPresent(arenas -> {
            for (Arena arena : arenas) {
                if (!addArena(arena)) {
                    onArenaFailedToLoad(arena);
                }
            }
        });
    }

    /**
     * Called when the arena failed to load
     *
     * @param arena the arena
     */
    public void onArenaFailedToLoad(Arena arena) {
        // EMPTY
    }
}
