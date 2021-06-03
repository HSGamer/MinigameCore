package me.hsgamer.minigamecore.base;

import java.util.*;

public abstract class ArenaManager<A extends Arena> implements Initializer {
    private final Map<Class<? extends GameState>, GameState> gameStateMap = new IdentityHashMap<>();
    private final Map<Class<? extends Feature>, Feature> featureMap = new IdentityHashMap<>();
    private final List<A> arenaList = new LinkedList<>();

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

    public abstract List<GameState> loadGameStates();

    public abstract List<Feature> loadFeatures();

    public abstract List<A> loadArenas();

    public <T extends GameState> T getGameState(Class<T> gameStateClass) {
        GameState gameState = gameStateMap.get(gameStateClass);
        return gameStateClass.isInstance(gameState) ? gameStateClass.cast(gameState) : null;
    }

    public <T extends Feature> T getFeature(Class<T> featureClass) {
        Feature feature = featureMap.get(featureClass);
        return featureClass.isInstance(feature) ? featureClass.cast(feature) : null;
    }

    public Optional<A> getArenaByName(String name) {
        return arenaList.parallelStream().filter(arena -> arena.getName().equals(name)).findFirst();
    }

    public List<A> getAllArenas() {
        return Collections.unmodifiableList(arenaList);
    }
}
