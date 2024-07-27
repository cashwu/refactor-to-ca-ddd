package tw.teddysoft.tasks.entities;

import tw.teddysoft.ezddd.core.entity.ValueObject;

public record ProjectName(String value) implements ValueObject {

    public static ProjectName of(String projectName) {
        return new ProjectName(projectName);
    }

    @Override
    public String toString() {
        return value;
    }
}
