package tw.teddysoft.tasks.usecase.service;

import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.usecase.port.in.todolist.error.ErrorInput;
import tw.teddysoft.tasks.usecase.port.in.todolist.error.ErrorUseCase;

import static java.lang.String.format;

public class ErrorService implements ErrorUseCase {

    @Override
    public CqrsOutput execute(ErrorInput input) {
        StringBuilder sb = new StringBuilder();
        sb.append(format("I don't know what the command \"%s\" is.", input.command));
        sb.append("\n");
        return  CqrsOutput.create().fail().setMessage(sb.toString());
    }
}
