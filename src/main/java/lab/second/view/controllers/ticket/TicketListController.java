package lab.second.view.controllers.ticket;

import airship.model.Client;
import airship.model.Ticket;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import lab.second.view.controllers.MainControl;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TicketListController extends MainControl implements Initializable {

    private ObservableList<Ticket> tableTickets = FXCollections.observableArrayList();

    public static Client client;

    @FXML
    private Button mainRoutesButton;

    @FXML
    private Button mainTicketsButton;

    @FXML
    private Button mainClientsButton;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField airshipTextField;

    @FXML
    private TextField routeFromTextField;

    @FXML
    private TextField routeToTextField;

    @FXML
    private Button addTicketButton;

    @FXML
    private Button editTicketButton;

    @FXML
    private Button deleteTicketButton;

    @FXML
    private TableView<Ticket> tableViewTickets;

    @FXML
    private TableColumn<Ticket, String> tableTicketColumnId;

    @FXML
    private TableColumn<Ticket, String> tableTicketColumnAirship;

    @FXML
    private TableColumn<Ticket, String> tableTicketColumnRouteFrom;

    @FXML
    private TableColumn<Ticket, String> tableTicketColumnRouteTo;

    public void InputTextFieldKeyReleased(KeyEvent keyEvent) {
        try {
            List<Ticket> tickets = util.getTicketDAO().getList().stream()
                    .filter(it -> it.getId().toString().toLowerCase().trim().contains(idTextField.getText().trim()))
                    .filter(it -> it.getAirship().getModel().toLowerCase().trim().contains(airshipTextField.getText().trim().trim()))
                    .filter(it -> it.getRoute().getStartPoint().toLowerCase().trim().contains(routeFromTextField.getText().trim()))
                    .filter(it -> it.getRoute().getEndPoint().toLowerCase().trim().contains(routeToTextField.getText().trim()))
                    .collect(Collectors.toList());
            tableTickets.setAll(tickets);
            tableViewTickets.setItems(tableTickets);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addTicketButtonAction(ActionEvent event) {
        AddTicketController.client = client;
        toScene("ticket/new_ticket.fxml", "New Ticket", event);
    }

    @FXML
    void deleteTicketButtonAction(ActionEvent event) {
        if (tableViewTickets.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        try {
            util.getTicketDAO().remove(tableViewTickets.getSelectionModel().getSelectedItem());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        refreshTable();
    }

    @FXML
    void editTicketButtonAction(ActionEvent event) throws Exception {
        if (tableViewTickets.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        EditTicketController.editTicket = tableViewTickets.getSelectionModel().getSelectedItem();
        toScene("ticket/edit_ticket.fxml", "Edit ticket", event);
    }

    private void refreshTable() {
        try {
            if (client == null) {
                tableTickets.setAll(util.getTicketDAO().getList());
                tableViewTickets.setItems(tableTickets);
            } else {
                List<Ticket> ticketsOfClient = new ArrayList<>();
                for (int i = 0; i < util.getClientDAO().getList().size(); i++) {
                    if(util.getClientDAO().getList().get(i).getId().equals(client.getId())) {
                        ticketsOfClient = util.getClientDAO().getList().get(i).getTickets();
                        break;
                    }
                }
                tableTickets.setAll(ticketsOfClient);
                tableViewTickets.setItems(tableTickets);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (client == null) {
            addTicketButton.setVisible(false);
            addTicketButton.managedProperty().bind(addTicketButton.visibleProperty());
        } else {
            addTicketButton.setVisible(true);
        }

        tableTicketColumnId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        tableTicketColumnAirship.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAirship().getModel()));
        tableTicketColumnRouteFrom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoute().getStartPoint()));
        tableTicketColumnRouteTo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoute().getEndPoint()));

        refreshTable();
    }
}
