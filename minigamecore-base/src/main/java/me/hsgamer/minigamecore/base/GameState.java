package me.hsgamer.minigamecore.base;

/**
 * The game state, also known as the phase of the game
 */
public interface GameState extends Initializer {
    /**
     * Handle the logic of the arena at the start of the state.
     * This is usually called on the first tick of the arena.
     * Default will call {@link GameState#update(Arena)}
     *
     * @param arena the arena
     */
    default void start(Arena arena) {
        // EMPTY
    }

    /**
     * Handle the logic of the arena on the "in-game" tick.
     * Called when the arena is ticked in the next ticks.
     *
     * @param arena the arena
     */
    default void update(Arena arena) {
        // EMPTY
    }

    /**
     * Handle the logic of the arena at the end of the state.
     * This is usually called when the state of the arena is changed.
     *
     * @param arena the arena
     */
    default void end(Arena arena) {
        // EMPTY
    }
}
