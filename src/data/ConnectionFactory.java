package data;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;

public class ConnectionFactory {
    private static ConnectionFactory instance;
    private static int MAX_CONNECTIONS = 100;

    ArrayList<Connection> connections;
    ArrayList<Boolean> inUse;
    String url = "localhost:3306";
    String user = "bitsx";
    String password = "bitsx";
    String database = "bitsx";
    String configuration = "allowPublicKeyRetrieval=true&useSSL=false&autoReconnect=true";

    private ConnectionFactory(){
        connections = new ArrayList<>();
        inUse = new ArrayList<>();
        for(int i = 0; i < MAX_CONNECTIONS; ++i){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://"+url+"/"+database+"?"+configuration, user, password);
                connections.add(conn);
                inUse.add(false);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    static public ConnectionFactory getInstance(){
        if(instance == null) instance = new ConnectionFactory();
        return instance;
    }

    public Connection getConnection(){
        for(int i = 0; i < MAX_CONNECTIONS; ++i){
            if(!inUse.get(i)){
                inUse.set(i, true);
                try {
                    if(!connections.get(i).isValid(1)) {
                        Class.forName("com.mysql.jdbc.Driver");
                        System.out.println("Reconexion " + i + ": ");
                        Connection conn = DriverManager.getConnection("jdbc:mysql://"+url+"/"+database+"?"+configuration, user, password);
                        connections.set(i, conn);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                System.out.println("Se ocupa conexion: " + i);
                return connections.get(i);
            }
        }
        return null;
    }

    public void releaseConnection(Connection conn){
        int index = connections.indexOf(conn);
        System.out.println("Index: " + index);
        inUse.set(index, false);
    }
}
