package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lab.second.Client;

import java.io.IOException;

public class SampleController {

    private Client client;

    @FXML
    private TextField TextArea;

    @FXML
    private Button EnterButton;

    @FXML
    void EnterButtonAction(ActionEvent event) throws IOException {
        client.sendMessage(TextArea.getText());
        TextArea.clear();
    }

    @FXML
    void initialize() {
        client = new Client();
    }
}

