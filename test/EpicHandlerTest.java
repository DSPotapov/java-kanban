import com.google.gson.Gson;
import components.Epic;
import components.Task;
import components.TaskStatus;
import components.TaskType;
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

public class EpicHandlerTest {
    HttpTaskServer httpTaskServer;
    TaskManager manager;
    HttpClient httpClient;
    Gson gson = HttpTaskServer.getGson();
    String path = "http://localhost:8080/epics";

    public EpicHandlerTest() throws IOException {
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
    public void getEpicByIdTest() throws IOException, InterruptedException {
        Epic epic = new Epic("Test 1", "Testing epic 1", 1);
        // конвертируем её в JSON
        String taskJson = gson.toJson(epic);
        System.out.println("taskJson = " + taskJson);

        // создаём HTTP-клиент и запрос
        URI url = URI.create(path + "epics");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .version(HttpClient.Version.HTTP_1_1)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .header("Content-Type", "application/json")
                .build();

        // вызываем рест, отвечающий за создание задач
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(200, response.statusCode());

        url = URI.create(path + "epics/" + epic.getId());
        request = HttpRequest.newBuilder()
                .uri(url)
                .version(HttpClient.Version.HTTP_1_1)
                .GET()
                .header("Content-Type", "application/json")
                .build();

        // вызываем рест, отвечающий за создание задач
        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(200, response.statusCode());
        Epic responseEpic = gson.fromJson(response.body(), Epic.class);
        assertEquals(epic, responseEpic, "Задачи не совпадают");
    }

    @Test
    public void createEpicsTest() throws IOException, InterruptedException {
        // создаём задачу
        Epic epic = new Epic("Test 2", "Testing epic 2", 1);
        // конвертируем её в JSON
        String taskJson = gson.toJson(epic);
        System.out.println("taskJson = " + taskJson);

        // создаём HTTP-клиент и запрос
        URI url = URI.create(path);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .version(HttpClient.Version.HTTP_1_1)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .header("Content-Type", "application/json")
                .build();

        // вызываем рест, отвечающий за создание задач
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(200, response.statusCode());

        // проверяем, что создалась одна задача с корректным именем
        List<Epic> tasksFromManager = manager.getEpics();
        System.out.println("tasksFromManager = " + tasksFromManager);

        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals("Test 2", tasksFromManager.get(0).getName(), "Некорректное имя задачи");
    }

    @Test
    public void updateEpicsTest() throws IOException, InterruptedException {
        // создаём задачу
        Epic epic = new Epic("Test 2", "Testing epic 2", 1);
        // конвертируем её в JSON
        String taskJson = gson.toJson(epic);

        // создаём HTTP-клиент и запрос
        URI url = URI.create(path);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .version(HttpClient.Version.HTTP_1_1)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .header("Content-Type", "application/json")
                .build();

        // вызываем рест, отвечающий за создание задач
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        // проверяем код ответа
        assertEquals(200, response.statusCode());

        Epic updateTask = new Epic("Test 223", "Testing epic 223", epic.getId());

        taskJson = gson.toJson(updateTask);

        url = URI.create(path + "/" + epic.getId());
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
        List<Epic> tasksFromManager = manager.getEpics();

        assertEquals(1,
                tasksFromManager.size(),
                "Некорректное количество задач");

        Optional<Epic> findTask = tasksFromManager.stream()
                .filter(el -> el.getId() == epic.getId())
                .findFirst();
        assertTrue(findTask.isPresent());

        assertEquals("Test 223",
                findTask.get().getName(),
                "Некорректное имя задачи");

        assertEquals("Testing epic 223",
                findTask.get().getDescription(),
                "Некорректное описание задачи");
    }

    @Test
    public void deleteTaskTest() {
    }

    @Test
    public void unknownPathTest() {
    }

}

