package tw.teddysoft.tasks.usecase.port.out;

import java.util.ArrayList;
import java.util.List;

public class ProjectPo implements Comparable<ProjectPo> {

    private String name;

    private int order;

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
