package tw.teddysoft.tasks.entities;

public class ReadOnlyTask extends Task {
    public ReadOnlyTask(Task task) {
        super(task.getId(), task.getDescription(), task.isDone());
    }

    @Override
    public void setDone(boolean done) {
        throw new UnsupportedOperationException("cannot modify read-only task");
    }
}
