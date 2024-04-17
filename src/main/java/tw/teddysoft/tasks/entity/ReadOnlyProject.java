package tw.teddysoft.tasks.entity;

import java.util.List;

public class ReadOnlyProject extends Project {

    private Project real;

    public ReadOnlyProject(Project real) {
        super(real.getName(), real.getTasks());
        this.real = real;
    }

    @Override
    public List<Task> getTasks() {
        return real.getTasks().stream().map(task -> (Task) new ReadOnlyTask(task)).toList();
    }

    @Override
    public void setTaskDone(TaskId taskId, boolean done){
        throw new UnsupportedOperationException("Read only");
    }

    @Override
    public void addTask(Task task){
        throw new UnsupportedOperationException("Read only");
    }
}
