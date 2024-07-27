package tw.teddysoft.tasks.entity;

public record ProjectName(String value) {
    public static ProjectName of(String projectName) {
        return new ProjectName(projectName);
    }

    @Override
    public String toString() {
        return value;
    }
}
