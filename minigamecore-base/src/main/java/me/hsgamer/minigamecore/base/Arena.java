package me.hsgamer.minigamecore.base;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The arena. The unit that handles the game
 */
public class Arena implements Runnable, Initializer {
    private final ArenaUnit arenaUnit = new ArenaUnit();
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

    /**
     * Load the game states for all arenas
     *
     * @return the game states
     */
    protected List<GameState> loadGameStates() {
        return Collections.emptyList();
    }

    /**
     * Load the features for all arenas
     *
     * @return the features
     */
    protected List<Feature> loadFeatures() {
        return Collections.emptyList();
    }

    /**
     * Initialize the arena
     *
     * @see Initializer#init()
     */
    protected void initArena() {
        // Override this method to do something
    }

    /**
     * Post-initialize the arena
     *
     * @see Initializer#postInit()
     */
    protected void postInitArena() {
        // Override this method to do something
    }

    /**
     * Clear the arena
     *
     * @see Initializer#clear()
     */
    protected void clearArena() {
        // Override this method to do something
    }

    /**
     * This is called when the state is changed.
     * This is usually used to do actions on state changed.
     * If you did change the state with {@link #setNextState(Class)}, set the return value to false.
     *
     * @param oldStage the old state
     * @param newStage the new state
     * @return true if the change is successful, otherwise false
     */
    protected boolean callStateChanged(GameState oldStage, GameState newStage) {
        return true;
    }

    /**
     * Check if the arena is valid.
     * Mainly called when the arena is being registered to the arena manager.
     *
     * @return true if the arena is valid, otherwise false
     */
    public boolean isValid() {
        return true;
    }

    @Override
    public final void init() {
        arenaUnit.loadFeatures(loadFeatures());
        arenaUnit.loadGameStates(loadGameStates());
        arenaUnit.init();
        initArena();
    }

    @Override
    public final void postInit() {
        arenaUnit.postInit();
        postInitArena();
    }

    @Override
    public final void clear() {
        clearArena();
        arenaUnit.clear();
    }

    @Override
    public final void run() {
        Optional<GameState> currentStateOptional = getCurrentStateInstance();
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
     * Get the instance of the game state
     *
     * @param gameStateClass the class of the game state
     * @param <T>            the type of the game state
     * @return the instance of the game state
     */
    public <T extends GameState> T getGameState(Class<T> gameStateClass) {
        T gameState = arenaUnit.getGameState(gameStateClass);
        if (gameState == null) {
            gameState = arenaManager.getGameState(gameStateClass);
        }
        return gameState;
    }

    /**
     * Get the game state of the arena
     *
     * @return the class of the game state
     */
    public Class<? extends GameState> getCurrentState() {
        return this.currentState.get();
    }

    /**
     * Get the instance of the current game state of the arena
     *
     * @return the instance of the game state
     */
    public Optional<GameState> getCurrentStateInstance() {
        return Optional.ofNullable(getCurrentState()).map(this::getGameState);
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
     * Set the next game state of the arena.
     *
     * @param stateClass the class of the game state
     */
    public void setNextState(Class<? extends GameState> stateClass) {
        this.nextState.lazySet(stateClass);
    }

    /**
     * Get the instance of the next game state of the arena
     *
     * @return the instance of the game state
     */
    public Optional<GameState> getNextStateInstance() {
        return Optional.ofNullable(getNextState()).map(this::getGameState);
    }

    /**
     * Get the instance of the feature
     *
     * @param featureClass the class of the feature
     * @param <T>          the type of the feature
     * @return the instance of the feature
     */
    public <T extends Feature> T getFeature(Class<T> featureClass) {
        T feature = arenaUnit.getFeature(featureClass);
        if (feature == null) {
            feature = arenaManager.getFeature(featureClass);
        }
        return feature;
    }

    /**
     * Convenient method. Remove the arena from the arena manager
     */
    public void removeFromManager() {
        arenaManager.removeArena(this);
    }
}
