package me.hsgamer.minigamecore.base;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/**
 * An internal class for the arena {@link Feature} and {@link GameState}
 */
class ArenaUnit implements Initializer {
    private final Map<Class<? extends GameState>, GameState> gameStateMap = new IdentityHashMap<>();
    private final Map<Class<? extends Feature>, Feature> featureMap = new IdentityHashMap<>();

    /**
     * Load the game states
     *
     * @param units the units
     */
    void loadGameStates(List<Unit<GameState>> units) {
        units.forEach(unit -> gameStateMap.put(unit.clazz, unit.instance));
    }

    /**
     * Load the features
     *
     * @param units the units
     */
    void loadFeatures(List<Unit<Feature>> units) {
        units.forEach(unit -> featureMap.put(unit.clazz, unit.instance));
    }

    @Override
    public void init() {
        featureMap.values().forEach(Initializer::init);
        gameStateMap.values().forEach(Initializer::init);
    }

    @Override
    public void postInit() {
        featureMap.values().forEach(Initializer::postInit);
        gameStateMap.values().forEach(Initializer::postInit);
    }

    @Override
    public void clear() {
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
    <T extends GameState> T getGameState(Class<T> gameStateClass) {
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
    <T extends Feature> T getFeature(Class<T> featureClass) {
        Feature feature = featureMap.get(featureClass);
        return featureClass.isInstance(feature) ? featureClass.cast(feature) : null;
    }
}
