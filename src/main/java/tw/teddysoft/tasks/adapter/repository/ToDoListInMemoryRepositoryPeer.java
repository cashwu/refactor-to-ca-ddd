package tw.teddysoft.tasks.adapter.repository;


import tw.teddysoft.tasks.usecase.port.out.ToDoListPo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ToDoListInMemoryRepositoryPeer {

    private final List<ToDoListPo> store;

    public ToDoListInMemoryRepositoryPeer() {
        store = new ArrayList<>();
    }

    public Optional<ToDoListPo> findById(String id) {
        return store.stream().filter(x-> x.getId().equals(id)).findFirst();
    }

    public void save(ToDoListPo toDoListPo) {
        store.removeIf(x -> x.getId().equals(toDoListPo.getId()));
        store.add(toDoListPo);
    }

    public void delete(ToDoListPo toDoListPo) {
        store.removeIf(x -> x.getId().equals(toDoListPo.getId()));
    }
}
