package lab.second.view.controllers.ticket;

import airship.model.Airship;
import airship.model.Route;
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
import java.util.ResourceBundle;

public class EditTicketController extends MainControl implements Initializable {

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
            AlertDialog.showAlert("The choice boxes should be not empty");
            return;
        }
        selectedTicket.setRoute(routeChoiceBox.getValue());
        selectedTicket.setAirship(airshipChoiceBox.getValue());
        try {
            if (selectedTicket != null) {
                daoProvider.getTicketDAO().add(selectedTicket);
                toScene("ticket/list_tickets.fxml", "List Tickets of " + selectedTicket.getId().toString(), event);
            } else {
                daoProvider.getTicketDAO().add(selectedTicket);
                toScene("ticket/list_tickets.fxml", "List Tickets", event);
            }
        } catch (IOException e) {
            AlertDialog.showErrorAlert(e);
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize (URL url, ResourceBundle resourceBundle) {
        try {
            routeChoiceBox.setItems(FXCollections.observableArrayList(daoProvider.getRouteDAO().getList()));
            airshipChoiceBox.setItems(FXCollections.observableArrayList(daoProvider.getAirshipDAO().getList()));
        } catch (RemoteException e) {
            AlertDialog.showErrorAlert(e);
            e.printStackTrace();
        }
        routeChoiceBox.setValue(selectedTicket.getRoute());
        airshipChoiceBox.setValue(selectedTicket.getAirship());
    }
}
