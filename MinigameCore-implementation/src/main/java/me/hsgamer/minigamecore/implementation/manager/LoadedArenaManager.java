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
        Optional.ofNullable(loadArenas()).ifPresent(arenas -> arenas.forEach(this::addArena));
    }

    /**
     * Load the arenas
     *
     * @return the arenas
     */
    protected abstract List<Arena> loadArenas();
}
