package tw.teddysoft.tasks.usecase.port.out;

public class TaskPo {

    private String id;

    private String description;

    private Boolean done;


    public TaskPo() {
    }

    public TaskPo(String id, String description, Boolean done) {
        this.id = id;
        this.description = description;
        this.done = done;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }
}
