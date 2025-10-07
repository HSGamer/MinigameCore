package io.github.projectunified.minigamecore.bukkit.extra;

import io.github.projectunified.minigamecore.extra.DisplayName;
import org.bukkit.ChatColor;

/**
 * The colored display name
 */
public interface ColoredDisplayName extends DisplayName {
    /**
     * Get the colored display name
     *
     * @return the colored display name
     */
    default String getColoredDisplayName() {
        return ChatColor.translateAlternateColorCodes('&', getDisplayName());
    }
}
