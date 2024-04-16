package tw.teddysoft.tasks.entity;

import tw.teddysoft.ezddd.core.entity.ValueObject;

public record ToDoListId(String value) implements ValueObject {
    public static ToDoListId of(String id) {
        return new ToDoListId(id);
    }
}
