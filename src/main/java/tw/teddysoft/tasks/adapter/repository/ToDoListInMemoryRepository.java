package tw.teddysoft.tasks.adapter.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import tw.teddysoft.tasks.entity.ToDoList;
import tw.teddysoft.tasks.entity.ToDoListId;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;

public class ToDoListInMemoryRepository implements ToDoListRepository {

    private final List<ToDoList> store;

    public ToDoListInMemoryRepository() {
        this.store = new ArrayList<>();
    }

    @Override
    public void save(ToDoList toDoList) {
        store.removeIf(x -> x.getId().equals(toDoList.getId()));
        store.add(toDoList);
    }

    @Override
    public void delete(ToDoList toDoList) {
        store.removeIf(x -> x.getId().equals(toDoList.getId()));
    }

    @Override
    public Optional<ToDoList> findById(ToDoListId toDoListId) {
        return store.stream().filter(x -> x.getId().equals(toDoListId)).findFirst();
    }
}
