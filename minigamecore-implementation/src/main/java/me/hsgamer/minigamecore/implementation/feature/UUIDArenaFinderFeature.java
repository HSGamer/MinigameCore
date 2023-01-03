package me.hsgamer.minigamecore.implementation.feature;

import me.hsgamer.minigamecore.base.Arena;

import java.util.UUID;

/**
 * The {@link ArenaFinderFeature} with the {@link UUID} as the index
 */
public class UUIDArenaFinderFeature extends ArenaFinderFeature<UUID> {
    @Override
    protected UUID createIndex(Arena arena) {
        return UUID.randomUUID();
    }
}
