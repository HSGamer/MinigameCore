package io.github.projectunified.minigamecore.editor;

import java.lang.reflect.Array;
import java.util.*;

/**
 * The helper class to handle editor status
 *
 * @param <B> the builder
 * @param <S> the section
 * @param <T> the object that is built from the builder
 */
public abstract class EditorStatusDisplay<B, S, T> {
    /**
     * Create a new builder
     *
     * @return the builder
     */
    protected abstract B newBuilder();

    /**
     * Create a new section
     *
     * @param builder the builder
     * @param level   the section level
     * @return the section
     */
    protected abstract S newSection(B builder, int level);

    /**
     * Add key to the section
     *
     * @param section        the section
     * @param key            the key
     * @param fromCollection whether the key is from a collection
     */
    protected abstract void appendKey(S section, String key, boolean fromCollection);

    /**
     * Add size to the section
     *
     * @param section the section
     * @param size    the size
     */
    protected abstract void appendSize(S section, int size);

    /**
     * Add value to the section
     *
     * @param section the section
     * @param value   the value
     * @param editor  the current editor
     */
    protected abstract void appendValue(S section, Object value, Editor<?> editor);

    /**
     * Add section to the builder
     *
     * @param section the section
     * @param builder the builder
     * @return the new builder
     */
    protected abstract B addToBuilder(S section, B builder);

    /**
     * Build the final object from the builder
     *
     * @param builder the builder
     * @return the object
     */
    protected abstract T build(B builder);

    /**
     * Preprocess the editor value
     *
     * @param value  the value
     * @param editor the editor
     * @return the preprocessed object
     */
    protected Object preprocessValue(Object value, Editor<?> editor) {
        while (value instanceof Editor<?>) {
            Editor<?> subEditor = (Editor<?>) value;
            value = subEditor.status();
        }
        return value;
    }

    /**
     * Display the editor by converting the editor status to an object
     *
     * @param editor the editor
     * @return the status object
     */
    public T display(Editor<?> editor) {
        B builder = newBuilder();
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

            S section = newSection(builder, level);
            
            appendKey(section, entry.key(), entry.fromCollection());
            if (value instanceof Collection<?>) {
                Collection<?> collection = (Collection<?>) value;
                appendSize(section, collection.size());
                List<Object> copyList = new ArrayList<>(collection);
                for (int i = copyList.size() - 1; i >= 0; i--) {
                    Object o = copyList.get(i);
                    deque.addFirst(new Entry(level + 1, Integer.toString(i), o, true));
                }
            } else if (value.getClass().isArray()) {
                int length = Array.getLength(value);
                appendSize(section, length);
                for (int i = length - 1; i >= 0; i--) {
                    Object o = Array.get(value, i);
                    deque.addFirst(new Entry(level + 1, Integer.toString(i), o, true));
                }
            } else if (value instanceof Map<?, ?>) {
                Map<?, ?> subMap = (Map<?, ?>) value;
                appendSize(section, subMap.size());
                List<Map.Entry<?, ?>> copyList = new ArrayList<>(subMap.entrySet());
                for (int i = subMap.size() - 1; i >= 0; i--) {
                    Map.Entry<?, ?> o = copyList.get(i);
                    deque.addFirst(new Entry(level + 1, Objects.toString(o.getKey()), o.getValue(), false));
                }
            } else if (value instanceof Map.Entry<?, ?>) {
                Map.Entry<?, ?> subEntry = (Map.Entry<?, ?>) value;
                deque.addFirst(new Entry(level + 1, Objects.toString(subEntry.getKey()), subEntry.getValue(), false));
            } else {
                appendValue(section, value, editor);
            }
            builder = addToBuilder(section, builder);
        }
        return build(builder);
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
    }
}
