package me.hsgamer.minigamecore.bukkit;

import me.hsgamer.minigamecore.base.Arena;
import me.hsgamer.minigamecore.base.ArenaManager;
import me.hsgamer.minigamecore.base.GameState;
import me.hsgamer.minigamecore.bukkit.event.ArenaChangeStateEvent;
import org.bukkit.Bukkit;

/**
 * The {@link Arena} for Bukkit
 */
public abstract class BukkitArena extends Arena {
    /**
     * Create a new arena
     *
     * @param name         the name of the arena
     * @param arenaManager the arena manager
     */
    protected BukkitArena(String name, ArenaManager arenaManager) {
        super(name, arenaManager);
    }

    /**
     * Does the arena run asynchronously?
     *
     * @return true if it does
     */
    public abstract boolean isAsync();

    @Override
    protected boolean callStateChanged(GameState oldStage, GameState newStage) {
        ArenaChangeStateEvent event = new ArenaChangeStateEvent(this, oldStage, newStage);
        Bukkit.getPluginManager().callEvent(event);
        return !event.isCancelled();
    }
}
