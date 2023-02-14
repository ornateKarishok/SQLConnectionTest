package com.example.myapplication;

import static com.google.protobuf.CodedOutputStream.DEFAULT_BUFFER_SIZE;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.concurrent.Executors;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {
    private boolean serverUp = false;
    private HttpServer mHttpServer = null;
    private TextView serverTextView;
    private Button serverButton;
    private final HttpHandler rootHandler = exchange -> {
        if ("GET".equals(exchange.getRequestMethod())) {
         //   exchange.getResponseHeaders().add("Content-Disposition", "attachment; filename=conn.php");
            URL url = getClass().getClassLoader().getResource("php_script/conn.php");
            File file = new File(url.getFile());
//            try {
//                exchange.sendResponseHeaders(200, file.length());
//                OutputStream outputStream = exchange.getResponseBody();
//                Files.copy(file.toPath(), outputStream);
//                outputStream.close();
//            } catch (IOException exception) {
//                exception.printStackTrace();}
            sendResponse(exchange, "Welcome to my server");
        }
    };
    private final HttpHandler messageHandler = httpExchange -> {
        switch (httpExchange.getRequestMethod()) {
            case "GET":
                sendResponse(httpExchange, "Would be all messages stringified json");
                break;
            case "POST":
                InputStream inputStream = httpExchange.getRequestBody();
                String requestBody = streamToString(inputStream);
                JSONObject jsonBody;
                try {
                    jsonBody = new JSONObject(requestBody);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                sendResponse(httpExchange, jsonBody.toString());
        }

    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int port = 5000;
        serverTextView = findViewById(R.id.serverTextView);
        serverButton = findViewById(R.id.serverButton);
        serverButton.setOnClickListener(view -> {
            if (!serverUp) {
                try {
                    startServer(port);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                serverUp = true;
            } else {
                stopServer();
                serverUp = false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private String streamToString(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        scanner.useDelimiter("\\A");
        if (scanner.hasNext()) {
            return scanner.next();
        } else {
            return "";
        }
    }

    private void sendResponse(HttpExchange httpExchange, String responseText) throws IOException {
        httpExchange.sendResponseHeaders(200, responseText.length());
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(responseText.getBytes());
        outputStream.close();
    }

    private void stopServer() {
        if (mHttpServer != null) {
            mHttpServer.stop(0);
            serverTextView.setText(getString(R.string.server_down));
            serverButton.setText(getString(R.string.start_server));
        }
    }

    private void startServer(int port) throws IOException {
        mHttpServer = HttpServer.create(new InetSocketAddress(port), 0);
        mHttpServer.setExecutor(Executors.newCachedThreadPool());

        mHttpServer.createContext("/", rootHandler);
        mHttpServer.createContext("/index", rootHandler);

        mHttpServer.createContext("/messages", messageHandler);
        mHttpServer.start();
        serverTextView.setText(getString(R.string.server_running));
        serverButton.setText(getString(R.string.stop_server));
    }

}