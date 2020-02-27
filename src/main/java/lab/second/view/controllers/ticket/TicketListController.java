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
import javafx.scene.input.InputMethodEvent;
import lab.second.view.controllers.MainControl;

import java.net.URL;
import java.util.ResourceBundle;

public class TicketListController extends MainControl implements Initializable {

    protected ObservableList<Ticket> tableTickets = FXCollections.observableArrayList();

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
    private TextField firstAirshipTextField;

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

    @FXML
    void addTicketButtonAction(ActionEvent event) throws Exception {
        AddTicketController.client = client;
        toScene("ticket/new_ticket.fxml", "New Ticket", event);
    }

    @FXML
    void deleteTicketButtonAction(ActionEvent event) {
        if (tableViewTickets.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        TicketDAOImpl.getInstance().remove(tableViewTickets.getSelectionModel().getSelectedItem());
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

    @FXML
    void idInputTextFieldAction(InputMethodEvent event) {

    }

    private void refreshTable() {
        if (client == null) {
            tableTickets.setAll(TicketDAOImpl.getInstance().getList());
            tableViewTickets.setItems(tableTickets);
        } else {
            tableTickets.setAll(TicketDAOImpl.getInstance().getListByClient(client));
            tableViewTickets.setItems(tableTickets);
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
