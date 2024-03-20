package me.hsgamer.minigamecore.bukkit;

import me.hsgamer.minigamecore.base.Arena;
import me.hsgamer.minigamecore.base.FeatureUnit;
import me.hsgamer.minigamecore.base.GameState;
import me.hsgamer.minigamecore.bukkit.event.ArenaChangeStateEvent;
import org.bukkit.Bukkit;

import java.util.List;

/**
 * The {@link Arena} for Bukkit
 */
public abstract class BukkitArena extends Arena {
    /**
     * Create a new arena
     *
     * @param parentList the parent {@link FeatureUnit} list
     */
    public BukkitArena(List<FeatureUnit> parentList) {
        super(parentList);
    }

    /**
     * Create a new arena
     *
     * @param parent the parent {@link FeatureUnit}
     */
    public BukkitArena(FeatureUnit... parent) {
        super(parent);
    }

    @Override
    protected boolean callStateChanged(GameState oldStage, GameState newStage) {
        ArenaChangeStateEvent event = new ArenaChangeStateEvent(this, oldStage, newStage);
        Bukkit.getPluginManager().callEvent(event);
        return !event.isCancelled();
    }
}
