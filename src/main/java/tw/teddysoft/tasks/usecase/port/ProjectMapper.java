package tw.teddysoft.tasks.usecase.port;


import tw.teddysoft.tasks.entity.Project;

import java.util.List;

public class ProjectMapper {

    public static ProjectDto toDto(Project project) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.name = project.getName().value();
        projectDto.taskDtos = TaskMapper.toDto(project.getTasks());
        return projectDto;
    }

    public static List<ProjectDto> toDto(List<Project> projects) {
        return projects.stream().map(ProjectMapper::toDto).toList();
    }
}