package lab.second.view.controllers.ticket;

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
import lab.second.view.AlertDialog;
import lab.second.view.ConverterToFX;
import lab.second.view.controllers.MainControl;
import lab.second.view.model.TicketFX;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

public class TicketListController extends MainControl implements Initializable {

    private ObservableList<TicketFX> ticketFXObservableList = FXCollections.observableArrayList();

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
    private Button searchTicketButton;

    @FXML
    private Button addTicketButton;

    @FXML
    private Button editTicketButton;

    @FXML
    private Button deleteTicketButton;

    @FXML
    private TableView<TicketFX> tableViewTickets;

    @FXML
    private TableColumn<TicketFX, String> tableTicketColumnId;

    @FXML
    private TableColumn<TicketFX, String> tableTicketColumnAirship;

    @FXML
    private TableColumn<TicketFX, String> tableTicketColumnRouteFrom;

    @FXML
    private TableColumn<TicketFX, String> tableTicketColumnRouteTo;

    @FXML
    void searchTicketButtonAction(ActionEvent event) {
            try {
                if(selectedClient == null) {
                    findItems(daoProvider.getTicketDAO().getList());
                } else {
                    findItems(selectedClient.getTickets());
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
    }

    @FXML
    void addTicketButtonAction(ActionEvent event) {
        toScene("ticket/new_ticket.fxml", "New Ticket", event);
    }

    @FXML
    void deleteTicketButtonAction(ActionEvent event) {
        TicketFX selectedItem = tableViewTickets.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }
        try {
            if(!daoProvider.getTicketDAO().remove(ConverterToFX.convertFxToModel(selectedItem))) {
                AlertDialog.showAlert("There is a link to this item");
            } else {
                ticketFXObservableList.remove(selectedItem);
            }
        } catch (RemoteException e) {
            AlertDialog.showErrorAlert(e);
            e.printStackTrace();
        }
    }

    @FXML
    void editTicketButtonAction(ActionEvent event) throws Exception {
        TicketFX selectedItem = tableViewTickets.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }
        MainControl.selectedTicket = ConverterToFX.convertFxToModel(selectedItem);
        toScene("ticket/edit_ticket.fxml", "Edit ticket", event);
    }

    private void findItems(List<Ticket> list) {
        ticketFXObservableList.clear();
        for (Ticket ticket : list) {
            if (ticket.getId().toString().trim().startsWith(idTextField.getText().trim())
                    && ticket.getAirship().getModel().trim().startsWith(airshipTextField.getText().trim())
                    && ticket.getRoute().getStartPoint().trim().startsWith(routeFromTextField.getText().trim())
                    && ticket.getRoute().getEndPoint().trim().startsWith(routeToTextField.getText().trim())) {
                ticketFXObservableList.add(ConverterToFX.convertToFx(ticket));
            }
        }
    }

    private void setItems(List<Ticket> list) {
        ticketFXObservableList.clear();
        for (Ticket ticket : list) {
            ticketFXObservableList.add(ConverterToFX.convertToFx(ticket));
        }
        tableViewTickets.setItems(ticketFXObservableList);
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tableTicketColumnId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        tableTicketColumnAirship.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAirship().getModel()));
        tableTicketColumnRouteFrom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoute().getStartPoint()));
        tableTicketColumnRouteTo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoute().getEndPoint()));


        try {
            if (selectedClient == null) {
                addTicketButton.setVisible(false);
                addTicketButton.managedProperty().bind(addTicketButton.visibleProperty());
                setItems(daoProvider.getTicketDAO().getList());
            } else {
                addTicketButton.setVisible(true);
                setItems(selectedClient.getTickets());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
