package me.hsgamer.minigamecore.base;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The arena. The unit that handles the game
 */
public abstract class Arena implements Runnable, Initializer {
    private final AtomicReference<Long> lastTime = new AtomicReference<>(System.currentTimeMillis());
    private final AtomicReference<Class<? extends GameState>> currentState = new AtomicReference<>();
    private final String name;
    private final ArenaManager arenaManager;

    /**
     * Create a new arena
     *
     * @param name         the name of the arena
     * @param arenaManager the arena manager
     */
    protected Arena(String name, ArenaManager arenaManager) {
        this.name = name;
        this.arenaManager = arenaManager;
    }

    @Override
    public void run() {
        long current = System.currentTimeMillis();
        getStateInstance().ifPresent(gameState -> gameState.handle(this, getDeltaTime(current, lastTime.get())));
        lastTime.set(current);
    }

    /**
     * Get the delta time (the offset of the current time and the last time) in milliseconds
     *
     * @param current the current time
     * @param last    the last time
     * @return the delta time
     */
    protected long getDeltaTime(long current, long last) {
        return current - last;
    }

    /**
     * Get the last time that is used to calculate the delta time
     *
     * @return the last time
     */
    public long getLastTime() {
        return lastTime.get();
    }

    /**
     * Get the name of the arena
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the manager that the arena is belonged to
     *
     * @return the manager
     */
    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    /**
     * Get the game state of the arena
     *
     * @return the class of the game state
     */
    public Class<? extends GameState> getState() {
        return this.currentState.get();
    }

    /**
     * Set the game state of the arena
     *
     * @param stateClass the class of the game state
     */
    public void setState(Class<? extends GameState> stateClass) {
        this.currentState.set(stateClass);
    }

    /**
     * Get the instance of the game state of the arena
     *
     * @return the instance of the game state
     */
    public Optional<GameState> getStateInstance() {
        return Optional.ofNullable(getState()).map(arenaManager::getGameState);
    }

    /**
     * Convenient method. Get the instance of the feature
     *
     * @param featureClass the class of the feature
     * @param <T>          the type of the feature
     * @return the instance of the feature
     */
    public <T extends Feature> T getFeature(Class<T> featureClass) {
        return arenaManager.getFeature(featureClass);
    }

    /**
     * Convenient method. Get the feature for the arena
     *
     * @param arenaFeatureClass the class of the arena feature
     * @param <T>               the type of the feature in the arena feature
     * @param <A>               the type of the arena feature
     * @return the feature
     */
    public <T extends Feature, A extends ArenaFeature<T>> T getArenaFeature(Class<A> arenaFeatureClass) {
        return getFeature(arenaFeatureClass).getFeature(this);
    }

    /**
     * Convenient method. Remove the arena from the arena manager
     */
    public void removeFromManager() {
        arenaManager.removeArena(this);
    }
}
