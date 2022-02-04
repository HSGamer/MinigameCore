package me.hsgamer.minigamecore.bukkit.event;

import me.hsgamer.minigamecore.base.Arena;
import me.hsgamer.minigamecore.base.GameState;
import me.hsgamer.minigamecore.bukkit.BukkitArena;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * The event called when an arena changed its state
 */
public class ArenaChangeStateEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final BukkitArena arena;
    private final GameState oldState;
    private final GameState newState;

    /**
     * Construct the event
     *
     * @param arena    the arena
     * @param oldState the old game state
     * @param newState the new game state
     */
    public ArenaChangeStateEvent(BukkitArena arena, GameState oldState, GameState newState) {
        super(arena.isAsync());
        this.arena = arena;
        this.oldState = oldState;
        this.newState = newState;
    }

    /**
     * Get the handler list
     *
     * @return the handler list
     */
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
     * Get the old game state
     *
     * @return the old game state
     */
    public GameState getOldState() {
        return oldState;
    }

    /**
     * Get the new game state
     *
     * @return the new game state
     */
    public GameState getNewState() {
        return newState;
    }
}
