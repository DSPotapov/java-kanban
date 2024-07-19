import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import components.Task;
import components.TaskStatus;
import http.HttpTaskServer;
import managers.Managers;
import managers.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TaskHandlerTest {
    HttpTaskServer httpTaskServer;
    TaskManager manager;
    HttpClient httpClient;
    Gson gson = HttpTaskServer.getGson();

    public TaskHandlerTest() throws IOException {
        manager = Managers.getDefault();
        httpTaskServer = new HttpTaskServer(manager, 8080);
        httpClient = HttpClient.newHttpClient();
    }

    @BeforeEach
    public void setUp() {
        manager.clearAllTasks();
        httpTaskServer.start();
    }

    @AfterEach
    public void shutDown() {
        httpTaskServer.stop();
    }

    @Test
    public void getTasksTest() {
    }

    @Test
    public void getTaskByIdTest() {
    }

    @Test
    public void createTaskTest() throws IOException, InterruptedException {
        // ������ ������
        Task task = new Task("Test 2", "Testing task 2", LocalDateTime.now(), Duration.ofMinutes(5));
        // ������������ � � JSON
        String taskJson = gson.toJson(task);
        System.out.println("taskJson = " + taskJson);

        // ������ HTTP-������ � ������
        // HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .build();

        // �������� ����, ���������� �� �������� �����
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        // ��������� ��� ������
        assertEquals(200, response.statusCode());

        // ���������, ��� ��������� ���� ������ � ���������� ������
        List<Task> tasksFromManager = manager.getTasks();

        assertNotNull(tasksFromManager, "������ �� ������������");
        assertEquals(1, tasksFromManager.size(), "������������ ���������� �����");
        assertEquals("Test 2", tasksFromManager.get(0).getName(), "������������ ��� ������");
    }

    @Test
    public void updateTasksTest() {
    }

    @Test
    public void deleteTaskTest() {
    }

    @Test
    public void unknownPathTest() {
    }

}
