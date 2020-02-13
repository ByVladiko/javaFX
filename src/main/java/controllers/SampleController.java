package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import second.Client;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class SampleController {

    private Client client;

    @FXML
    private TextField TextField;

    @FXML
    private Button EnterButton;

    @FXML
    private TextArea TextArea;

    @FXML
    void EnterButtonAction(ActionEvent event) throws IOException, NotBoundException {
        TextArea.setText(client.sendMessage(TextField.getText()));
        TextField.clear();
    }

    @FXML
    void initialize() throws RemoteException {
        client = new Client();
    }
}