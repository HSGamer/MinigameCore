package me.hsgamer.minigamecore.base;

/**
 * The game state, also known as the phase of the game
 */
public interface GameState extends Initializer {
    /**
     * Handle the logic of the current state of the arena
     *
     * @param arena the arena
     * @param delta the offset of the current time and the last time in milliseconds
     */
    void handle(Arena arena, long delta);

    /**
     * Get the display name representing the state
     *
     * @return the display name
     */
    default String getDisplayName() {
        return getClass().getSimpleName();
    }
}
