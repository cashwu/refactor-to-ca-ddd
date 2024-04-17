package tw.teddysoft.tasks.usecase.port.out;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "project")
public class ProjectPo implements Comparable<ProjectPo> {

    @Id
    @Column(name = "name")
    private String name;

    @Column(name="project_order")
    private int order;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "id_fk")
    private List<TaskPo> taskPos;

    public ProjectPo() {
        taskPos = new ArrayList<>();
    }

    public ProjectPo(String name, int order) {
        this();
        this.name = name;
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public List<TaskPo> getTaskPos() {
        return new ArrayList<>(taskPos);
    }

    public void setTaskPos(List<TaskPo> taskPos) {
        this.taskPos = new ArrayList<>(taskPos);
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int compareTo(ProjectPo that) {

        return this.getOrder() - that.getOrder();
    }
}
