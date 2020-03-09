package lab.second.view.controllers.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lab.second.view.AlertDialog;
import lab.second.view.controllers.MainControl;

import java.io.IOException;

public class EditClientController extends MainControl {

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
        selectedClient.setFirstName(firstNameTextField.getText());
        selectedClient.setMiddleName(middleNameField.getText());
        selectedClient.setLastName(lastNameTextField.getText());
        try {
            daoProvider.getClientDAO().add(selectedClient);
            toScene("client/list_clients.fxml", "List Clients", event);
        } catch (IOException e) {
            AlertDialog.showErrorAlert(e);
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        firstNameTextField.setText(selectedClient.getFirstName());
        middleNameField.setText(selectedClient.getMiddleName());
        lastNameTextField.setText(selectedClient.getLastName());
    }
}
