package me.hsgamer.minigamecore.base;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The manager that handles all arenas
 */
public class ArenaManager implements Initializer {
    private final Map<Class<? extends GameState>, GameState> gameStateMap = new IdentityHashMap<>();
    private final Map<Class<? extends Feature>, Feature> featureMap = new IdentityHashMap<>();
    private final List<Arena> arenaList = new LinkedList<>();

    /**
     * Load the game states for all arenas
     *
     * @return the game states
     */
    protected List<Unit<GameState>> loadGameStates() {
        return Collections.emptyList();
    }

    /**
     * Load the features for all arenas
     *
     * @return the features
     */
    protected List<Unit<Feature>> loadFeatures() {
        return Collections.emptyList();
    }

    @Override
    public void init() {
        loadFeatures().forEach(unit -> featureMap.put(unit.clazz, unit.instance));
        loadGameStates().forEach(unit -> gameStateMap.put(unit.clazz, unit.instance));
        featureMap.values().forEach(Initializer::init);
        gameStateMap.values().forEach(Initializer::init);
    }

    @Override
    public void postInit() {
        featureMap.values().forEach(Feature::postInit);
        gameStateMap.values().forEach(GameState::postInit);
        arenaList.forEach(Arena::postInit);
    }

    @Override
    public void clear() {
        clearAllArenas();
        gameStateMap.values().forEach(Initializer::clear);
        featureMap.values().forEach(Initializer::clear);
        gameStateMap.clear();
        featureMap.clear();
    }

    /**
     * Get the instance of the game state
     *
     * @param gameStateClass the class of the game state
     * @param <T>            the type of the game state
     * @return the instance of the game state
     */
    public <T extends GameState> T getGameState(Class<T> gameStateClass) {
        GameState gameState = gameStateMap.get(gameStateClass);
        return gameStateClass.isInstance(gameState) ? gameStateClass.cast(gameState) : null;
    }

    /**
     * Get the instance of the feature
     *
     * @param featureClass the class of the feature
     * @param <T>          the type of the feature
     * @return the instance of the feature
     */
    public <T extends Feature> T getFeature(Class<T> featureClass) {
        Feature feature = featureMap.get(featureClass);
        return featureClass.isInstance(feature) ? featureClass.cast(feature) : null;
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
