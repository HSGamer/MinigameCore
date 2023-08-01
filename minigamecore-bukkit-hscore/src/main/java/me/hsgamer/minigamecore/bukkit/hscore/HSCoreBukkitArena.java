package me.hsgamer.minigamecore.bukkit.hscore;

import me.hsgamer.hscore.bukkit.scheduler.Scheduler;
import me.hsgamer.hscore.bukkit.scheduler.Task;
import me.hsgamer.minigamecore.base.FeatureUnit;
import me.hsgamer.minigamecore.bukkit.BukkitArena;
import me.hsgamer.minigamecore.extra.TimePeriod;

import java.util.List;

/**
 * The implementation of {@link BukkitArena} that uses {@link me.hsgamer.hscore.bukkit.scheduler.Scheduler} to run the arena.
 * {@link #getPeriod()} and {@link #getDelay()} will return the time in Bukkit's ticks (20 ticks = 1 seconds).
 */
public class HSCoreBukkitArena extends BukkitArena implements TimePeriod {
    private Task task;

    /**
     * Create a new arena
     *
     * @param name       the name of the arena
     * @param parentList the parent {@link FeatureUnit} list
     */
    public HSCoreBukkitArena(String name, List<FeatureUnit> parentList) {
        super(name, parentList);
    }

    /**
     * Create a new arena
     *
     * @param name   the name of the arena
     * @param parent the parent {@link FeatureUnit}
     */
    public HSCoreBukkitArena(String name, FeatureUnit... parent) {
        super(name, parent);
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
        this.task = Scheduler.providingPlugin(HSCoreBukkitArena.class).runner(isAsync()).runTaskTimer(this, this.getDelay(), this.getPeriod());
    }

    @Override
    protected void clearArena() {
        if (this.task != null) {
            this.task.cancel();
        }
    }
}
