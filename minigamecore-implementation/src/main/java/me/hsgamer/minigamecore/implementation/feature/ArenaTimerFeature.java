package me.hsgamer.minigamecore.implementation.feature;

import me.hsgamer.minigamecore.base.Arena;
import me.hsgamer.minigamecore.base.ArenaFeature;

/**
 * The timer feature for each arena
 */
public class ArenaTimerFeature extends ArenaFeature<TimerFeature> {
    @Override
    protected TimerFeature createFeature(Arena arena) {
        return new TimerFeature();
    }
}
