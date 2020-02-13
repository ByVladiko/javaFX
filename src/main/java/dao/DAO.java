package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DAO extends Remote {

    String toUpperCase(String text) throws RemoteException;

}
