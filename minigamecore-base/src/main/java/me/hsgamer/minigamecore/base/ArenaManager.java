package me.hsgamer.minigamecore.base;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The manager that handles all arenas
 */
public abstract class ArenaManager implements Initializer {
    private final ArenaUnit arenaUnit = new ArenaUnit();
    private final List<Arena> arenaList = new LinkedList<>();

    /**
     * Load the game states for all arenas
     *
     * @return the game states
     */
    protected abstract List<Unit<GameState>> loadGameStates();

    /**
     * Load the features for all arenas
     *
     * @return the features
     */
    protected abstract List<Unit<Feature>> loadFeatures();

    @Override
    public void init() {
        arenaUnit.loadFeatures(loadFeatures());
        arenaUnit.loadGameStates(loadGameStates());
        arenaUnit.init();
    }

    @Override
    public void postInit() {
        arenaUnit.postInit();
        arenaList.forEach(Arena::postInit);
    }

    @Override
    public void clear() {
        clearAllArenas();
        arenaUnit.clear();
    }

    /**
     * Get the instance of the game state
     *
     * @param gameStateClass the class of the game state
     * @param <T>            the type of the game state
     * @return the instance of the game state
     */
    public <T extends GameState> T getGameState(Class<T> gameStateClass) {
        return arenaUnit.getGameState(gameStateClass);
    }

    /**
     * Get the instance of the feature
     *
     * @param featureClass the class of the feature
     * @param <T>          the type of the feature
     * @return the instance of the feature
     */
    public <T extends Feature> T getFeature(Class<T> featureClass) {
        return arenaUnit.getFeature(featureClass);
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
