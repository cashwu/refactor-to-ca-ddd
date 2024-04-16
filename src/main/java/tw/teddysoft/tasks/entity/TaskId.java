package tw.teddysoft.tasks.entity;

import tw.teddysoft.ezddd.core.entity.ValueObject;

public record TaskId(String value) implements ValueObject {
    public static TaskId of(long id) {
        return new TaskId(String.valueOf(id));
    }

    public static TaskId of(String id) {
        return new TaskId(id);
    }

    @Override
    public String toString(){
        return value;
    }
}
