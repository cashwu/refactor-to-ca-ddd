package tw.teddysoft.tasks.usecase;

import tw.teddysoft.ezddd.core.usecase.Repository;
import tw.teddysoft.tasks.entity.TodoList;
import tw.teddysoft.tasks.entity.TodoListId;

public interface TodoListRepository extends Repository<TodoList, TodoListId> {
}
