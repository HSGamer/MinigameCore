package me.hsgamer.minigamecore.implementation.feature.single;

import me.hsgamer.minigamecore.base.Feature;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * The timer feature, which provides duration
 */
public class TimerFeature implements Feature {
    private final AtomicLong cooldown = new AtomicLong();

    /**
     * Set the duration of the timer
     *
     * @param duration the duration
     * @param unit     the time unit of the duration
     */
    public void setDuration(long duration, TimeUnit unit) {
        long current = System.currentTimeMillis();
        long durationMillis = unit.toMillis(duration);
        cooldown.lazySet(current + durationMillis);
    }

    /**
     * Get the duration of the timer
     *
     * @param unit the time unit of the duration
     * @return the duration
     */
    public long getDuration(TimeUnit unit) {
        long current = System.currentTimeMillis();
        long endTime = cooldown.get();
        long durationMillis = Math.max(0, endTime - current);
        return unit.convert(durationMillis, TimeUnit.MILLISECONDS);
    }

    @Override
    public void clear() {
        cooldown.lazySet(0);
    }
}
