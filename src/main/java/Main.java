import airship.dao.FactoryDAO;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Main {

    private static final String REMOTE_SERVICE_PATH = "rmi://localhost:5555/FactoryDAO";

    public static void main(String[] args) {
        try {
            FactoryDAO factoryDAO = (FactoryDAO) Naming.lookup(REMOTE_SERVICE_PATH);
            System.out.println(factoryDAO.getAirshipDAO().getList().get(0).toString());
            System.exit(0);
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}