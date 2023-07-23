package me.hsgamer.minigamecore.manager;

import me.hsgamer.minigamecore.base.Arena;
import me.hsgamer.minigamecore.base.FeatureUnit;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The manager that handles all arenas
 */
public abstract class ArenaManager extends FeatureUnit {
    private final List<Arena> arenaList = new LinkedList<>();

    /**
     * Create a new arena manager
     *
     * @param parentFeatureUnit the parent {@link FeatureUnit}
     */
    public ArenaManager(FeatureUnit parentFeatureUnit) {
        super(parentFeatureUnit);
    }

    /**
     * Create a new arena manager
     */
    public ArenaManager() {
        super();
    }

    @Override
    public void postInit() {
        super.postInit();
        arenaList.forEach(Arena::postInit);
    }

    @Override
    public void clear() {
        clearAllArenas();
        super.clear();
    }

    /**
     * Get the arena by its name
     *
     * @param name the name of the arena
     * @return the arena
     */
    public Optional<Arena> getArenaByName(String name) {
        return arenaList.parallelStream().filter(arena -> arena.getName().equals(name)).findFirst();
    }

    /**
     * Get all arenas
     *
     * @return the list of arenas
     */
    public List<Arena> getAllArenas() {
        return Collections.unmodifiableList(arenaList.stream().filter(Objects::nonNull).collect(Collectors.toList()));
    }

    /**
     * Add an arena
     *
     * @param arena the arena
     */
    public boolean addArena(Arena arena) {
        if (!arena.isValid()) return false;
        arena.init();
        arenaList.add(arena);
        return true;
    }

    /**
     * Remove an arena
     *
     * @param arena the arena
     */
    public void removeArena(Arena arena) {
        if (arenaList.remove(arena)) {
            arena.clear();
        }
    }

    /**
     * Clear all arenas
     */
    public void clearAllArenas() {
        arenaList.forEach(arena -> {
            if (arena == null) return;
            arena.clear();
        });
        arenaList.clear();
    }
}
