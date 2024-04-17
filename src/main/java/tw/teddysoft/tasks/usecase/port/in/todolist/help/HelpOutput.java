package tw.teddysoft.tasks.usecase.port.in.todolist.help;

import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;

public class HelpOutput extends CqrsOutput<HelpOutput> {

    public HelpDto helpDto;

    public static HelpOutput create(){
        return new HelpOutput();
    }

}
