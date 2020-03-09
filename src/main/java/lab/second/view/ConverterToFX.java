package lab.second.view;

import airship.model.Airship;
import airship.model.Client;
import airship.model.Route;
import airship.model.Ticket;
import lab.second.view.model.AirshipFX;
import lab.second.view.model.ClientFX;
import lab.second.view.model.RouteFX;
import lab.second.view.model.TicketFX;

public class ConverterToFX {

    private ConverterToFX() {}

    public static RouteFX convertToFx(Route route) {

        return new RouteFX(route);
    }

    public static Route convertFxToModel(RouteFX routeFx) {

        return new Route(routeFx.getId(), routeFx.getStartPoint(), routeFx.getEndPoint());

    }

    public static AirshipFX convertToFx(Airship airship) {

        return new AirshipFX(airship);
    }

    public static Airship convertFxToModel(AirshipFX airshipFx) {

        return new Airship(airshipFx.getId(), airshipFx.getModel(), airshipFx.getNumberOfSeat());

    }

    public static ClientFX convertToFx(Client client) {

        return new ClientFX(client);
    }

    public static Client convertFxToModel(ClientFX clientFx) {

        return new Client(clientFx.getId(), clientFx.getFirstName(), clientFx.getMiddleName(), clientFx.getLastName(), clientFx.getTickets());

    }

    public static TicketFX convertToFx(Ticket ticket) {

        return new TicketFX(ticket);
    }

    public static Ticket convertFxToModel(TicketFX ticketFx) {

        return new Ticket(ticketFx.getId(), convertFxToModel(ticketFx.getAirship()), convertFxToModel(ticketFx.getRoute()));
    }
}
