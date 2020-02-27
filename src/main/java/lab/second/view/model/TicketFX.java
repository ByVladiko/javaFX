package lab.second.view.model;

import airship.model.Ticket;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lab.second.view.ConverterToFX;

import java.util.UUID;

public class TicketFX {
    private StringProperty id;
    private AirshipFX airship;
    private RouteFX route;

    private ConverterToFX converter = new ConverterToFX();

    public TicketFX(StringProperty id, AirshipFX airship, RouteFX route) {
        this.id = id;
        this.airship = airship;
        this.route = route;
    }

    public TicketFX(Ticket ticket) {
        this.id = new SimpleStringProperty(ticket.getId().toString());
        this.airship = converter.convertToFx(ticket).getAirship();
        this.route = converter.convertToFx(ticket).getRoute();
    }

    public UUID getId() {
        return UUID.fromString(id.toString());
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(StringProperty id) {
        this.id = id;
    }

    public AirshipFX getAirship() {
        return airship;
    }

    public void setAirship(AirshipFX airship) {
        this.airship = airship;
    }

    public RouteFX getRoute() {
        return route;
    }

    public void setRoute(RouteFX route) {
        this.route = route;
    }
}
