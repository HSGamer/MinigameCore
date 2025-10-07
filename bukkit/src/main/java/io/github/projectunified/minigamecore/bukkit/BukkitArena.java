package io.github.projectunified.minigamecore.bukkit;

import io.github.projectunified.minigamecore.base.Arena;
import io.github.projectunified.minigamecore.base.FeatureUnit;
import io.github.projectunified.minigamecore.base.GameState;
import io.github.projectunified.minigamecore.bukkit.event.ArenaChangeStateEvent;
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

    /**
     * Whether the task is async or not. Default is true
     *
     * @return true if the task is async
     */
    public boolean isAsync() {
        return true;
    }

    @Override
    protected boolean callStateChanged(GameState oldStage, GameState newStage) {
        ArenaChangeStateEvent event = new ArenaChangeStateEvent(this, oldStage, newStage);
        Bukkit.getPluginManager().callEvent(event);
        return !event.isCancelled();
    }
}
