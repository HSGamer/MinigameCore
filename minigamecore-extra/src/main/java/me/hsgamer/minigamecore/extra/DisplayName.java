package me.hsgamer.minigamecore.extra;

/**
 * The interface for the display name
 */
public interface DisplayName {
    /**
     * Get the display name
     *
     * @return the display name
     */
    default String getDisplayName() {
        return getClass().getSimpleName();
    }
}
