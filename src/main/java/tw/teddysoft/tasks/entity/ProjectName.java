package tw.teddysoft.tasks.entity;

import tw.teddysoft.ezddd.core.entity.ValueObject;

public record ProjectName(String name) implements ValueObject {
    public static ProjectName of(String projectName) {
        return new ProjectName(projectName);
    }

    @Override
    public String toString() {
        return name;
    }
}
