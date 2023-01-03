package me.hsgamer.minigamecore.bukkit;

import me.hsgamer.minigamecore.base.Arena;
import me.hsgamer.minigamecore.base.ArenaManager;
import me.hsgamer.minigamecore.base.GameState;
import me.hsgamer.minigamecore.base.extra.TimePeriod;
import me.hsgamer.minigamecore.bukkit.event.ArenaChangeStateEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

/**
 * The {@link Arena} for Bukkit.
 * {@link #getPeriod()} and {@link #getDelay()} will return the time in Bukkit's ticks (20 ticks = 1 seconds).
 */
public abstract class BukkitArena extends Arena implements TimePeriod {
    private BukkitTask task;

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
     * Does the task run asynchronously?
     *
     * @return true if it does
     */
    public abstract boolean isAsync();

    @Override
    protected void initArena() {
        JavaPlugin plugin = JavaPlugin.getProvidingPlugin(this.getClass());
        if (isAsync()) {
            this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this, this.getDelay(), this.getPeriod());
        } else {
            this.task = Bukkit.getScheduler().runTaskTimer(plugin, this, this.getDelay(), this.getPeriod());
        }
    }

    @Override
    protected void clearArena() {
        if (this.task != null) {
            this.task.cancel();
        }
    }

    @Override
    protected boolean callStateChanged(GameState oldStage, GameState newStage) {
        ArenaChangeStateEvent event = new ArenaChangeStateEvent(this, oldStage, newStage);
        Bukkit.getPluginManager().callEvent(event);
        return !event.isCancelled();
    }
}
