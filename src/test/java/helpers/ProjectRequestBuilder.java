package helpers;

import com.alfonzjanfrithz.project.todoboard.api.ProjectsApi;
import com.alfonzjanfrithz.project.todoboard.model.ProjectRequest;
import com.alfonzjanfrithz.project.todoboard.model.ProjectResponse;
import com.alfonzjanfrithz.todoboard.todoboardapi.entities.Project;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ProjectRequestBuilder {
    private static ObjectMapper mapper = new ObjectMapper();

    public static String project(String name) {
        ProjectRequest pr = new ProjectRequest();
        pr.setName(name);

        return toJson(pr);
    }

    private static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to parse JSON object", e);
        }
    }
}
