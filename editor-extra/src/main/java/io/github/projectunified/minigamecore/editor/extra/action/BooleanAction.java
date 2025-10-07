package io.github.projectunified.minigamecore.editor.extra.action;

import io.github.projectunified.minigamecore.editor.EditorAction;
import io.github.projectunified.minigamecore.editor.EditorActor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The {@link EditorAction} for boolean value
 */
public abstract class BooleanAction implements EditorAction {
    @Override
    public String usage() {
        return "<true|false>";
    }

    @Override
    public List<String> complete(EditorActor actor, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("true", "false");
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
    public abstract boolean execute(EditorActor actor, boolean value, String[] args);

    @Override
    public boolean execute(EditorActor actor, String[] args) {
        if (args.length < 1) {
            return actor.sendUsage(this);
        }
        return execute(actor, Boolean.parseBoolean(args[0]), Arrays.copyOfRange(args, 1, args.length));
    }
}
