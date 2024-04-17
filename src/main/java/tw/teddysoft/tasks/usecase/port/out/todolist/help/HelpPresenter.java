package tw.teddysoft.tasks.usecase.port.out.todolist.help;

import tw.teddysoft.tasks.usecase.port.in.todolist.help.HelpDto;

public interface HelpPresenter {

    void present(HelpDto helpDto);
}