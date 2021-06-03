package me.hsgamer.minigamecore.base;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The arena. The unit that handles the game
 */
public abstract class Arena implements Runnable, Initializer {
    private final AtomicReference<Class<GameState>> currentState = new AtomicReference<>();
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
        getStateInstance().ifPresent(gameState -> gameState.handle(this));
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
    public Class<GameState> getState() {
        return this.currentState.get();
    }

    /**
     * Set the game state of the arena
     *
     * @param stateClass the class of the game state
     */
    public void setState(Class<GameState> stateClass) {
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
}
