package lab.second.view.controllers.client;

import airship.model.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lab.second.view.AlertDialog;
import lab.second.view.ConverterToFX;
import lab.second.view.controllers.MainControl;
import lab.second.view.model.ClientFX;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientListController extends MainControl implements Initializable {

    private ObservableList<ClientFX> clientFXObservableList  = FXCollections.observableArrayList();

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
    private Button searchClientButton;

    @FXML
    private Button addClientButton;

    @FXML
    private Button editClientButton;

    @FXML
    private Button deleteClientButton;

    @FXML
    private TableView<ClientFX> tableViewClients;

    @FXML
    private TableRow<Client> tableRowClient;

    @FXML
    private TableColumn<ClientFX, String> tableClientColumnId;

    @FXML
    private TableColumn<ClientFX, String> tableClientColumnFirstName;

    @FXML
    private TableColumn<ClientFX, String> tableClientsColumnMiddleName;

    @FXML
    private TableColumn<ClientFX, String> tableClientsColumnLastName;

    @FXML
    void showTicketsMouseClicked(MouseEvent event) {
        ClientFX selectedItem = tableViewClients.getSelectionModel().getSelectedItem();
        if (event.getClickCount() == 2) {
            if (selectedItem == null) {
                return;
            }
            MainControl.selectedClient = ConverterToFX.convertFxToModel(selectedItem);
            toScene("ticket/list_tickets.fxml",
                    "List tickets of "
                            + selectedClient.getFirstName()
                            + " " + selectedClient.getMiddleName()
                            + " " + selectedClient.getLastName(), event);
        }
    }

    @FXML
    void searchClientButtonAction(ActionEvent event) {
        List<Client> repository = new ArrayList<>();
        try {
            repository = daoProvider.getClientDAO().getList();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        clientFXObservableList.clear();
        for (Client client : repository) {
            if (client.getId().toString().trim().startsWith(idTextField.getText().trim())
                    && client.getFirstName().trim().startsWith(firstNameTextField.getText().trim())
                    && client.getMiddleName().trim().startsWith(middleNameTextField.getText().trim())
                    && client.getLastName().trim().startsWith(lastNameTextField.getText().trim())) {
                clientFXObservableList.add(ConverterToFX.convertToFx(client));
            }
        }
    }

    @FXML
    void addClientButtonAction(ActionEvent event) {
        toScene("client/new_client.fxml", "New Client", event);
    }

    @FXML
    void deleteClientButtonAction(ActionEvent event) {
        ClientFX selectedItem = tableViewClients.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }
        try {
            if(!daoProvider.getClientDAO().remove(ConverterToFX.convertFxToModel(selectedItem))) {
                AlertDialog.showAlert("There is a link to this item");
            } else {
                clientFXObservableList.remove(selectedItem);
            }
        } catch (RemoteException e) {
            AlertDialog.showErrorAlert(e);
            e.printStackTrace();
        }
    }

    @FXML
    void editClientButtonAction(ActionEvent event) {
        ClientFX selectedItem = tableViewClients.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }
        MainControl.selectedClient = ConverterToFX.convertFxToModel(selectedItem);
        toScene("client/edit_client.fxml", "Edit client " + selectedItem.getId().toString(), event);
    }

    private void setItems(List<Client> list) {
        clientFXObservableList.clear();
        for (Client client : list) {
            clientFXObservableList.add(ConverterToFX.convertToFx(client));
        }
        tableViewClients.setItems(clientFXObservableList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tableClientColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableClientColumnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tableClientsColumnMiddleName.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        tableClientsColumnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        try {
            setItems(daoProvider.getClientDAO().getList());
        } catch (RemoteException e) {
            AlertDialog.showErrorAlert(e);
            e.printStackTrace();
        }
    }
}

