package tw.teddysoft.tasks.usecase.port.out;

import tw.teddysoft.ezddd.core.usecase.Repository;
import tw.teddysoft.tasks.entity.ToDoList;
import tw.teddysoft.tasks.entity.ToDoListId;

public interface ToDoListRepository extends Repository<ToDoList, ToDoListId> {
}

