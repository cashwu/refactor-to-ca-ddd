package tw.teddysoft.tasks.adapter.in.controller.console;

import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.io.standard.ToDoListApp;
import tw.teddysoft.tasks.usecase.port.in.task.deadline.DeadlineInput;
import tw.teddysoft.tasks.usecase.port.in.task.deadline.DeadlineUseCase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import static tw.teddysoft.ucontract.Contract.require;

public class DeadlineConsoleController implements ConsoleController {
    private final DeadlineUseCase deadlineUseCase;

    public DeadlineConsoleController(DeadlineUseCase deadlineUseCase) {
        this.deadlineUseCase = deadlineUseCase;
    }

    @Override
    public Response<CqrsOutput> execute(Request request) {
        require("two arguments", () -> request.getArgs().size() >= 2);
        require("argument 2 is a correct date", () -> parseDate(request.getArg(1)).isPresent());

        DeadlineInput deadlineInput = new DeadlineInput();
        deadlineInput.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
        deadlineInput.taskId = request.getArg(0);
        deadlineInput.deadline = parseDate(request.getArg(1)).get().atStartOfDay();
        return Response.of(deadlineUseCase.execute(deadlineInput));
    }

    public static Optional<LocalDate> parseDate(String dateString) {
        DateTimeFormatter[] formatters = {
                DateTimeFormatter.ofPattern("yyyy-M-d"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("yyyy-M-dd"),
                DateTimeFormatter.ofPattern("yyyy-MM-d")
        };

        for (DateTimeFormatter formatter : formatters) {
            try {
                var date = LocalDate.parse(dateString, formatter);
                return Optional.of(date);
            } catch (DateTimeParseException e) {
                // ignore
            }
        }
        return Optional.empty();
    }
}
