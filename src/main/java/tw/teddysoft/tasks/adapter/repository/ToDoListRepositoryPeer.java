package tw.teddysoft.tasks.adapter.repository;

import tw.teddysoft.tasks.usecase.port.out.ToDoListPo;

import java.util.Optional;

public interface ToDoListRepositoryPeer {
    Optional<ToDoListPo> findById(String id);
    void save(ToDoListPo toDoListPo);
    void delete(ToDoListPo toDoListPo);
}

