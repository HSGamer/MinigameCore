package me.hsgamer.minigamecore.base;

import java.util.*;

/**
 * An internal class for the arena {@link Feature} and {@link GameState}
 */
class ArenaUnit implements Initializer {
    private final Map<Class<? extends GameState>, GameState> gameStateMap = new IdentityHashMap<>();
    private final Map<Class<? extends Feature>, Feature> featureMap = new IdentityHashMap<>();

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
     * @param units the units
     */
    void loadGameStates(List<Unit<GameState>> units) {
        units.forEach(unit -> getSuperClasses(GameState.class, unit.clazz).forEach(clazz -> gameStateMap.put(clazz, unit.instance)));
    }

    /**
     * Load the features
     *
     * @param units the units
     */
    void loadFeatures(List<Unit<Feature>> units) {
        units.forEach(unit -> getSuperClasses(Feature.class, unit.clazz).forEach(clazz -> featureMap.put(clazz, unit.instance)));
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
