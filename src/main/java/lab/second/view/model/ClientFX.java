package lab.second.view.model;

import airship.model.Client;
import airship.model.Ticket;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lab.second.view.ConverterToFX;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientFX {
    private StringProperty id;
    private StringProperty firstName;
    private StringProperty middleName;
    private StringProperty lastName;
    private ObservableList<TicketFX> tickets;

    public ClientFX(StringProperty id, StringProperty firstName, StringProperty middleName, StringProperty lastName, ObservableList<TicketFX> tickets) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.tickets = tickets;
    }

    public ClientFX(Client client) {
        id = new SimpleStringProperty(client.getId().toString());
        firstName = new SimpleStringProperty(client.getFirstName());
        middleName = new SimpleStringProperty(client.getMiddleName());
        lastName = new SimpleStringProperty(client.getLastName());
        tickets = FXCollections.observableArrayList();
        for (int i = 0; i < client.getTickets().size(); i++) {
            tickets.add(ConverterToFX.convertToFx(client.getTickets().get(i)));
        }
    }

    public UUID getId() {
        return UUID.fromString(id.getValue());
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(StringProperty id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getMiddleName() {
        return middleName.get();
    }

    public StringProperty middleNameProperty() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName.set(middleName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public ObservableList<TicketFX> getTicketsFX() {
        return tickets;
    }

    public List<Ticket> getTickets() {
        ArrayList<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < this.tickets.size(); i++) {
            tickets.add(ConverterToFX.convertFxToModel(this.tickets.get(i)));
        }
        return tickets;
    }

    public void setTickets(ObservableList<TicketFX> tickets) {
        this.tickets = tickets;
    }
}
