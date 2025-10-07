package io.github.projectunified.minigamecore.editor;

import java.util.Collection;
import java.util.Collections;

public interface EditorAction<A> {
    boolean execute(A actor, String[] args);

    String description();

    default Collection<String> complete(A actor, String[] args) {
        return Collections.emptyList();
    }

    default String usage() {
        return "";
    }

    default boolean requiresArgs() {
        return !usage().isEmpty();
    }
}
