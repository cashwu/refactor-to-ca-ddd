package tw.teddysoft.tasks.entity;

import tw.teddysoft.ezddd.core.entity.ValueObject;

public record TodoListId(String id) implements ValueObject {

    public static TodoListId of(int id) {
        return new TodoListId(String.valueOf(id));
    }
}
