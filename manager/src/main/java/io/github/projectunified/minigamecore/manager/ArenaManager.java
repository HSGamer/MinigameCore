package io.github.projectunified.minigamecore.manager;

import io.github.projectunified.minigamecore.base.Arena;
import io.github.projectunified.minigamecore.base.FeatureUnit;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * The manager that handles all arenas
 *
 * @param <T> the type of the identifier of the arena
 * @param <A> the type of the arena
 */
public abstract class ArenaManager<T, A extends Arena & ManagedArena<T>> extends FeatureUnit {
    private final Map<T, A> arenaMap = new HashMap<>();

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
     * Check if the arena manager contains the arena
     *
     * @param identifier the identifier
     * @return true if it does
     */
    public boolean containsArena(T identifier) {
        return arenaMap.containsKey(identifier);
    }

    /**
     * Get the arena by its identifier
     *
     * @param identifier the identifier
     * @return the arena
     */
    public Optional<A> getArena(T identifier) {
        return Optional.ofNullable(arenaMap.get(identifier));
    }

    /**
     * Get the arena map
     *
     * @return the arena map
     */
    public Map<T, A> getArenaMap() {
        return Collections.unmodifiableMap(arenaMap);
    }

    /**
     * Get all arenas
     *
     * @return the collection of arenas
     */
    public Collection<A> getAllArenas() {
        return Collections.unmodifiableCollection(arenaMap.values());
    }

    /**
     * Add an arena
     *
     * @param arena the arena
     */
    public boolean addArena(A arena) {
        T identifier = arena.getIdentifier();

        if (arenaMap.containsKey(identifier)) return false;

        if (!arena.isValid()) return false;
        arena.init();

        arenaMap.put(identifier, arena);
        return true;
    }

    /**
     * Remove an arena
     *
     * @param identifier the identifier
     */
    public void removeArena(T identifier) {
        Arena removed = arenaMap.remove(identifier);
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
     * @param identifier       the identifier
     * @param arenaCreator     the arena creator
     * @param onCreateConsumer the consumer that will be called when the arena is created
     * @return the created arena
     */
    public Optional<A> createArena(T identifier, Function<T, A> arenaCreator, Consumer<A> onCreateConsumer) {
        if (containsArena(identifier)) return Optional.empty();

        A arena = arenaCreator.apply(identifier);
        if (arena == null) return Optional.empty();

        onCreateConsumer.accept(arena);

        if (addArena(arena)) {
            return Optional.of(arena);
        } else {
            return Optional.empty();
        }
    }
}
