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
import lab.second.view.controllers.MainControl;
import lab.second.view.controllers.ticket.TicketListController;

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
            List<Client> clients = util.getClientDAO().getList().stream()
                    .filter(it -> it.getId().toString().toLowerCase().trim().contains(idTextField.getText().trim()))
                    .filter(it -> it.getFirstName().toLowerCase().trim().contains(firstNameTextField.getText().trim()))
                    .filter(it -> it.getMiddleName().toLowerCase().trim().contains(middleNameTextField.getText().trim()))
                    .filter(it -> it.getLastName().toLowerCase().trim().contains(lastNameTextField.getText().trim()))
                    .collect(Collectors.toList());
            tableClients.setAll(clients);
            tableViewClients.setItems(tableClients);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showTicketsMouseClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            if (tableViewClients.getSelectionModel().getSelectedItem() == null) {
                return;
            }
            TicketListController.client = tableViewClients.getSelectionModel().getSelectedItem();
            toScene("ticket/list_tickets.fxml", "List tickets of " + tableViewClients.getSelectionModel().getSelectedItem().getId().toString(), event);
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
            util.getClientDAO().remove(tableViewClients.getSelectionModel().getSelectedItem());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        refreshTable();
    }

    @FXML
    void editClientButtonAction(ActionEvent event) {
        if (tableViewClients.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        EditClientController.editClient = tableViewClients.getSelectionModel().getSelectedItem();
        toScene("route/edit_route.fxml", "List Routes", event);
    }

    private void refreshTable() {
        try {
            tableClients.setAll(util.getClientDAO().getList());
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

