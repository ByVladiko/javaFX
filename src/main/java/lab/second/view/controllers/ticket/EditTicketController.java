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
import lab.second.view.controllers.MainControl;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class EditTicketController extends MainControl implements Initializable {

    static Ticket editTicket;

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
    private Button saveClientButton;

    @FXML
    void saveClientButtonAction(ActionEvent event) {
        if(routeChoiceBox.getValue() == null || airshipChoiceBox.getValue() == null) {
            util.showAlert("The choice boxes should be not empty");
            return;
        }
        editTicket.setRoute(routeChoiceBox.getValue());
        editTicket.setAirship(airshipChoiceBox.getValue());
        try {
            if (TicketListController.client != null) {
                util.getTicketDAO().add(editTicket);
                toScene("ticket/list_tickets.fxml", "List Tickets of " + TicketListController.client.getId().toString(), event);
            } else {
                util.getTicketDAO().add(editTicket);
                toScene("ticket/list_tickets.fxml", "List Tickets", event);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize (URL url, ResourceBundle resourceBundle) {
        try {
            routeChoiceBox.setItems(FXCollections.observableArrayList(util.getRouteDAO().getList()));
            airshipChoiceBox.setItems(FXCollections.observableArrayList(util.getAirshipDAO().getList()));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        routeChoiceBox.setValue(editTicket.getRoute());
        airshipChoiceBox.setValue(editTicket.getAirship());
    }
}
