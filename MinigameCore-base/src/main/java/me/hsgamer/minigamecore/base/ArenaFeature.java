package me.hsgamer.minigamecore.base;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The feature handler for each arena
 *
 * @param <T> the type of the feature being handled
 */
public abstract class ArenaFeature<T extends Feature> implements Feature {
    private final Map<Arena, T> arenaFeatureMap = new ConcurrentHashMap<>();

    /**
     * Create the feature for the arena
     *
     * @param arena the arena
     * @return the feature
     */
    protected abstract T createFeature(Arena arena);

    /**
     * Get the feature for the arena
     *
     * @param arena the arena
     * @return the feature
     */
    public T getFeature(Arena arena) {
        return arenaFeatureMap.computeIfAbsent(arena, a -> {
            T feature = this.createFeature(a);
            feature.init();
            return feature;
        });
    }

    @Override
    public void init() {
        arenaFeatureMap.clear();
    }

    @Override
    public void clear() {
        arenaFeatureMap.values().forEach(Initializer::clear);
        arenaFeatureMap.clear();
    }
}
