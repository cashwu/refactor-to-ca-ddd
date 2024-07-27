package tw.teddysoft.tasks.entities;

import tw.teddysoft.ezddd.core.entity.ValueObject;

public record ToDoListId(String value) implements ValueObject {

    public static ToDoListId of(int id) {
        return new ToDoListId(String.valueOf(id));
    }
}
