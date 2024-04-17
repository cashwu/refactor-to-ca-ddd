package tw.teddysoft.tasks.usecase.port;


import tw.teddysoft.tasks.entity.Project;
import tw.teddysoft.tasks.entity.ProjectName;
import tw.teddysoft.tasks.usecase.port.out.ProjectPo;

import java.util.ArrayList;
import java.util.Collections;
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

    public static Project toDomain(ProjectPo projectPo) {
        return new Project(
                ProjectName.of(projectPo.getName()),
                TaskMapper.toDomain(projectPo.getTaskPos()));
    }

    public static List<Project> toDomain(List<ProjectPo> projectPos) {
        Collections.sort(new ArrayList<>(projectPos));
        return projectPos.stream().map(ProjectMapper::toDomain).toList();
    }

    public static ProjectPo toPo(Project project, int order) {
        ProjectPo projectPo = new ProjectPo(project.getName().value(), order);
        projectPo.setTaskPos(TaskMapper.toPo(project.getTasks()));
        return projectPo;
    }

    public static List<ProjectPo> toPo(List<Project> projects) {
        List<ProjectPo> projectPos = new ArrayList<>();
        for(int i = 0; i < projects.size(); i++){
            projectPos.add(toPo(projects.get(i), i));
        }
        Collections.sort(projectPos);
        return projectPos;
    }
}