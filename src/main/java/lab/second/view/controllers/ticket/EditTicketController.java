package lab.second.view.controllers.ticket;

import airship.model.Airship;
import airship.model.Route;
import airship.model.Ticket;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import lab.second.view.controllers.MainControl;

import java.net.URL;
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
    void saveClientButtonAction(ActionEvent event) throws Exception {
        if(routeChoiceBox.getValue() == null || airshipChoiceBox.getValue() == null) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Attention");
            a.setHeaderText("Ops!");
            String version = System.getProperty("java.version");
            String content = String.format("The choice boxes should be not empty", version);
            a.setContentText(content);
            a.showAndWait();
            return;
        }
        editTicket.setRoute(routeChoiceBox.getValue());
        editTicket.setAirship(airshipChoiceBox.getValue());
        if(TicketListController.client != null) {
            TicketDAOImpl.getInstance().add(editTicket);
            toScene("ticket/list_tickets.fxml", "List Tickets of " + TicketListController.client.getId().toString(), event);
        } else {
            TicketDAOImpl.getInstance().add(editTicket);
            toScene("ticket/list_tickets.fxml", "List Tickets", event);
        }
    }

    @FXML
    public void initialize (URL url, ResourceBundle resourceBundle) {
        RouteDAOImpl routeDAO = RouteDAOImpl.getInstance();
        AirshipDAOImpl airshipDAO = AirshipDAOImpl.getInstance();
        routeChoiceBox.setItems(FXCollections.observableArrayList(routeDAO.getList()));
        airshipChoiceBox.setItems(FXCollections.observableArrayList(airshipDAO.getList()));
        routeChoiceBox.setValue(editTicket.getRoute());
        airshipChoiceBox.setValue(editTicket.getAirship());
    }
}
