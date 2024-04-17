package tw.teddysoft.tasks.usecase.port.out;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ToDoListPo {

    private String id;

    private Long lastId;

    private Set<ProjectPo> projectPos;

    public ToDoListPo() {
        projectPos = new HashSet<>();
    }

    public ToDoListPo(String id, Long lastId) {
        this();
        this.id = id;
        this.lastId = lastId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getLastId() {
        return lastId;
    }

    public void setLastId(Long lastId) {
        this.lastId = lastId;
    }


    public List<ProjectPo> getProjectPos() {
        return new ArrayList<>(projectPos);
    }

    public void setProjectPos(List<ProjectPo> projectPos) {
        this.projectPos = new HashSet<>(projectPos);
    }
}
