package tw.teddysoft.tasks.usecase.port.out;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "todolist")
public class ToDoListPo {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "last_task_id")
    private Long lastId;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "id_fk")
    @OrderBy("order")
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
        this.projectPos = new LinkedHashSet<>(projectPos);
    }
}
