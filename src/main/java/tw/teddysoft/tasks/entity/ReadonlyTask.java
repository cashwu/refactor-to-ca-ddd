package tw.teddysoft.tasks.entity;

public class ReadonlyTask extends Task{


    public ReadonlyTask(Task task) {
        super(task.getId(), task.getDescription(), task.isDone());
    }

    @Override
    public void setDone(boolean done) {
        throw new UnsupportedOperationException("cannot modify read only task");
    }
}
