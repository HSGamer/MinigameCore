package io.github.projectunified.minigamecore.editor;

import java.util.*;

public abstract class ValueEditor<A, T> implements Editor<A, T> {
    private final Map<String, EditorAction<A>> actionMap;
    private T value;

    protected ValueEditor() {
        this.actionMap = new HashMap<>();
        this.actionMap.put("set", new EditorAction<A>() {
            @Override
            public boolean execute(A actor, String[] args) {
                T value = create(actor, args);
                if (value == null) {
                    sendMessage(actor, "Cannot create (" + Arrays.toString(args) + ")");
                    return false;
                }
                ValueEditor.this.value = value;
                sendMessage(actor, "Set (" + Arrays.toString(args) + ")");
                return true;
            }

            @Override
            public String description() {
                return "Set the value";
            }

            @Override
            public String usage() {
                return createUsage();
            }

            @Override
            public Collection<String> complete(A actor, String[] args) {
                return createComplete(actor, args);
            }
        });
        this.actionMap.put("edit", new EditorAction<A>() {
            @Override
            public boolean execute(A actor, String[] args) {
                if (value == null) {
                    return false;
                }
                T edited = edit(value, actor, args);
                if (edited == null) {
                    sendMessage(actor, "Cannot edit (" + Arrays.toString(args) + ")");
                    return false;
                }
                if (edited != value) {
                    value = edited;
                }
                sendMessage(actor, "Edited (" + Arrays.toString(args) + ")");
                return true;
            }

            @Override
            public String description() {
                return "Edit the value";
            }

            @Override
            public String usage() {
                return editUsage();
            }

            @Override
            public Collection<String> complete(A actor, String[] args) {
                if (value == null) return Collections.emptyList();
                return editComplete(value, actor, args);
            }
        });
    }

    protected abstract void sendMessage(A actor, String message);

    protected abstract T create(A actor, String[] args);

    protected abstract Collection<String> createComplete(A actor, String[] args);

    protected String createUsage() {
        return "[args...]";
    }

    protected abstract T edit(T data, A actor, String[] args);

    protected abstract Collection<String> editComplete(T data, A actor, String[] args);

    protected String editUsage() {
        return "[args...]";
    }

    @Override
    public Map<String, EditorAction<A>> actions() {
        return actionMap;
    }

    @Override
    public void reset() {
        value = null;
    }

    @Override
    public Object status() {
        return value == null ? "null" : value;
    }

    @Override
    public Optional<T> export(A actor) {
        return Optional.ofNullable(value);
    }

    @Override
    public void migrate(T data) {
        this.value = data;
    }
}
