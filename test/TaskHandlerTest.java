import com.google.gson.Gson;
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

public class TaskHandlerTest {
    HttpTaskServer httpTaskServer;
    TaskManager manager;
    HttpClient httpClient;
    Gson gson = HttpTaskServer.getGson();
    String path = "http://localhost:8080/";

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
    public void getTaskByIdTest() throws IOException, InterruptedException {
        Task task = new Task("Test 2", "Testing task 2", 1);
        // конвертируем её в JSON
        String taskJson = gson.toJson(task);
        System.out.println("taskJson = " + taskJson);

        // создаём HTTP-клиент и запрос
        URI url = URI.create(path + "tasks");
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

        url = URI.create(path + "tasks/" + task.getId());
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
        Task responseTask = gson.fromJson(response.body(), Task.class);
        assertEquals(task, responseTask, "Задачи не совпадают");
    }

    @Test
    public void createTaskTest() throws IOException, InterruptedException {
        // создаём задачу
        Task task = new Task("Test 2", "Testing task 2");
        // конвертируем её в JSON
        String taskJson = gson.toJson(task);
        System.out.println("taskJson = " + taskJson);

        // создаём HTTP-клиент и запрос
        URI url = URI.create(path + "tasks");
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
        List<Task> tasksFromManager = manager.getTasks();

        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals("Test 2", tasksFromManager.get(0).getName(), "Некорректное имя задачи");
    }

    @Test
    public void updateTasksTest() throws IOException, InterruptedException {
        // создаём задачу
        Task task = new Task("Test 2", "Testing task 2", 1);
        // конвертируем её в JSON
        String taskJson = gson.toJson(task);

        // создаём HTTP-клиент и запрос
        URI url = URI.create(path + "tasks");
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

        Task updateTask = new Task("Test 223", "Testing task 223", task.getId());
        updateTask.setTaskStatus(TaskStatus.DONE);

        taskJson = gson.toJson(updateTask);

        url = URI.create(path + "tasks/" + task.getId());
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
        List<Task> tasksFromManager = manager.getTasks();

        assertEquals(1,
                tasksFromManager.size(),
                "Некорректное количество задач");

        Optional<Task> findTask = tasksFromManager.stream()
                .filter(el -> el.getId() == task.getId())
                .findFirst();
        assertTrue(findTask.isPresent());
        assertEquals(TaskType.TASK, findTask.get().getTaskType(), "Тип задачи не совпадает");

        assertEquals("Test 223",
                findTask.get().getName(),
                "Некорректное имя задачи");

        assertEquals(TaskStatus.DONE,
                findTask.get().getTaskStatus(),
                "Некорректное состояние задачи задачи");

        assertEquals("Testing task 223",
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
