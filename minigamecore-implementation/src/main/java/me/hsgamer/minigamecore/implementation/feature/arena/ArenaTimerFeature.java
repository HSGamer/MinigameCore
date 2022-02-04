package me.hsgamer.minigamecore.implementation.feature.arena;

import me.hsgamer.minigamecore.base.Arena;
import me.hsgamer.minigamecore.base.ArenaFeature;
import me.hsgamer.minigamecore.implementation.feature.single.TimerFeature;

/**
 * The timer feature for each arena
 */
public class ArenaTimerFeature extends ArenaFeature<TimerFeature> {
    @Override
    protected TimerFeature createFeature(Arena arena) {
        return new TimerFeature();
    }
}
