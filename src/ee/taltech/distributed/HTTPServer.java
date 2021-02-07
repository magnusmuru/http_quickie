package ee.taltech.distributed;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class HTTPServer {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        HttpContext context = server.createContext("/magmur");
        context.setHandler(HTTPServer::handleRequest);
        server.start();
    }

    private static void handleRequest(HttpExchange exchange) throws IOException {
        URI requestURI = exchange.getRequestURI();
        String response = "\nrequest query:\n" + requestURI.getQuery();
        exchange.sendResponseHeaders(200, 1000);
        OutputStream os = exchange.getResponseBody();
        os.write("request headers: \n".getBytes(StandardCharsets.UTF_8));
        for (Map.Entry<String, List<String>> header : exchange.getRequestHeaders().entrySet()) {
            String output = header.getKey() + "=" + header.getValue().toString() + "\n";
            os.write(output.getBytes(StandardCharsets.UTF_8));
        }
        os.write(response.getBytes(StandardCharsets.UTF_8));

        os.close();
    }
}