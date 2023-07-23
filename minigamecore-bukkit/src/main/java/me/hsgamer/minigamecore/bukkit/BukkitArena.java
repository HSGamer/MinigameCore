package me.hsgamer.minigamecore.bukkit;

import me.hsgamer.minigamecore.base.Arena;
import me.hsgamer.minigamecore.base.FeatureUnit;
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
     * @param name   the name of the arena
     * @param parent the parent {@link FeatureUnit}
     */
    public BukkitArena(String name, FeatureUnit parent) {
        super(name, parent);
    }

    /**
     * Create a new arena
     *
     * @param name the name of the arena
     */
    public BukkitArena(String name) {
        super(name);
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
