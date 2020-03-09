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
import lab.second.view.AlertDialog;
import lab.second.view.ConverterToFX;
import lab.second.view.controllers.MainControl;
import lab.second.view.model.RouteFX;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RouteListController extends MainControl implements Initializable {

    private ObservableList<RouteFX> routeFXObservableList = FXCollections.observableArrayList();

    @FXML
    private TextField fromTextField;

    @FXML
    private TextField toTextField;

    @FXML
    private TextField idTextField;

    @FXML
    private Button searchRouteButton;

    @FXML
    private TableView<RouteFX> tableViewRoutes;

    @FXML
    private TableColumn<RouteFX, String> tableRoutesColumnId;

    @FXML
    private TableColumn<RouteFX, String> tableRoutesColumnFrom;

    @FXML
    private TableColumn<RouteFX, String> tableRoutesColumnTo;

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
    void searchRouteButtonAction(ActionEvent event) {
        List<Route> repository = new ArrayList<>();
        try {
            repository = daoProvider.getRouteDAO().getList();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        routeFXObservableList.clear();
        for (Route route : repository) {
            if (route.getId().toString().trim().startsWith(idTextField.getText().trim())
                    && route.getStartPoint().trim().startsWith(fromTextField.getText().trim())
                    && route.getEndPoint().trim().startsWith(toTextField.getText().trim())) {
                routeFXObservableList.add(ConverterToFX.convertToFx(route));
            }
        }
    }

    @FXML
    public void addRouteButtonAction(ActionEvent event) {
        toScene("route/new_route.fxml", "New Route", event);
    }

    @FXML
    public void deleteRouteButtonAction(ActionEvent event) {
        RouteFX selectedItem = tableViewRoutes.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }
        try {
            if(!daoProvider.getRouteDAO().remove(ConverterToFX.convertFxToModel(selectedItem))) {
                AlertDialog.showAlert("There is a link to this item");
            } else {
                routeFXObservableList.remove(selectedItem);
            }
        } catch (RemoteException e) {
            AlertDialog.showErrorAlert(e);
            e.printStackTrace();
        }
    }

    @FXML
    void editRouteButtonAction(ActionEvent event) {
        RouteFX selectedItem = tableViewRoutes.getSelectionModel().getSelectedItem();
        if(selectedItem == null) {
            return;
        }
        MainControl.selectedRoute = ConverterToFX.convertFxToModel(selectedItem);
        toScene("route/edit_route.fxml", "List Routes", event);
    }

    private void setItems(List<Route> list) {
        routeFXObservableList.clear();
        for (Route route : list) {
            routeFXObservableList.add(ConverterToFX.convertToFx(route));
        }
        tableViewRoutes.setItems(routeFXObservableList);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {

        tableRoutesColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableRoutesColumnFrom.setCellValueFactory(new PropertyValueFactory<>("startPoint"));
        tableRoutesColumnTo.setCellValueFactory(new PropertyValueFactory<>("endPoint"));

        try {
            setItems(daoProvider.getRouteDAO().getList());
        } catch (RemoteException e) {
            AlertDialog.showErrorAlert(e);
            e.printStackTrace();
        }
    }
}

