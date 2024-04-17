package tw.teddysoft.tasks.adapter.repository;

import java.util.Optional;

import tw.teddysoft.tasks.entity.ToDoList;
import tw.teddysoft.tasks.entity.ToDoListId;
import tw.teddysoft.tasks.usecase.port.ToDoListMapper;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;

public class ToDoListInMemoryRepository implements ToDoListRepository {

    private final ToDoListRepositoryPeer peer;

    public ToDoListInMemoryRepository(ToDoListRepositoryPeer peer) {
        this.peer = peer;
    }

    @Override
    public void save(ToDoList toDoList) {
        peer.save(ToDoListMapper.toPo(toDoList));
    }

    @Override
    public void delete(ToDoList toDoList) {
        peer.delete(ToDoListMapper.toPo(toDoList));
    }

    @Override
    public Optional<ToDoList> findById(ToDoListId toDoListId) {
        return peer.findById(toDoListId.value()).map(ToDoListMapper::toDomain);
    }
}
