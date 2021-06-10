package me.hsgamer.minigamecore.standalone;

import me.hsgamer.minigamecore.base.Arena;
import me.hsgamer.minigamecore.base.ArenaManager;
import me.hsgamer.minigamecore.base.TimePeriod;

import java.util.Timer;
import java.util.TimerTask;

public abstract class StandaloneArena extends Arena implements TimePeriod {
    private final Timer timer = new Timer();

    /**
     * Create a new arena
     *
     * @param name         the name of the arena
     * @param arenaManager the arena manager
     */
    protected StandaloneArena(String name, ArenaManager arenaManager) {
        super(name, arenaManager);
    }

    @Override
    public void init() {
        Runnable runnable = this;
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runnable.run();
            }
        }, this.getDelay(), this.getPeriod());
    }

    @Override
    public void clear() {
        this.timer.cancel();
    }

    /**
     * Get the timer
     *
     * @return the timer
     */
    public Timer getTimer() {
        return this.timer;
    }
}
