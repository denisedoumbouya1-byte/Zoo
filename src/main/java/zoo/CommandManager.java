package zoo;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.logging.Logger;

public class CommandManager<T> {

    private static final Logger LOGGER =
            Logger.getLogger(CommandManager.class.getName());

    private final Deque<Command<T>> undoStack = new ArrayDeque<>();
    private final Deque<Command<T>> redoStack = new ArrayDeque<>();

    public void executeCommand(Command<T> command, T target) {
        LOGGER.info("executeCommand gestartet: " + command.description());

        Result<ZooError, String> result = command.execute(target);

        if (result instanceof Result.Ok<ZooError, String> ok) {
            LOGGER.info(ok.value());
            undoStack.push(command);
            redoStack.clear();
        } else if (result instanceof Result.Err<ZooError, String> err) {
            LOGGER.warning("Fehler: " + err.error());
        }

        LOGGER.info("Zustand: " + target);
    }

    public void undo(T target) {
        if (undoStack.isEmpty()) {
            LOGGER.warning("Kein Undo möglich.");
            return;
        }

        Command<T> command = undoStack.pop();
        Result<ZooError, String> result = command.undo(target);

        if (result instanceof Result.Ok<ZooError, String> ok) {
            LOGGER.info(ok.value());
            redoStack.push(command);
        } else if (result instanceof Result.Err<ZooError, String> err) {
            LOGGER.warning("Undo-Fehler: " + err.error());
        }

        LOGGER.info("Zustand: " + target);
    }

    public void redo(T target) {
        if (redoStack.isEmpty()) {
            LOGGER.warning("Kein Redo möglich.");
            return;
        }

        Command<T> command = redoStack.pop();
        Result<ZooError, String> result = command.execute(target);

        if (result instanceof Result.Ok<ZooError, String> ok) {
            LOGGER.info(ok.value());
            undoStack.push(command);
        } else if (result instanceof Result.Err<ZooError, String> err) {
            LOGGER.warning("Redo-Fehler: " + err.error());
        }

        LOGGER.info("Zustand: " + target);
    }
}
