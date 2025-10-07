package io.github.projectunified.minigamecore.manager.extra;

import io.github.projectunified.minigamecore.base.Arena;
import io.github.projectunified.minigamecore.base.FeatureUnit;
import io.github.projectunified.minigamecore.manager.ArenaManager;
import io.github.projectunified.minigamecore.manager.ManagedArena;

import java.util.List;

/**
 * The {@link ArenaManager} with loaded arenas on initialization
 *
 * @param <T> the type of the identifier of the arena
 * @param <A> the type of the arena
 */
public abstract class LoadedArenaManager<T, A extends Arena & ManagedArena<T>> extends ArenaManager<T, A> {
    /**
     * Create a new arena manager
     *
     * @param parentList the parent {@link FeatureUnit} list
     */
    public LoadedArenaManager(List<FeatureUnit> parentList) {
        super(parentList);
    }

    /**
     * Create a new arena manager
     *
     * @param parent the parent {@link FeatureUnit}
     */
    public LoadedArenaManager(FeatureUnit... parent) {
        super(parent);
    }

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
    protected abstract List<A> loadArenas();

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
    public void onArenaFailToLoad(A arena) {
        // EMPTY
    }

    /**
     * Called when the arena succeeds to load
     *
     * @param arena the arena
     */
    public void onArenaSucceedToLoad(A arena) {
        // EMPTY
    }
}
