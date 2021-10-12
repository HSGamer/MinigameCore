package me.hsgamer.minigamecore.base;

/**
 * The game state, also known as the phase of the game
 */
public interface GameState extends Initializer {
    /**
     * Handle the logic of the current state of the arena
     *
     * @param arena the arena
     */
    void handle(Arena arena);

    /**
     * Get the display name representing the state
     *
     * @return the display name
     */
    default String getDisplayName() {
        return getClass().getSimpleName();
    }
}
