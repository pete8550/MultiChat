package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MainClient extends Application {

    DataOutputStream toServer = null;
    DataInputStream fromServer = null;

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane paneForTextField = new BorderPane();
        paneForTextField.setPadding(new Insets(5, 5, 5, 5));
        paneForTextField.setStyle("-fx-border-color: green");
        paneForTextField.setLeft(new Label("Message: "));

        TextField typeField = new TextField();
        typeField.setAlignment(Pos.BOTTOM_RIGHT);
        paneForTextField.setCenter(typeField);

        BorderPane mainPane = new BorderPane();

        TextArea chatArea = new TextArea();
        mainPane.setCenter(new ScrollPane(chatArea));
        mainPane.setTop(paneForTextField);

        Scene scene = new Scene(mainPane, 450, 200);
        primaryStage.setTitle("Client 1");
        primaryStage.setScene(scene);
        primaryStage.show();

        typeField.setOnAction(e -> {

            try {
                String msg = (typeField.getText().trim());

                toServer.writeUTF(msg);
                toServer.flush();

                chatArea.appendText("Client 1: " + msg + "\n");
            }
            catch (IOException ex) {
                System.err.println(ex);
            }
        });

        try {
            Socket socket = new Socket("localhost", 8000);

            fromServer = new DataInputStream(socket.getInputStream());

            toServer = new DataOutputStream(socket.getOutputStream());
        }
        catch (IOException ex) {
            chatArea.appendText(ex.toString() + '\n');
        }
    }

    public static void main(String[] args) {
        launch(args);

    }
}
