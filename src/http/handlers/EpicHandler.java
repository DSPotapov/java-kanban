package http.handlers;

import com.sun.net.httpserver.HttpExchange;
import managers.TaskManager;

import java.io.IOException;

public class EpicHandler extends BaseHttpHandler {
    public EpicHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
}
