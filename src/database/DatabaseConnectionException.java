package database;

/**
 * Eccezione specifica lanciata in caso di fallimento della connessione al database.
 * Viene utilizzata dalla classe DbAccess per segnalare un problema di connettività.
 * Estende Exception, rendendola una eccezione controllata (checked exception).
 */
public class DatabaseConnectionException extends Exception {

    /**
     * Costruttore che crea una DatabaseConnectionException con un messaggio di dettaglio.
     * @param message Il messaggio di dettaglio (String) per l'eccezione.
     */
    public DatabaseConnectionException(String message) {
        super(message);
    }

    /**
     * Costruttore di default. Crea una DatabaseConnectionException senza messaggio di dettaglio.
     */
    public DatabaseConnectionException() {}
}
