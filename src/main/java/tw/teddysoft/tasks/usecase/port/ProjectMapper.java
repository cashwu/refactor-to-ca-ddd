package tw.teddysoft.tasks.usecase.port;

import tw.teddysoft.tasks.entity.Project;

import java.util.List;

public class ProjectMapper {

    public static List<ProjectDto> toDto(List<Project> project) {

        return project.stream().map(ProjectMapper::toDto).toList();
    }

    public static ProjectDto toDto(Project project) {

        ProjectDto projectDto = new ProjectDto();

        projectDto.name = project.getName().value();
        projectDto.tasks = TaskMapper.toDto(project.getTasks());

        return projectDto;
    }
}
