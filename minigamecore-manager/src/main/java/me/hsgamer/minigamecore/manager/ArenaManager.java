package me.hsgamer.minigamecore.manager;

import me.hsgamer.minigamecore.base.Arena;
import me.hsgamer.minigamecore.base.FeatureUnit;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.function.Consumer;

/**
 * The manager that handles all arenas
 */
public abstract class ArenaManager extends FeatureUnit {
    private final Map<String, Arena> arenaMap = new HashMap<>();

    /**
     * Create a new arena manager
     *
     * @param parentList the parent {@link FeatureUnit} list
     */
    public ArenaManager(List<FeatureUnit> parentList) {
        super(parentList);
    }

    /**
     * Create a new arena manager
     *
     * @param parent the parent {@link FeatureUnit}
     */
    public ArenaManager(FeatureUnit... parent) {
        super(parent);
    }

    @Override
    public void postInit() {
        super.postInit();
        arenaMap.values().forEach(Arena::postInit);
    }

    @Override
    public void clear() {
        clearAllArenas();
        super.clear();
    }

    /**
     * Get the arena by its name
     *
     * @param name the name of the arena
     * @return the arena
     */
    public Optional<Arena> getArenaByName(String name) {
        return Optional.ofNullable(arenaMap.get(name));
    }

    /**
     * Get all arenas
     *
     * @return the list of arenas
     */
    public List<Arena> getAllArenas() {
        return new ArrayList<>(arenaMap.values());
    }

    /**
     * Add an arena
     *
     * @param arena the arena
     */
    public boolean addArena(Arena arena) {
        String name = arena.getName();
        if (arenaMap.containsKey(name)) return false;

        if (!arena.isValid()) return false;
        arena.init();

        arenaMap.put(name, arena);
        return true;
    }

    /**
     * Remove an arena
     *
     * @param arena the arena
     */
    public void removeArena(Arena arena) {
        Arena removed = arenaMap.remove(arena.getName());
        if (removed != null) {
            removed.clear();
        }
    }

    /**
     * Remove an arena
     *
     * @param name the name of the arena
     */
    public void removeArena(String name) {
        Arena removed = arenaMap.remove(name);
        if (removed != null) {
            removed.clear();
        }
    }

    /**
     * Clear all arenas
     */
    public void clearAllArenas() {
        arenaMap.values().forEach(arena -> {
            if (arena == null) return;
            arena.clear();
        });
        arenaMap.clear();
    }

    /**
     * Create an arena
     *
     * @param name             the name of the arena
     * @param arenaClass       the class of the arena
     * @param onCreateConsumer the consumer that will be called when the arena is created
     * @param <T>              the type of the arena
     * @return the arena or empty if it cannot be created
     */
    public <T extends Arena> Optional<T> createArena(String name, Class<T> arenaClass, Consumer<T> onCreateConsumer) {
        T arena = null;

        try {
            Constructor<T> constructor = arenaClass.getConstructor(String.class, this.getClass());
            arena = constructor.newInstance(name, this);
        } catch (Exception ignored) {
            // IGNORED
        }

        try {
            Constructor<T> constructor = arenaClass.getConstructor(String.class, FeatureUnit.class);
            arena = constructor.newInstance(name, this);
        } catch (Exception ignored) {
            // IGNORED
        }

        if (arena == null) {
            try {
                Constructor<T> constructor = arenaClass.getConstructor(String.class);
                arena = constructor.newInstance(name);
            } catch (Exception ignored) {
                // IGNORED
            }
        }

        if (arena == null) return Optional.empty();

        onCreateConsumer.accept(arena);

        if (addArena(arena)) {
            return Optional.of(arena);
        } else {
            return Optional.empty();
        }
    }
}
