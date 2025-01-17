package ru.dsis.atms.client.tasks;

import static ru.dsis.atms.client.tasks.util.TasksUtilKt.DEFAULT_TASKS_BODY_REQUEST;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import ru.dsis.atms.client.tasks.dao.TaskDao;
import ru.dsis.atms.client.tasks.dao.TaskManagementProjectDao;

import java.util.List;

public class TasksManagementClient {
    private final RestClient restClient;
    private final String baseUrl;
    private final String authToken;

    public TasksManagementClient(RestClient restCliet, String baseUrl, String authToken) {
        this.restClient = restCliet;
        this.baseUrl = baseUrl;
        this.authToken = authToken;
    }

    public List<TaskDao> fetchTasks(String endpoint, int projectId) {
        return restClient.post()
                         .uri(baseUrl + endpoint, projectId)
                         .header("Authorization", "Bearer " + authToken)
                         .contentType(MediaType.APPLICATION_JSON)
                         .body(DEFAULT_TASKS_BODY_REQUEST)
                         .retrieve()
                         .body(new ParameterizedTypeReference<List<TaskDao>>() {});
    }

    public List<TaskManagementProjectDao> fetchProjects(String endpoint) {
        return restClient.get()
                         .uri(baseUrl + endpoint)
                         .header("Authorization", "Bearer " + authToken)
                         .retrieve()
                         .body(new ParameterizedTypeReference<List<TaskManagementProjectDao>>() {});
    }
}
