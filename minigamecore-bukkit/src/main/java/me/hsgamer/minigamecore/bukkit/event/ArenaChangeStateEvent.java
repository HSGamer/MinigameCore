package me.hsgamer.minigamecore.bukkit.event;

import me.hsgamer.minigamecore.base.Arena;
import me.hsgamer.minigamecore.base.GameState;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * The event called when an arena changed its state
 */
public class ArenaChangeStateEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Arena arena;
    private final Class<? extends GameState> stateClass;

    /**
     * Construct the event
     *
     * @param arena      the arena
     * @param stateClass the state class
     */
    public ArenaChangeStateEvent(Arena arena, Class<? extends GameState> stateClass) {
        this.arena = arena;
        this.stateClass = stateClass;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return getHandlerList();
    }

    /**
     * Get the arena
     *
     * @return the arena
     */
    public Arena getArena() {
        return arena;
    }

    /**
     * Get the class of the state
     *
     * @return the state class
     */
    public Class<? extends GameState> getStateClass() {
        return stateClass;
    }

    /**
     * Get the instance of the state
     *
     * @return the state
     */
    public GameState getState() {
        return arena.getArenaManager().getGameState(stateClass);
    }
}
