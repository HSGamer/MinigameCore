package me.hsgamer.minigamecore.base;

/**
 * The interface for dealing with time period
 */
public interface TimePeriod {
    /**
     * Get the delay before the task runs
     *
     * @return the delay time
     */
    long getDelay();

    /**
     * Get the period between task calling
     *
     * @return the period time
     */
    long getPeriod();
}
