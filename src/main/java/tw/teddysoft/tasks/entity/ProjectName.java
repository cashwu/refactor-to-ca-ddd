package tw.teddysoft.tasks.entity;

import tw.teddysoft.ezddd.core.entity.ValueObject;

public record ProjectName(String value) implements ValueObject {
    public static ProjectName of(String name) {
        return new ProjectName(name);
    }

    @Override
    public String toString(){
        return value;
    }
}
