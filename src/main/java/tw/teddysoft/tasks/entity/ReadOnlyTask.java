package tw.teddysoft.tasks.entity;

public class ReadOnlyTask extends Task {
    public ReadOnlyTask(Task real) {
        super(real.getId(), real.getDescription(), real.isDone());
    }

    @Override
    public void setDone(boolean done) {
        throw new UnsupportedOperationException("Read only");
    }
}
