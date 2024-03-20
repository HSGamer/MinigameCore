package me.hsgamer.minigamecore.bukkit.simple;

import me.hsgamer.minigamecore.base.FeatureUnit;
import me.hsgamer.minigamecore.bukkit.BukkitArena;
import me.hsgamer.minigamecore.extra.TimePeriod;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

/**
 * The simple implementation of {@link BukkitArena} that uses {@link BukkitTask} to run the arena.
 * {@link #getPeriod()} and {@link #getDelay()} will return the time in Bukkit's ticks (20 ticks = 1 seconds).
 */
public class SimpleBukkitArena extends BukkitArena implements TimePeriod {
    private BukkitTask task;

    /**
     * Create a new arena
     *
     * @param parentList the parent {@link FeatureUnit} list
     */
    public SimpleBukkitArena(List<FeatureUnit> parentList) {
        super(parentList);
    }

    /**
     * Create a new arena
     *
     * @param parent the parent {@link FeatureUnit}
     */
    public SimpleBukkitArena(FeatureUnit... parent) {
        super(parent);
    }

    /**
     * Whether the task is async or not. Default is true
     *
     * @return true if the task is async
     */
    protected boolean isAsync() {
        return true;
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
