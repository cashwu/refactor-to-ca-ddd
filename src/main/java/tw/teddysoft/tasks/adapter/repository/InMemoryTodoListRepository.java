package tw.teddysoft.tasks.adapter.repository;

import tw.teddysoft.ezddd.core.usecase.RepositorySaveException;
import tw.teddysoft.tasks.entity.TodoList;
import tw.teddysoft.tasks.entity.TodoListId;
import tw.teddysoft.tasks.usecase.port.out.TodoListRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryTodoListRepository implements TodoListRepository {

    private final List<TodoList> store = new ArrayList<>();

    @Override
    public Optional<TodoList> findById(TodoListId todoListId) {
        return store.stream().filter(a -> a.getId().equals(todoListId)).findFirst();
//        return Optional.empty();
    }

    @Override
    public void save(TodoList data) throws RepositorySaveException {
        store.add(data);
    }

    @Override
    public void delete(TodoList data) {
        store.remove(data);
    }
}
