package me.hsgamer.minigamecore.base;

public interface GameState extends Initializer {
    void handle(Arena arena);
}
