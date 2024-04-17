package tw.teddysoft.tasks.usecase.port;

import java.util.ArrayList;
import java.util.List;

public class ToDoListDto {
    public String id;

    public List<ProjectDto> projectDots;

    public ToDoListDto() {
        this.projectDots = new ArrayList<>();
    }
}
