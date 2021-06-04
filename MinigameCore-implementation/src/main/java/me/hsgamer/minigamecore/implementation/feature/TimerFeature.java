package me.hsgamer.minigamecore.implementation.feature;

import me.hsgamer.minigamecore.base.Feature;

import java.util.concurrent.TimeUnit;

/**
 * The timer feature, which provides duration
 */
public class TimerFeature implements Feature {
    private long endTime = 0;

    /**
     * Set the duration of the timer
     *
     * @param duration the duration
     * @param unit     the time unit of the duration
     */
    public void setDuration(long duration, TimeUnit unit) {
        long current = System.currentTimeMillis();
        long durationMillis = unit.toMillis(duration);
        endTime = current + durationMillis;
    }

    /**
     * Get the duration of the timer
     *
     * @param unit the time unit of the duration
     * @return the duration
     */
    public long getDuration(TimeUnit unit) {
        long current = System.currentTimeMillis();
        if (endTime <= current) {
            return 0;
        } else {
            long durationMillis = endTime - current;
            return unit.convert(durationMillis, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void init() {
        endTime = 0;
    }

    @Override
    public void clear() {
        endTime = 0;
    }
}
