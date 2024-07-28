package tw.teddysoft.tasks.usecase;

import tw.teddysoft.ezddd.core.usecase.Input;
import tw.teddysoft.tasks.entity.ProjectName;

public class AddProjectInput implements Input {

    public String projectName;

    public String toDoListId;
}
