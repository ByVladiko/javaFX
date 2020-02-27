package lab.second.view.controllers.route;

import airship.model.Route;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import lab.second.view.controllers.MainControl;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.util.ResourceBundle;

public class RouteListController extends MainControl implements Initializable {

    private ObservableList<Route> tableRoutes  = FXCollections.observableArrayList();

    @FXML
    private TextField fromTextField;

    @FXML
    private TextField toTextField;

    @FXML
    private TextField idTextField;

    @FXML
    private TableView<Route> tableViewRoutes;

    @FXML
    private TableColumn<Route, Integer> tableRoutesColumnId;

    @FXML
    private TableColumn<Route, String> tableRoutesColumnFrom;

    @FXML
    private TableColumn<Route, String> tableRoutesColumnTo;

    @FXML
    private Button addRouteButton;

    @FXML
    private Button editRouteButton;

    @FXML
    private Button deleteRouteButton;

    @FXML
    private Button mainRoutesButton;

    @FXML
    private Button mainTicketsButton;

    @FXML
    private Button mainClientsButton;

    @FXML
    public void addRouteButtonAction(ActionEvent event) throws Exception {
        toScene("route/new_route.fxml", "New Route", event);
    }

    @FXML
    public void deleteRouteButtonAction(ActionEvent event) {
        if (tableViewRoutes.getSelectionModel().getSelectedItem() == null) {
            return;
        }
            RouteDAOImpl.getInstance().remove(tableViewRoutes.getSelectionModel().getSelectedItem());
            refreshTable();
    }

    @FXML
    void editRouteButtonAction(ActionEvent event) throws Exception {
        if(tableViewRoutes.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        EditRouteController.editRoute = tableViewRoutes.getSelectionModel().getSelectedItem();
        toScene("route/edit_route.fxml", "List Routes", event);
    }

    @FXML
    void idInputTextFieldAction(InputMethodEvent event) {

    }

    private void refreshTable() {
        try {
            tableRoutes.setAll(netClient.getFactoryDAO());
        } catch (IOException | NotBoundException e) {
            e.printStackTrace();
        }
        tableViewRoutes.setItems(tableRoutes);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {

        tableRoutesColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableRoutesColumnFrom.setCellValueFactory(new PropertyValueFactory<>("startPoint"));
        tableRoutesColumnTo.setCellValueFactory(new PropertyValueFactory<>("endPoint"));

        refreshTable();
    }
}

