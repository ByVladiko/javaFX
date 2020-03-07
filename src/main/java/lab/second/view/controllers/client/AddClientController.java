package lab.second.view.controllers.client;

import airship.model.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lab.second.view.controllers.AlertDialog;
import lab.second.view.controllers.MainControl;

import java.io.IOException;

public class AddClientController extends MainControl {

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
        if(!firstNameTextField.getText().matches(regex) || !middleNameField.getText().matches(regex) || !lastNameTextField.getText().matches(regex)) {
            AlertDialog.showAlert("Incorrect input");
            return;
        }
        try {
            daoProvider.getClientDAO().add(new Client(firstNameTextField.getText(), middleNameField.getText(), lastNameTextField.getText()));
            toScene("client/list_clients.fxml", "List Clients", event);
        } catch (IOException e) {
            AlertDialog.showErrorAlert(e);
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
    }
}
