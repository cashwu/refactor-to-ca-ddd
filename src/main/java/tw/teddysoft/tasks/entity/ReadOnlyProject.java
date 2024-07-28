package tw.teddysoft.tasks.entity;

import java.util.List;

public class ReadOnlyProject extends Project {

    public ReadOnlyProject(Project project) {
        super(project.getName(), project.getTasks());
    }

    @Override
    public List<Task> getTasks() {
        return super.getTasks().stream()
                .map(a -> (Task)new ReadonlyTask(a))
                .toList();
    }
}