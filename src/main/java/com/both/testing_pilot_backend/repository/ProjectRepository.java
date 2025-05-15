package com.both.testing_pilot_backend.repository;

import com.both.testing_pilot_backend.model.entity.Project;
import org.apache.ibatis.annotations.*;

import java.util.UUID;

@Mapper
public interface ProjectRepository {

    @Results(id = "projectMapper", value = {@Result(property = "projectId", column = "project_id"),
            @Result(property = "projectName", column = "project_name"),
            @Result(property = "projectDescription", column = "project_description"),
            @Result(property = "projectOwner", column = "project_owner_id", one = @One(select = "com.both.testing_pilot_backend.repository.UserRepository.findById"))})
    @Select("""
                INSERT INTO projects (project_name, project_description, project_owner_id)
                VALUES (#{project.projectName}, #{project.projectDescription}, #{ownerId})
                RETURNING *;
            """)
    Project saveProject(Project project, UUID ownerId);

    @Select("""
                SELECT EXISTS(
                    SELECT 1
                    FROM projects
                    WHERE project_id = #{projectId}
                    AND project_owner_id = #{userId}
                )
            """)
    boolean isProjectOwner(UUID projectId, UUID userId);

    @Delete("""
                    DELETE FROM projects
                    WHERE project_id = #{projectId}
            """)
    void deleteByProjectId(UUID projectId);

    @Select("""
        SELECT * FROM projects
        WHERE project_id = #{projectId};
    """)
    Project findByProjectId(UUID projectId);
}
