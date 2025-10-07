package io.github.projectunified.minigamecore.editor;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class ListEditor<A, T> implements Editor<A, List<T>> {
    private final List<T> list = new ArrayList<>();
    private final Map<String, EditorAction<A>> actionMap;

    public ListEditor() {
        this.actionMap = new HashMap<>();
        this.actionMap.put("add", new EditorAction<A>() {
            @Override
            public boolean execute(A actor, String[] args) {
                T value = create(actor, args);
                if (value == null) {
                    sendMessage(actor, "Cannot create (" + Arrays.toString(args) + ")");
                    return false;
                }
                list.add(value);
                sendMessage(actor, "Added (" + Arrays.toString(args) + ") at index " + (list.size() - 1));
                return true;
            }

            @Override
            public Collection<String> complete(A actor, String[] args) {
                return createComplete(actor, args);
            }

            @Override
            public String description() {
                return "Add element to list";
            }

            @Override
            public String usage() {
                return createUsage();
            }
        });
        this.actionMap.put("edit", new EditorAction<A>() {
            @Override
            public boolean execute(A actor, String[] args) {
                if (args.length < 1) {
                    return false;
                }
                int index;
                try {
                    index = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    sendMessage(actor, "Invalid index: " + args[0]);
                    return false;
                }
                if (index < 0 || index >= list.size()) {
                    sendMessage(actor, "Index out of bounds: " + index);
                    return false;
                }
                T value = list.get(index);
                T edited = edit(value, actor, Arrays.copyOfRange(args, 1, args.length));
                if (edited == null) {
                    sendMessage(actor, "Cannot edit (" + Arrays.toString(args) + ")");
                    return false;
                }
                if (edited != value) {
                    list.set(index, edited);
                }
                sendMessage(actor, "Edited (" + Arrays.toString(args) + ")");
                return true;
            }

            @Override
            public Collection<String> complete(A actor, String[] args) {
                if (args.length < 1) {
                    return Collections.emptyList();
                }
                if (args.length == 1) {
                    return IntStream.range(0, list.size())
                            .mapToObj(String::valueOf)
                            .collect(Collectors.toList());
                }
                int index;
                try {
                    index = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    return Collections.emptyList();
                }
                if (index < 0 || index >= list.size()) {
                    return Collections.emptyList();
                }
                T value = list.get(index);
                return editComplete(value, actor, Arrays.copyOfRange(args, 1, args.length));
            }

            @Override
            public String description() {
                return "Edit element in the list";
            }

            @Override
            public String usage() {
                return "<index> " + editUsage();
            }
        });
        this.actionMap.put("remove", new EditorAction<A>() {
            @Override
            public boolean execute(A actor, String[] args) {
                if (args.length < 1) {
                    return false;
                }
                int index;
                try {
                    index = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    sendMessage(actor, "Invalid index: " + args[0]);
                    return false;
                }
                if (index < 0 || index >= list.size()) {
                    sendMessage(actor, "Index out of bounds: " + index);
                    return false;
                }
                list.remove(index);
                sendMessage(actor, "Removed element at index " + index);
                return true;
            }

            @Override
            public Collection<String> complete(A actor, String[] args) {
                if (args.length == 1) {
                    return IntStream.range(0, list.size())
                            .mapToObj(String::valueOf)
                            .collect(Collectors.toList());
                }
                return Collections.emptyList();
            }

            @Override
            public String description() {
                return "Remove element at index";
            }

            @Override
            public String usage() {
                return "<index>";
            }
        });
        this.actionMap.put("move", new EditorAction<A>() {
            @Override
            public boolean execute(A actor, String[] args) {
                if (args.length < 2) {
                    return false;
                }

                int index;
                try {
                    index = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    sendMessage(actor, "Invalid index: " + args[0]);
                    return false;
                }

                int newIndex;
                try {
                    newIndex = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    sendMessage(actor, "Invalid new index: " + args[1]);
                    return false;
                }

                if (index < 0 || index >= list.size()) {
                    sendMessage(actor, "Index out of bounds: " + index);
                    return false;
                }

                if (newIndex < 0 || newIndex >= list.size()) {
                    sendMessage(actor, "New index out of bounds: " + newIndex);
                    return false;
                }

                T value = list.get(index);
                list.remove(index);
                list.add(newIndex, value);
                sendMessage(actor, "Moved element at index " + index + " to index " + newIndex);
                return true;
            }

            @Override
            public Collection<String> complete(A actor, String[] args) {
                if (args.length == 1 || args.length == 2) {
                    return IntStream.range(0, list.size())
                            .mapToObj(String::valueOf)
                            .collect(Collectors.toList());
                }
                return Collections.emptyList();
            }

            @Override
            public String description() {
                return "Move element to a new index";
            }

            @Override
            public String usage() {
                return "<index> <newIndex>";
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
        this.list.clear();
    }

    @Override
    public Object status() {
        return list;
    }

    @Override
    public Optional<List<T>> export(A actor) {
        return Optional.of(this.list);
    }

    public List<T> list() {
        return this.list;
    }

    @Override
    public void migrate(List<T> data) {
        this.list.addAll(data);
    }
}
