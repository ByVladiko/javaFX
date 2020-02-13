package dao;

import java.rmi.RemoteException;

public class DAOImpl implements DAO{

    @Override
    public String toUpperCase(String text) throws RemoteException {
        return text.toUpperCase();
    }

}
