package me.hsgamer.minigamecore.base;

import java.util.*;

/**
 * An internal class for the arena {@link Feature} and {@link GameState}
 */
class ArenaUnit implements Initializer {
    private final Map<Class<? extends GameState>, GameState> gameStateMap = new IdentityHashMap<>();
    private final Map<Class<? extends Feature>, Feature> featureMap = new IdentityHashMap<>();
    private final List<Feature> features = new ArrayList<>();
    private final List<GameState> gameStates = new ArrayList<>();

    private static <T> Set<Class<? extends T>> getSuperClasses(Class<T> baseClass, Class<? extends T> childClass) {
        Set<Class<? extends T>> classSet = new HashSet<>();
        classSet.add(childClass);

        Queue<Class<? extends T>> classQueue = new LinkedList<>();
        classQueue.add(childClass);
        while (true) {
            Class<? extends T> currentClass = classQueue.poll();
            if (currentClass == null) {
                break;
            }

            List<Class<?>> superClasses = new ArrayList<>(Arrays.asList(currentClass.getInterfaces()));
            Optional.ofNullable(currentClass.getSuperclass()).ifPresent(superClasses::add);

            for (Class<?> superClassOrInterface : superClasses) {
                if (baseClass.isAssignableFrom(superClassOrInterface)) {
                    Class<? extends T> superClassOrInterfaceSubClass = superClassOrInterface.asSubclass(baseClass);
                    if (classSet.add(superClassOrInterfaceSubClass)) {
                        classQueue.add(superClassOrInterfaceSubClass);
                    }
                }
            }
        }
        return classSet;
    }

    /**
     * Load the game states
     *
     * @param gameStates the game states
     */
    void loadGameStates(List<GameState> gameStates) {
        this.gameStates.addAll(gameStates);
        gameStates.forEach(gameState -> getSuperClasses(GameState.class, gameState.getClass()).forEach(clazz -> gameStateMap.put(clazz, gameState)));
    }

    /**
     * Load the features
     *
     * @param features the features
     */
    void loadFeatures(List<Feature> features) {
        this.features.addAll(features);
        features.forEach(feature -> getSuperClasses(Feature.class, feature.getClass()).forEach(clazz -> featureMap.put(clazz, feature)));
    }

    @Override
    public void init() {
        features.forEach(Initializer::init);
        gameStates.forEach(Initializer::init);
    }

    @Override
    public void postInit() {
        features.forEach(Initializer::postInit);
        gameStates.forEach(Initializer::postInit);
    }

    @Override
    public void clear() {
        for (int i = gameStates.size() - 1; i >= 0; i--) {
            gameStates.get(i).clear();
        }
        for (int i = features.size() - 1; i >= 0; i--) {
            features.get(i).clear();
        }
        featureMap.clear();
        gameStateMap.clear();
        features.clear();
        gameStates.clear();
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
