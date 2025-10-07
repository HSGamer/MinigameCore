package io.github.projectunified.minigamecore.editor;

/**
 * The actor that interacts the editor
 */
public interface EditorActor {
    /**
     * Send the message
     *
     * @param message the message
     * @param success true if it's a success message
     */
    void sendMessage(String message, boolean success);

    /**
     * Send the usage of the action
     *
     * @param action the action
     * @return true if the usage is sent successfully
     */
    boolean sendUsage(EditorAction action);

    /**
     * Send the usage of the editor
     *
     * @param editor the editor
     * @return true if the usage is sent successfully
     */
    boolean sendUsage(Editor<?> editor);
}
