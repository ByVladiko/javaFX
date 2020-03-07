package lab.second.view.controllers.client;

import airship.model.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lab.second.view.controllers.AlertDialog;
import lab.second.view.controllers.MainControl;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ClientListController extends MainControl implements Initializable {

    private ObservableList<Client> tableClients  = FXCollections.observableArrayList();

    @FXML
    private Button mainRoutesButton;

    @FXML
    private Button mainTicketsButton;

    @FXML
    private Button mainClientsButton;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField middleNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private Button addClientButton;

    @FXML
    private Button editClientButton;

    @FXML
    private Button deleteClientButton;

    @FXML
    private TableView<Client> tableViewClients;

    @FXML
    private TableRow<Client> tableRowClient;

    @FXML
    private TableColumn<Client, String> tableClientColumnId;

    @FXML
    private TableColumn<Client, String> tableClientColumnFirstName;

    @FXML
    private TableColumn<Client, String> tableClientsColumnMiddleName;

    @FXML
    private TableColumn<Client, String> tableClientsColumnLastName;

    @FXML
    public void InputTextFieldKeyReleased(KeyEvent keyEvent) {
        try {
            List<Client> clients = daoProvider.getClientDAO().getList().stream()
                    .filter(it -> it.getId().toString().toLowerCase().trim().startsWith(idTextField.getText().toLowerCase().trim()))
                    .filter(it -> it.getFirstName().toLowerCase().trim().startsWith(firstNameTextField.getText().toLowerCase().trim()))
                    .filter(it -> it.getMiddleName().toLowerCase().trim().startsWith(middleNameTextField.getText().toLowerCase().trim()))
                    .filter(it -> it.getLastName().toLowerCase().trim().startsWith(lastNameTextField.getText().toLowerCase().trim()))
                    .collect(Collectors.toList());
            tableClients.setAll(clients);
            tableViewClients.setItems(tableClients);
        } catch (RemoteException e) {
            AlertDialog.showErrorAlert(e);
            e.printStackTrace();
        }
    }

    @FXML
    void showTicketsMouseClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            if (tableViewClients.getSelectionModel().getSelectedItem() == null) {
                return;
            }
            selectedClient = tableViewClients.getSelectionModel().getSelectedItem();
            toScene("ticket/list_tickets.fxml",
                    "List tickets of "
                            + selectedClient.getFirstName()
                            + selectedClient.getMiddleName()
                            + selectedClient.getLastName(), event);
        }
    }

    @FXML
    void addClientButtonAction(ActionEvent event) {
        toScene("client/new_client.fxml", "New Client", event);
    }

    @FXML
    void deleteClientButtonAction(ActionEvent event) {
        if (tableViewClients.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        try {
            daoProvider.getClientDAO().remove(tableViewClients.getSelectionModel().getSelectedItem());
        } catch (RemoteException e) {
            AlertDialog.showErrorAlert(e);
            e.printStackTrace();
        }
        refreshTable();
    }

    @FXML
    void editClientButtonAction(ActionEvent event) {
        if (tableViewClients.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        selectedClient = tableViewClients.getSelectionModel().getSelectedItem();
        toScene("client/edit_client.fxml", "Edit client " + selectedClient.getId().toString(), event);
    }

    private void refreshTable() {
        try {
            tableClients.setAll(daoProvider.getClientDAO().getList());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        tableViewClients.setItems(tableClients);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tableClientColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableClientColumnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tableClientsColumnMiddleName.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        tableClientsColumnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        refreshTable();

    }
}

