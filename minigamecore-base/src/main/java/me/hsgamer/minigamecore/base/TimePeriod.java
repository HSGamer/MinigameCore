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

    /**
     * Should the period be removed from the delta time
     *
     * @return true if the period should be removed
     */
    default boolean isRemovePeriodFromDeltaTime() {
        return true;
    }
}
