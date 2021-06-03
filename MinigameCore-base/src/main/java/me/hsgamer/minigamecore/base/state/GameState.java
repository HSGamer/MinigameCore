package me.hsgamer.minigamecore.base.state;

import me.hsgamer.minigamecore.base.arena.Arena;
import me.hsgamer.minigamecore.base.interfaces.Initializer;

public interface GameState extends Initializer {
    void handle(Arena arena);
}
