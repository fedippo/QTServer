package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * La classe DbAccess gestisce la connessione con un database relazionale MySQL.
 * Mantiene le credenziali e i parametri di connessione e fornisce metodi per inizializzare, recuperare e chiudere la connessione.
 */
public class DbAccess {

    /**
     * Nome della classe del driver JDBC per MySQL.
     */
    private String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    /**
     * Tipo di sistema di gestione del database.
     */
    private final String DBMS = "jdbc:mysql";
    /**
     * Indirizzo del server del database.
     */
    private final String SERVER = "localhost";
    /**
     * Nome del database.
     */
    private final String DATABASE = "MapDB";
    /**
     * Porta di comunicazione con il database.
     */
    private final String PORT = "3306";
    /**
     * ID utente per l'accesso al database.
     */
    private final String USER_ID = "MapUser";
    /**
     * Password dell'utente per l'accesso al database.
     */
    private final String PASSWORD = "map";
    /**
     * Oggetto Connection che rappresenta la connessione attiva con il database.
     */
    private Connection conn;

    /**
     * Inizializza la connessione al database.
     * Il metodo esegue i seguenti passi:
     * 1. Carica la classe del driver JDBC.
     * 2. Costruisce la stringa di connessione (URL).
     * 3. Stabilisce la connessione tramite DriverManager.getConnection().
     *
     * @throws DatabaseConnectionException Se si verifica un errore durante il caricamento del driver,
     * l'accesso al driver o durante l'effettiva connessione SQL.
     */
    public void initConnection() throws DatabaseConnectionException {
        try {
            Class.forName(DRIVER_CLASS_NAME).newInstance();
        } catch(ClassNotFoundException e) {
            System.out.println("[!] Driver not found: " + e.getMessage());
            throw new DatabaseConnectionException();
        } catch(InstantiationException e){
            System.out.println("[!] Error during the instantiation : " + e.getMessage());
            throw new DatabaseConnectionException();
        } catch(IllegalAccessException e){
            System.out.println("[!] Cannot access the driver : " + e.getMessage());
            throw new DatabaseConnectionException();
        }

        String connectionString = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE + "?user=" + USER_ID + "&password=" + PASSWORD + "&serverTimezone=UTC";

        System.out.println("Connection's String: " + connectionString);

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch(SQLException e) {
            System.out.println("[!] SQLException: " + e.getMessage());
            System.out.println("[!] SQLState: " + e.getSQLState());
            System.out.println("[!] VendorError: " + e.getErrorCode());
            throw new DatabaseConnectionException();
        }
    }

    /**
     * Restituisce l'oggetto Connection stabilito, permettendo ad altre classi di eseguire query SQL.
     * @return L'oggetto Connection corrente.
     */
    public Connection getConnection(){
        return conn;
    }

    /**
     * Chiude la connessione attiva al database.
     * @throws SQLException Se si verifica un errore SQL durante la chiusura della connessione.
     */
    public void closeConnection() throws SQLException {
        conn.close();
    }

}
