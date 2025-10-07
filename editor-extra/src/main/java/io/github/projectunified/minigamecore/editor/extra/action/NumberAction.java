package io.github.projectunified.minigamecore.editor.extra.action;

import io.github.projectunified.minigamecore.editor.EditorAction;
import io.github.projectunified.minigamecore.editor.EditorActor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * The {@link EditorAction} for number value
 */
public abstract class NumberAction implements EditorAction {
    @Override
    public String usage() {
        return "<value>";
    }

    @Override
    public List<String> complete(EditorActor actor, String[] args) {
        if (args.length == 1) {
            return valueComplete(actor).map(Objects::toString).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * Execute the action
     *
     * @param actor the actor
     * @param value the value
     * @param args  the arguments
     * @return true if the action is executed successfully
     */
    public abstract boolean execute(EditorActor actor, Number value, String[] args);

    /**
     * Get number suggestions
     *
     * @param actor the actor
     * @return the suggestions
     */
    public Stream<? extends Number> valueComplete(EditorActor actor) {
        return IntStream.range(0, 10).boxed();
    }

    @Override
    public boolean execute(EditorActor actor, String[] args) {
        if (args.length < 1) {
            return actor.sendUsage(this);
        }
        String value = args[0];
        try {
            return execute(actor, Double.parseDouble(value), Arrays.copyOfRange(args, 1, args.length));
        } catch (NumberFormatException e) {
            actor.sendMessage("Invalid Number: " + value, false);
            return false;
        }
    }
}
