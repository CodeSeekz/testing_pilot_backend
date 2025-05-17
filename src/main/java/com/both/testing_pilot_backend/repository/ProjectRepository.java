package com.both.testing_pilot_backend.repository;

import com.both.testing_pilot_backend.model.entity.Project;
import com.both.testing_pilot_backend.model.request.PageRequest;
import com.both.testing_pilot_backend.model.request.apiFeature.Filter;
import com.both.testing_pilot_backend.model.request.apiFeature.Sort;
import com.both.testing_pilot_backend.repository.provider.SqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Mapper
public interface ProjectRepository {

    @Results(id = "projectMapper", value = {@Result(property = "projectId", column = "project_id"),
            @Result(property = "projectId", column = "id"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "projectName", column = "name"),
            @Result(property = "projectDescription", column = "description"),
            @Result(property = "projectOwner", column = "project_owner_id", one = @One(select = "com.both.testing_pilot_backend.repository.UserRepository.findById"))})
    @Select("""
                INSERT INTO projects (name, description, project_owner_id)
                VALUES (#{project.projectName}, #{project.projectDescription}, #{ownerId})
                RETURNING *;
            """)
    Project saveProject(Project project, UUID ownerId);

    @ResultMap("projectMapper")
    @SelectProvider(type = SqlProvider.class, method = "buildFindAllQuery")
    List<Project> getAllProjects(@Param("filters") List<Filter> filters,
                                 @Param("sorts") List<Sort> sorts,
                                 @Param("search") List<Filter> search,
                                 @Param("pageRequest")PageRequest pageRequest,
                                 @Param("cursor") String cursor,
                                 @Param("tableName") String table);

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
