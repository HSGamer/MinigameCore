package me.hsgamer.minigamecore.implementation.feature.single;

import me.hsgamer.minigamecore.base.Arena;
import me.hsgamer.minigamecore.base.Feature;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A finder to search for the arena quicker with the use of map and indexing
 *
 * @param <T> the type of the index
 */
public abstract class ArenaFinderFeature<T> implements Feature {
    private final Map<T, Arena> arenaMap = new HashMap<>();

    /**
     * Create a unique index for the arena
     *
     * @param arena the arena
     * @return the index
     */
    protected abstract T createIndex(Arena arena);

    /**
     * Add the arena to the map.
     * It's recommended to call this method in the constructor or the {@link Arena#init()} of the {@link Arena}.
     *
     * @param arena the arena
     * @return the index of the arena
     */
    public T addArena(Arena arena) {
        T index = createIndex(arena);
        arenaMap.put(index, arena);
        return index;
    }

    /**
     * Remove the arena by the index
     *
     * @param index the index
     * @return the removed arena
     */
    public Arena removeArena(T index) {
        return arenaMap.remove(index);
    }

    /**
     * Remove the arena from the map.
     * It's recommended to call this method in the {@link Arena#clear()} of the {@link Arena}.
     *
     * @param arena the arena
     */
    public void removeArena(Arena arena) {
        arenaMap.entrySet().removeIf(entry -> entry.getValue() == arena);
    }

    /**
     * Get the arena by the index
     *
     * @param index the index
     * @return the arena, or null if not found
     */
    public Arena getArena(T index) {
        return arenaMap.get(index);
    }

    /**
     * Get the arena map as an unmodifiable map
     *
     * @return the arena map
     */
    public Map<T, Arena> getArenaMap() {
        return Collections.unmodifiableMap(arenaMap);
    }
}
