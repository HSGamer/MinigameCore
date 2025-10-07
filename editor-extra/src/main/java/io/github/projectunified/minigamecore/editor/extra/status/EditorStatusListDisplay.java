package io.github.projectunified.minigamecore.editor.extra.status;

import java.util.ArrayList;
import java.util.List;

public abstract class EditorStatusListDisplay<S> extends EditorStatusDisplay<List<S>, S, List<S>> {
    @Override
    protected List<S> newBuilder() {
        return new ArrayList<>();
    }

    @Override
    protected List<S> addToBuilder(S section, List<S> builder) {
        builder.add(section);
        return builder;
    }

    @Override
    protected List<S> build(List<S> builder) {
        return builder;
    }
}
