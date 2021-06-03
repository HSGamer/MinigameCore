package me.hsgamer.minigamecore.base;

import java.util.*;

/**
 * The manager that handles all arenas
 */
public abstract class ArenaManager implements Initializer {
    private final Map<Class<? extends GameState>, GameState> gameStateMap = new IdentityHashMap<>();
    private final Map<Class<? extends Feature>, Feature> featureMap = new IdentityHashMap<>();
    private final List<Arena> arenaList = new LinkedList<>();

    @Override
    public void init() {
        Optional.ofNullable(loadFeatures())
                .ifPresent(features -> features.forEach(feature -> featureMap.put(feature.getClass(), feature)));
        Optional.ofNullable(loadGameStates())
                .ifPresent(gameStates -> gameStates.forEach(gameState -> gameStateMap.put(gameState.getClass(), gameState)));
        Optional.ofNullable(loadArenas())
                .ifPresent(arenaList::addAll);
        featureMap.values().forEach(Initializer::init);
        gameStateMap.values().forEach(Initializer::init);
        arenaList.forEach(Initializer::init);
    }

    @Override
    public void clear() {
        arenaList.forEach(Initializer::clear);
        gameStateMap.values().forEach(Initializer::clear);
        featureMap.values().forEach(Initializer::clear);
        arenaList.clear();
        gameStateMap.clear();
        featureMap.clear();
    }

    /**
     * Load the game states for all arenas
     *
     * @return the game states
     */
    protected abstract List<GameState> loadGameStates();

    /**
     * Load the features for all arenas
     *
     * @return the features
     */
    protected abstract List<Feature> loadFeatures();

    /**
     * Load the arenas
     *
     * @return the arenas
     */
    protected abstract List<Arena> loadArenas();

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
        return Collections.unmodifiableList(arenaList);
    }
}
