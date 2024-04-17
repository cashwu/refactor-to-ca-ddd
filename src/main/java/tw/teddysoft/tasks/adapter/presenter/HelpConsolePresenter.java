package tw.teddysoft.tasks.adapter.presenter;

import java.io.PrintWriter;

import tw.teddysoft.tasks.usecase.port.in.todolist.help.HelpDto;
import tw.teddysoft.tasks.usecase.port.out.todolist.help.HelpPresenter;

public class HelpConsolePresenter implements HelpPresenter {

    private final PrintWriter out;

    public HelpConsolePresenter(PrintWriter out) {
        this.out = out;
    }

    @Override
    public void present(HelpDto helpDto) {
        out.println(helpDto.heading);
        for(var command : helpDto.commands)
            out.printf("  %s%n", command);
        out.println();
    }
}
