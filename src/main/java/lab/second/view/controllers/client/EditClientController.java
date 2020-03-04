package lab.second.view.controllers.client;

import airship.model.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lab.second.view.controllers.MainControl;

import java.io.IOException;

public class EditClientController extends MainControl {

    static Client editClient;

    @FXML
    private Button mainRoutesButton;

    @FXML
    private Button mainTicketsButton;

    @FXML
    private Button mainClientsButton;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField middleNameField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private Button saveClientButton;

    @FXML
    void saveClientButtonAction(ActionEvent event) {
        String regex = "^[a-zA-Z0-9А-Яа-я._-]{3,}$";
        if(!firstNameTextField.getText().matches(regex) || !middleNameField.getText().matches(regex)) {
            util.showAlert("Incorrect input");
            return;
        }
        editClient.setFirstName(firstNameTextField.getText());
        editClient.setMiddleName(middleNameField.getText());
        editClient.setLastName(lastNameTextField.getText());
        try {
            util.getClientDAO().add(editClient);
            toScene("client/list_clients.fxml", "List Clients", event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        firstNameTextField.setText(editClient.getFirstName());
        middleNameField.setText(editClient.getMiddleName());
        lastNameTextField.setText(editClient.getLastName());
    }
}
