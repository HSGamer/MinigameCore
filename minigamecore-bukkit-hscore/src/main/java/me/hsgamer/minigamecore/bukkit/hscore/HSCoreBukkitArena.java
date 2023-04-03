package me.hsgamer.minigamecore.bukkit.hscore;

import me.hsgamer.hscore.bukkit.scheduler.Scheduler;
import me.hsgamer.hscore.bukkit.scheduler.Task;
import me.hsgamer.minigamecore.base.ArenaManager;
import me.hsgamer.minigamecore.base.extra.TimePeriod;
import me.hsgamer.minigamecore.bukkit.BukkitArena;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The implementation of {@link BukkitArena} that uses {@link me.hsgamer.hscore.bukkit.scheduler.Scheduler} to run the arena.
 * {@link #getPeriod()} and {@link #getDelay()} will return the time in Bukkit's ticks (20 ticks = 1 seconds).
 */
public class HSCoreBukkitArena extends BukkitArena implements TimePeriod {
    private Task task;

    /**
     * Create a new arena
     *
     * @param name         the name of the arena
     * @param arenaManager the arena manager
     */
    public HSCoreBukkitArena(String name, ArenaManager arenaManager) {
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
        this.task = Scheduler.CURRENT.runTaskTimer(JavaPlugin.getProvidingPlugin(HSCoreBukkitArena.class), this, this.getDelay(), this.getPeriod(), isAsync());
    }

    @Override
    protected void clearArena() {
        if (this.task != null) {
            this.task.cancel();
        }
    }
}
