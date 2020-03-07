package lab.second.view.controllers.ticket;

import airship.model.Airship;
import airship.model.Route;
import airship.model.Ticket;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import lab.second.view.controllers.AlertDialog;
import lab.second.view.controllers.MainControl;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

public class AddTicketController extends MainControl implements Initializable {

    @FXML
    private Button mainRoutesButton;

    @FXML
    private Button mainTicketsButton;

    @FXML
    private Button mainClientsButton;

    @FXML
    private ComboBox<Route> routeChoiceBox;

    @FXML
    private ComboBox<Airship> airshipChoiceBox;

    @FXML
    private Button saveTicketButton;

    @FXML
    void saveTicketButtonAction(ActionEvent event) {
        if (routeChoiceBox.getValue() == null || airshipChoiceBox.getValue() == null) {
            AlertDialog.showAlert("The choice boxes should be not empty");
            return;
        }
            Ticket newTicket = new Ticket(airshipChoiceBox.getValue(), routeChoiceBox.getValue());
            List<Ticket> tickets = selectedClient.getTickets();
            tickets.add(newTicket);
            selectedClient.setTickets(tickets);
        try {
            daoProvider.getClientDAO().add(selectedClient);
            toScene("ticket/list_tickets.fxml", "List Tickets", event);
        } catch (IOException e) {
            AlertDialog.showErrorAlert(e);
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            routeChoiceBox.setItems(FXCollections.observableArrayList(daoProvider.getRouteDAO().getList()));
            airshipChoiceBox.setItems(FXCollections.observableArrayList(daoProvider.getAirshipDAO().getList()));
        } catch (RemoteException e) {
            AlertDialog.showErrorAlert(e);
            e.printStackTrace();
        }
    }
}

