package me.chrr.scribble.tool.statemanager;


import me.chrr.scribble.tool.commandmanager.Restorable;

import java.util.LinkedList;

public class MementoStateManager<T> {

    private static final int EMPTY_STACK_INDEX = -1;

    private final Restorable<T> restorable;
    private final int maxHistorySize;
    private final LinkedList<T> stateStack;

    // fixme Index of the last executed command, or -1 if no commands have been executed.
    private int currentStateIndex = EMPTY_STACK_INDEX;

    public MementoStateManager(Restorable<T> restorable, int maxHistorySize) {
        this.restorable = restorable;
        this.maxHistorySize = maxHistorySize;
        this.stateStack = new LinkedList<>();
    }

    public void clear() {
        currentStateIndex = EMPTY_STACK_INDEX;
        stateStack.clear();
    }

    public void saveState() {
        // fixme dropAllAboveCurrentIndex is needed for cases like:
        // execute A - currentIndex at A
        // execute B - currentIndex at B
        // execute C - currentIndex at C
        // undo - moves currentIndex to B
        // undo - moves currentIndex to A
        // execute D - will drop all commands that were above currentIndex(above A)
        dropAllAboveCurrentIndex();

        if (stateStack.size() >= maxHistorySize) {
            // size limit was reached
            // drop a command from the bottom of the stack
            stateStack.pollFirst();
            // also more the current index by one
            currentStateIndex--;
        }

        T newState = restorable.scribble$createMemento();
        stateStack.add(newState);
        currentStateIndex++;
    }

    /**
     * fixme Removes any commands that are above the current execution point in the history stack.
     */
    private void dropAllAboveCurrentIndex() {
        while (currentStateIndex < stateStack.size() - 1) {
            stateStack.pollLast();
        }
    }

    /**
     * fixme Attempts to undo the last executed command.
     *
     * @return True if the undo operation was successful, false otherwise.
     */
    public boolean tryUndo() {
        if (canUndo()) {
            currentStateIndex--;
            T previousState = stateStack.get(currentStateIndex);
            restorable.scribble$restore(previousState);
            return true;
        }
        return false;
    }

    /**
     * fixme Checks if an undo operation is possible.
     *
     * @return True if an undo operation is possible, false otherwise.
     */
    private boolean canUndo() {
        if (stateStack.isEmpty()) {
            currentStateIndex = EMPTY_STACK_INDEX;
            return false;
        } else {
            return currentStateIndex > 0;
        }
    }

    /**
     * fixme Attempts to redo the last undone command, reapplying its effects.
     *
     * @return True if the redo operation was successful, false otherwise.
     */
    public boolean tryRedo() {
        if (canRedo()) {
            currentStateIndex++;
            T nextState = stateStack.get(currentStateIndex);
            restorable.scribble$restore(nextState);
            return true;
        } else {
            return false;
        }
    }

    public boolean canRedo() {
        int lastAvailableIndexInStack = stateStack.size() - 1;
        return currentStateIndex < lastAvailableIndexInStack;
    }
}
