package me.hsgamer.minigamecore.standalone;

import me.hsgamer.minigamecore.base.Arena;
import me.hsgamer.minigamecore.base.FeatureUnit;
import me.hsgamer.minigamecore.extra.TimePeriod;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The standalone {@link Arena}
 */
public abstract class StandaloneArena extends Arena implements TimePeriod {
    private final Timer timer = new Timer();
    private TimerTask timerTask;

    /**
     * Create a new arena
     *
     * @param name   the name of the arena
     * @param parent the parent {@link FeatureUnit}
     */
    protected StandaloneArena(String name, FeatureUnit parent) {
        super(name, parent);
    }

    /**
     * Create a new arena
     *
     * @param name the name of the arena
     */
    protected StandaloneArena(String name) {
        super(name);
    }

    @Override
    protected void initArena() {
        this.timerTask = new TimerTask() {
            @Override
            public void run() {
                StandaloneArena.this.run();
            }
        };
        this.timer.schedule(timerTask, this.getDelay(), this.getPeriod());
    }

    @Override
    protected void clearArena() {
        if (this.timerTask != null) {
            this.timerTask.cancel();
        }
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
