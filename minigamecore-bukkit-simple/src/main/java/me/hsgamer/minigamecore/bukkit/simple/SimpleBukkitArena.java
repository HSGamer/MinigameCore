package me.hsgamer.minigamecore.bukkit.simple;

import me.hsgamer.minigamecore.base.ArenaManager;
import me.hsgamer.minigamecore.base.extra.TimePeriod;
import me.hsgamer.minigamecore.bukkit.BukkitArena;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

/**
 * The simple implementation of {@link BukkitArena} that uses {@link BukkitTask} to run the arena.
 * {@link #getPeriod()} and {@link #getDelay()} will return the time in Bukkit's ticks (20 ticks = 1 seconds).
 */
public class SimpleBukkitArena extends BukkitArena implements TimePeriod {
    private BukkitTask task;

    /**
     * Create a new arena
     *
     * @param name         the name of the arena
     * @param arenaManager the arena manager
     */
    public SimpleBukkitArena(String name, ArenaManager arenaManager) {
        super(name, arenaManager);
    }

    @Override
    public long getDelay() {
        return 20;
    }

    @Override
    public long getPeriod() {
        return 20;
    }

    @Override
    public boolean isAsync() {
        return true;
    }

    @Override
    protected void initArena() {
        JavaPlugin plugin = JavaPlugin.getProvidingPlugin(SimpleBukkitArena.class);
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
}
