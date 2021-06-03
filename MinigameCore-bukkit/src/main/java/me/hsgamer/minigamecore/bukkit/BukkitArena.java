package me.hsgamer.minigamecore.bukkit;

import me.hsgamer.minigamecore.base.Arena;
import me.hsgamer.minigamecore.base.ArenaManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

/**
 * The {@link Arena} for Bukkit
 */
public abstract class BukkitArena extends Arena {
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
     * Get the delay (in ticks) before the task runs
     *
     * @return the delay ticks
     */
    protected abstract int getDelay();

    /**
     * Get the period (in ticks) between task calling
     *
     * @return the period ticks
     */
    protected abstract int getPeriod();

    /**
     * Does the task run asynchronously?
     *
     * @return true if it does
     */
    protected abstract boolean isAsync();

    @Override
    public void init() {
        JavaPlugin plugin = JavaPlugin.getProvidingPlugin(getClass());
        if (isAsync()) {
            task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this, getDelay(), getPeriod());
        } else {
            task = Bukkit.getScheduler().runTaskTimer(plugin, this, getDelay(), getPeriod());
        }
    }

    @Override
    public void clear() {
        if (task != null && !task.isCancelled()) {
            task.cancel();
        }
    }
}
