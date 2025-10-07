package io.github.projectunified.minigamecore.editor;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public interface Editor<A, T> extends EditorAction<A> {
    Map<String, EditorAction<A>> actions();

    void reset();

    Object status();

    Optional<T> export(A actor);

    void migrate(T data);

    default boolean sendActionUsage(A actor) {
        return false;
    }

    @Override
    default String description() {
        return getClass().getSimpleName();
    }

    @Override
    default String usage() {
        return "<action> [args]";
    }

    @Override
    default Collection<String> complete(A actor, String[] args) {
        if (args.length == 0) {
            return Collections.emptyList();
        }
        Map<String, EditorAction<A>> actions = actions();
        if (args.length == 1) {
            return actions.keySet();
        }
        String actionName = args[0];
        EditorAction<A> action = actions.get(actionName);
        if (action == null) {
            return Collections.emptyList();
        }
        String[] subArgs = new String[args.length - 1];
        System.arraycopy(args, 1, subArgs, 0, args.length - 1);
        return action.complete(actor, subArgs);
    }

    default boolean execute(A actor, String[] args) {
        if (args.length == 0) {
            return sendActionUsage(actor);
        }
        String command = args[0];
        EditorAction<A> action = actions().get(command);
        if (action == null) {
            return false;
        }
        String[] subArgs = new String[args.length - 1];
        System.arraycopy(args, 1, subArgs, 0, args.length - 1);
        return action.execute(actor, subArgs);
    }
}
