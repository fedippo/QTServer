package database;

/**
 * Eccezione specifica lanciata da una classe di accesso al database quando una query SQL non restituisce alcun risultato.
 * Estende Exception, rendendola una eccezione controllata (checked exception).
 */
public class EmptySetException extends Exception {

    /**
     * Costruttore che crea una EmptySetException con un messaggio di dettaglio.
     * @param message Il messaggio di dettaglio (String) per l'eccezione.
     */
    public EmptySetException(String message) {
        super(message);
    }

    /**
     * Costruttore di default. Crea una EmptySetException senza messaggio di dettaglio.
     */
    public EmptySetException() {}
}
