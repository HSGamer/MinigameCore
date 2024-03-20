package me.hsgamer.minigamecore.bukkit.minelib;

import io.github.projectunified.minelib.scheduler.async.AsyncScheduler;
import io.github.projectunified.minelib.scheduler.common.task.Task;
import io.github.projectunified.minelib.scheduler.global.GlobalScheduler;
import me.hsgamer.minigamecore.base.FeatureUnit;
import me.hsgamer.minigamecore.bukkit.BukkitArena;
import me.hsgamer.minigamecore.extra.TimePeriod;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * The MineLib implementation of {@link BukkitArena} that uses {@link Task} to run the arena.
 * {@link #getPeriod()} and {@link #getDelay()} will return the time in Bukkit's ticks (20 ticks = 1 seconds).
 */
public class MineLibArena extends BukkitArena implements TimePeriod {
    private Task task;

    /**
     * Create a new arena
     *
     * @param parentList the parent {@link FeatureUnit} list
     */
    public MineLibArena(List<FeatureUnit> parentList) {
        super(parentList);
    }

    /**
     * Create a new arena
     *
     * @param parent the parent {@link FeatureUnit}
     */
    public MineLibArena(FeatureUnit... parent) {
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
        JavaPlugin plugin = JavaPlugin.getProvidingPlugin(MineLibArena.class);
        this.task = (isAsync() ? AsyncScheduler.get(plugin) : GlobalScheduler.get(plugin)).runTimer(this, getDelay(), getPeriod());
    }

    @Override
    protected void clearArena() {
        if (task != null) {
            task.cancel();
        }
    }
}
