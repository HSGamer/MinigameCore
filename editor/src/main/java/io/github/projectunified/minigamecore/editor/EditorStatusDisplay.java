package io.github.projectunified.minigamecore.editor;

import java.lang.reflect.Array;
import java.util.*;

public abstract class EditorStatusDisplay<B, T> {
    protected abstract B newBuilder();

    protected abstract void appendPadding(B builder, int level);

    protected abstract void appendKey(B builder, String key, boolean fromCollection);

    protected abstract void appendSize(B builder, int size);

    protected abstract void appendValue(B builder, Object value, int level, Editor<?, ?> editor);

    protected abstract T build(B builder);

    protected Object preprocessValue(Object value, Editor<?, ?> editor) {
        return value;
    }

    public List<T> display(Editor<?, ?> editor) {
        List<T> list = new ArrayList<>();
        Deque<Entry> deque = new ArrayDeque<>();
        deque.addFirst(new Entry(0, "ROOT", editor, false));
        while (!deque.isEmpty()) {
            Entry entry = deque.removeFirst();
            int level = entry.level();
            Object value = entry.value();
            Object preprocessedValue = preprocessValue(value, editor);
            if (preprocessedValue != value) {
                deque.addFirst(new Entry(level, entry.key(), preprocessedValue, entry.fromCollection()));
                continue;
            }

            while (value instanceof Editor<?, ?>) {
                Editor<?, ?> subEditor = (Editor<?, ?>) value;
                value = subEditor.status();
            }

            B builder = newBuilder();
            appendPadding(builder, level);
            appendKey(builder, entry.key(), entry.fromCollection());
            if (value instanceof Collection<?>) {
                Collection<?> collection = (Collection<?>) value;
                appendSize(builder, collection.size());
                List<Object> copyList = new ArrayList<>(collection);
                for (int i = copyList.size() - 1; i >= 0; i--) {
                    Object o = copyList.get(i);
                    deque.addFirst(new Entry(level + 1, Integer.toString(i), o, true));
                }
            } else if (value.getClass().isArray()) {
                int length = Array.getLength(value);
                appendSize(builder, length);
                for (int i = length - 1; i >= 0; i--) {
                    Object o = Array.get(value, i);
                    deque.addFirst(new Entry(level + 1, Integer.toString(i), o, true));
                }
            } else if (value instanceof Map<?, ?>) {
                Map<?, ?> subMap = (Map<?, ?>) value;
                appendSize(builder, subMap.size());
                List<Map.Entry<?, ?>> copyList = new ArrayList<>(subMap.entrySet());
                for (int i = subMap.size() - 1; i >= 0; i--) {
                    Map.Entry<?, ?> o = copyList.get(i);
                    deque.addFirst(new Entry(level + 1, Objects.toString(o.getKey()), o.getValue(), false));
                }
            } else if (value instanceof Map.Entry<?, ?>) {
                Map.Entry<?, ?> subEntry = (Map.Entry<?, ?>) value;
                deque.addFirst(new Entry(level + 1, Objects.toString(subEntry.getKey()), subEntry.getValue(), false));
            } else {
                appendValue(builder, value, level, editor);
            }
            list.add(build(builder));
        }
        return list;
    }

    private static final class Entry {
        private final int level;
        private final String key;
        private final Object value;
        private final boolean fromCollection;

        private Entry(int level, String key, Object value, boolean fromCollection) {
            this.level = level;
            this.key = key;
            this.value = value;
            this.fromCollection = fromCollection;
        }

        public int level() {
            return level;
        }

        public String key() {
            return key;
        }

        public Object value() {
            return value;
        }

        public boolean fromCollection() {
            return fromCollection;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            Entry that = (Entry) obj;
            return this.level == that.level &&
                    Objects.equals(this.key, that.key) &&
                    Objects.equals(this.value, that.value) &&
                    this.fromCollection == that.fromCollection;
        }

        @Override
        public int hashCode() {
            return Objects.hash(level, key, value, fromCollection);
        }

        @Override
        public String toString() {
            return "Entry[" +
                    "level=" + level + ", " +
                    "key=" + key + ", " +
                    "value=" + value + ", " +
                    "fromCollection=" + fromCollection + ']';
        }
    }
}
