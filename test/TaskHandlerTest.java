import http.HttpTaskServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

public class TaskHandlerTest {
    HttpTaskServer httpTaskServer;

    public TaskHandlerTest() throws IOException {
        httpTaskServer = new HttpTaskServer(8080);
    }

    @BeforeEach
    public void startServer() {
        httpTaskServer.start();
    }

    @AfterEach
    public void stopServer() {
        httpTaskServer.stop();
    }


}
