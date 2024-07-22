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
        Epic epic = new Epic("Epic 1", "Testing epic 1", 1);
        String taskJson = gson.toJson(epic);
        System.out.println("taskJson = " + taskJson);

        // создаём HTTP-клиент и запрос
        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .version(HttpClient.Version.HTTP_1_1)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(200, response.statusCode());

        SubTask subTask = new SubTask("Test 2", "Testing subtask 2", 2,1);
        // конвертируем её в JSON
        taskJson = gson.toJson(subTask);
        System.out.println("taskJson = " + taskJson);

        // создаём HTTP-клиент и запрос
        url = URI.create(path);
        request = HttpRequest.newBuilder()
                .uri(url)
                .version(HttpClient.Version.HTTP_1_1)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .header("Content-Type", "application/json")
                .build();

        // вызываем рест, отвечающий за создание задач
        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(200, response.statusCode());

        // проверяем, что создалась одна задача с корректным именем
        List<SubTask> tasksFromManager = manager.getSubTasks();

        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals("Test 2", tasksFromManager.get(0).getName(), "Некорректное имя задачи");
    }

    @Test
    public void updateSubTaskTest() throws IOException, InterruptedException {

        Epic epic = new Epic("Epic 1", "Testing epic 1", 1);
        String taskJson = gson.toJson(epic);
        System.out.println("taskJson = " + taskJson);

        // создаём HTTP-клиент и запрос
        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .version(HttpClient.Version.HTTP_1_1)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(200, response.statusCode());
        // создаём задачу
        SubTask subTask = new SubTask("Test 2", "Testing subTask 2", 2, 1);
        // конвертируем её в JSON
        taskJson = gson.toJson(subTask);

        // создаём HTTP-клиент и запрос
        url = URI.create(path);
        request = HttpRequest.newBuilder()
                .uri(url)
                .version(HttpClient.Version.HTTP_1_1)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .header("Content-Type", "application/json")
                .build();

        // вызываем рест, отвечающий за создание задач
        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(200, response.statusCode());

        SubTask updateTask = new SubTask("Test 223", "Testing subTask 223", subTask.getId(), epic.getId());

        taskJson = gson.toJson(updateTask);

        url = URI.create(path + "/" + subTask.getId());
        request = HttpRequest.newBuilder()
                .uri(url)
                .version(HttpClient.Version.HTTP_1_1)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .header("Content-Type", "application/json")
                .build();

        // вызываем рест, отвечающий за создание задач
        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(201,
                response.statusCode(),
                "неверный статус при обновлении задачи");

        // проверяем, что создалась одна задача с корректным именем
        List<SubTask> tasksFromManager = manager.getSubTasks();

        assertEquals(1,
                tasksFromManager.size(),
                "Некорректное количество задач");

        Optional<SubTask> findTask = tasksFromManager.stream()
                .filter(el -> el.getId() == subTask.getId())
                .findFirst();
        assertTrue(findTask.isPresent());

        assertEquals("Test 223",
                findTask.get().getName(),
                "Некорректное имя задачи");

        assertEquals("Testing subTask 223",
                findTask.get().getDescription(),
                "Некорректное описание задачи");
    }

    @Test
    public void deleteSubTaskTest() {
    }

    @Test
    public void unknownPathTest() {

    }

}

