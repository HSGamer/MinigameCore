package me.hsgamer.minigamecore.base;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The arena. The unit that handles the game
 */
public class Arena implements Runnable, Initializer {
    private final AtomicReference<Class<? extends GameState>> currentState = new AtomicReference<>();
    private final AtomicReference<Class<? extends GameState>> nextState = new AtomicReference<>();
    private final String name;
    private final ArenaManager arenaManager;

    /**
     * Create a new arena
     *
     * @param name         the name of the arena
     * @param arenaManager the arena manager
     */
    public Arena(String name, ArenaManager arenaManager) {
        this.name = name;
        this.arenaManager = arenaManager;
    }

    @Override
    public final void run() {
        Optional<GameState> currentStateOptional = getStateInstance();
        Optional<GameState> nextStateOptional = getNextStateInstance();
        if (nextStateOptional.isPresent()) {
            GameState nextStateInstance = nextStateOptional.get();
            if (callStateChanged(currentStateOptional.orElse(null), nextStateInstance)) {
                currentState.set(nextStateInstance.getClass());
                nextState.set(null);
                currentStateOptional.ifPresent(gameState -> gameState.end(this));
                nextStateInstance.start(this);
                return;
            }
        }
        currentStateOptional.ifPresent(gameState -> gameState.update(this));
    }

    /**
     * This is called when the state is changed.
     * This is usually used to do actions on state changed.
     * If you did change the state with {@link #setNextState(Class)} or {@link #setNextStateLazy(Class)}, set the return value to false.
     *
     * @param oldStage the old state
     * @param newStage the new state
     * @return true if the change is successful, otherwise false
     */
    protected boolean callStateChanged(GameState oldStage, GameState newStage) {
        return true;
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
     * Get the instance of the game state of the arena
     *
     * @return the instance of the game state
     */
    public Optional<GameState> getStateInstance() {
        return Optional.ofNullable(getState()).map(arenaManager::getGameState);
    }

    /**
     * Get the next game state of the arena
     *
     * @return the class of the game state
     */
    public Class<? extends GameState> getNextState() {
        return this.nextState.get();
    }

    /**
     * Set the next game state of the arena
     *
     * @param stateClass the class of the game state
     */
    public void setNextState(Class<? extends GameState> stateClass) {
        this.nextState.set(stateClass);
    }

    /**
     * Eventually set the next game state of the arena
     *
     * @param stateClass the class of the game state
     */
    public void setNextStateLazy(Class<? extends GameState> stateClass) {
        this.nextState.lazySet(stateClass);
    }

    /**
     * Get the instance of the next game state of the arena
     *
     * @return the instance of the game state
     */
    public Optional<GameState> getNextStateInstance() {
        return Optional.ofNullable(getNextState()).map(arenaManager::getGameState);
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
