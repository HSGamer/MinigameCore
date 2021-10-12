package me.hsgamer.minigamecore.bukkit;

import me.hsgamer.minigamecore.base.ArenaManager;

/**
 * The simple {@link BukkitArena} that runs every seconds
 */
public class SimpleBukkitArena extends BukkitArena {
    /**
     * Create a new arena
     *
     * @param name         the name of the arena
     * @param arenaManager the arena manager
     */
    public SimpleBukkitArena(String name, ArenaManager arenaManager) {
        super(name, arenaManager);
    }

    @Override
    public int getDelay() {
        return 20;
    }

    @Override
    public int getPeriod() {
        return 20;
    }

    @Override
    public boolean isAsync() {
        return false;
    }
}
