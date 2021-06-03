package me.hsgamer.minigamecore.base.arena;

import me.hsgamer.minigamecore.base.feature.Feature;
import me.hsgamer.minigamecore.base.interfaces.Initializer;
import me.hsgamer.minigamecore.base.manager.ArenaManager;
import me.hsgamer.minigamecore.base.state.GameState;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public abstract class Arena implements Runnable, Initializer {
    private final AtomicReference<Class<GameState>> currentState = new AtomicReference<>();
    private final String name;
    private final ArenaManager arenaManager;

    protected Arena(String name, ArenaManager arenaManager) {
        this.name = name;
        this.arenaManager = arenaManager;
    }

    @Override
    public void run() {
        Optional.ofNullable(getState())
                .map(arenaManager::getGameState)
                .ifPresent(gameState -> gameState.handle(this));
    }

    public String getName() {
        return name;
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    public void setState(Class<GameState> stateClass) {
        this.currentState.set(stateClass);
    }

    public Class<GameState> getState() {
        return this.currentState.get();
    }

    public <T extends Feature> T getFeature(Class<T> featureClass) {
        return arenaManager.getFeature(featureClass);
    }
}
