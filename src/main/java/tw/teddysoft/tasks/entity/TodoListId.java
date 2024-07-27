package tw.teddysoft.tasks.entity;

import tw.teddysoft.ezddd.core.entity.ValueObject;

public record TodoListId(String value) implements ValueObject {

    public static TodoListId of(String id) {
        return new TodoListId(id);
    }
}
