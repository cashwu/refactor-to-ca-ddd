package tw.teddysoft.tasks.usecase.service;

import tw.teddysoft.ezddd.core.usecase.ExitCode;
import tw.teddysoft.ezddd.core.usecase.Input;
import tw.teddysoft.tasks.usecase.port.in.todolist.help.HelpDto;
import tw.teddysoft.tasks.usecase.port.in.todolist.help.HelpOutput;
import tw.teddysoft.tasks.usecase.port.in.todolist.help.HelpUseCase;
import tw.teddysoft.tasks.usecase.port.out.todolist.help.HelpPresenter;


public class HelpService implements HelpUseCase {

    private final HelpPresenter presenter;

    public HelpService(HelpPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public HelpOutput execute(Input.NullInput input) {

        HelpDto helpDto = new HelpDto();
        helpDto.heading = "Commands:";
        helpDto.commands.add("show");
        helpDto.commands.add("add project <project name>");
        helpDto.commands.add("add task <project name> <task description>");
        helpDto.commands.add("check <task ID>");
        helpDto.commands.add("uncheck <task ID>");
        presenter.present(helpDto);

        var output = HelpOutput.create();
        output.helpDto = helpDto;
        return output.setExitCode(ExitCode.SUCCESS).setMessage("");
    }
}
