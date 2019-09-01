package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class MainServer extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        TextArea serverTextArea = new TextArea();

        Scene scene = new Scene(new ScrollPane(serverTextArea), 450, 200);
        primaryStage.setTitle("Server");
        primaryStage.setScene(scene);
        primaryStage.show();

        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(8000);
                ServerSocket serverSocket2 = new ServerSocket(9000);
                Platform.runLater(() ->
                        serverTextArea.appendText("Server started at " + new Date() + '\n'));

                Socket socket = serverSocket.accept();
                Socket socket2 = serverSocket2.accept();

                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                DataInputStream in2 = new DataInputStream(socket2.getInputStream());
                DataOutputStream out2 = new DataOutputStream(socket2.getOutputStream());

                while (true) {
                    serverTextArea.appendText("Client 1: " + in.readUTF() + '\n');
                    serverTextArea.appendText("Client 2: " + in2.readUTF() + '\n');

                    String chat = String.valueOf(serverTextArea);
                    out.writeUTF(chat);
                    out2.writeUTF(chat);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) { launch(args); }
}
