package tw.teddysoft.tasks.entity;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDoListTest {

    @Test
    public void add_a_project_with_duplicated_name_has_no_effect(){
        ToDoList toDoList = new ToDoList(ToDoListId.of("001"));
        toDoList.addProject(ProjectName.of("p1"));
        assertEquals(1, toDoList.getProjects().size());
        toDoList.addProject(ProjectName.of("p1"));
        assertEquals(1, toDoList.getProjects().size());
    }
}
