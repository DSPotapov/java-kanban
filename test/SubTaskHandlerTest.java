import com.google.gson.Gson;
import components.*;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class SubTaskHandlerTest {
    HttpTaskServer httpTaskServer;
    TaskManager manager;
    HttpClient httpClient;
    Gson gson = HttpTaskServer.getGson();
    String path = "http://localhost:8080/subtasks";

    public SubTaskHandlerTest() throws IOException {
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
    public void getSubTasksTest() {
    }

    @Test
    public void getSubTaskByIdTest() {
    }

    @Test
    public void createSubTaskTest() throws IOException, InterruptedException {
        // ������ ������
        SubTask subTask = new SubTask("Test 2", "Testing subtask 2", 2,1);
        // ������������ � � JSON
        String taskJson = gson.toJson(subTask);
        System.out.println("taskJson = " + taskJson);

        // ������ HTTP-������ � ������
        URI url = URI.create(path);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .version(HttpClient.Version.HTTP_1_1)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .header("Content-Type", "application/json")
                .build();

        // �������� ����, ���������� �� �������� �����
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        // ��������� ��� ������
        assertEquals(200, response.statusCode());

        // ���������, ��� ��������� ���� ������ � ���������� ������
        List<SubTask> tasksFromManager = manager.getSubTasks();
        System.out.println("tasksFromManager = " + tasksFromManager);

        assertNotNull(tasksFromManager, "������ �� ������������");
        assertEquals(1, tasksFromManager.size(), "������������ ���������� �����");
        assertEquals("Test 2", tasksFromManager.get(0).getName(), "������������ ��� ������");
    }

    @Test
    public void updateSubTaskTest() throws IOException, InterruptedException {
        // ������ ������
        SubTask subTask = new SubTask("Test 2", "Testing subTask 2", 2, 1);
        // ������������ � � JSON
        String taskJson = gson.toJson(subTask);

        // ������ HTTP-������ � ������
        URI url = URI.create(path);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .version(HttpClient.Version.HTTP_1_1)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .header("Content-Type", "application/json")
                .build();

        // �������� ����, ���������� �� �������� �����
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        // ��������� ��� ������
        assertEquals(200, response.statusCode());

        SubTask updateTask = new SubTask("Test 223", "Testing subTask 223", subTask.getId());

        taskJson = gson.toJson(updateTask);

        url = URI.create(path + "/" + subTask.getId());
        request = HttpRequest.newBuilder()
                .uri(url)
                .version(HttpClient.Version.HTTP_1_1)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .header("Content-Type", "application/json")
                .build();

        // �������� ����, ���������� �� �������� �����
        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        // ��������� ��� ������
        assertEquals(201,
                response.statusCode(),
                "�������� ������ ��� ���������� ������");

        // ���������, ��� ��������� ���� ������ � ���������� ������
        List<SubTask> tasksFromManager = manager.getSubTasks();

        assertEquals(1,
                tasksFromManager.size(),
                "������������ ���������� �����");

        Optional<SubTask> findTask = tasksFromManager.stream()
                .filter(el -> el.getId() == subTask.getId())
                .findFirst();
        assertTrue(findTask.isPresent());

        assertEquals("Test 223",
                findTask.get().getName(),
                "������������ ��� ������");

        assertEquals("Testing epic 223",
                findTask.get().getDescription(),
                "������������ �������� ������");
    }

    @Test
    public void deleteSubTaskTest() {
    }

    @Test
    public void unknownPathTest() {

    }

}

