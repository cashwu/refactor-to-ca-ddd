package tw.teddysoft.tasks.adapter.repository;


import tw.teddysoft.tasks.usecase.port.out.ToDoListPo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ToDoListInMemoryRepositoryPeer implements ToDoListRepositoryPeer {

    private final List<ToDoListPo> store;

    public ToDoListInMemoryRepositoryPeer() {
        store = new ArrayList<>();
    }

    @Override
    public Optional<ToDoListPo> findById(String id) {
        return store.stream().filter(x-> x.getId().equals(id)).findFirst();
    }

    @Override
    public void save(ToDoListPo toDoListPo) {
        store.removeIf(x -> x.getId().equals(toDoListPo.getId()));
        store.add(toDoListPo);
    }

    @Override
    public void delete(ToDoListPo toDoListPo) {
        store.removeIf(x -> x.getId().equals(toDoListPo.getId()));
    }
}
