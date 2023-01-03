package me.hsgamer.minigamecore.implementation.feature;

import me.hsgamer.minigamecore.base.Feature;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * The timer feature, which provides duration
 */
public class TimerFeature implements Feature {
    private final AtomicLong currentEndTime = new AtomicLong();

    /**
     * Get the duration of the timer in milliseconds
     *
     * @return the duration
     */
    public long getDuration() {
        long current = System.currentTimeMillis();
        long endTime = currentEndTime.get();
        return Math.max(0, endTime - current);
    }

    /**
     * Set the duration of the timer in milliseconds
     *
     * @param duration the duration
     */
    public void setDuration(long duration) {
        long current = System.currentTimeMillis();
        currentEndTime.set(current + duration);
    }

    /**
     * Get the duration of the timer
     *
     * @param unit the time unit of the duration
     * @return the duration
     */
    public long getDuration(TimeUnit unit) {
        return unit.convert(getDuration(), TimeUnit.MILLISECONDS);
    }

    /**
     * Set the duration of the timer
     *
     * @param duration the duration
     * @param unit     the time unit of the duration
     */
    public void setDuration(long duration, TimeUnit unit) {
        setDuration(unit.toMillis(duration));
    }

    @Override
    public void clear() {
        currentEndTime.lazySet(0);
    }
}
