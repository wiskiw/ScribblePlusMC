package me.chrr.scribble.tool.commandmanager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Class for commands that support undo/redo functionality.
 *
 * <p>This class encapsulates the logic for creating a memento before command execution and restoring
 * the state from the memento when the command is undone.</p>
 *
 * @param <T> The type of the memento used to capture the state of the restorable object.
 */
public abstract class RestorableCommand<T> implements Command {

    @NotNull
    private final Restorable<T> restorable;

    @Nullable
    private T memento;

    /**
     * Constructs a new RestorableCommand instance.
     *
     * @param restorable The restorable object to be managed by this command.
     */
    protected RestorableCommand(@NotNull Restorable<T> restorable) {
        this.restorable = restorable;
    }

    /**
     * Executes the command.
     * <p>
     * If the command is being executed for the first time,
     * it creates and stores a memento of the current state.
     * <p>
     * If the command is being executed again (e.g., as part of a redo operation),
     * it restores the state from the previously saved memento to ensure
     * that the {@link #restorable} is in the exact state it was before the original execution.
     */
    @Override
    public void execute() {
        if (memento == null) {
            // Create a memento of the current state if this is the first execution
            memento = restorable.scribble$createMemento();
        } else {
            // Restore the first/original state from the saved memento for redo operations
            restorable.scribble$restore(memento);
        }
        doo();
    }

    /**
     * Does the command action.
     */
    public abstract void doo();

    /**
     * Undoes the command by restoring the state from the previously created memento.
     * <p>
     * Do nothing if nothing to undo.
     *
     * @return true if the state that was stored before the last execution was restore, otherwise false
     */
    @Override
    public boolean undo() {
        if (memento != null) {
            restorable.scribble$restore(memento);
            return true;
        } else {
            return false;
        }
    }
}

