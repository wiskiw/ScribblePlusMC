package me.chrr.scribble.tool.commandmanager;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class CommandManagerTest {

    @Test
    public void testIfCantUndoIfCommandHistoryIsEmpty() {
        CommandManager commandManager = new CommandManager();

        // Assert
        assertFalse(commandManager.canUndo());
    }

    @Test
    public void testThatCantRedoIfCommandHistoryIsEmpty() {
        CommandManager commandManager = new CommandManager();

        // Assert
        assertFalse(commandManager.canRedo());
    }

    @Test
    public void testIfTryUndoReturnsFalseIfCommandHistoryIsEmpty() {
        CommandManager commandManager = new CommandManager();

        // Assert
        assertFalse(commandManager.tryUndo());
    }

    @Test
    public void testIfTryRedoReturnsFalseIfCommandHistoryIsEmpty() {
        CommandManager commandManager = new CommandManager();

        // Assert
        assertFalse(commandManager.tryRedo());
    }

    @Test
    public void testIfCommandAddedToCommandHistoryWhenExecuted() {
        CommandManager commandManager = new CommandManager();
        assertFalse(commandManager.canUndo(), "Unexpected commandManager state");
        Command doNothingCommand = mock();

        // Action
        commandManager.execute(doNothingCommand);

        // Assert
        assertTrue(commandManager.canUndo());
    }

    @Test
    public void testIfCantUndoAnymoreWhenAllCommandsWereUndo() {
        CommandManager commandManager = new CommandManager();
        Command doNothingCommand = mock();


        // Action
        commandManager.execute(doNothingCommand);
        assertTrue(commandManager.tryUndo(), "Unexpected commandManager state");


        // Assert
        assertFalse(commandManager.tryUndo());

        // command's undo should be called only once - on .tryUndo() call
        verify(doNothingCommand, times(1)).undo();
    }

    @Test
    public void testIfDropsFirstExecutedCommandWhenCommandHistoryOverflowed() {
        // Arrange
        CommandManager commandManager = new CommandManager(3);

        // execute 3 commands to fill up the stack
        Command commandToBeDroppedFirst = mock("commandToBeDroppedFirst");
        commandManager.execute(commandToBeDroppedFirst);

        Command commandThatWillStay1 = mock("commandThatWillStay1");
        commandManager.execute(commandThatWillStay1);

        Command commandThatWillStay2 = mock("commandThatWillStay2");
        commandManager.execute(commandThatWillStay2);


        // Action
        Command commandThatWillReachTheStackLimit = mock("commandThatWillReachTheStackLimit");
        commandManager.execute(commandThatWillReachTheStackLimit);
        // call undo for all commands in stack
        assertTrue(commandManager.tryUndo());
        assertTrue(commandManager.tryUndo());
        assertTrue(commandManager.tryUndo());


        // Assert
        // ensure command stack is empty
        assertFalse(commandManager.canUndo());

        // undo - should not be called for the first added command
        verify(commandToBeDroppedFirst, never()).undo();

        // ... and should be called for the reset
        InOrder inOrder = inOrder(commandThatWillStay2, commandThatWillStay1);
        inOrder.verify(commandThatWillStay2, times(1)).undo();
        inOrder.verify(commandThatWillStay1, times(1)).undo();
        verify(commandThatWillReachTheStackLimit, times(1)).undo();
    }

    @Test
    public void testIfUndoCalledOnlyForTheLastExecutedCommand() {
        // Arrange
        CommandManager commandManager = new CommandManager();

        Command commandToChill = mock("commandToChill");
        commandManager.execute(commandToChill);

        Command commandToUndo = mock("commandToUndo");
        commandManager.execute(commandToUndo);

        // Action
        commandManager.tryUndo();

        // Assert
        verify(commandToUndo, times(1)).undo();
        verify(commandToChill, never()).undo();
        assertTrue(commandManager.canUndo()); // ensure it's still possible to undo commandToChill
    }

    @Test
    public void testIfThereAreNothingToRedoWhenNewCommandExecutedAfterUndo() {
        // Arrange
        CommandManager commandManager = new CommandManager();

        Command commandToStay1 = mock("commandToStay1");
        commandManager.execute(commandToStay1);

        Command commandToStay2 = mock("commandToStay2");
        commandManager.execute(commandToStay2);

        Command commandToBeRemoved1 = mock("commandToBeRemoved1");
        commandManager.execute(commandToBeRemoved1);

        Command commandToBeRemoved2 = mock("commandToBeRemoved2");
        commandManager.execute(commandToBeRemoved2);

        // Action
        commandManager.tryUndo();
        commandManager.tryUndo();

        Command commandToClearRedoCommands = mock("commandToClearRedoCommands");
        commandManager.execute(commandToClearRedoCommands);


        // Assert
        verify(commandToStay1, never()).undo();
        verify(commandToStay2, never()).undo();

        InOrder inOrder = inOrder(commandToBeRemoved2, commandToBeRemoved1);
        inOrder.verify(commandToBeRemoved2).undo();
        inOrder.verify(commandToBeRemoved1).undo();

        verify(commandToBeRemoved2, times(1)).undo();
        verify(commandToBeRemoved1, times(1)).undo();

        assertFalse(commandManager.canRedo());
    }

    @Test
    public void testIfCanExecuteCommandWhenMaxHistorySizeIsZero() {
        // Arrange
        CommandManager commandManager = new CommandManager(0);
        Command command = mock();

        // Action
        commandManager.execute(command);

        // Assert
        verify(command, times(1)).execute();
    }
}
