package tw.teddysoft.tasks.usecase.port;

import java.time.LocalDateTime;

public class TaskDto {
    public String id;
    public String description;
    public boolean done;
    public LocalDateTime deadline;
}
