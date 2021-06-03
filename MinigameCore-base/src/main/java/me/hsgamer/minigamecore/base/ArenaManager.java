package me.hsgamer.minigamecore.base;

import java.util.*;

public abstract class ArenaManager implements Initializer {
    protected final Map<Class<? extends GameState>, GameState> gameStateMap = new IdentityHashMap<>();
    protected final Map<Class<? extends Feature>, Feature> featureMap = new IdentityHashMap<>();
    protected final List<Arena> arenaList = new LinkedList<>();

    public <T extends GameState> T getGameState(Class<T> gameStateClass) {
        GameState gameState = gameStateMap.get(gameStateClass);
        return gameStateClass.isInstance(gameState) ? gameStateClass.cast(gameState) : null;
    }

    public <T extends Feature> T getFeature(Class<T> featureClass) {
        Feature feature = featureMap.get(featureClass);
        return featureClass.isInstance(feature) ? featureClass.cast(feature) : null;
    }

    public Optional<Arena> getArenaByName(String name) {
        return arenaList.parallelStream().filter(arena -> arena.getName().equals(name)).findFirst();
    }
}
